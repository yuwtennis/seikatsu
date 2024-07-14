package org.example;

import org.example.dags.Dag;
import org.example.dags.HelloWorldDag;

public class DagDispatcher {

    /***
     * Dispatches with the correct dag to the app client
     * @param dagType
     * @return
     */
    public static Dag dispatch(DagType dagType) {
        Dag d = null;

        switch (dagType) {
            case DagType.HELLOWORLD:
                d = new HelloWorldDag();
            case DagType.REALESTATES:
                d = new HelloWorldDag();
        }

        return d;
    }
}
