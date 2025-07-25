package org.example.dags.realestate.vertices;

import org.apache.beam.io.requestresponse.RequestResponseIO;
import org.apache.beam.io.requestresponse.Result;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.values.PCollection;
import org.example.dags.webapi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContentDownloader {
    static Logger LOG = LoggerFactory.getLogger(ContentDownloader.class);

    public static class DownloadUrl
            extends PTransform<
            PCollection<WebApiHttpRequest>,
            PCollection<WebApiHttpResponse>> {
        /**
         *
         * @param request
         * @return
         */
        @Override
        public PCollection<WebApiHttpResponse> expand(PCollection<WebApiHttpRequest> request) {
            LOG.info("Getting the url for");
            Result<WebApiHttpResponse> results = request
                    .apply(
                            "GetUrls",
                            RequestResponseIO.of(
                                    WebApiHttpClient.of(),
                                    WebApiHttpResponseCoder.of()));

            return results.getResponses().setCoder(WebApiHttpResponseCoder.of());
        }
    }
}