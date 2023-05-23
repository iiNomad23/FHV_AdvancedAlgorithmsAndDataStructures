package org.example.games.puzzle;

import java.util.LinkedList;

public class PuzzleGame {

    public PuzzleGame() {
    }

    public void solve() {
        PuzzleGameState gameState = new PuzzleGameState(null);

        gameState.shift(Direction.UP);
        gameState.shift(Direction.LEFT);

        LinkedList<PuzzleGameState> openList = new LinkedList<>();
        openList.add(gameState);

        while (!openList.isEmpty()) {
            PuzzleGameState currentState = openList.removeFirst();

            System.out.println("currentState: " + currentState.toString());
            if (PuzzleGameState.isTerminationState(currentState)) {
                System.out.println("Puzzle solved!");
                break;
            }

            openList.addAll(currentState.findSuccessors());
        }
    }
}
