package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.geojson.FeatureCollection;
import org.junit.Before;
import org.junit.Test;

public class UtilsTest {

    /**
     *
     */
    private File geoJson;

    /**
     *
     */
    private static final int POINT_ID = 16002670;

    /**
     *
     */
    @Before
    public void setUp() {
        this.geoJson = new File("src/test/resources/geo.json");
    }

    /**
     *
     */
    @Test
    public void testAsFeatureCollection() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            FeatureCollection json = mapper
                    .readValue(this.geoJson, FeatureCollection.class);
            String jsonStr = mapper.writeValueAsString(json);
            FeatureCollection result = Utils.asFeatureCollection(jsonStr);
            assertEquals(POINT_ID,
                    (int) result
                            .getFeatures()
                            .getFirst()
                            .getProperty("point_id"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
