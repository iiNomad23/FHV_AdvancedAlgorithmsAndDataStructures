package org.example.games.puzzle;

import java.util.Comparator;
import java.util.PriorityQueue;

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
