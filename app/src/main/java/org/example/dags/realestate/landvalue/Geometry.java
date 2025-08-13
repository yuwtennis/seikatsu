package org.example.dags.realestate.landvalue;

import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.extensions.avro.coders.AvroCoder;

@DefaultCoder(AvroCoder.class)
public class Geometry {
    /**
     *
     */
    private String type;

    /**
     *
     */
    private double[] coordinates;
}
