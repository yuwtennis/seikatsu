package org.example.dags.webapi;

import com.google.auto.value.AutoValue;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.apache.beam.sdk.schemas.AutoValueSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

@DefaultSchema(AutoValueSchema.class)
@AutoValue
public abstract class WebApiHttpRequest implements Serializable {
    static Builder builder() {
        return new AutoValue_WebApiHttpRequest.Builder();
    }

    /**
     *
     * @param url
     * @return WebApiHttpRequest
     */
    public static WebApiHttpRequest of(final String url) {
        return builder()
                .setUrl(url)
                .setHeaders(new HashMap<String, String>())
                .build();
    }

    /**
     *
     * @param url
     * @param headers
     * @return WebApiHttpRequest
     */
    public static WebApiHttpRequest of(
            final String url, final Map<String, String> headers) {
        return builder()
                .setUrl(url)
                .setHeaders(headers)
                .build();
    }

    /**
     *
     * @return String
     */
    public abstract String getUrl();

    /**
     *
     * @return Map
     */
    public abstract Map<String, String> getHeaders();

    @AutoValue.Builder
    abstract static class Builder {
        /**
         *
         * @param url
         * @return Builder
         */
        abstract Builder setUrl(String url);

        /**
         *
         * @param headers
         * @return Builder
         */
        abstract Builder setHeaders(Map<String, String> headers);

        /**
         *
         * @return WebApiHttpRequest
         */
        abstract WebApiHttpRequest build();
    }
}
