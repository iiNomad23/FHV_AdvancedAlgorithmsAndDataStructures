package org.example;

import jdk.jshell.spi.ExecutionControl;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ExecutionControl.NotImplementedException {

        AdjStruct struct = Graph.create(new File("src/main/resources/matrix.txt"));

        Vertex<Integer> v = new Vertex<>("B", 0);

        List<Vertex<Integer>> neighbors = struct.getNeighbors(v);

        for (Vertex<Integer> neighbor : neighbors) {
            System.out.println(neighbor.getName());
        }

        struct.print();

        struct.traversal(Traversal.RecursiveDepthSearch);
        System.out.println();
        struct.traversal(Traversal.IterativeDepthSearch);
        System.out.println();
        struct.traversal(Traversal.IterativeBreadth);
    }
}