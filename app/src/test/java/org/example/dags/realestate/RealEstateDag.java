package org.example.dags.realestate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.beam.sdk.testing.NeedsRunner;
import org.apache.beam.sdk.testing.TestPipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.values.PCollection;
import org.example.dags.realestate.landvalue.GeoLandValue;
import org.geojson.Feature;
import org.geojson.FeatureCollection;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RealEstateDag {
    File geoJson;

    @Rule
    public final transient TestPipeline p = TestPipeline.create();

    @Before
    public void setUp() {
        geoJson = new File("src/test/resources/geo.json");
    }


}
