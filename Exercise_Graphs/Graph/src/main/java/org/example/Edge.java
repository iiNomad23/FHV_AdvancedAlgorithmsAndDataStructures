package org.example;

import java.util.Objects;

public class Edge {
    private final float weight;
    private Vertex<Integer> from;
    private Vertex<Integer> to;

    public Edge(float weight, Vertex<Integer> from, Vertex<Integer> to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }

    public float getWeight() {
        return weight;
    }

    public Vertex<Integer> getFrom() {
        return from;
    }

    public Vertex<Integer> getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Float.compare(edge.weight, weight) == 0 && Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight, from, to);
    }

    @Override
    public String toString() {
        return from + "->" + to;
    }
}
