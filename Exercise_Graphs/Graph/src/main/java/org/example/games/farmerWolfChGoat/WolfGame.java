package org.example.games.farmerWolfChGoat;

import org.javatuples.Pair;

import java.util.*;

public class WolfGame {
    HashMap<Entities, Side> entitiesSidesHashMap = new HashMap<>();
    List<HashMap<Entities, Side>> stateHistory = new ArrayList<>();

    List<Entities> moveables = new ArrayList<>(Arrays.asList(Entities.WOLF, Entities.GOAT, Entities.CH, null));

    public WolfGame() {
        this.entitiesSidesHashMap.put(Entities.FARMER, Side.LEFT);
        this.entitiesSidesHashMap.put(Entities.WOLF, Side.LEFT);
        this.entitiesSidesHashMap.put(Entities.GOAT, Side.LEFT);
        this.entitiesSidesHashMap.put(Entities.CH, Side.LEFT);
    }

    public void solve_deltawoolf() {

        List<Pair<HashMap<Entities, Side>, List<HashMap<Entities, Side>>>> openStates = new ArrayList<>();
        openStates.add(new Pair<>(this.entitiesSidesHashMap, new ArrayList<>()));

        while (!openStates.isEmpty()) {
            Pair<HashMap<Entities, Side>, List<HashMap<Entities, Side>>> currStep = openStates.remove(0);

            for (Entities entity : moveables) {

                HashMap<Entities, Side> currState = new HashMap<>(currStep.getValue0());
                List<HashMap<Entities, Side>> currHistory = currStep.getValue1();

                currState.put(Entities.FARMER, currState.get(Entities.FARMER).inverted());

                if (entity != null) {
                    currState.put(entity, currState.get(entity).inverted());
                }

                if (isEndState(currState)) {
                    for (HashMap<Entities, Side> map : currHistory) {
                        System.out.println(map);
                    }
                    System.out.println(currState);
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
        stateHistory.add(state);

        HashMap<Entities, Side> tempState = stateHistory.get(stateHistory.size() - 1);

        for (Entities entity : moveables) {

            HashMap<Entities, Side> currState = new HashMap<>(tempState);

            currState.put(Entities.FARMER, currState.get(Entities.FARMER).inverted());
            currState.put(entity, currState.get(entity).inverted());

            if (isEndState(currState)) {
                System.out.println("Game solved!");
                return null;
            }

            if (isValidState(currState) && !stateHistory.contains(currState)) {
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
