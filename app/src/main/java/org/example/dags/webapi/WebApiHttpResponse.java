package org.example.dags.webapi;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

@AutoValue
public abstract class WebApiHttpResponse implements Serializable {
    /**
     *
     * @return Builder
     */
    public static Builder builder() {
        return new AutoValue_WebApiHttpResponse.Builder();
    }

    /**
     *
     * @return byte[]
     */
    @SuppressWarnings("mutable")
    public abstract byte[] getData();

    @AutoValue.Builder
    public abstract static class Builder {
        /**
         *
         * @param value
         * @return Builder
         */
        public abstract Builder setData(byte[] value);

        /**
         *
         * @return WebApiHttpResponse
         */
        public abstract WebApiHttpResponse build();
    }

}
