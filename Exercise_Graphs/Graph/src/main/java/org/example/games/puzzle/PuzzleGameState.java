package org.example.games.puzzle;

import java.util.*;

public class PuzzleGameState {

    private PuzzleGameState parentState;
    private final Integer[][] puzzleField;
    private Integer depth;
    private Integer starValue;

    public PuzzleGameState(PuzzleGameState parent) {

        if (parent == null) {
//            puzzleField = new Integer[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, null}};
            puzzleField = new Integer[][]{{5, 8, 2}, {1, 3, null}, {4, 7, 6}};

            depth = 0;
            starValue = Integer.MAX_VALUE;
        } else {
            depth = parent.depth++;
            puzzleField = PuzzleGameState.deepCopyIntMatrix(parent.puzzleField);
        }

        parentState = parent;
    }

    public static PuzzleGameState createRandomPuzzleStartState(Integer successorSteps) {
        PuzzleGameState startState = new PuzzleGameState(null);

        for (int i = 0; i < successorSteps; i++) {
            List<PuzzleGameState> possibleStates = startState.findSuccessors();
            int random = new Random().nextInt(possibleStates.size());
            startState = possibleStates.get(random);
        }

        startState.parentState = null;
        startState.depth = 0;
        startState.starValue = Integer.MAX_VALUE;

        return startState;
    }

    public Integer getStarValue() {
        return starValue;
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

                nextState.starValue = nextState.depth + nextState.calculateWrongPositionCnt();

                puzzleGameStates.add(nextState);
            }
        }

        return puzzleGameStates;
    }

    private Integer calculateWrongPositionCnt() {
        Integer wrongPositionCnt = 0;

        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (puzzleField[y][x] == null) {
                    if (y != 2 || x != 2) {
                        wrongPositionCnt++;
                    }

                    continue;
                }

                if (puzzleField[y][x] != (3 * y + x + 1)) {
                    wrongPositionCnt++;
                }
            }
        }

        return wrongPositionCnt;
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

        return Arrays.deepEquals(gameState.puzzleField, puzzleFieldToCheck);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PuzzleGameState that = (PuzzleGameState) o;
        return Arrays.deepEquals(puzzleField, that.puzzleField);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(puzzleField);
    }

    @Override
    public String toString() {

        StringBuilder puzzleFieldStr = new StringBuilder();
        for (Integer[] array : puzzleField) {
            puzzleFieldStr.append(Arrays.toString(Arrays.stream(array).toArray()));
        }

        return "PuzzleGameState{" +
                "parentState=" + parentState +
                ", puzzleField=" + puzzleFieldStr +
                '}';
    }
}
