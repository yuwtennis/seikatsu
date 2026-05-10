package org.example.dags.realestate;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.beam.io.requestresponse.RequestResponseIO;
import org.apache.beam.io.requestresponse.Result;
import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.io.gcp.bigquery.BigQueryIO;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.beam.sdk.values.TupleTagList;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.example.App;
import org.example.dags.Dag;
import org.example.dags.realestate.dofn.ValidationFn;
import org.example.dags.realestate.endpoints.RealEstatePriceEndpoint;
import org.example.dags.realestate.endpoints.StandardLandPriceEndpoint;
import org.example.dags.realestate.endpoints.Url;
import org.example.dags.realestate.mlit.RealEstatesPrice;
import org.example.dags.realestate.mlit.StandardLandPrice;
import org.example.dags.webapi.WebApiHttpClient;
import org.example.dags.webapi.WebApiHttpRequest;
import org.example.dags.webapi.WebApiHttpResponse;
import org.example.dags.webapi.WebApiHttpResponseCoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RealEstateDag implements Dag {
  private static final String AREA_TOKYO = "13";
  private static final String DIVISION_RESIDENTIAL_AREA = "00";
  private static final String BQ_PROJECT = "elite-caster-125113";
  private static final String BQ_DATASET = "descr_analyt";
  private static final String BQ_RSP_TABLE = "real_estates_price";
  private static final String BQ_SLP_TABLE = "standard_land_price";
  public static final TupleTag<RealEstatesPrice> TXN_TAG = new TupleTag<>() {};
  public static final TupleTag<StandardLandPrice> SLP_TAG = new TupleTag<>() {};

  /** */
  static final Logger LOG = LoggerFactory.getLogger(RealEstateDag.class);

  /***
   *
   * @param p
   */
  public void process(final Pipeline p) {
    App.DagOptions options = p.getOptions().as(App.DagOptions.class);
    int backtracked = options.getBacktrackedYears().get();
    int currentYear = Year.now().getValue();
    ArrayList<Url> urls = new ArrayList<>();
    Map<String, String> headers = new HashMap<>();
    headers.put("Ocp-Apim-Subscription-Key", Env.OCP_APIM_SUBSCRIPTION_KEY);

    // Create Key Value pairs of request {Type: Url}
    for (int i = currentYear; i > currentYear - backtracked; i--) {
      urls.add(new RealEstatePriceEndpoint.Builder(i, AREA_TOKYO).build().toUrl());

      urls.add(
          new StandardLandPriceEndpoint.Builder(i, AREA_TOKYO, DIVISION_RESIDENTIAL_AREA)
              .build()
              .toUrl());
    }

    PCollection<WebApiHttpRequest> requests =
        p.apply(Create.of(urls))
            .apply(
                MapElements.into(TypeDescriptor.of(WebApiHttpRequest.class))
                    .via((Url u) -> (WebApiHttpRequest.of(u, headers))));

    Result<WebApiHttpResponse> result =
        requests.apply(
            "Scrape", RequestResponseIO.of(WebApiHttpClient.of(), WebApiHttpResponseCoder.of()));

    PCollectionTuple responses =
        result
            .getResponses()
            .setCoder(WebApiHttpResponseCoder.of())
            .apply(
                "Validation",
                ParDo.of(new ValidationFn()).withOutputTags(TXN_TAG, TupleTagList.of(SLP_TAG)));

    // Branch 1 RealEstatesPrice
    responses
        .get(TXN_TAG)
        .apply(
            "LoadTo" + RealEstatesPrice.class.getSimpleName(),
            BigQueryIO.<RealEstatesPrice>write()
                .useBeamSchema()
                .to(BQ_PROJECT + ":" + BQ_DATASET + "." + BQ_RSP_TABLE)
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE));
    // Branch 2 LandValue
    responses
        .get(SLP_TAG)
        .apply(
            "LoadTo" + StandardLandPrice.class.getSimpleName(),
            BigQueryIO.<StandardLandPrice>write()
                .useBeamSchema()
                .to(BQ_PROJECT + ":" + BQ_DATASET + "." + BQ_SLP_TABLE)
                .withCreateDisposition(BigQueryIO.Write.CreateDisposition.CREATE_IF_NEEDED)
                .withWriteDisposition(BigQueryIO.Write.WriteDisposition.WRITE_TRUNCATE));

    p.run().waitUntilFinish();
  }
}
