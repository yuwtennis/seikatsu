package org.example.dags;


import org.apache.beam.sdk.Pipeline;

public interface Dag {
    /**
     *
     * @param p
     */
    void process(Pipeline p);
}
