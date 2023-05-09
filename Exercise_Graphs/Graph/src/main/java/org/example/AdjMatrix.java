package org.example;

import org.javatuples.Triplet;

import java.util.*;

public class AdjMatrix extends AdjStruct {
    private final List<Vertex<Integer>> vertices;
    private final List<List<Edge>> edges;
    private List<Vertex<Integer>> closedListVertices;
    private int edgeCount = 0;

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

            this.edges.get(rowIndex).set(colIndex, new Edge(Float.parseFloat(edge.getValue2()), new Vertex<>(row, 0), new Vertex<>(col, 0)));
            edgeCount++;
        }
    }

    public void traversal(Traversal traversalMethod) {
        closedListVertices = new ArrayList<>();

        Vertex<Integer> vertex = vertices.get(0);

        switch (traversalMethod) {
            case RecursiveDepthSearch:
                recursiveDepthSearch(vertex);
                break;

            case IterativeDepthSearch:
                iterativeDepthSearch(vertex);
                break;

            case IterativeBreadthSearch:
                iterativeBreadthSearch(vertex);
                break;

            case EulerPathSearch:
                if (eulerPathExists(vertex)) {
                    System.out.print("Euler path does exist!");
                    eulerPathSearch(vertex);
                } else {
                    System.out.print("No euler path :(");
                    return;
                }
                break;
        }

        for (Vertex<Integer> neighborVertex : closedListVertices) {
            System.out.println(neighborVertex.getName());
        }
    }

    private void eulerPathSearch(Vertex<Integer> vertex) {
        List<Edge> visitedEdges = new LinkedList<>();

        this.findEulerPathRecursive(vertex, visitedEdges);
    }

    private void findEulerPathRecursive(Vertex<Integer> currentVertex, List<Edge> visitedEdges){
        if(visitedEdges.size() == (edgeCount/2)){
            System.out.println(visitedEdges);
            System.out.println("end");
            return;
        }

        for (Edge edge: getEdges(currentVertex)) {
            if (!visitedEdges.contains(edge) && edge != null) {
                visitedEdges.add(edge);
                currentVertex = edge.getFrom().equals(currentVertex) ? edge.getTo() : edge.getFrom();
                findEulerPathRecursive(currentVertex, visitedEdges);
                currentVertex = edge.getFrom().equals(currentVertex) ? edge.getTo() : edge.getFrom();
                visitedEdges.remove(edge);
            }
        }
    }

    private List<Edge> getEdges(Vertex<Integer> vertex){
        return edges.get(vertices.indexOf(vertex));
    }

    private boolean eulerPathExists(Vertex<Integer> vertex) {
        boolean eulerPathExists = true;
        int oddEdges = 0;

        Stack<Vertex<Integer>> openListVerticesStack = new Stack<>();
        openListVerticesStack.add(vertex);

        while (!openListVerticesStack.isEmpty()) {
            Vertex<Integer> currentVertex = openListVerticesStack.pop();

            if (!closedListVertices.contains(currentVertex)) {
                closedListVertices.add(currentVertex);

                List<Vertex<Integer>> vertexList = getNeighbors(currentVertex);

                if (vertexList.size() % 2 == 0) {
                    eulerPathExists = false;
                } else {
                    oddEdges += 1;

                    if (oddEdges > 2) {
                        break;
                    }
                }

                openListVerticesStack.addAll(getNeighbors(currentVertex));
            }
        }

        if (!eulerPathExists) {
            eulerPathExists = oddEdges <= 2;
        }

        return eulerPathExists;
    }

    private void recursiveDepthSearch(Vertex<Integer> vertex) {
        if (closedListVertices.contains(vertex)) {
            return;
        }

        closedListVertices.add(vertex);
        for (Vertex<Integer> neighborVertex : getNeighbors(vertex)) {
            recursiveDepthSearch(neighborVertex);
        }
    }

    private void iterativeDepthSearch(Vertex<Integer> vertex) {
        Stack<Vertex<Integer>> openListVerticesStack = new Stack<>();
        openListVerticesStack.add(vertex);

        while (!openListVerticesStack.isEmpty()) {
            Vertex<Integer> currentVertex = openListVerticesStack.pop();

            if (!closedListVertices.contains(currentVertex)) {
                closedListVertices.add(currentVertex);
                openListVerticesStack.addAll(getNeighbors(currentVertex));
            }
        }
    }

    private void iterativeBreadthSearch(Vertex<Integer> vertex) {
        List<Vertex<Integer>> openListVerticesHeap = new ArrayList<>();
        openListVerticesHeap.add(vertex);

        while (!openListVerticesHeap.isEmpty()) {
            Vertex<Integer> currentVertex = openListVerticesHeap.remove(0);

            if (!closedListVertices.contains(currentVertex)) {
                closedListVertices.add(currentVertex);
                openListVerticesHeap.addAll(getNeighbors(currentVertex));
            }
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
