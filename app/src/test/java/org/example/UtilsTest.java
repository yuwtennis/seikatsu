package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.geojson.FeatureCollection;
import org.geojson.GeoJsonObject;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.*;

public class UtilsTest {

    File geoJson ;

    @Before
    public void setUp() {
        this.geoJson = new File("src/test/resources/geo.json");
    }

    @Test
    public void testAsFeatureCollection() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            FeatureCollection json = mapper.readValue(geoJson, FeatureCollection.class);
            String jsonStr = mapper.writeValueAsString(json);
            FeatureCollection result= Utils.asFeatureCollection(jsonStr);
            assertEquals(16002670, (int) result.getFeatures().getFirst().getProperty("point_id"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
