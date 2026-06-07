package org.example.helpers;

import org.apache.beam.sdk.transforms.DoFn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * PrintFn.
 */
public class PrintFn extends DoFn<Object, Object> {
  /**
   * Logger.
   */
  private static final Logger LOG = LoggerFactory.getLogger(PrintFn.class);

  /**
   * Process the element.
   *
   * @param c context
   */
  @ProcessElement
  public void processElement(ProcessContext c) {
    LOG.info(c.element().toString());

    c.output(c.element());
  }
}
