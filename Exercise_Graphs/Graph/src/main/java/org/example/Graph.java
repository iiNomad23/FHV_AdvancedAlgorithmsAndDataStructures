package org.example;

import jdk.jshell.spi.ExecutionControl;
import org.javatuples.Triplet;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Graph extends AdjStruct {
    private final AdjStruct adjStruct;

    private Graph(AdjStruct adjStruct) {
        this.adjStruct = adjStruct;
    }

    public static Graph create(File file) {

        Scanner myReader = null;

        try {
            myReader = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<Vertex<Integer>> vertices = new LinkedList<>();
        List<Triplet<String, String, String>> triplets = new LinkedList<>();

        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();

            if (data.charAt(0) == 'V') {
                data = data.substring(3, data.length() - 1);
                String[] vertexStrings = data.split(",");

                for (String vertexString : vertexStrings) {
                    vertices.add(new Vertex<>(vertexString, 0));
                }

            } else if (data.charAt(0) == 'E') {
                data = data.substring(3, data.length() - 1);
                String[] edgeStrings = data.split(";");

                for (String edgeString : edgeStrings) {
                    edgeString = edgeString.substring(1, edgeString.length() - 1);
                    String[] values = edgeString.split(",");
                    triplets.add(new Triplet<String, String, String>(values[0], values[1], values[2]));
                }
            }
        }

        return new Graph(new AdjMatrix(vertices, triplets));
    }

    public void traversal(Traversal traversalMethod) throws ExecutionControl.NotImplementedException {
        adjStruct.traversal(traversalMethod);
    }

    public List<Vertex<Integer>> getNeighbors(Vertex<Integer> vertex) throws ExecutionControl.NotImplementedException {
        return adjStruct.getNeighbors(vertex);
    }

    public void print() throws ExecutionControl.NotImplementedException {
        adjStruct.print();
    }
}