package org.example.dags.realestate.endpoints;

/** Endpoint. */
public abstract class Endpoint {

  /** Builder class. */
  abstract static class Builder<T extends Builder<T>> {
    abstract Endpoint build();

    protected abstract T self();
  }

  /**
   * Constructor.
   *
   * @param builder Builder
   */
  Endpoint(final Builder builder) {}

  /**
   * Returns URL to access.
   *
   * @return Returns URL to access
   */
  public abstract Url toUrl();
}
