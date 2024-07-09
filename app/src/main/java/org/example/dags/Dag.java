package org.example.dags;

import org.apache.beam.sdk.Pipeline;

public interface Dag {
    public void construct(Pipeline p);
}
