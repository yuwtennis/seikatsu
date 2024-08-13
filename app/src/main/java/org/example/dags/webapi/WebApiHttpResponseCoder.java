package org.example.dags.webapi;

import org.apache.beam.sdk.coders.ByteArrayCoder;
import org.apache.beam.sdk.coders.CustomCoder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class WebApiHttpResponseCoder extends CustomCoder<WebApiHttpResponse> {
    private final ByteArrayCoder BYTE_ARRAY_CODER = ByteArrayCoder.of();

    public static WebApiHttpResponseCoder of() {
        return new WebApiHttpResponseCoder();
    }

    /**
     *
     * @param value
     * @param outStream
     * @throws IOException
     */
    @Override
    public void encode(WebApiHttpResponse value, OutputStream outStream) throws IOException {
        BYTE_ARRAY_CODER.encode(value.getData(), outStream);
    }

    /**
     *
     * @param inStream
     * @return
     * @throws IOException
     */
    @Override
    public WebApiHttpResponse decode(InputStream inStream) throws IOException {
        byte[] data = BYTE_ARRAY_CODER.decode(inStream);

        return WebApiHttpResponse.builder().setData(data).build();
    }
}
