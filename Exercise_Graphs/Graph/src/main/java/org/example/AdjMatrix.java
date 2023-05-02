package org.example;

import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AdjMatrix extends AdjStruct {
    private final List<Vertex<Integer>> vertices;
    private final List<List<Edge>> edges;

    public AdjMatrix(List<Vertex<Integer>> vertices, List<Triplet<String, String, String>> edges) {
        this.vertices = vertices;
        this.edges = new ArrayList<>();

        for (int i = 0; i < vertices.size(); i++) {
            this.edges.add(i, new ArrayList<>(Collections.nCopies(vertices.size(), null)));
        }

        for (Triplet<String, String, String> edge : edges) {
            String row = edge.getValue0();
            String col = edge.getValue1();
            int rowIndex = vertices.indexOf(new Vertex<>(row, 0));
            int colIndex = vertices.indexOf(new Vertex<>(col, 0));

            this.edges.get(rowIndex).set(colIndex, new Edge(Float.parseFloat(edge.getValue2())));
        }
    }

    @Override
    public List<Vertex<Integer>> getNeighbors(Vertex<Integer> vertex) {
        List<Vertex<Integer>> neighbors = new LinkedList<>();
        int vertexIndex = vertices.indexOf(vertex);

        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(vertexIndex).get(i) != null) {
                neighbors.add(vertices.get(i));
            }
        }

        return neighbors;
    }
}
