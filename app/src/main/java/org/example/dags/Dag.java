package org.example.dags;

import org.apache.beam.sdk.Pipeline;

/**
 * DAG.
 */
public interface Dag {
  /**
   * Process.
   *
   * @param p Pipeline
   */
  void process(Pipeline p);
}
