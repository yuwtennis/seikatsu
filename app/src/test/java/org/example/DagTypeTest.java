package org.example;

import org.example.dags.helloworld.HelloWorldDag;
import org.junit.Before;
import org.junit.Test;

import static org.example.DagDispatcher.dispatch;
import static org.junit.Assert.assertTrue;

public class DagTypeTest {
    private final String DAGTYPE_HELLOWORLD = "HELLOWORLD";

    @Before
    public void setUp() {}

    @Test
    public void testDispatchHelloWorld() {
        assertTrue(dispatch(DagType.valueOf(DAGTYPE_HELLOWORLD)) instanceof HelloWorldDag);
    }

}
