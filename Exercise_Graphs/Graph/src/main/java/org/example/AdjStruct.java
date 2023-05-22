package org.example;

import jdk.jshell.spi.ExecutionControl;

import java.util.HashMap;
import java.util.List;

public abstract class AdjStruct {
    public void traversal(Traversal traversalMethod) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("[AdjStruct] traversal not implemented!");
    }

    public List<Vertex<Integer>> getNeighbors(Vertex<Integer> vertex) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("[AdjStruct] getNeighbors not implemented!");
    }

    public HashMap<Vertex<Integer>, Vertex<Integer>> findSpanningTree(Vertex<Integer> start) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("[AdjStruct] findMinimumSpanningTree not implemented!");
    }

    public HashMap<Vertex<Integer>, Vertex<Integer>> findMinimumSpanningTree_prim() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("[AdjStruct] findMinimumSpanningTree not implemented!");
    }

    public List<Edge> findMinimumSpanningTree_kruskal() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("[AdjStruct] findMinimumSpanningTree not implemented!");
    }

    public void print() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("[AdjStruct] print not implemented!");
    }
}
