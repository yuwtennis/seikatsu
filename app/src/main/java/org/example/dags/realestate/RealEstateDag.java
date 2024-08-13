package org.example.dags.realestate;

import com.google.api.services.bigquery.model.TableRow;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.example.App;
import org.example.Utils;
import org.example.dags.Dag;
import org.example.dags.realestate.endpoints.EndpointKind;
import org.example.dags.realestate.endpoints.RealEstateLandValueCsvDlEndpoint;
import org.example.dags.realestate.endpoints.RealEstateTxnCsvDlEndpoint;
import org.example.dags.realestate.http_response_schema.AppRaisalsResponseSchema;
import org.example.dags.realestate.http_response_schema.TxnResponseSchema;
import org.example.dags.realestate.landvalue.LandValue;
import org.example.dags.realestate.txn.ResidentialLandTxn;
import org.example.dags.realestate.txn.UsedApartmentTxn;
import org.example.dags.realestate.vertices.ZipContentHandler;
import org.example.dags.realestate.vertices.ContentDownloader;
import org.example.dags.webapi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Year;
import java.util.*;

import static org.example.dags.realestate.BqMetaData.*;
import static org.example.dags.realestate.vertices.ZipContentHandler.*;

public class RealEstateDag implements Dag {
    static Logger LOG = LoggerFactory.getLogger(RealEstateDag.class);
    /***
     *
     * @param p
     */
    public void process(Pipeline p) {
        int backtrackedYears = p.getOptions().as(App.DagOptions.class).getBacktrackedYears();
        final List<String> urlsForTxn = new ArrayList<>();
        final List<String> urlsForLV = new ArrayList<>();

        for (int i = 1; i <= backtrackedYears; i++) {
            int backtracked = Year.now().minusYears(i).getValue();

            urlsForTxn.add(new RealEstateTxnCsvDlEndpoint.Builder(
                    EndpointKind.RESIDENTIAL_LAND,
                    backtracked,
                    backtracked).build().toUrl());

            urlsForTxn.add(new RealEstateTxnCsvDlEndpoint.Builder(
                    EndpointKind.USED_APARTMENT,
                    backtracked,
                    backtracked).build().toUrl());

            urlsForLV.add(
                    new RealEstateLandValueCsvDlEndpoint.Builder(backtracked)
                            .build().toUrl());
        }

        PCollection<String> txnUrls = p.apply(Create.of(urlsForTxn));
        PCollection<String> lvUrls = p.apply(Create.of(urlsForLV));

        txnDag(asWebApiHttpRequest(txnUrls));
        lvDag(asWebApiHttpRequest(lvUrls));

        p.run().waitUntilFinish();
    }

    /**
     *
     */
    static class ParseBodyFn extends DoFn<WebApiHttpResponse, byte[]> {
        @ProcessElement
        public void processElement(ProcessContext c) {
            c.output(c.element().getData());
        }
    }

    /**
     *
     */
    static class ParseUrlFn extends DoFn<byte[], String> {
        @ProcessElement
        public void processElement(ProcessContext c) {
            String s = new String(c.element());
            TxnResponseSchema json = (TxnResponseSchema)Utils.asJson(s, TxnResponseSchema.class);
            LOG.info(json.toString());
            c.output(json.url);
        }
    }

    /**
     *
     */
    static class DecodeBase64Fn extends DoFn<byte[], byte[]> {
        @ProcessElement
        public void processElement(ProcessContext c) {
            String s = new String(c.element());
            AppRaisalsResponseSchema json = (AppRaisalsResponseSchema)Utils.asJson(s, AppRaisalsResponseSchema.class);
            c.output(Base64.getDecoder().decode(json.body));
        }
    }

    /**
     *
     * @param requests
     */
    private void txnDag(PCollection<WebApiHttpRequest> requests) {
        // NOTE Unable to apply MapElements after Custom Transform
        PCollection<String> dlUrls = requests
                .apply("DownloadUrls", new ContentDownloader.DownloadUrl())
                .apply(ParDo.of(new ParseBodyFn()))
                .apply(ParDo.of(new ParseUrlFn()));

        // 2. Next get the zip contents
        PCollection<WebApiHttpRequest> parsedUrls = asWebApiHttpRequest(dlUrls);

        PCollectionTuple entities = parsedUrls
                .apply(new ContentDownloader.DownloadUrl())
                .apply(ParDo.of(new ParseBodyFn()))
                .apply(new ZipContentHandler.Extract());

        // 3. Insert into Bigquery using Bigquery Storage API
        PCollection<ResidentialLandTxn> residentialLandXacts = entities.get(residentialLand);
        PCollection<UsedApartmentTxn> usedApartmentXacts = entities.get(usedApartment);

        PCollection<TableRow> rlTableRows =  residentialLandXacts.apply(MapElements
                .into(TypeDescriptor.of(TableRow.class))
                .via((ResidentialLandTxn e) -> {
                    assert e != null;
                    return e.toTableRow();
                }));

        rlTableRows.apply("To"+FQTN_RESIDENTIAL_LAND,
                BigQueryIO
                        .writeTableRows()
                        .to(FQTN_RESIDENTIAL_LAND)
                        .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
                        .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE)
                        .withMethod(BigQueryIO.Write.Method.DEFAULT));

        PCollection<TableRow> uATableRows = usedApartmentXacts.apply(MapElements
                .into(TypeDescriptor.of(TableRow.class))
                .via((UsedApartmentTxn e) -> {
                    assert e != null;
                    return e.toTableRow();
                }));

        uATableRows.apply("To"+FQTN_USED_APARTMENT,
                BigQueryIO
                        .writeTableRows()
                        .to(FQTN_USED_APARTMENT)
                        .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
                        .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE)
                        .withMethod(BigQueryIO.Write.Method.DEFAULT));

    }

    /**
     *
     * @return
     */
    private void lvDag(PCollection<WebApiHttpRequest> requests) {
        PCollectionTuple entities = requests
                .apply(new ContentDownloader.DownloadUrl())
                .apply(ParDo.of(new ParseBodyFn()))
                .apply(ParDo.of(new DecodeBase64Fn()))
                .apply(new ZipContentHandler.Extract());

        PCollection<LandValue> landValues = entities.get(landValue);
        PCollection<TableRow> lVTableRows = landValues.apply(MapElements
                .into(TypeDescriptor.of(TableRow.class))
                .via((LandValue e) -> {
                    assert e != null;
                    return e.toTableRow();
                }));

        lVTableRows.apply("To"+FQTN_LAND_VALUE,
                BigQueryIO
                        .writeTableRows()
                        .to(FQTN_LAND_VALUE)
                        .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_NEVER)
                        .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE)
                        .withMethod(BigQueryIO.Write.Method.DEFAULT));
    }

    /**
     *
     * @param urls
     * @return
     */
    private PCollection<WebApiHttpRequest> asWebApiHttpRequest(PCollection<String> urls) {
        Map<String, String> headers = new HashMap<>();
        headers.put(
                OcpApimSubscriptionKeyHeader.NAME,
                OcpApimSubscriptionKeyHeader.VALUE);

        return  urls
                    .apply("AsWebApiHttpRequest", MapElements
                            .into(TypeDescriptor.of(WebApiHttpRequest.class))
                            .via((String url) -> WebApiHttpRequest.of(url, headers)))
                    .setCoder(WebApiHttpRequestCoder.of());
    }
}