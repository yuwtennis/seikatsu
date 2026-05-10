package org.example.dags.realestate.endpoints;

/** */
public final class StandardLandPriceEndpoint extends Endpoint {
  /** */
  public static final String CATEGORY = "standard_land_price";

  /** */
  private static final String BASE_URL = "https://www.reinfolib.mlit.go.jp/ex-api/external/XCT001";

  /** */
  private final int year;

  /** */
  private final String area;

  /** */
  private final String division;

  /** */
  public static class Builder extends Endpoint.Builder<Builder> {
    /** */
    private final int year;

    /** */
    private final String area;

    /** */
    private final String division;

    /**
     * Constructor for the Builder class.
     *
     * @param year
     * @param area
     * @param division
     */
    public Builder(final int year, final String area, final String division) {
      this.year = year;
      this.area = area;
      this.division = division;
    }

    /**
     * @return Builder instance
     */
    @Override
    public StandardLandPriceEndpoint build() {
      return new StandardLandPriceEndpoint(this);
    }

    /**
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
