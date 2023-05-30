package org.example.games.puzzle;

import org.example.Edge;
import org.example.Vertex;
import org.javatuples.Pair;

import java.util.*;

public class PuzzleGame {

    public PuzzleGame() {
    }

    public void solve() {
        PuzzleGameState gameState = new PuzzleGameState(null);
//        PuzzleGameState gameState = PuzzleGameState.createRandomPuzzleStartState(15);

        PriorityQueue<PuzzleGameState> openList = new PriorityQueue<>(new Comparator<PuzzleGameState>() {
            @Override
            public int compare(PuzzleGameState state1, PuzzleGameState state2) {
                if (state1.getStarValue() > state2.getStarValue()) {
                    return 1;
                } else if (state1.getStarValue() < state2.getStarValue()) {
                    return -1;
                }

                return 0;
            }
        });

//        LinkedList<PuzzleGameState> openList = new LinkedList<>();

        openList.add(gameState);

        int testCnt = 0;

        while (!openList.isEmpty()) {
//            PuzzleGameState currentState = openList.removeFirst();
            PuzzleGameState currentState = openList.poll();

            testCnt++;

            if (PuzzleGameState.isTerminationState(currentState)) {
                System.out.println("Puzzle solved in " + testCnt + " steps!");
                break;
            }

            openList.addAll(currentState.findSuccessors());
        }
    }
}
