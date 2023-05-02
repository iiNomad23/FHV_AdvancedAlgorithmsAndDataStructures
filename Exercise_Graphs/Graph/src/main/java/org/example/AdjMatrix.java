package org.example;

import org.javatuples.Triplet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class AdjMatrix extends AdjStruct {
    private final List<Vertex<Integer>> vertices;
    private final List<List<Edge>> edges;
    private final List<Vertex<Integer>> closedListVertices = new ArrayList<>();

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

    public void traversal(Traversal traversalMethod) {
        switch (traversalMethod) {
            case RecursiveDepthSearch:
                recursiveDepthSearch(vertices.get(0));
                for (Vertex<Integer> neighborVertex: closedListVertices) {
                    System.out.println(neighborVertex.getName());
                }
                break;

            case IterativeDepthSearch:
                iterativeDepthSearch();
                break;

            case IterativeBreadth:
                iterativeBreadth();
                break;
        }
    }

    private void recursiveDepthSearch(Vertex<Integer> vertex) {
        if (closedListVertices.contains(vertex)) {
            return;
        }

        closedListVertices.add(vertex);
        for (Vertex<Integer> neighborVertex: getNeighbors(vertex)) {
            recursiveDepthSearch(neighborVertex);
        }
    }

    private void iterativeDepthSearch() {

    }

    private void iterativeBreadth() {

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

    @Override
    public void print() {
        System.out.println("****************************************************************+");
        System.out.print(" *** |");
        vertices.forEach(vertex -> System.out.print("  " + vertex.getName() + "  |"));
        System.out.println();
        vertices.forEach(vertex -> {
            int vertexIndex = vertices.indexOf(vertex);

            System.out.print("  " + vertex.getName() + "  |");
            edges.get(vertexIndex).forEach(edge -> {
                if (edge == null) {
                    System.out.print(" -x- |");
                } else if (edge.getWeight() < 10) {
                    System.out.print("  " + edge.getWeight() + "|");
                } else {
                    System.out.print(" " + edge.getWeight() + "|");
                }
            });
            System.out.println();
        });
        System.out.println("****************************************************************+");
    }
}
