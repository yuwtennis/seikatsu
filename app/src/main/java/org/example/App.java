package org.example;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.options.Default;
import org.apache.beam.sdk.options.Description;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.options.PipelineOptionsFactory;
import org.example.dags.Dag;

public final class App {
    private App() {
        throw new UnsupportedOperationException();
    }

    public interface DagOptions extends PipelineOptions {
        /**
         *
         * @return String
         */
        @Description("Dag options")
        @Default.String("HELLOWORLD")
        String getDagType();

        /**
         *
         * @param dagType
         */
        void setDagType(String dagType);

        /**
         *
         * @return int
         */
        @Description("Number of years to backtrack the data. ")
        int getBacktrackedYears();

        /**
         *
         * @param backtrackedYears
         */
        void setBacktrackedYears(int backtrackedYears);

    }

    /**
     *
     * @param args
     */
    public static void main(final String[] args) {
        DagOptions options = PipelineOptionsFactory
                .fromArgs(args)
                .withValidation()
                .as(DagOptions.class);
        Pipeline p = Pipeline.create(options);

        Dag dag = DagDispatcher.dispatch(DagType.valueOf(options.getDagType()));
        dag.process(p);
    }
}
