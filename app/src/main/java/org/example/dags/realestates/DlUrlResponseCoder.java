package org.example.dags.realestates;

import org.apache.beam.sdk.coders.CustomCoder;
import org.apache.beam.sdk.coders.StringUtf8Coder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class DlUrlResponseCoder extends CustomCoder<DlUrlResponse> {
    private final StringUtf8Coder STRING_UTF_8_CODER = StringUtf8Coder.of();

    public static DlUrlResponseCoder of() {
        return new DlUrlResponseCoder();
    }

    @Override
    public void encode(DlUrlResponse value, OutputStream outStream) throws IOException {
        STRING_UTF_8_CODER.encode(value.getData(), outStream);
    }

    @Override
    public DlUrlResponse decode(InputStream inStream) throws IOException {
        String data = STRING_UTF_8_CODER.decode(inStream);

        return DlUrlResponse.builder().setData(data).build();
    }
}
