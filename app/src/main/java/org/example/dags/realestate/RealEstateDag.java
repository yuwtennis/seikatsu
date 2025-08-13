package org.example.dags.realestate;

import static org.example.dags.realestate.BqMetaData.FQTN_RESIDENTIAL_LAND;
import static org.example.dags.realestate.BqMetaData.FQTN_GEO_LAND_VALUE;
import static org.example.dags.realestate.BqMetaData.FQTN_LAND_VALUE;
import static org.example.dags.realestate.BqMetaData.FQTN_USED_APARTMENT;
import static org.example.dags.realestate.vertices.ZipContentHandler.LANDVALUE_TUPLE_TAG;
import static org.example.dags.realestate.vertices.ZipContentHandler.RESIDENTIAL_LAND_TXN_TUPLE_TAG;
import static org.example.dags.realestate.vertices.ZipContentHandler.USED_APARTENT_TXN_TUPLE_TAG;

import com.google.api.services.bigquery.model.TableRow;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.example.App;
import org.example.dags.Dag;
import org.example.dags.realestate.endpoints.EndpointKind;
import org.example.dags.realestate.endpoints.RealEstateGeoJsonLandValueDlEndpoint;
import org.example.dags.realestate.endpoints.RealEstateLandValueCsvDlEndpoint;
import org.example.dags.realestate.endpoints.RealEstateTxnCsvDlEndpoint;
import org.example.dags.realestate.landvalue.GeoLandValue;
import org.example.dags.realestate.landvalue.LandValue;
import org.example.dags.realestate.txn.ResidentialLandTxn;
import org.example.dags.realestate.txn.UsedApartmentTxn;
import org.example.dags.realestate.vertices.Base64DecoderDoFn;
import org.example.dags.realestate.vertices.ContentDownloader;
import org.example.dags.realestate.vertices.GeoLandValueFn;
import org.example.dags.realestate.vertices.ParseBodyDoFn;
import org.example.dags.realestate.vertices.ParseUrlDoFn;
import org.example.dags.realestate.vertices.ZipContentHandler;
import org.example.dags.webapi.WebApiHttpRequest;
import org.example.dags.webapi.WebApiHttpRequestCoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealEstateDag implements Dag {
    /**
     *
     */
    static final Logger LOG = LoggerFactory.getLogger(RealEstateDag.class);
    /***
     *
     * @param p
     */
    public void process(final Pipeline p) {
        int backtrackedYears = p.getOptions()
                .as(App.DagOptions.class).getBacktrackedYears();
        final List<String> urlsForTxn = new ArrayList<>();
        final List<String> urlsForLV = new ArrayList<>();
        final List<String> urlsForGeoJsonLV = new ArrayList<>();
        final int xStart = 7264;
        final int xEnd = 7278;
        final int yStart = 3223;
        final int yEnd = 3229;

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
            // Geo Json LandValue
            for (int x = xStart; x <= xEnd; x++) {
                for (int y = yStart; y <= yEnd; y++) {
                    urlsForGeoJsonLV.add(
                            new RealEstateGeoJsonLandValueDlEndpoint.Builder(
                            x,
                            y,
                            backtracked
                    ).build().toUrl());
                }
            }
        }

        LOG.info(urlsForGeoJsonLV.toString());

        PCollection<String> txnUrls = p.apply(Create.of(urlsForTxn));
        PCollection<String> lvUrls = p.apply(Create.of(urlsForLV));
        PCollection<String> geoJsonLvUrls = p.apply(
                Create.of(urlsForGeoJsonLV));

        txnDag(asWebApiHttpRequest(txnUrls));
        lvDag(asWebApiHttpRequest(lvUrls));
        geoLvDag(asWebApiHttpRequest(geoJsonLvUrls));

        p.run().waitUntilFinish();
    }


    /**
     *
     * @param requests
     */
    private void txnDag(final PCollection<WebApiHttpRequest> requests) {
        // NOTE Unable to apply MapElements after Custom Transform
        PCollection<String> dlUrls = requests
                .apply("DownloadUrls", new ContentDownloader.DownloadUrl())
                .apply(ParDo.of(new ParseBodyDoFn.ParseBodyFn()))
                .apply(ParDo.of(new ParseUrlDoFn.ParseUrlFn()));

        // 2. Next get the zip contents
        PCollection<WebApiHttpRequest> parsedUrls = asWebApiHttpRequest(dlUrls);

        PCollectionTuple entities = parsedUrls
                .apply(new ContentDownloader.DownloadUrl())
                .apply(ParDo.of(new ParseBodyDoFn.ParseBodyFn()))
                .apply(new ZipContentHandler.Extract());

        // 3. Insert into Bigquery using Bigquery Storage API
        PCollection<ResidentialLandTxn> residentialLandXacts =
                entities.get(RESIDENTIAL_LAND_TXN_TUPLE_TAG);
        PCollection<UsedApartmentTxn> usedApartmentXacts =
                entities.get(USED_APARTENT_TXN_TUPLE_TAG);

        PCollection<TableRow> rlTableRows =  residentialLandXacts.apply(
                MapElements
                    .into(TypeDescriptor.of(TableRow.class))
                    .via((ResidentialLandTxn e) -> {
                        assert e != null;
                        return e.toTableRow();
                    }));

        rlTableRows.apply("To" + FQTN_RESIDENTIAL_LAND,
                BigQueryIO
                        .writeTableRows()
                        .to(FQTN_RESIDENTIAL_LAND)
                        .withCreateDisposition(
                                BigQueryIO
                                        .Write.CreateDisposition.CREATE_NEVER)
                        .withWriteDisposition(
                                BigQueryIO
                                        .Write.WriteDisposition.WRITE_TRUNCATE)
                        .withMethod(BigQueryIO
                                .Write.Method.DEFAULT));

        PCollection<TableRow> uATableRows = usedApartmentXacts.apply(MapElements
                .into(TypeDescriptor.of(TableRow.class))
                .via((UsedApartmentTxn e) -> {
                    assert e != null;
                    return e.toTableRow();
                }));

        uATableRows.apply("To" + FQTN_USED_APARTMENT,
                BigQueryIO
                        .writeTableRows()
                        .to(FQTN_USED_APARTMENT)
                        .withCreateDisposition(
                                BigQueryIO
                                        .Write.CreateDisposition.CREATE_NEVER)
                        .withWriteDisposition(
                                BigQueryIO
                                        .Write.WriteDisposition.WRITE_TRUNCATE)
                        .withMethod(
                                BigQueryIO.Write.Method.DEFAULT));

    }

    /**
     *
     * @param requests
     */
    private void lvDag(final PCollection<WebApiHttpRequest> requests) {
        PCollectionTuple entities = requests
                .apply(new ContentDownloader.DownloadUrl())
                .apply(ParDo.of(new ParseBodyDoFn.ParseBodyFn()))
                .apply(ParDo.of(new Base64DecoderDoFn.Base64DecoderFn()))
                .apply(new ZipContentHandler.Extract());

        PCollection<LandValue> landValues = entities.get(LANDVALUE_TUPLE_TAG);
        PCollection<TableRow> lVTableRows = landValues.apply(MapElements
                .into(TypeDescriptor.of(TableRow.class))
                .via((LandValue e) -> {
                    assert e != null;
                    return e.toTableRow();
                }));

        lVTableRows.apply("To" + FQTN_LAND_VALUE,
                BigQueryIO
                        .writeTableRows()
                        .to(FQTN_LAND_VALUE)
                        .withCreateDisposition(
                                BigQueryIO
                                        .Write.CreateDisposition.CREATE_NEVER)
                        .withWriteDisposition(
                                BigQueryIO
                                        .Write.WriteDisposition.WRITE_TRUNCATE)
                        .withMethod(BigQueryIO.Write.Method.DEFAULT));
    }

    /**
     *
     * @param requests
     */
    private void geoLvDag(final PCollection<WebApiHttpRequest> requests) {
        PCollection<TableRow> geoLvRows = requests
                .apply(new ContentDownloader.DownloadUrl())
                .apply(ParDo.of(new GeoLandValueFn.FromWebApiHttpResponseFn()))
                .apply(
                        MapElements
                                .into(TypeDescriptor.of(TableRow.class))
                                .via((GeoLandValue lv) -> lv.toTableRow())
                );
        geoLvRows.apply(
                BigQueryIO
                        .writeTableRows()
                        .to(FQTN_GEO_LAND_VALUE)
                        .withCreateDisposition(
                                BigQueryIO
                                        .Write.CreateDisposition.CREATE_NEVER)
                        .withWriteDisposition(
                                BigQueryIO
                                        .Write.WriteDisposition.WRITE_TRUNCATE)
                        .withMethod(BigQueryIO.Write.Method.DEFAULT));
    }

    /**
     *
     * @param urls
     * @return PCollection including WebApiHttpRequest
     */
    private PCollection<WebApiHttpRequest> asWebApiHttpRequest(
            final PCollection<String> urls) {
        Map<String, String> headers = new HashMap<>();
        headers.put(
                OcpApimSubscriptionKeyHeader.NAME,
                OcpApimSubscriptionKeyHeader.VALUE);

        return  urls
                    .apply("AsWebApiHttpRequest", MapElements
                            .into(TypeDescriptor.of(WebApiHttpRequest.class))
                            .via((String url) -> WebApiHttpRequest.of(
                                    url, headers)))
                    .setCoder(WebApiHttpRequestCoder.of());
    }
}
