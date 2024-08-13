package org.example.dags.webapi;

import com.google.auto.value.AutoValue;
import org.apache.beam.sdk.schemas.AutoValueSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@DefaultSchema(AutoValueSchema.class)
@AutoValue
public abstract class WebApiHttpRequest implements Serializable {
    static Builder builder() {
        return new AutoValue_WebApiHttpRequest.Builder();
    }

    /**
     *
     * @param url
     * @return
     */
    public static WebApiHttpRequest of(String url) {
        return builder()
                .setUrl(url)
                .setHeaders(new HashMap<String, String>())
                .build();
    }

    /**
     *
     * @param url
     * @param headers
     * @return
     */
    public static WebApiHttpRequest of(String url, Map<String, String> headers) {
        return builder()
                .setUrl(url)
                .setHeaders(headers)
                .build();
    }

    public abstract String getUrl();
    public abstract Map<String, String> getHeaders();

    @AutoValue.Builder
    abstract static class Builder {
        /**
         *
         * @param url
         * @return
         */
        abstract Builder setUrl(String url);

        /**
         *
         * @param headers
         * @return
         */
        abstract Builder setHeaders(Map<String, String> headers);

        /**
         *
         * @return
         */
        abstract WebApiHttpRequest build();
    }
}
