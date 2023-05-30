package org.example;

import jdk.jshell.spi.ExecutionControl;
import org.example.games.farmerWolfChGoat.WolfGame;
import org.example.games.farmerWolfChGoat.Wolfgame_v2.WolfGame_2;
import org.example.games.puzzle.PuzzleGame;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws ExecutionControl.NotImplementedException {

        System.out.println();

//        AdjStruct struct = Graph.create(new File("src/main/resources/matrix.txt"));
//
//        Vertex<Integer> v = new Vertex<>("A", 0);
//
//        List<Vertex<Integer>> neighbors = struct.getNeighbors(v);
//        for (Vertex<Integer> neighbor : neighbors) {
//            System.out.println(neighbor.getName());
//        }
//
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
//
//        System.out.println(struct.findSpanningTree(v));
//
//        System.out.println(struct.findMinimumSpanningTree_prim());
//
//        System.out.println(struct.findMinimumSpanningTree_kruskal());
//
//        WolfGame wolfGame = new WolfGame();
//        wolfGame.solve_deltawoolf();
//
//        WolfGame_2 wolfGame = new WolfGame_2();
//        wolfGame.solve();

        PuzzleGame puzzleGame = new PuzzleGame();
        puzzleGame.solve();

        System.out.println();
    }
}