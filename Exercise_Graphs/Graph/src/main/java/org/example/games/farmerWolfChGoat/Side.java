package org.example.games.farmerWolfChGoat;

public enum Side {
    LEFT,
    RIGHT;

    public Side inverted() {
        return this == RIGHT ? LEFT : RIGHT;
    }
}
