package org.example.dags.realestate;

import java.io.File;
import org.apache.beam.sdk.testing.TestPipeline;
import org.junit.Before;
import org.junit.Rule;

public class RealEstateDagTest {
    File geoJson;

    @Rule
    public final transient TestPipeline p = TestPipeline.create();

    @Before
    public void setUp() {
        geoJson = new File("src/test/resources/geo.json");
    }


}
