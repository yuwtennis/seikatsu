package org.example;

import org.example.dags.Dag;
import org.example.dags.HelloWorldDag;

import java.util.Objects;

public class Dispatcher {

    /***
     * Dispatches with the correct dag to the app client
     * @param dagType
     * @return
     */
    public static Dag dispatch(DagType dagType) {
        Dag d = null;
        if (Objects.requireNonNull(dagType) == DagType.HELLOWORLD) {
            d = new HelloWorldDag();
        }

        return d;
    }
}
