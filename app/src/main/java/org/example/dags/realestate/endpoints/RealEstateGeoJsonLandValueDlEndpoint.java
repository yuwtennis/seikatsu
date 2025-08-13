package org.example.dags.realestate.endpoints;

import org.example.Env;
import org.example.Magics;

import static org.example.Utils.asInt;

/**
 *
 */
public final class RealEstateGeoJsonLandValueDlEndpoint extends Endpoint {
    /**
     *
     */
    private static final String ENDPOINT =
            "https://www.reinfolib.mlit.go.jp";

    /**
     *
     */
    private final int x;

    /**
     *
     */
    private final int y;

    /**
     *
     */
    private final int z;

    /**
     *
     */
    private final int year;

    /**
     *
     */
    private final int priceClassification;

    public static class Builder extends Endpoint.Builder<Builder> {
        /**
         *
         */
        private final int x;

        /**
         *
         */
        private final int y;

        /**
         *
         */
        private final int year;

        /**
         *
         */
        private int z = asInt(Magics.NUM_0.getValue());

        /**
         *
         */
        private int priceClassification = 0;

        /**
         *
         * @param val
         * @return Builder instance
         */
        public Builder z(final int val) {
            z = val;
            return this;
        }

        /**
         *
         * @param tileX
         * @param tileY
         * @param targetYear
         */
        public Builder(
                final int tileX,
                final int tileY,
                final int targetYear) {
            this.x = tileX;
            this.y = tileY;
            this.year = targetYear;
        }

        /**
         *
         * @param val
         * @return Builder instance
         */
        public Builder priceClassification(final int val) {
            priceClassification = val;
            return this;
        }

        /**
         *
         * @return RealEstateGeoJsonLandValueDlEndpoint
         */
        @Override
        public RealEstateGeoJsonLandValueDlEndpoint build() {
            return new RealEstateGeoJsonLandValueDlEndpoint(this);
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

    private RealEstateGeoJsonLandValueDlEndpoint(final Builder builder) {
        super(builder);

        this.x = builder.x;
        this.y = builder.y;
        this.z = builder.z;
        this.year = builder.year;
        this.priceClassification = builder.priceClassification;
    }

    /**
     *
     * @return Url string
     */
    public String toUrl() {
        String url = "https://" + Env.REALESTATE_INFO_LIBRARY;
        url = url + "/ex-api/external/XPT002";
        url = url + "?response_format=geojson";
        url = url + "&x=" + this.x;
        url = url + "&y=" + this.y;
        url = url + "&z=" + this.z;
        url = url + "&price_classification=" + this.priceClassification;
        return url;
    }
}
