package org.example.dags;

import org.apache.beam.sdk.testing.TestPipeline;
import org.junit.Rule;
import org.junit.Test;

public class HelloWorldDagTest {
    @Rule
    public final transient TestPipeline p = TestPipeline.create();

    @Test
    public void testHelloWorldDag() {
        HelloWorldDag h = new HelloWorldDag();
        h.process(p);
        p.run();
    }
}
