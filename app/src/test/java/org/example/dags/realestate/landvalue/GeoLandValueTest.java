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
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.ParDo;
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
    /**
     *
     */
    private File geoJson;

    /**
     *
     */
    private static final int PRICE_PER_SQM = 670000;

    /**
     *
     */
    @Rule
    public final transient TestPipeline p = TestPipeline.create();

    /**
     *
     */
    @Before
    public void setUp() {
        geoJson = new File("src/test/resources/geo.json");
    }

    /**
     *
     */
    @Test
    public void testGeoLandValue() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            FeatureCollection json = mapper.readValue(
                    this.geoJson, FeatureCollection.class);
            GeoLandValue geoLV = GeoLandValue.of(
                    json.getFeatures().getFirst());
            assertEquals("高円寺北２丁目７３０番２７",
                    geoLV.getLocationNumber());
            assertEquals(PRICE_PER_SQM, geoLV.getPricePerSqm());
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     */
    private static final class MockEmptyIterableResponse
            implements Caller<File, WebApiHttpResponse> {
        @Override
        public WebApiHttpResponse call(final File file)
                throws UserCodeExecutionException {

            ObjectMapper mapper = new ObjectMapper();
            byte[] b = null;
            try {
                b = mapper.writeValueAsBytes(
                        mapper.readValue(file, FeatureCollection.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            return WebApiHttpResponse
                    .builder()
                    .setData(b)
                    .build();
        }
    }

    /**
     *
     * @throws IOException
     */
    @Test
    @Category(NeedsRunner.class)
    public void testGeoLandValueIsSerializable() throws IOException {
        List<File> files = new ArrayList<>();
        files.add(this.geoJson);
        Result<WebApiHttpResponse> resps = p
                .apply(Create.of(files))
                .apply(RequestResponseIO.of(
                        new MockEmptyIterableResponse(),
                        WebApiHttpResponseCoder.of()));

        PCollection<String> results = resps.getResponses()
                .apply(
                        ParDo.of(new GeoLandValueFn.FromWebApiHttpResponseFn()))
                .apply(MapElements
                        .into(TypeDescriptors.strings())
                        .via((GeoLandValue g) -> g.getLocationNumber()));

        PAssert.that(results).containsInAnyOrder("高円寺北２丁目７３０番２７");
        p.run().waitUntilFinish();
    }
}
