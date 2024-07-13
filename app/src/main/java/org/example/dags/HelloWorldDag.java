package org.example.dags;

import org.apache.beam.sdk.Pipeline;
import org.apache.beam.sdk.transforms.Create;
import org.apache.beam.sdk.transforms.MapElements;
import org.apache.beam.sdk.transforms.SimpleFunction;
import org.apache.beam.sdk.values.TypeDescriptor;
import org.apache.beam.sdk.values.TypeDescriptors;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class HelloWorldDag implements Dag {

    static Logger LOG = LoggerFactory.getLogger(HelloWorldDag.class);

    public void process(Pipeline p) {

          p.apply(Create.of("Hello", "World"))
                  .apply(MapElements.into(TypeDescriptors.strings()).via((String s) -> s));
    }
}
