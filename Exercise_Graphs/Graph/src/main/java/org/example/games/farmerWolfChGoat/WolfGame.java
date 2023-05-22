package org.example.games.farmerWolfChGoat;

import org.javatuples.Pair;

import java.util.*;

public class WolfGame {
    HashMap<Entities, Side> entitiesSidesHashMap = new HashMap<>();
    List<HashMap<Entities, Side>> stateHistory = new ArrayList<>();

    List<Entities> moveables = new ArrayList<>(Arrays.asList(Entities.GOAT, Entities.WOLF, Entities.CH));

    public WolfGame() {
        this.entitiesSidesHashMap.put(Entities.FARMER, Side.LEFT);
        this.entitiesSidesHashMap.put(Entities.WOLF, Side.LEFT);
        this.entitiesSidesHashMap.put(Entities.CH, Side.LEFT);
        this.entitiesSidesHashMap.put(Entities.GOAT, Side.LEFT);
    }

    public void solve() {

        List<Pair<HashMap<Entities, Side>, List<HashMap<Entities, Side>>>> openStates = new ArrayList<>();
        openStates.add(new Pair<>(this.entitiesSidesHashMap, new ArrayList<>()));

        while (!openStates.isEmpty()) {
            Pair<HashMap<Entities, Side>, List<HashMap<Entities, Side>>> currStep = openStates.remove(0);

            for (Entities entity: moveables) {

                HashMap<Entities, Side> currState = new HashMap<>(currStep.getValue0());
                List<HashMap<Entities, Side>> currHistory = currStep.getValue1();

                currState.put(Entities.FARMER, currState.get(Entities.FARMER).inverted());
                currState.put(entity, currState.get(entity).inverted());

                if (this.isEndState(currState)) {
                    System.out.println("Game solved!");
                    return;
                }

                if (isValidState(currState) && !currHistory.contains(currState)) {
                    currHistory.add(currState);
                    openStates.add(new Pair<>(currState, currHistory));
                }
            }
        }
    }

    private HashMap<Entities, Side> tryMove(HashMap<Entities, Side> state) {
        this.stateHistory.add(state);

        HashMap<Entities, Side> tempState = this.stateHistory.get(this.stateHistory.size() - 1);

        for (Entities entity: moveables) {

            HashMap<Entities, Side> currState = new HashMap<>(tempState);

            currState.put(Entities.FARMER, currState.get(Entities.FARMER).inverted());
            currState.put(entity, currState.get(entity).inverted());

            if (this.isEndState(currState)) {
                System.out.println("Game solved!");
                return null;
            }

            if (isValidState(currState) && !this.stateHistory.contains(currState)) {
                return currState;
            }

        }

        return null;
    }

    private boolean isValidState(HashMap<Entities, Side> state) {
        boolean isValid = true;

        if (state.get(Entities.GOAT).equals(state.get(Entities.WOLF)) && !state.get(Entities.FARMER).equals(state.get(Entities.GOAT))) {
            isValid = false;
        } else if (state.get(Entities.GOAT).equals(state.get(Entities.CH)) && !state.get(Entities.FARMER).equals(state.get(Entities.GOAT))) {
            isValid = false;
        }

        return isValid;
    }

    public boolean isEndState(HashMap<Entities, Side> entitiesSidesHashMap) {
        return entitiesSidesHashMap
                .entrySet()
                .stream()
                .allMatch(entry -> entry.getValue().equals(Side.RIGHT));
    }
}
