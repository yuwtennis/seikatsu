package org.example.dags.realestate;

/**
 * Env.
 */
public final class Env {
  /**
   * Private constructor.
   */
  private Env() {
    throw new UnsupportedOperationException();
  }

  /**
   * Subscription key.
   */
  public static final String OCP_APIM_SUBSCRIPTION_KEY = System.getenv("OCP_APIM_SUBSCRIPTION_KEY");
}
