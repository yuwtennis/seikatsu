package org.example.dags;

import org.apache.beam.sdk.Pipeline;

public class HelloWorldDag implements Dag {

    @Override
    public void construct(Pipeline p) {
        System.out.println("Do something!");
    }
}
