package org.example.dags.realestate;

public final class Env {
  private Env() {
    throw new UnsupportedOperationException();
  }

  /** */
  public static final String OCP_APIM_SUBSCRIPTION_KEY = System.getenv("OCP_APIM_SUBSCRIPTION_KEY");
}
