package org.example.dags.realestate.endpoints;

public abstract class Endpoint {

    abstract static class Builder<T extends Builder<T>> {
        abstract Endpoint build();
        protected abstract T self();
    }
    Endpoint(Builder builder) {}

    public abstract String toUrl();
}
