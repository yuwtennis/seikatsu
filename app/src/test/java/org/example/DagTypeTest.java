package org.example;

import static org.example.DagDispatcher.dispatch;
import static org.junit.Assert.assertTrue;

import org.example.dags.helloworld.HelloWorldDag;
import org.example.dags.realestate.RealEstateDag;
import org.junit.Test;

public class DagTypeTest {
    /**
     *
     */
    private static final String DAGTYPE_HELLOWORLD = "HELLOWORLD";

    /**
     *
     */
    private static final String DAGTYPE_REALESTATE = "REALESTATE";

    /**
     *
     */
    @Test
    public void testDispatchHelloWorld() {
        assertTrue(dispatch(
                DagType.valueOf(DAGTYPE_HELLOWORLD)) instanceof HelloWorldDag);
    }

    /**
     *
     */
    @Test
    public void testDispatchRealEstate() {
        assertTrue(dispatch(
                DagType.valueOf(DAGTYPE_REALESTATE)) instanceof RealEstateDag);
    }

}
