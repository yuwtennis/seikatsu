package org.example.dags.realestate.vertices;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.beam.sdk.transforms.DoFn;
import org.example.dags.realestate.landvalue.GeoLandValue;
import org.example.dags.webapi.WebApiHttpResponse;
import org.geojson.Feature;
import org.geojson.FeatureCollection;

import java.io.IOException;

public class GeoLandValueFn {
    public static class FromWebApiHttpResponseFn
            extends DoFn<WebApiHttpResponse, org.example.dags.realestate.landvalue.GeoLandValue> {
        @ProcessElement
        public void processElement(ProcessContext c) throws IOException {
            ObjectMapper mapper = new ObjectMapper();
            FeatureCollection fc = mapper
                    .readValue(c.element()
                            .getData(), FeatureCollection.class);

            // Flatten
            for(Feature f : fc.getFeatures()) {
                try {
                    GeoLandValue geo = GeoLandValue.of(f);
                    c.output(geo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
