package org.example.dags.webapi;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

@AutoValue
public abstract class WebApiHttpResponse implements Serializable {
    static Builder builder() {
        return new AutoValue_WebApiHttpResponse.Builder();
    }

    @SuppressWarnings("mutable")
    public abstract byte[] getData();

    @AutoValue.Builder
    abstract static class Builder {
        /**
         *
         * @param value
         * @return
         */
        abstract Builder setData(byte[] value);

        /**
         *
         * @return
         */
        abstract WebApiHttpResponse build();
    }

}
