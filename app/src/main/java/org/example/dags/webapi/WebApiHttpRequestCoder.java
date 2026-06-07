package org.example.dags.webapi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import org.apache.beam.sdk.coders.CustomCoder;
import org.apache.beam.sdk.coders.MapCoder;
import org.apache.beam.sdk.coders.StringUtf8Coder;

/**
 * WebApiHttpRequestCoder.
 */
public class WebApiHttpRequestCoder extends CustomCoder<WebApiHttpRequest> {
  /**
   * StringUtf8Coder.
   */
  private static final StringUtf8Coder STRING_UTF_8_CODER = StringUtf8Coder.of();

  /**
   * MapCoder.
   */
  private static final MapCoder<String, String> MAP_CODER =
      MapCoder.of(STRING_UTF_8_CODER, STRING_UTF_8_CODER);

  /**
   * Of.
   *
   * @return WebApiHttpRequestCoder
   */
  public static WebApiHttpRequestCoder of() {
    return new WebApiHttpRequestCoder();
  }

  /**
   * Encode.
   *
   * @param value WebApiHttpRequest
   * @param outputStream OutputStream
   * @throws IOException IOException
   */
  @Override
  public void encode(final WebApiHttpRequest value, final OutputStream outputStream)
      throws IOException {
    STRING_UTF_8_CODER.encode(value.getUrl(), outputStream);
    STRING_UTF_8_CODER.encode(value.getCategory(), outputStream);
    MAP_CODER.encode(value.getHeaders(), outputStream);
  }

  /**
   * Decode.
   *
   * @param inputStream InputStream
   * @return WebApiHttpRequest
   * @throws IOException IOException
   */
  @Override
  public WebApiHttpRequest decode(final InputStream inputStream) throws IOException {
    String url = STRING_UTF_8_CODER.decode(inputStream);
    String category = STRING_UTF_8_CODER.decode(inputStream);
    Map<String, String> headers = MAP_CODER.decode(inputStream);
    return WebApiHttpRequest.builder()
        .setUrl(url)
        .setCategory(category)
        .setHeaders(headers)
        .build();
  }
}
