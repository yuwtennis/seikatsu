package org.example.dags.realestate;

public enum EndpointKind {
    RESIDENTIAL_LAND("residential"),
    USED_APARTMENT("used"),
    LAND_VALUE("appraisals");

    public final String value;
    EndpointKind(String value) {
        this.value = value;
    }
}
