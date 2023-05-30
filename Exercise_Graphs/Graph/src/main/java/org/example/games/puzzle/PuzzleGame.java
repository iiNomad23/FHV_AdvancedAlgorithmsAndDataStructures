package org.example.games.puzzle;

import org.example.Edge;
import org.example.Vertex;
import org.javatuples.Pair;

import java.util.*;

public class PuzzleGame {

    public PuzzleGame() {
    }

    public void solve() {
//        PuzzleGameState gameState = new PuzzleGameState(null);
        PuzzleGameState gameState = PuzzleGameState.createRandomPuzzleStartState(15);

        PriorityQueue<PuzzleGameState> openList = new PriorityQueue<>(Comparator.comparingInt(PuzzleGameState::calculateStarValue));
//        LinkedList<PuzzleGameState> openList = new LinkedList<>();

        openList.add(gameState);

        int steps = 0;
        while (!openList.isEmpty()) {
//            PuzzleGameState currentState = openList.removeFirst();
            PuzzleGameState currentState = openList.poll();

            steps++;

            if (PuzzleGameState.isTerminationState(currentState)) {
                System.out.println("Puzzle solved in " + steps + " steps!");
                break;
            }

            openList.addAll(currentState.findSuccessors());
        }
    }
}
