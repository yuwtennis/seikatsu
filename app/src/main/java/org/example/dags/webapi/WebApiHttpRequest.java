package org.example.dags.webapi;

import com.google.auto.value.AutoValue;
import java.io.Serializable;
import java.util.Map;
import org.example.dags.realestate.endpoints.Url;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * WebApiHttpRequest.
 */
@AutoValue
public abstract class WebApiHttpRequest implements Serializable {
  static Builder builder() {
    return new AutoValue_WebApiHttpRequest.Builder();
  }

  private static final Logger LOG = LoggerFactory.getLogger(WebApiHttpRequest.class);

  /**
   * Create a new WebApiHttpRequest.
   *
   * @param url Url
   * @param headers Map. String as Key, String as Value
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
   * Get the URL to access.
   *
   * @return String
   */
  public abstract String getUrl();

  /**
   * Get the category.
   *
   * @return String
   */
  public abstract String getCategory();

  /**
   * Get the headers.
   *
   * @return Map
   */
  public abstract Map<String, String> getHeaders();

  @AutoValue.Builder
  abstract static class Builder {
    /**
     * Set the URL to access.
     *
     * @param apiEndpointUrl String
     * @return Builder
     */
    abstract Builder setUrl(String apiEndpointUrl);

    /**
     * Set the category.
     *
     * @param realEstateCategory String
     * @return Builder
     */
    abstract Builder setCategory(String realEstateCategory);

    /**
     * Set the headers.
     *
     * @param httpRequestHeaders Map
     * @return Builder
     */
    abstract Builder setHeaders(Map<String, String> httpRequestHeaders);

    /**
     * Build the WebApiHttpRequest.
     *
     * @return WebApiHttpRequest
     */
    abstract WebApiHttpRequest build();
  }
}
