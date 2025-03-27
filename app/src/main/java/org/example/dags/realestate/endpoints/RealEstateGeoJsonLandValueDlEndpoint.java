package org.example.dags.realestate.endpoints;

public class RealEstateGeoJsonLandValueDlEndpoint extends Endpoint {
    private static final String ENDPOINT = "https://www.reinfolib.mlit.go.jp/ex-api/external/XPT002?response_format=geojson";

    private final int x;
    private final int y;
    private final int z;
    private final int year;
    private final int priceClassification;

    public static class Builder extends Endpoint.Builder<Builder> {
        private final int x;
        private final int y;
        private final int year;

        private int z = 13;
        private int priceClassification = 0;

        /**
         *
         * @param val
         * @return
         */
        public Builder z(int val) { z = val; return this; }

        public Builder(int tile_x, int tile_y, int year) {
            this.x = tile_x;
            this.y = tile_y;
            this.year = year;
        }

        public Builder priceClassification(int val) { priceClassification = val; return this; }

        /**
         *
         * @return
         */
        @Override
        public RealEstateGeoJsonLandValueDlEndpoint build() {
            return new RealEstateGeoJsonLandValueDlEndpoint(this);
        }

        @Override
        protected Builder self() { return this; }
    }

    private RealEstateGeoJsonLandValueDlEndpoint(Builder builder) {
        super(builder);

        x = builder.x;
        y = builder.y;
        z = builder.z;
        year = builder.year;
        priceClassification = builder.priceClassification;
    }

    /**
     *
     * @return
     */
    public String toUrl() {
        return String.format("%s&x=%d&y=%d&z=%d&year=%d&priceClassification=%d",
                ENDPOINT,
                x,
                y,
                z,
                year,
                priceClassification
                );
    }
}
