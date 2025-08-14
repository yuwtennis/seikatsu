package org.example.dags.realestate.endpoints;

import org.example.Env;

public final class RealEstateTxnCsvDlEndpoint extends Endpoint {
    /**
     *
     */
    private static final int FROM_QUARTER = 1;

    /**
     *
     */
    private static final int TO_QUARTER = 4;

    /**
     *
     */
    private final EndpointKind kind;

    /**
     *
     */
    private final int seasonFrom;

    /**
     *
     */
    private final int seasonTo;

    /**
     *
     */
    private final String language;

    /**
     *
     */
    private final String areaCondition;

    /**
     *
     */
    private final String prefecture;

    /**
     *
     */
    private final String transactionPrice;

    /**
     *
     */
    private final String closedPrice;

    public static class Builder extends Endpoint.Builder<Builder> {
        /**
         *
         */
        private final int seasonFrom;

        /**
         *
         */
        private final int seasonTo;

        /**
         *
         */
        private EndpointKind kind;

        /**
         *
         */
        private String language = "ja";

        /**
         *
         */
        private String areaCondition = "address";

        /**
         *
         */
        private String prefecture = "13";

        /**
         *
         */
        private String transactionPrice = "true";

        /**
         *
         */
        private String closedPrice = "true";

        /**
         *
         * @param ek
         * @param from
         * @param to
         */
        public Builder(
                final EndpointKind ek,
                final int from,
                final int to) {
            this.kind = ek;
            this.seasonFrom = from;
            this.seasonTo = to;
        }

        /**
         *
         * @param val
         * @return Builder instance
         */
        public Builder language(final String val) {
            language = val;
            return this;
        }

        /**
         *
         * @param val
         * @return Builder instance
         */
        public Builder areaCondition(final String val) {
            areaCondition = val;
            return this;
        }

        /**
         *
         * @param val
         * @return Builder instance
         */
        public Builder prefecture(final String val) {
            prefecture = val;
            return this;
        }

        /**
         *
         * @param val
         * @return Builder instance
         */
        public Builder transactionPrice(final String val) {
            transactionPrice = val;
            return this;
        }

        /**
         *
         * @param val
         * @return Builder instance
         */
        public Builder closedPrice(final String val) {
            closedPrice = val;
            return this;
        }

        /**
         *
         * @param val
         * @return Builder instance
         */
        public Builder kind(final EndpointKind val) {
            kind = val;
            return this;
        }

        /**
         *
         * @return Builder instance
         */
        @Override
        public RealEstateTxnCsvDlEndpoint build() {
            return new RealEstateTxnCsvDlEndpoint(this);
        }

        /**
         *
         * @return Builder
         */
        @Override
        protected Builder self() {
            return this;
        }
    }

    private RealEstateTxnCsvDlEndpoint(final Builder builder) {
        super(builder);

        seasonFrom = builder.seasonFrom;
        seasonTo = builder.seasonTo;
        language = builder.language;
        areaCondition = builder.areaCondition;
        prefecture = builder.prefecture;
        transactionPrice = builder.transactionPrice;
        closedPrice = builder.closedPrice;
        kind = builder.kind;
    }

    /**
     *
     * @return Url
     */
    public String toUrl() {
        String url = "https://" + Env.REALESTATE_INFO_LIBRARY;
        url = url + "/in-api/api-aur/aur/csv/transactionPrices";
        url = "?language=" + language;
        url = url + "&areaCondition=" + areaCondition;
        url = url + "&prefecture=" + prefecture;
        url = url + "&transactionPrice=" + transactionPrice;
        url = url + "&closedPrice=" + closedPrice;
        url = url + "&kind=" + kind.getValue();
        url = url + "&seasonFrom=" + seasonFrom + FROM_QUARTER;
        url = url + "&seasonTo=" + seasonTo + TO_QUARTER;
        return url;
    }
}
