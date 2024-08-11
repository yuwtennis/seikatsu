package org.example.dags.realestate;

import com.google.api.services.bigquery.model.TableRow;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
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
import static org.example.dags.realestate.RealEstateEnv.FQTN_RESIDENTIAL_LAND;
import static org.example.dags.realestate.RealEstateEnv.FQTN_USED_APARTMENT;

public class RealEstateDag implements Dag {
    static Logger LOG = LoggerFactory.getLogger(RealEstateDag.class);

    public static final String URL_PREFIX = "https://www.reinfolib.mlit.go.jp/in-api/api-aur/aur/csv/transactionPrices?language=ja&areaCondition=address&prefecture=13&transactionPrice=true&closedPrice=true";
    public static final String KIND_RESIDENTIAL_LAND = "residential";
    public static final String KIND_USED_APARTMENT = "used";

    /***
     *
     * @param p
     */
    public void process(Pipeline p) {

        final List<String> urls = createUrls(
                p.getOptions().as(App.DagOptions.class).getBacktrackedYears());

        LOG.info("Start running {}", RealEstateDag.class.getSimpleName());

        // 1. Start by getting the actual url provided by the server
        PCollection<String> dlUrls = p.apply(Create.of(urls))
                .apply(new GetDlUrlVertices.DownloadUrl(
                        p.getOptions().as(App.DagOptions.class).getSubscriptionKey()));

        // 2. Next get the zip contents
        PCollection<ResidentialLandTxn> residentialLandXacts = dlUrls
                .apply(new ExtractZipContentsVertices.Extract()).get(residentialLand);
        PCollection<UsedApartmentTxn> usedApartmentXacts = dlUrls
                .apply(new ExtractZipContentsVertices.Extract()).get(usedApartment);

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
            urls.add(String.format(
                    "%s&kind=%s&seasonFrom=%d1&seasonTo=%d4",
                    URL_PREFIX, KIND_RESIDENTIAL_LAND, backtracked, backtracked));
            urls.add(String.format(
                    "%s&kind=%s&seasonFrom=%d1&seasonTo=%d4",
                    URL_PREFIX, KIND_USED_APARTMENT, backtracked, backtracked));
        }

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