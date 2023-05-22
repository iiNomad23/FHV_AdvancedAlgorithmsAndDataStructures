package org.example;

import jdk.jshell.spi.ExecutionControl;
import org.example.games.farmerWolfChGoat.WolfGame;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ExecutionControl.NotImplementedException {

        AdjStruct struct = Graph.create(new File("src/main/resources/matrix.txt"));

        System.out.println();

        Vertex<Integer> v = new Vertex<>("A", 0);

        List<Vertex<Integer>> neighbors = struct.getNeighbors(v);
        for (Vertex<Integer> neighbor : neighbors) {
            System.out.println(neighbor.getName());
        }

//        System.out.println("AdjMatrix:");
//        struct.print();
//        System.out.println();
//
//
//        System.out.println("RecursiveDepthSearch:");
//        struct.traversal(Traversal.RecursiveDepthSearch);
//        System.out.println();
//        System.out.println("IterativeDepthSearch:");
//        struct.traversal(Traversal.IterativeDepthSearch);
//        System.out.println();
//        System.out.println("IterativeBreadthSearch:");
//        struct.traversal(Traversal.IterativeBreadthSearch);
//        System.out.println();
//        System.out.println("EulerPathSearch:");
//        struct.traversal(Traversal.EulerPathSearch);
//
//        System.out.println();
//        System.out.println("MinSpanningTee with kruskal:");
//
//        List<Edge> edgeList = struct.findMinimumSpanningTree();
//        for (Edge edge : edgeList) {
//            System.out.println(edge.toString());
//        }

        System.out.println(struct.findSpanningTree(v));

        System.out.println(struct.findMinimumSpanningTree_prim());

        System.out.println(struct.findMinimumSpanningTree_kruskal());

        WolfGame game = new WolfGame();
        game.solve_deltawoolf();
    }
}