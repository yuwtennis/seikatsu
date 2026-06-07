package org.example.dags.realestate.endpoints;

/** RealEstatePriceEndpoint. */
public final class RealEstatePriceEndpoint extends Endpoint {
  /** Category. */
  public static final String CATEGORY = "txn";

  /** Base URL. */
  private static final String BASE_URL =
      "https://www.reinfolib.mlit.go" + ".jp/ex-api/external/XIT001";

  /** Year. */
  private final int year;

  /** Area. */
  private final String area;

  /** Language. */
  private final String language;

  /** Builder class. */
  public static class Builder extends Endpoint.Builder<Builder> {
    /** Year. */
    private final int year;

    /** Area. */
    private final String area;

    /** Language. */
    private final String language;

    /**
     * Constructor for the Builder class.
     *
     * @param year year
     * @param area area
     */
    public Builder(final int year, final String area, final String language) {
      this.year = year;
      this.area = area;
      this.language = language;
    }

    /**
     * Build the RealEstatePriceEndpoint instance.
     *
     * @return Builder instance
     */
    @Override
    public RealEstatePriceEndpoint build() {
      return new RealEstatePriceEndpoint(this);
    }

    /**
     * Get the Builder instance.
     *
     * @return Builder
     */
    @Override
    protected Builder self() {
      return this;
    }
  }

  /**
   * Constructor for the RealEstatePriceEndpoint class.
   *
   * @param builder Builder
   */
  private RealEstatePriceEndpoint(final Builder builder) {
    super(builder);

    year = builder.year;
    area = builder.area;
    language = builder.language;
  }

  /**
   * Get the URL to access.
   *
   * @return Url
   */
  public Url toUrl() {
    String url = BASE_URL;
    url = url + "?year=" + year;
    url = url + "&area=" + area;
    url = url + "&language=" + language;
    return new Url(CATEGORY, url);
  }
}
