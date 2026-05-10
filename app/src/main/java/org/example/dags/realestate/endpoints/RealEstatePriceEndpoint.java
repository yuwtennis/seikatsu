package org.example.dags.realestate.endpoints;

public final class RealEstatePriceEndpoint extends Endpoint {
  /** */
  public static final String CATEGORY = "txn";

  /** */
  private static final String BASE_URL =
      "https://www.reinfolib.mlit.go" + ".jp/ex-api/external/XIT001";

  /** */
  private final int year;

  /** */
  private final String area;

  public static class Builder extends Endpoint.Builder<Builder> {
    /** */
    private final int year;

    /** */
    private final String area;

    /**
     * @param year
     * @param area
     */
    public Builder(final int year, final String area) {
      this.year = year;
      this.area = area;
    }

    /**
     * @return Builder instance
     */
    @Override
    public RealEstatePriceEndpoint build() {
      return new RealEstatePriceEndpoint(this);
    }

    /**
     * @return Builder
     */
    @Override
    protected Builder self() {
      return this;
    }
  }

  private RealEstatePriceEndpoint(final Builder builder) {
    super(builder);

    year = builder.year;
    area = builder.area;
  }

  /**
   * @return Url
   */
  public Url toUrl() {
    String url = BASE_URL;
    url = url + "?year=" + year;
    url = url + "&area=" + area;
    return new Url(CATEGORY, url);
  }
}
