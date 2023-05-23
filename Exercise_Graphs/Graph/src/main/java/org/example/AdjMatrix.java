package org.example;

import org.javatuples.Triplet;

import java.util.*;
import java.util.stream.Collectors;

public class AdjMatrix extends AdjStruct {
    private final List<Vertex<Integer>> vertices;
    private List<List<Edge>> edges;
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
                    System.out.print("Euler path does exist!\n");
                    eulerPathSearch(vertex);
                } else {
                    System.out.print("No euler path :(\n");
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
        System.out.println();
    }

    private void findEulerPathRecursive(Vertex<Integer> currentVertex, List<Edge> visitedEdges) {
        if (visitedEdges.size() == (edgeCount / 2)) {
            System.out.println(visitedEdges);
            System.out.println("end");
            return;
        }

        for (Edge edge : getEdgesFromVertex(currentVertex)) {
            if (edge != null && !visitedEdges.contains(edge)) {
                visitedEdges.add(edge);
                currentVertex = edge.getFrom().equals(currentVertex) ? edge.getTo() : edge.getFrom();
                findEulerPathRecursive(currentVertex, visitedEdges);
                currentVertex = edge.getFrom().equals(currentVertex) ? edge.getTo() : edge.getFrom();
                visitedEdges.remove(edge);
            }
        }
    }

    /**
     * Dijkstra
     */
    public HashMap<Vertex<Integer>, Vertex<Integer>> findSpanningTree(Vertex<Integer> start) {

        List<Vertex<Integer>> unvisitedVertices = new ArrayList<>(this.vertices);
        HashMap<Vertex<Integer>, Float> vertexValuesMap = new HashMap<>();
        HashMap<Vertex<Integer>, Vertex<Integer>> spanningTreeMap = new HashMap<>();

        for (Vertex<Integer> vertex : this.vertices) {
            vertexValuesMap.put(vertex, Float.MAX_VALUE);
        }

        vertexValuesMap.put(start, 0f);

        while (!unvisitedVertices.isEmpty()) {
            Vertex<Integer> minValueVertex = getMinValueVertex(vertexValuesMap, unvisitedVertices);

            unvisitedVertices.remove(minValueVertex);

            List<Edge> edgeList = getEdgesFromVertex(minValueVertex);

            for (Edge edge : edgeList) {
                if (edge == null) {
                    continue;
                }

                Float sumOfEdgeWeightAndMinVertex = vertexValuesMap.get(minValueVertex) + edge.getWeight();
                Vertex<Integer> neighborVertex = edge.getTo();

                if (unvisitedVertices.contains(neighborVertex) && sumOfEdgeWeightAndMinVertex < vertexValuesMap.get(neighborVertex)) {
                    vertexValuesMap.put(neighborVertex, sumOfEdgeWeightAndMinVertex);
                    spanningTreeMap.put(edge.getTo(), minValueVertex);
                }
            }
        }

        return spanningTreeMap;
    }

    /**
     * Prim
     */
    public HashMap<Vertex<Integer>, Vertex<Integer>> findMinimumSpanningTree_prim() {

        Vertex<Integer> start = findVertexWithLowestEdgeWeight();
        if (start == null) {
            System.out.println("WELL....");
        }

        List<Vertex<Integer>> unvisitedVertices = new ArrayList<>(this.vertices);

        HashMap<Vertex<Integer>, Float> vertexValuesMap = new HashMap<>();
        for (Vertex<Integer> vertex : this.vertices) {
            vertexValuesMap.put(vertex, Float.MAX_VALUE);
        }

        vertexValuesMap.put(start, 0f);

        HashMap<Vertex<Integer>, Vertex<Integer>> spanningTreeMap = new HashMap<>();

        while (!unvisitedVertices.isEmpty()) {
            Vertex<Integer> minValueVertex = getMinValueVertex(vertexValuesMap, unvisitedVertices);

            unvisitedVertices.remove(minValueVertex);

            List<Edge> edgeList = getEdgesFromVertex(minValueVertex);

            for (Edge edge : edgeList) {
                if (edge == null) {
                    continue;
                }

                Vertex<Integer> neighborVertex = edge.getTo();

                if (unvisitedVertices.contains(neighborVertex) && edge.getWeight() < vertexValuesMap.get(neighborVertex)) {
                    vertexValuesMap.put(neighborVertex, edge.getWeight());
                    spanningTreeMap.put(edge.getTo(), minValueVertex);
                }
            }
        }

        return spanningTreeMap;
    }

    /**
     * Kruskal
     */
    public List<Edge> findMinimumSpanningTree_kruskal() {
        LinkedList<Edge> edges = getAllEdges();
        edges.sort((a, b) -> Float.compare(a.getWeight(), b.getWeight()));

        // prepare sets
        List<HashSet<Vertex<Integer>>> vertexSetList = new ArrayList<>();
        for (Vertex<Integer> vertex : this.vertices) {
            HashSet<Vertex<Integer>> hashSet = new HashSet<>();
            hashSet.add(vertex);
            vertexSetList.add(hashSet);
        }

        // iterate over all edges and mark the important ones
        List<Edge> edgeList = new ArrayList<>();
        while (!edges.isEmpty()) {
            Edge edge = edges.removeFirst();

            Vertex<Integer> from = edge.getFrom();
            Vertex<Integer> to = edge.getTo();

            for (HashSet<Vertex<Integer>> vertexSet : vertexSetList) {
                if (vertexSet.contains(from)) {
                    if (!vertexSet.contains(to)) {

                        // search vertexSet from "to", unify the two vertexSets and delete one
                        final Iterator<HashSet<Vertex<Integer>>> setIteratorForDelete = vertexSetList.iterator();
                        while (setIteratorForDelete.hasNext()) {
                            HashSet<Vertex<Integer>> setToDelete = setIteratorForDelete.next();

                            if (!vertexSet.equals(setToDelete) && setToDelete.contains(to)) {
                                vertexSet.addAll(setToDelete);
                                setIteratorForDelete.remove();
                                break;
                            }
                        }

                        edgeList.add(edge);
                    }

                    break;
                }
            }
        }

        return edgeList;
    }

    private Vertex<Integer> getMinValueVertex(HashMap<Vertex<Integer>, Float> vertexValues, List<Vertex<Integer>> unvisitedVertices) {

        Float minValue = Float.MAX_VALUE;
        Vertex<Integer> vertex = null;

        for (Map.Entry<Vertex<Integer>, Float> entry : vertexValues.entrySet()) {
            if (unvisitedVertices.contains(entry.getKey()) && entry.getValue() <= minValue) {
                vertex = entry.getKey();
                minValue = entry.getValue();
            }
        }

        return vertex;
    }

    private Vertex<Integer> findVertexWithLowestEdgeWeight() {
        LinkedList<Edge> edges = getAllEdges();
        edges.sort((a, b) -> Float.compare(a.getWeight(), b.getWeight()));

        return edges.get(0).getTo();
    }

    private List<Edge> getEdgesFromVertex(Vertex<Integer> vertex) {
        return edges.get(vertices.indexOf(vertex));
    }

    private LinkedList<Edge> getAllEdges() {
        LinkedList<Edge> edges = new LinkedList<>();

        for (List<Edge> edgeList : this.edges) {
            edges.addAll(
                    edgeList
                            .stream()
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList())
            );
        }

        return edges;
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
