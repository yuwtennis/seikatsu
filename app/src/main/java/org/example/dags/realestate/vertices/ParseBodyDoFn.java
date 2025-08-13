package org.example.dags.realestate.vertices;

import org.apache.beam.sdk.transforms.DoFn;
import org.example.dags.webapi.WebApiHttpResponse;

public class ParseBodyDoFn {
    /**
     *
     */
    public static class ParseBodyFn extends DoFn<WebApiHttpResponse, byte[]> {
        /**
         *
         * @param c
         */
        @ProcessElement
        public void processElement(final ProcessContext c) {
            c.output(c.element().getData());
        }
    }
}
