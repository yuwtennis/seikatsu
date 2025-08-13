package org.example.dags.realestate.endpoints;

import org.example.Env;

/**
 *
 */
public final class RealEstateLandValueCsvDlEndpoint extends Endpoint {

    /**
     *
     */
    private final int yearOfPrice;

    /**
     *
     */
    private final String selectedPrefId;

    /**
     *
     */
    public static class Builder extends Endpoint.Builder<Builder> {
        /**
         *
         */
        private final int yearOfPrice;

        // TODO Should be enum `県コード
        /**
         *
         */
        private String selectedPrefId = "13";

        /**
         *
         * @param price
         */
        public Builder(final int price) {
            this.yearOfPrice = price;
        }

        /**
         *
         * @return Builder instance
         */
        @Override
        public RealEstateLandValueCsvDlEndpoint build() {
            return new RealEstateLandValueCsvDlEndpoint(this);
        }

        /**
         *
         * @param val
         * @return Builder instance
         */
        public Builder selectedPrefId(final String val) {
            this.selectedPrefId = val;
            return this;
        }

        /**
         *
         * @return Builder instance
         */
        @Override
        protected Builder self() {
            return this;
        }
    }

    private RealEstateLandValueCsvDlEndpoint(final Builder builder) {
        super(builder);
        this.yearOfPrice = builder.yearOfPrice;
        this.selectedPrefId = builder.selectedPrefId;
    }

    /**
     *
     * @return Url
     */
    public String toUrl() {
        String url = "https://" + Env.REALESTATE_INFO_LIBRARY;
        url = url + "/in-api/api-aup/aup/csv/appraisals";
        url = url + "?yearOfPrice=" + this.yearOfPrice;
        url = url + "&selectedPrefId=" + this.selectedPrefId;
        return url;
    }
}
