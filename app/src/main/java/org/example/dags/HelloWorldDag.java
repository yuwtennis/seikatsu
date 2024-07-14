package org.example.dags;


import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.Create;

public class HelloWorldDag implements Dag {

    public void process(Pipeline p) {
        p.apply(Create.of("Hello", "World", "!"))
                .apply(new HelloWorldVertices.SimpleVertex());
        p.run().waitUntilFinish();
    }
}
