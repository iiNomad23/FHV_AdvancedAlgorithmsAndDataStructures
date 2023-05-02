package org.example;

import jdk.jshell.spi.ExecutionControl;

import java.util.List;

public abstract class AdjStruct {
    public List<Vertex<Integer>> getNeighbors(Vertex<Integer> vertex) throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("[AdjStruct] getNeighbors not implemented!");
    }

    public void print() throws ExecutionControl.NotImplementedException {
        throw new ExecutionControl.NotImplementedException("[AdjStruct] print not implemented!");
    }
}
