package org.example.dags.webapi;

import com.google.auto.value.AutoValue;
import java.io.Serializable;

/**
 * WebApiHttpResponse.
 */
@AutoValue
public abstract class WebApiHttpResponse implements Serializable {
  /**
   * Builder.
   *
   * @return Builder
   */
  public static Builder builder() {
    return new AutoValue_WebApiHttpResponse.Builder();
  }

  /**
   * Get the data.
   *
   * @return byte[]
   */
  @SuppressWarnings("mutable")
  public abstract byte[] getData();

  /**
   * Get the category.
   *
   * @return String
   */
  public abstract String getCategory();

  /**
   * Builder.
   */
  @AutoValue.Builder
  public abstract static class Builder {
    /**
     * Set the data.
     *
     * @param responseContents byte[]
     * @return Builder
     */
    public abstract Builder setData(byte[] responseContents);

    /**
     * Set the category.
     *
     * @param realEstateCategory String
     * @return Builder
     */
    public abstract Builder setCategory(String realEstateCategory);

    /**
     * Build the WebApiHttpResponse.
     *
     * @return WebApiHttpResponse
     */
    public abstract WebApiHttpResponse build();
  }
}
