package org.example.dags.realestate.landvalue;

public enum LandValueType {
    RESIDENTIAL_LAND("宅地");

    public final String value;

    LandValueType(String value) {
        this.value = value;
    }
}
