package com.example.javahammer.data;

public class WarhammerGameState {


    private BattleRound battleRound;
    private Phase phase;
    private PlayerTurn playerTurn;

    public BattleRound getBattleRound() {
        return battleRound;
    }

    public Phase getPhase() {
        return phase;
    }

    public PlayerTurn getPlayerTurn() {
        return playerTurn;
    }
}
