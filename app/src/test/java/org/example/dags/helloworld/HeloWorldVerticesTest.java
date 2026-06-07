package org.example.dags.helloworld;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.beam.sdk.testing.NeedsRunner;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.values.PCollection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/** HeloWorldVerticesTest. */
public class HeloWorldVerticesTest {
  /** Sample lines as input of the pipeline. */
  private List<String> samples;

  /** Mock pipeline. */
  @Rule public final transient TestPipeline pipeline = TestPipeline.create();

  /** Setup. */
  @Before
  public void setUp() {
    samples = new ArrayList<String>(Arrays.asList("Hello", "World."));
  }

  /** Test StringForwardFn. */
  @Test
  @Category(NeedsRunner.class)
  public void testStringForwardFn() {
    PCollection<String> lines =
        pipeline
            .apply(Create.of(samples))
            .apply(MapElements.via(new HelloWorldVertices.StringForwardFn()));
    PAssert.that(lines).containsInAnyOrder(samples);
    pipeline.run().waitUntilFinish();
  }

  /** Test SimpleVertex. */
  @Test
  @Category(NeedsRunner.class)
  public void testSimpleVertex() {
    PCollection<String> lines =
        pipeline.apply(Create.of(samples)).apply(new HelloWorldVertices.SimpleVertex());
    PAssert.that(lines).containsInAnyOrder(samples);
    pipeline.run().waitUntilFinish();
  }
}
