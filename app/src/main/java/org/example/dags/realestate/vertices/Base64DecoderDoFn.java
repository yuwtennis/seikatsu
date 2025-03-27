package org.example.dags.realestate.vertices;

import org.apache.beam.sdk.transforms.DoFn;
import org.example.Utils;
import org.example.dags.realestate.http_response_schema.AppRaisalsResponseSchema;

import java.util.Base64;

public class Base64DecoderDoFn {
    /**
     *
     */
    public static class Base64DecoderFn extends DoFn<byte[], byte[]> {
        @ProcessElement
        public void processElement(ProcessContext c) {
            String s = new String(c.element());
            AppRaisalsResponseSchema json = (AppRaisalsResponseSchema) Utils.asJson(s, AppRaisalsResponseSchema.class);
            c.output(Base64.getDecoder().decode(json.body));
        }
    }
}
