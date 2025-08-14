package org.example.dags.realestate.vertices;

import org.apache.beam.sdk.transforms.DoFn;
import org.example.Utils;
import org.example.dags.realestate.http_response_schema.TxnResponseSchema;

public class ParseUrlDoFn {
    /**
     *
     */
    public static class ParseUrlFn extends DoFn<byte[], String> {
        /**
         *
         * @param c
         */
        @ProcessElement
        public void processElement(final ProcessContext c) {
            String s = new String(c.element());
            TxnResponseSchema json = (TxnResponseSchema) Utils.asJson(
                    s, TxnResponseSchema.class);
            c.output(json.getUrl());
        }
    }
}
