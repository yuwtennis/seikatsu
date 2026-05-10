package org.example.dags.webapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.beam.sdk.coders.ByteArrayCoder;
import org.apache.beam.sdk.coders.CustomCoder;
import org.apache.beam.sdk.coders.StringUtf8Coder;

public class WebApiHttpResponseCoder extends CustomCoder<WebApiHttpResponse> {
  /** */
  private static final StringUtf8Coder STRING_UTF_8_CODER = StringUtf8Coder.of();

  /** */
  private static final ByteArrayCoder BYTE_ARRAY_CODER = ByteArrayCoder.of();

  /**
   * @return WebApiHttpResponseCoder
   */
  public static WebApiHttpResponseCoder of() {
    return new WebApiHttpResponseCoder();
  }

  /**
   * @param value
   * @param outStream
   * @throws IOException
   */
  @Override
  public void encode(final WebApiHttpResponse value, final OutputStream outStream)
      throws IOException {
    BYTE_ARRAY_CODER.encode(value.getData(), outStream);
    STRING_UTF_8_CODER.encode(value.getCategory(), outStream);
  }

  /**
   * @param inStream
   * @return
   * @throws IOException
   */
  @Override
  public WebApiHttpResponse decode(final InputStream inStream) throws IOException {
    byte[] data = BYTE_ARRAY_CODER.decode(inStream);
    String category = STRING_UTF_8_CODER.decode(inStream);

    return WebApiHttpResponse.builder().setData(data).setCategory(category).build();
  }
}
