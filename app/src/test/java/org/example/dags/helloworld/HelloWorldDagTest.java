package org.example.dags.helloworld;

import static org.example.Utils.asJsonStr;
import static org.junit.Assert.assertTrue;

import org.apache.beam.sdk.testing.NeedsRunner;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.testing.TestPipelineOptions;
import org.example.App;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * HelloWorldDagTest.
 */
public class HelloWorldDagTest {
  /**
   * TestHWOptions.
   */
  public interface TestHWOptions extends TestPipelineOptions, App.DagOptions {}

  /**
   * Type of the DAG.
   */
  private String dagType;

  /**
   * Mock pipeline.
   */
  @Rule public final transient TestPipeline pipeline = TestPipeline.create();

  /**
   * Setup.
   */
  @Before
  public void setUp() {
    // Test pipeline options as JSON array
    // https://beam.apache.org/releases
    //   /javadoc/2.57.0/org/apache/beam/sdk/testing/TestPipeline.html
    // https://www.json.org/json-en.html
    String[] pipelineOptions = new String[] {"--runner=DirectRunner"};
    System.setProperty("beamTestPipelineOptions", asJsonStr(pipelineOptions));
    dagType = "HELLOWORLD";
  }

  /**
   * Test process.
   */
  @Test
  @Category(NeedsRunner.class)
  public void testProcess() {
    pipeline.getOptions().as(App.DagOptions.class).setDagType(dagType);
    pipeline.run();
    assertTrue(true);
  }
}
