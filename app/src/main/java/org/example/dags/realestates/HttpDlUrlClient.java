package org.example.dags.realestates;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.http.HttpRequestFactory;
import org.apache.beam.io.requestresponse.Caller;
import org.apache.beam.io.requestresponse.UserCodeExecutionException;
import org.apache.beam.sdk.values.KV;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class HttpDlUrlClient
        implements Caller<KV<String, DlUrlRequest>, KV<String, DlUrlResponse>> {
    private static final HttpRequestFactory REQUEST_FACTORY =
            new NetHttpTransport().createRequestFactory();
    static HttpDlUrlClient of() {
        return new HttpDlUrlClient();
    }

    @Override
    public KV<String, DlUrlResponse> call(KV<String, DlUrlRequest> requestKV)
            throws UserCodeExecutionException {
        try {
            String key = requestKV.getKey();
            GenericUrl url = new GenericUrl(key);
            HttpRequest request = REQUEST_FACTORY.buildGetRequest(url);
            HttpResponse response = request.execute();

            // TODO Improve status code handling
            InputStream is = response.getContent();

            return KV.of(
              key,
              DlUrlResponse
                      .builder()
                      .setData(new String(is.readAllBytes(), StandardCharsets.UTF_8)
              ).build()
            );

        } catch (NullPointerException | IOException e) {
            throw new UserCodeExecutionException(e);
        }
    }
}
