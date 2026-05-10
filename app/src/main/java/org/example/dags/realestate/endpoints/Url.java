package org.example.dags.realestate.endpoints;

import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.extensions.avro.coders.AvroCoder;

/**
 * Represents the category of a URL, describing its classification or type. This field is used to
 * identify the nature or purpose of the URL within a specific context.
 */
@DefaultCoder(AvroCoder.class)
public class Url {

  /** Category of the URL. */
  private String category;

  /** The actual URL string. */
  private String url;

  /** Url constructor.*/
  public Url() {}

  /**
   * Constructor for the Url class.
   *
   * @param category Categorization of the URL.
   * @param url Actual URL string.
   */
  public Url(final String category, final String url) {
    this.category = category;
    this.url = url;
  }

  /**
   * Get the category of the URL.
   *
   * @return String
   */
  public String getCategory() {
    return category;
  }

  /**
   * Get the actual URL string.
   *
   * @return String
   */
  public String getUrl() {
    return url;
  }
}
