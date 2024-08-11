package org.example.dags.land;

import org.apache.beam.sdk.coders.DefaultCoder;
import org.apache.beam.sdk.extensions.avro.coders.AvroCoder;

/***
 * Land value publicly announced by MLIT , Japan
 * i.e. 地価公示 in Japanese
 */
@DefaultCoder(AvroCoder.class)
public class PublicLandValue {

}
