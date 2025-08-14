package org.example.dags.realestate.endpoints;


public abstract class Endpoint {

    abstract static class Builder<T extends Builder<T>> {
        abstract Endpoint build();
        protected abstract T self();
    }

    /**
     *
     * @param builder
     */
    Endpoint(final Builder builder) { }

    /**
     *
     * @return Returns URL to access
     */
    public abstract String toUrl();
}
