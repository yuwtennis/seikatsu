package org.example.dags.realestate.landvalue;

import static org.junit.Assert.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.apache.beam.io.requestresponse.Caller;
import org.apache.beam.io.requestresponse.RequestResponseIO;
import org.apache.beam.io.requestresponse.Result;
import org.apache.beam.io.requestresponse.UserCodeExecutionException;
import org.apache.beam.sdk.testing.NeedsRunner;
import org.apache.beam.sdk.testing.PAssert;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.*;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.TypeDescriptors;
import org.example.dags.realestate.vertices.GeoLandValueFn;
import org.example.dags.webapi.WebApiHttpResponse;
import org.example.dags.webapi.WebApiHttpResponseCoder;
import org.geojson.FeatureCollection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

public class GeoLandValueTest {
    File geoJson;

    @Rule
    public final transient TestPipeline p = TestPipeline.create();

    @Before
    public void setUp() {
        geoJson = new File("src/test/resources/geo.json");
    }

    @Test
    public void testGeoLandValue() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            FeatureCollection json = mapper.readValue(geoJson, FeatureCollection.class);
            GeoLandValue geoLV = GeoLandValue.of(json.getFeatures().getFirst());
            assertEquals("高円寺北２丁目７３０番２７", geoLV.locationNumber);
            assertEquals(670000, geoLV.pricePerSqm);
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private static class MockEmptyIterableResponse
            implements Caller<File, WebApiHttpResponse> {
        @Override
        public WebApiHttpResponse call(File file) throws UserCodeExecutionException {

            ObjectMapper mapper = new ObjectMapper();
            byte[] b = null;
            try {
                b = mapper.writeValueAsBytes(mapper.readValue(file, FeatureCollection.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return WebApiHttpResponse
                    .builder()
                    .setData(b)
                    .build();
        }
    }

    @Test
    @Category(NeedsRunner.class)
    public void testGeoLandValueIsSerializable() throws IOException {
        List<File> files = new ArrayList<>();
        files.add(geoJson);
        Result<WebApiHttpResponse> resps = p
                .apply(Create.of(files))
                .apply(RequestResponseIO.of(new MockEmptyIterableResponse(), WebApiHttpResponseCoder.of()));

        PCollection<String> results = resps.getResponses()
                .apply(
                        ParDo.of(new GeoLandValueFn.FromWebApiHttpResponseFn()))
                .apply(MapElements
                        .into(TypeDescriptors.strings())
                        .via((GeoLandValue g)-> g.locationNumber));

        PAssert.that(results).containsInAnyOrder("高円寺北２丁目７３０番２７");
        p.run().waitUntilFinish();
    }
}
