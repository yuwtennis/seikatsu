package org.example.dags.helloworld;

import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.PCollection;
import org.example.Utils;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** HelloWorldVertices. */
public final class HelloWorldVertices {
  private HelloWorldVertices() {
    throw new UnsupportedOperationException();
  }

  /** Logger. */
  static final Logger LOG = LoggerFactory.getLogger(HelloWorldVertices.class);

  /** StringForwardFn. */
  public static class StringForwardFn extends SimpleFunction<String, String> {
    /**
     * Forward the string.
     *
     * @param input String
     * @return String
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

  /** SimpleVertex. */
  public static class SimpleVertex extends PTransform<PCollection<String>, PCollection<String>> {
    /**
     * Expand.
     *
     * @param lines PCollection<String>
     * @return PCollection<String>
     * @throws NullPointerException if the input is null
     */
    @Override
    public @NonNull PCollection<String> expand(final PCollection<String> lines)
        throws NullPointerException {
      return lines.apply(MapElements.via(new StringForwardFn()));
    }
  }
}
