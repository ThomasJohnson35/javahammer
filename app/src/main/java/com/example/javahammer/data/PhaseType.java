package com.example.javahammer.data;

public enum PhaseType {
    COMMAND, MOVE, SHOOT, CHARGE, FIGHT, MORALE;

    private static final PhaseType[] vals = values();

    public PhaseType next() {
        return vals[(this.ordinal() + 1) % vals.length];
    }
}

