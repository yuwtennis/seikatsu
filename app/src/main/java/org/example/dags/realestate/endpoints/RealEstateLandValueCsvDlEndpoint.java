package org.example.dags.realestate.endpoints;

public class RealEstateLandValueCsvDlEndpoint extends Endpoint {
    private static final String ENDPOINT = "https://www.reinfolib.mlit.go.jp/in-api/api-aup/aup/csv/appraisals";
    private final int yearOfPrice;
    private final String selectedPrefId;

    public static class Builder extends Endpoint.Builder<Builder> {
        private final int yearOfPrice;

        // TODO Should be enum `県コード
        private String selectedPrefId = "13";

        public Builder(int yearOfPrice) {
            this.yearOfPrice = yearOfPrice;
        }

        /**
         *
         * @return
         */
        @Override
        public RealEstateLandValueCsvDlEndpoint build() {
            return new RealEstateLandValueCsvDlEndpoint(this);
        }

        /**
         *
         * @param val
         * @return
         */
        public Builder selectedPrefId(String val) { selectedPrefId = val; return this; }

        /**
         *
         * @return
         */
        @Override
        protected Builder self() { return this; }
    }

    private RealEstateLandValueCsvDlEndpoint(Builder builder) {
        super(builder);
        yearOfPrice = builder.yearOfPrice;
        selectedPrefId = builder.selectedPrefId;
    }

    /**
     *
     * @return
     */
    public String toUrl() {
        return String.format("%s?yearOfPrice=%s&selectedPrefId=%s",
                ENDPOINT,
                yearOfPrice,
                selectedPrefId
                );
    }
}
