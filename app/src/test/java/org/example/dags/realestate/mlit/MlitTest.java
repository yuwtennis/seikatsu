package org.example.dags.realestate.mlit;

import static org.junit.Assert.fail;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;
import org.junit.Before;
import org.junit.Test;

/** MlitTest. */
public class MlitTest {
  /** Fixture for testing deserialization. */
  private String txnJson;

  private String standardLandPriceJson;

  /** Setup. */
  @Before
  public void setUp() {
    txnJson = "txn.json";
    standardLandPriceJson = "standard_land_price.json";
  }

  /** Test deserialization of txn.json. */
  @Test
  public void testDeserializeTxnJson() {
    ObjectMapper mapper = new ObjectMapper();
    try (InputStream is = getClass().getClassLoader().getResourceAsStream(txnJson)) {
      Response m = mapper.readValue(is.readAllBytes(), Response.class);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }

  /** Test deserialization of standard_land_price.json. */
  @Test
  public void testDeserializeStandardPricingJson() {
    ObjectMapper mapper = new ObjectMapper();
    try (InputStream is = getClass().getClassLoader().getResourceAsStream(standardLandPriceJson)) {
      Response m = mapper.readValue(is.readAllBytes(), Response.class);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
    }
  }
}
