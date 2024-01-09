package com.example.javahammer.data;

import java.io.Serializable;

public class Timing implements Serializable {
    public PlayerTurn playerTurn;
    public TimingEnum timingEnum;
    public BattleRound battleRound;
    public enum TimingEnum {
        DECLARE_BATTLE_FORMATIONS, DEPLOY_ARMIES, PRE_BATTLE_RULES, BEGIN_BATTLE, COMMAND_START,
        COMMAND_MIDDLE, COMMAND_END, BATTLESHOCK, MOVEMENT_START, MOVEMENT_MIDDLE, MOVEMENT_END,
        REINFORCEMENTS, SHOOTING_START, SHOOTING_MIDDLE, SHOOTING_END, CHARGE_START, CHARGE_MIDDLE,
        CHARGE_END, FIGHT_START, FIGHT_MIDDLE, FIGHT_END;

        public static final TimingEnum[] vals = TimingEnum.values();

        public TimingEnum next() {
            return vals[(this.ordinal() + 1) % vals.length];
        }

    }

    public enum BattleRound {
        NONE, ONE, TWO, THREE, FOUR, FIVE;

        public static final BattleRound[] vals = BattleRound.values();

        public BattleRound next() {
            return vals[(this.ordinal() + 1) % vals.length];
        }
    }

    public enum PlayerTurn {
        NONE, PLAYER_TURN, OPPONENT_TURN,
    }


    public Timing(PlayerTurn playerTurn, TimingEnum timingEnum, BattleRound battleRound) {
        this.playerTurn = playerTurn;
        this.timingEnum = timingEnum;
        this.battleRound = battleRound;
    }

    public void next() {

        // If last phase in battleround
        if (timingEnum == TimingEnum.FIGHT_END) {

            // Go to top of battleround again
            timingEnum = TimingEnum.COMMAND_START;

            battleRound = battleRound.next();
        }

        timingEnum = timingEnum.next();

    }
}
