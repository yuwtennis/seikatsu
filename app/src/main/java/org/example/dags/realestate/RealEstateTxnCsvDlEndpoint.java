package org.example.dags.realestate;

public class RealEstateTxnCsvDlEndpoint {
    private static final String ENDPOINT = "https://www.reinfolib.mlit.go.jp/in-api/api-aur/aur/csv/transactionPrices";
    private static final int fromQuarter = 1;
    private static final int toQuarter = 4;

    private final TxnKind kind;
    private final int seasonFrom;
    private final int seasonTo;

    private final String language;
    private final String areaCondition;
    private final String prefecture;
    private final String transactionPrice;
    private final String closedPrice;

    public static class Builder {
        private final int seasonFrom;
        private final int seasonTo;
        private TxnKind kind;

        private String language = "ja";
        private String areaCondition = "address";
        private String prefecture = "13";
        private String transactionPrice = "true";
        private String closedPrice = "true";

        public Builder(TxnKind kind, int seasonFrom, int seasonTo) {
            this.kind = kind;
            this.seasonFrom = seasonFrom;
            this.seasonTo = seasonTo;
        }

        public Builder language(String val) { language = val; return this; }
        public Builder areaCondition(String val) { areaCondition = val; return this; }
        public Builder prefecture(String val) { prefecture = val; return this; }
        public Builder transactionPrice(String val) { transactionPrice = val; return this; }
        public Builder closedPrice(String val) { closedPrice = val; return this; }
        public Builder kind(TxnKind val) { kind = val; return this; }

        public RealEstateTxnCsvDlEndpoint build() {
            return new RealEstateTxnCsvDlEndpoint(this);
        }
    }

    private RealEstateTxnCsvDlEndpoint(Builder builder) {
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
     * @return
     */
    public String toUrl() {
        return String.format("%s?language=%s&areaCondition=%s&prefecture=%s&transactionPrice=%s&closedPrice=%s&kind=%s&seasonFrom=%d%d&seasonTo=%d%d",
                ENDPOINT,
                language,
                areaCondition,
                prefecture,
                transactionPrice,
                closedPrice,
                kind.value,
                seasonFrom,
                fromQuarter,
                seasonTo,
                toQuarter
                );
    }
}
