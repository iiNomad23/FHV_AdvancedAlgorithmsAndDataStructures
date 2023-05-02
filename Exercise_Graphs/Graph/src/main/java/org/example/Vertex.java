package org.example;

import java.util.Objects;

public class Vertex<T> {
    private final String name;
    private final T value;

    public Vertex(String name, T value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex<?> vertex = (Vertex<?>) o;
        return name.equals(vertex.name) && value.equals(vertex.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }
}
