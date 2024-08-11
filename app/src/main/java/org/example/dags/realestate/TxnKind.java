package org.example.dags.realestate;

public enum TxnKind {
    RESIDENTIAL_LAND("residential"),
    USED_APARTMENT("used");

    public final String value;
    TxnKind(String value) {
        this.value = value;
    }
}
