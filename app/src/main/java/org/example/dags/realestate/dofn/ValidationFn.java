package org.example.dags.realestate.dofn;

import static org.example.dags.realestate.RealEstateDag.SLP_TAG;
import static org.example.dags.realestate.RealEstateDag.TXN_TAG;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.beam.sdk.transforms.DoFn;
import org.example.dags.realestate.endpoints.RealEstatePriceEndpoint;
import org.example.dags.realestate.endpoints.StandardLandPriceEndpoint;
import org.example.dags.realestate.mlit.RealEstatesPrice;
import org.example.dags.realestate.mlit.Response;
import org.example.dags.realestate.mlit.StandardLandPrice;
import org.example.dags.webapi.WebApiHttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** ValidationFn. */
public class ValidationFn extends DoFn<WebApiHttpResponse, RealEstatesPrice> {

  private static final Logger LOG = LoggerFactory.getLogger(ValidationFn.class);

  /**
   * Validate the response.
   *
   * @param c context
   * @param output output
   */
  @ProcessElement
  public void processElement(ProcessContext c, MultiOutputReceiver output) {

    ObjectMapper objectMapper = new ObjectMapper();

    try {
      Response resp = objectMapper.readValue(c.element().getData(), Response.class);

      for (Object m : resp.getData()) {
        switch (c.element().getCategory()) {
          case RealEstatePriceEndpoint.CATEGORY:
            output.get(TXN_TAG).output((RealEstatesPrice) m);
            break;
          case StandardLandPriceEndpoint.CATEGORY:
            output.get(SLP_TAG).output((StandardLandPrice) m);
            break;
          default:
            throw new RuntimeException("Invalid Category. Category: " + c.element().getCategory());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    // TODO: implement dlq pattern
  }
}
