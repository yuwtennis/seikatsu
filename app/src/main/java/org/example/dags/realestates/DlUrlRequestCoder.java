package org.example.dags.realestates;

import org.apache.beam.sdk.coders.CustomCoder;
import org.apache.beam.sdk.coders.StringUtf8Coder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

class DlUrlRequestCoder extends CustomCoder<DlUrlRequest> {
    private static final StringUtf8Coder STRING_UTF_8_CODER = StringUtf8Coder.of();

    static DlUrlRequestCoder of() {
        return new DlUrlRequestCoder();
    }

    @Override
    public void encode(DlUrlRequest value, OutputStream outputStream) throws IOException {
        STRING_UTF_8_CODER.encode(value.getDlUrl(), outputStream);
    }

    @Override
    public DlUrlRequest decode(InputStream inputStream) throws IOException {
        String dlUrl = STRING_UTF_8_CODER.decode(inputStream);
        return DlUrlRequest.builder().setDlUrl(dlUrl).build();
    }
}
