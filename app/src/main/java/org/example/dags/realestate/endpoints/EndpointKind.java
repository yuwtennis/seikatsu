package org.example.dags.realestate.endpoints;

public enum EndpointKind {
    /**
     *
     */
    RESIDENTIAL_LAND("residential"),

    /**
     *
     */
    USED_APARTMENT("used"),

    /**
     *
     */
    LAND_VALUE("appraisals");

    /**
     *
     */
    private final String value;

    EndpointKind(final String v) {
        this.value = v;
    }

    /**
     *
     * @return String
     */
    public String getValue() {
        return value;
    }
}
