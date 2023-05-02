package org.example;

import org.javatuples.Tuple;

import java.util.HashMap;
import java.util.List;

public class AdjListe<T> extends AdjStruct {
    private final HashMap<Vertex<T>, List<Tuple>> map;

    public AdjListe() {
        this.map = new HashMap<>();
    }

    public AdjListe(HashMap<Vertex<T>, List<Tuple>> map) {
        this.map = map;
    }
}
