package org.example.games.puzzle;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public class PuzzleGameState {

    private PuzzleGameState parentState;
    private Integer[][] puzzleField;

    public PuzzleGameState(PuzzleGameState parent) {

        if (parent == null) {
            puzzleField = new Integer[3][3];
            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    puzzleField[y][x] = 3 * y + x + 1;
                }
            }
            puzzleField[2][2] = null;
        } else {
            puzzleField = PuzzleGameState.deepCopyIntMatrix(parent.puzzleField);
        }

        parentState = parent;
    }

    public static Integer[][] deepCopyIntMatrix(Integer[][] input) {
        if (input == null) {
            return null;
        }

        Integer[][] result = new Integer[input.length][];
        for (int i = 0; i < input.length; i++) {
            result[i] = input[i].clone();
        }

        return result;
    }

    public LinkedList<PuzzleGameState> findSuccessors() {
        LinkedList<PuzzleGameState> puzzleGameStates = new LinkedList<>();

        for (Direction direction : Direction.values()) {
            PuzzleGameState nextState = clonedState(direction);

            if (!nextState.hasCycles(nextState)) {
                puzzleGameStates.add(nextState);
            }
        }

        return puzzleGameStates;
    }

    private PuzzleGameState clonedState(Direction direction) {
        PuzzleGameState newState = new PuzzleGameState(this);

        newState.shift(direction);

        return newState;
    }

    public void shift(Direction direction) {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (puzzleField[y][x] == null) {
                    switch (direction) {
                        case UP:
                            if (y > 0) {
                                puzzleField[y][x] = puzzleField[y - 1][x];
                                puzzleField[y - 1][x] = null;
                            }

                            return;

                        case RIGHT:
                            if (x < 2) {
                                puzzleField[y][x] = puzzleField[y][x + 1];
                                puzzleField[y][x + 1] = null;
                            }

                            return;

                        case DOWN:
                            if (y < 2) {
                                puzzleField[y][x] = puzzleField[y + 1][x];
                                puzzleField[y + 1][x] = null;
                            }

                            return;

                        case LEFT:
                            if (x > 0) {
                                puzzleField[y][x] = puzzleField[y][x - 1];
                                puzzleField[y][x - 1] = null;
                            }

                            return;
                    }
                }
            }
        }
    }

    private boolean hasCycles(PuzzleGameState currentState) {

        boolean cycleFound = false;

        if (this.parentState == null) {
            return cycleFound;
        }

        if (currentState.equals(this.parentState)) {
            cycleFound = true;
        } else {
            cycleFound = this.parentState.hasCycles(currentState);
        }

        return cycleFound;
    }

    public static boolean isTerminationState(PuzzleGameState gameState) {
        Integer[][] puzzleFieldToCheck = new Integer[3][3];

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                puzzleFieldToCheck[y][x] = 3 * y + x + 1;
            }
        }
        puzzleFieldToCheck[2][2] = null;

        for (int i = 0; i < gameState.puzzleField.length; i++) {
            if (!Arrays.equals(gameState.puzzleField[i], puzzleFieldToCheck[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuzzleGameState gameState = (PuzzleGameState) o;
        return Objects.equals(parentState, gameState.parentState) && Arrays.equals(puzzleField, gameState.puzzleField);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(parentState);
        result = 31 * result + Arrays.hashCode(puzzleField);
        return result;
    }

    @Override
    public String toString() {

//        for (Integer[] array: puzzleField)) {
//
//        }

        return "PuzzleGameState{" +
                "parentState=" + parentState +
                ", puzzleField=" + Arrays.toString(puzzleField) +
                '}';
    }
}