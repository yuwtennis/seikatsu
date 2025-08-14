package org.example.dags.realestate;

public final class OcpApimSubscriptionKeyHeader {
    private OcpApimSubscriptionKeyHeader() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     */
    public static final String NAME = "Ocp-Apim-Subscription-Key";

    /**
     *
     */
    public static final String VALUE = System.getenv(
            "OCP_APIM_SUBSCRIPTION_KEY_VALUE");
}
