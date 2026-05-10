package org.example.dags.webapi;

import com.google.auto.value.AutoValue;
import java.io.Serializable;
import java.util.Map;
import org.example.dags.realestate.endpoints.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AutoValue
public abstract class WebApiHttpRequest implements Serializable {
  static Builder builder() {
    return new AutoValue_WebApiHttpRequest.Builder();
  }

  private static final Logger LOG = LoggerFactory.getLogger(WebApiHttpRequest.class);

  /**
   * @param url
   * @param headers
   * @return WebApiHttpRequest
   */
  public static WebApiHttpRequest of(final Url url, final Map<String, String> headers) {
    // Test if the headers are empty
    for (Map.Entry<String, String> header : headers.entrySet()) {
      if (header.getValue().isEmpty()) {
        throw new RuntimeException("Header: " + header.getKey() + " is not allowed");
      }
    }

    return builder()
        .setUrl(url.getUrl())
        .setCategory(url.getCategory())
        .setHeaders(headers)
        .build();
  }

  /**
   * @return String
   */
  public abstract String getUrl();

  /**
   * @return
   */
  public abstract String getCategory();

  /**
   * @return Map
   */
  public abstract Map<String, String> getHeaders();

  @AutoValue.Builder
  abstract static class Builder {
    /**
     * @param url
     * @return Builder
     */
    abstract Builder setUrl(String url);

    abstract Builder setCategory(String category);

    /**
     * @param headers
     * @return Builder
     */
    abstract Builder setHeaders(Map<String, String> headers);

    /**
     * @return WebApiHttpRequest
     */
    abstract WebApiHttpRequest build();
  }
}
