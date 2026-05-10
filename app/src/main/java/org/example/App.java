package org.example;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.apache.beam.sdk.options.ValueProvider;
import org.example.dags.Dag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * App.
 */
public final class App {
  private App() {
    throw new UnsupportedOperationException();
  }

  /**
   * Dag options.
   *
   */
  public interface DagOptions extends PipelineOptions {
    /**
     * Dag type.
     *
     * @return String
     */
    @Description("Dag options")
    @Default.String("HELLOWORLD")
    String getDagType();

    /**
     * Dag type.
     *
     * @param dagType String
     */
    void setDagType(String dagType);

    /**
     * Number of years to backtrack the data.
     *
     * @return int
     */
    @Description("Number of years to backtrack the data. ")
    ValueProvider<Integer> getBacktrackedYears();

    /**
     * Number of years to backtrack the data.
     *
     * @param backtrackedYears ValueProvider<Integer>
     */
    void setBacktrackedYears(ValueProvider<Integer> backtrackedYears);
  }

  /**
   * Main.
   *
   * @param args String[]
   */
  public static void main(final String[] args) {
    Logger root = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
    root.atLevel(org.slf4j.event.Level.INFO).log("Starting the application");

    DagOptions options =
        PipelineOptionsFactory.fromArgs(args).withValidation().as(DagOptions.class);
    Pipeline p = Pipeline.create(options);

    Dag dag = DagDispatcher.dispatch(DagType.valueOf(options.getDagType()));
    dag.process(p);
  }
}
