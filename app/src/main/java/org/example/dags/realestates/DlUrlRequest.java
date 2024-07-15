package org.example.dags.realestates;

import com.google.auto.value.AutoValue;
import org.apache.beam.sdk.schemas.AutoValueSchema;
import org.apache.beam.sdk.schemas.annotations.DefaultSchema;

import java.io.Serializable;

@DefaultSchema(AutoValueSchema.class)
@AutoValue
public abstract class DlUrlRequest implements Serializable {
    static Builder builder() {
        return AutoValue_DlUrlRequest.Builder();
    }

    static DlUrlRequest of(String url) {
        return builder().setDlUrl(url).build();
    }

    abstract String getDlUrl();

    @AutoValue.Builder
    abstract static class Builder {
        abstract Builder setDlUrl(String url);

        abstract DlUrlRequest build();
    }
}
