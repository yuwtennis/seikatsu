package org.example.dags;


import org.apache.beam.sdk.Pipeline;

public interface Dag {
    void process(Pipeline pipeline);
}
