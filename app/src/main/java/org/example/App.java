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

public final class App {
  private App() {
    throw new UnsupportedOperationException();
  }

  public interface DagOptions extends PipelineOptions {
    /**
     * @return String
     */
    @Description("Dag options")
    @Default.String("HELLOWORLD")
    String getDagType();

    /**
     * @param dagType
     */
    void setDagType(String dagType);

    /**
     * @return int
     */
    @Description("Number of years to backtrack the data. ")
    ValueProvider<Integer> getBacktrackedYears();

    /**
     * @param backtrackedYears
     */
    void setBacktrackedYears(ValueProvider<Integer> backtrackedYears);
  }

  /**
   * @param args
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
