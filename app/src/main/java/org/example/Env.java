package org.example;

public final class Env {
    private Env() {
        throw new UnsupportedOperationException();
    }

    /**
     *
     */
    public static final String REALESTATE_INFO_LIBRARY =
            System.getenv("REALESTATE_INFO_LIBRARY");
}
