package org.example.dags.realestate.landvalue;

public enum LandValueType {
    /**
     *
     */
    RESIDENTIAL_LAND("宅地");

    /**
     *
     */
    private final String value;

    LandValueType(final String v) {
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
