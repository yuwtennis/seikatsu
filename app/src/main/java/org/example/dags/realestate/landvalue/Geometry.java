package org.example.dags.realestate.landvalue;

import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.extensions.avro.coders.AvroCoder;

@DefaultCoder(AvroCoder.class)
public class Geometry {
    public String type;
    public double[] coordinates;
}
