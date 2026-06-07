package org.example.dags.realestate.endpoints;

/** StandardLandPriceEndpoint. */
public final class StandardLandPriceEndpoint extends Endpoint {
  /** Category. */
  public static final String CATEGORY = "standard_land_price";

  /** Base URL. */
  private static final String BASE_URL = "https://www.reinfolib.mlit.go.jp/ex-api/external/XCT001";

  /** Year. */
  private final int year;

  /** Area. */
  private final String area;

  /** Division. */
  private final String division;

  /** Builder class. */
  public static class Builder extends Endpoint.Builder<Builder> {
    /** Year. */
    private final int year;

    /** Area. */
    private final String area;

    /** Division. */
    private final String division;

    /**
     * Constructor for the Builder class.
     *
     * @param year year
     * @param area area
     * @param division division
     */
    public Builder(final int year, final String area, final String division) {
      this.year = year;
      this.area = area;
      this.division = division;
    }

    /**
     * Build the StandardLandPriceEndpoint instance.
     *
     * @return Builder instance
     */
    @Override
    public StandardLandPriceEndpoint build() {
      return new StandardLandPriceEndpoint(this);
    }

    /**
     * Get the Builder instance.
     *
     * @return Builder instance
     */
    @Override
    protected Builder self() {
      return this;
    }
  }

  private StandardLandPriceEndpoint(final Builder builder) {
    super(builder);
    this.year = builder.year;
    this.area = builder.area;
    this.division = builder.division;
  }

  /**
   * Get the URL to access.
   *
   * @return Url
   */
  public Url toUrl() {
    String url = BASE_URL;
    url = url + "?year=" + this.year;
    url = url + "&area=" + this.area;
    url = url + "&division=" + this.division;
    return new Url(CATEGORY, url);
  }
}
