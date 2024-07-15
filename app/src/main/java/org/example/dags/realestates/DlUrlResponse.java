package org.example.dags.realestates;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

public abstract class DlUrlResponse implements Serializable {
    static Builder builder() {
        return AutoValue_DlUrlResponse.Builder();
    }

    abstract String getData();

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder setData(String value);

        abstract DlUrlResponse build();
    }

}
