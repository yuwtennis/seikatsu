package org.example.dags.webapi;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.beam.io.requestresponse.Caller;
import org.apache.beam.io.requestresponse.UserCodeExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebApiHttpClient
        implements Caller<WebApiHttpRequest, WebApiHttpResponse> {

    /**
     *
     */
    static final Logger LOG = LoggerFactory.getLogger(WebApiHttpClient.class);

    /**
     *
     */
    private static final HttpRequestFactory REQUEST_FACTORY =
            new NetHttpTransport().createRequestFactory();

    /**
     *
     * @return WebApiHttpClient
     */
    public static WebApiHttpClient of() {
        return new WebApiHttpClient();
    }

    /**
     *
     * @param webApiHttpRequest
     * @return WebApiHttpRequest
     * @throws UserCodeExecutionException
     */
    @Override
    public WebApiHttpResponse call(final WebApiHttpRequest webApiHttpRequest)
            throws UserCodeExecutionException {
        try {
            GenericUrl url = new GenericUrl(webApiHttpRequest.getUrl());
            HttpRequest request = REQUEST_FACTORY
                    .buildGetRequest(url);

            if (!webApiHttpRequest.getHeaders().isEmpty()) {
                request.setHeaders(
                        createHeaders(webApiHttpRequest.getHeaders()));
            }

            HttpResponse response = request.execute();

            if (!response.isSuccessStatusCode()) {
                throw new RuntimeException(
                        "HTTP Status Code: " + response.getStatusCode());
            }

            InputStream is = response.getContent();

            return WebApiHttpResponse
                    .builder()
                    .setData(is.readAllBytes())
                    .build();

        } catch (IOException | RuntimeException e) {
            throw new UserCodeExecutionException(e);
        }
    }

    /***
     *
     * @param headers
     * @return HttpHeaders
     */
    private HttpHeaders createHeaders(
            final Map<String, String> headers) throws RuntimeException {
        HttpHeaders httpHeaders = new HttpHeaders();

        for (Map.Entry<String, String> header : headers.entrySet()) {
            httpHeaders.set(header.getKey(), header.getValue());
        }

        return httpHeaders;
    }
}
