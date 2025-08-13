package org.example.dags.realestate.http_response_schema;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class AppRaisalsResponseSchema {
    /**
     *
     */
    private Object contentType;

    /**
     *
     */
    private String body;

    /**
     *
     */
    private Boolean isBase64Encoded;

    /**
     *
     * @param headers
     */
    @JsonProperty("headers")
    private void unpackNested(final Map<String, Object> headers) {
        this.contentType = headers.get("Content-Type");
    }

    /**
     *
     */
    @JsonProperty("statusCode")
    private int statusCode;

    /**
     *
     * @return String
     */
    public String getBody() {
        return body;
    }
}
