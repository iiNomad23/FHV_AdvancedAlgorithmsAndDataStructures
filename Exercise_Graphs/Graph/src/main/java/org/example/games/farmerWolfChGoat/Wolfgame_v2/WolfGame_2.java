package org.example.games.farmerWolfChGoat.Wolfgame_v2;

import java.util.Arrays;
import java.util.LinkedList;

public class WolfGame_2 {
    private enum Position {
        Left,
        Right;

        public Position getOpposite() {
            return values()[(ordinal() + 1) % 2];
        }
    }

    private static class GameState {
        public static final int IDX_FARMER = 0;
        public static final int IDX_WOLVE = 1;
        public static final int IDX_CH = 2;
        public static final int IDX_GOAT = 3;
        public Position[] _positions;
        public GameState _parentState;

        public GameState(GameState parentState) {
            _positions = new Position[4];
            Arrays.fill(_positions, Position.Left);
            _parentState = parentState;
        }

        private GameState cloneChanged(int changePosition) {
            GameState gs = new GameState(this);
            gs._positions[IDX_FARMER] = _positions[IDX_FARMER].getOpposite(); // farmer always changes position
            for (int i = 1; i < 4; i += 1) {
                gs._positions[i] = (i == changePosition) ? _positions[i].getOpposite() : _positions[i];
            }
            return gs;
        }

        public LinkedList<GameState> createSuccessors() {
            LinkedList<GameState> successors = new LinkedList<>();
            for (int i = 0; i < 4; i += 1) {
                GameState newNode = cloneChanged(i);
                if (!containsCycle(newNode)) {
                    successors.add(newNode);
                }
            }
            return successors;
        }

        public boolean containsCycle(GameState gs) {
            GameState ancestor = gs._parentState;
            while (ancestor != null) {
                if (isSamePosition(gs, ancestor)) {
                    return true;
                }
                ancestor = ancestor._parentState;
            }
            return false;
        }

        public boolean isSamePosition(GameState gs1, GameState gs2) {
            for (int i = 0; i < 4; i += 1) {
                if (!gs1._positions[i].equals(gs2._positions[i])) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public String toString() {
            return "GameState [_positions=" + Arrays.toString(_positions) + "]";
        }
    }

    private boolean isValidGameState(GameState gs) {
        if (gs._positions[GameState.IDX_FARMER] != gs._positions[GameState.IDX_GOAT]) {
            if ((gs._positions[GameState.IDX_WOLVE] == gs._positions[GameState.IDX_GOAT]) ||
                    (gs._positions[GameState.IDX_GOAT] == gs._positions[GameState.IDX_CH])) {
                return false;
            }
        }

        return true;
    }

    private boolean isTerminationState(GameState gs) {
        for (int i = 0; i < 4; i += 1) {
            if (gs._positions[i] != Position.Right) {
                return false;
            }
        }

        return true;
    }

    public void solve() {
        GameState root = new GameState(null);
        LinkedList<GameState> openList = new LinkedList<>();
        openList.add(root);

        while (!openList.isEmpty()) {
            GameState currentState = openList.removeFirst();

            System.out.println("Expanding node " + currentState);
            if (isTerminationState(currentState)) {
                System.out.println("Found SOLUTION!");
				return;
            }

            for (GameState followupState : currentState.createSuccessors()) {
                if (isValidGameState(followupState)) {
                    openList.add(followupState);
                } else {
                    System.out.println("Ignoring invalid gamestate " + followupState);
                }
            }
        }
    }
}
