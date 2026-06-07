package org.example.dags.webapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.beam.sdk.coders.ByteArrayCoder;
import org.apache.beam.sdk.coders.CustomCoder;
import org.apache.beam.sdk.coders.StringUtf8Coder;
import org.jspecify.annotations.NonNull;

/**
 * WebApiHttpResponseCoder.
 */
public class WebApiHttpResponseCoder extends CustomCoder<WebApiHttpResponse> {
  /**
   * StringUtf8Coder.
   */
  private static final StringUtf8Coder STRING_UTF_8_CODER = StringUtf8Coder.of();

  /**
   * ByteArrayCoder.
   */
  private static final ByteArrayCoder BYTE_ARRAY_CODER = ByteArrayCoder.of();

  /**
   * Of.
   *
   * @return WebApiHttpResponseCoder
   */
  public static WebApiHttpResponseCoder of() {
    return new WebApiHttpResponseCoder();
  }

  /**
   * Encode.
   *
   * @param value WebApiHttpResponse
   * @param outStream OutputStream
   * @throws IOException IOException
   */
  @Override
  public void encode(
          final WebApiHttpResponse value, final @NonNull OutputStream outStream)
      throws IOException {
    BYTE_ARRAY_CODER.encode(value.getData(), outStream);
    STRING_UTF_8_CODER.encode(value.getCategory(), outStream);
  }

  /**
   * Decode.
   *
   * @param inStream InputStream
   * @return WebApiHttpResponse
   * @throws IOException IOException
   */
  @Override
  public WebApiHttpResponse decode(final @NonNull InputStream inStream) throws IOException {
    byte[] data = BYTE_ARRAY_CODER.decode(inStream);
    String category = STRING_UTF_8_CODER.decode(inStream);

    return WebApiHttpResponse.builder().setData(data).setCategory(category).build();
  }
}
