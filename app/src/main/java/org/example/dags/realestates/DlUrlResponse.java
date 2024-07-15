package org.example.dags.realestates;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

@AutoValue
abstract class DlUrlResponse implements Serializable {
    static Builder builder() {
        return new AutoValue_DlUrlResponse.Builder();
    }

    abstract String getData();

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder setData(String value);

        abstract DlUrlResponse build();
    }

}
