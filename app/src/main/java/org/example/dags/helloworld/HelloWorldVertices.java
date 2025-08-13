package org.example.dags.helloworld;

import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;
import org.example.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HelloWorldVertices {
    private HelloWorldVertices() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     */
    static final Logger LOG = LoggerFactory.getLogger(HelloWorldVertices.class);

    public static class StringForwardFn extends SimpleFunction<String, String> {
        /**
         *
         * @param input
         * @return
         */
        @Override
        public String apply(final String input) {
            if (Utils.isNullOrEmpty(input)) {
                throw new NullPointerException();
            }
            LOG.info("Sending {}", input);
            return input;
        }
    }

    public static class SimpleVertex
            extends PTransform<PCollection<String>, PCollection<String>> {
        /**
         *
         * @param pCol
         * @return PCollection<String>
         * @throws NullPointerException
         */
        @Override
        public PCollection<String> expand(
                final PCollection<String> pCol) throws NullPointerException {
            return pCol.apply(MapElements.via(new StringForwardFn()));
        }
    }
}
