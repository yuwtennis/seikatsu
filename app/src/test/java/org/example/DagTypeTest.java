package org.example;

import static org.example.DagDispatcher.dispatch;
import static org.junit.Assert.assertTrue;

import org.example.dags.helloworld.HelloWorldDag;
import org.example.dags.realestate.RealEstateDag;
import org.junit.Test;

/** DagTypeTest. */
public class DagTypeTest {
  /** DAGTYPE_HELLOWORLD. */
  private static final String DAGTYPE_HELLOWORLD = "HELLOWORLD";

  /** DAGTYPE_REALESTATE. */
  private static final String DAGTYPE_REALESTATE = "REALESTATE";

  /** Test dispatch. */
  @Test
  public void testDispatchHelloWorld() {
    assertTrue(dispatch(DagType.valueOf(DAGTYPE_HELLOWORLD)) instanceof HelloWorldDag);
  }

  /** Test dispatch. */
  @Test
  public void testDispatchRealEstate() {
    assertTrue(dispatch(DagType.valueOf(DAGTYPE_REALESTATE)) instanceof RealEstateDag);
  }
}
