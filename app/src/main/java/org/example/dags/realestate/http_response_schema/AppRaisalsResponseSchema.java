package org.example.dags.realestate.http_response_schema;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

public class AppRaisalsResponseSchema {
    private Object contentType;
    public String body;
    public Boolean isBase64Encoded;

    @JsonProperty("headers")
    private void unpackNested(Map<String, Object> headers) {
        this.contentType = headers.get("Content-Type");
    }

    @JsonProperty("statusCode")
    private int statusCode;
}
