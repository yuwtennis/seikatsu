package org.example.dags.realestate;

import com.google.api.services.bigquery.model.TableRow;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.example.App;
import org.example.dags.Dag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import static org.example.dags.realestate.ExtractZipContentsVertices.residentialLand;
import static org.example.dags.realestate.ExtractZipContentsVertices.usedApartment;
import static org.example.dags.realestate.BqMetaData.FQTN_RESIDENTIAL_LAND;
import static org.example.dags.realestate.BqMetaData.FQTN_USED_APARTMENT;

public class RealEstateDag implements Dag {
    static Logger LOG = LoggerFactory.getLogger(RealEstateDag.class);
    /***
     *
     * @param p
     */
    public void process(Pipeline p) {

        final List<String> urls = createUrls(
                p.getOptions().as(App.DagOptions.class).getBacktrackedYears());
        // 1. Start by getting the actual url provided by the server
        PCollection<String> dlUrls = p.apply(Create.of(urls))
                .apply(new GetDlUrlVertices.DownloadUrl(
                        OcpApimSubscriptionKeyHeader.VALUE));

        // 2. Next get the zip contents
        PCollectionTuple pCols = dlUrls
                .apply(new ExtractZipContentsVertices.Extract());
        PCollection<ResidentialLandTxn> residentialLandXacts = pCols.get(residentialLand);
        PCollection<UsedApartmentTxn> usedApartmentXacts = pCols.get(usedApartment);

        // 3. Insert into Bigquery using Bigquery Storage API
        PCollection<TableRow> rlTableRows =  residentialLandXacts.apply(MapElements
                        .into(TypeDescriptor.of(TableRow.class))
                        .via((ResidentialLandTxn e) -> {
                            assert e != null;
                            return e.toTableRow();
                        }));

        toBq(
                rlTableRows,
                FQTN_RESIDENTIAL_LAND,
                BigQueryIO.Write.CreateDisposition.CREATE_NEVER,
                BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE,
                BigQueryIO.Write.Method.DEFAULT);

        PCollection<TableRow> rATableRows = usedApartmentXacts.apply(MapElements
                        .into(TypeDescriptor.of(TableRow.class))
                        .via((UsedApartmentTxn e) -> {
                            assert e != null;
                            return e.toTableRow();
                        }));
        toBq(
                rATableRows,
                FQTN_USED_APARTMENT,
                BigQueryIO.Write.CreateDisposition.CREATE_NEVER,
                BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE,
                BigQueryIO.Write.Method.DEFAULT);

        p.run().waitUntilFinish();
    }

    /***
     *
     * @param backtrackedYears
     * @return
     */
    private List<String> createUrls(int backtrackedYears) {
        List<String> urls = new ArrayList<>();

        for (int i = 1; i <= backtrackedYears; i++) {
            int backtracked = Year.now().minusYears(i).getValue();
            urls.add(
                    new RealEstateTxnCsvDlEndpoint.Builder(
                            EndpointKind.RESIDENTIAL_LAND,
                            backtracked,
                            backtracked).build().toUrl());
            urls.add(
                    new RealEstateTxnCsvDlEndpoint.Builder(
                            EndpointKind.USED_APARTMENT,
                            backtracked,
                            backtracked).build().toUrl());
        }

        LOG.info(urls.toString());

        return urls;
    }

    /***
     *
     * @param rows
     * @param fullyQualifiedTableName
     * @param createDisposition
     * @param writeDisposition
     * @param writeMethod
     */
    private void toBq(
            PCollection<TableRow> rows,
            String fullyQualifiedTableName,
            BigQueryIO.Write.CreateDisposition createDisposition,
            BigQueryIO.Write.WriteDisposition writeDisposition,
            BigQueryIO.Write.Method writeMethod) {
        rows
                .apply("To"+fullyQualifiedTableName,
                        BigQueryIO
                            .writeTableRows()
                            .to(fullyQualifiedTableName)
                            .withCreateDisposition(createDisposition)
                            .withWriteDisposition(writeDisposition)
                            .withMethod(writeMethod));
    }
}