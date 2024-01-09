package com.example.javahammer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.javahammer.R;
import com.example.javahammer.data.Alert;
import com.example.javahammer.data.Timing;

import java.util.Arrays;
import java.util.HashSet;

public class GameManager extends AppCompatActivity {


   public Timing gameTiming;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Unpack roster

        // Unpack Mission Information

        // Default game alerts ex. CP Gain, Gambit on third round
        HashSet<Alert> gameAlerts = new HashSet<Alert>(
                Arrays.asList(
                        new Alert("CP GAIN", "Both Players Gain 1 CP", new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.COMMAND_START, Timing.BattleRound.NONE)),
                        new Alert("CP GAIN", "Both Players Gain 1 CP", new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.BATTLESHOCK, Timing.BattleRound.NONE)),
                        new Alert("CP GAIN", "Both Players Gain 1 CP", new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.COMMAND_START, Timing.BattleRound.NONE)),
                        new Alert("CP GAIN", "Both Players Gain 1 CP", new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.COMMAND_START, Timing.BattleRound.NONE))
                        )
        );

        gameTiming = new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.DECLARE_BATTLE_FORMATIONS, Timing.BattleRound.NONE);



        // First Page
        // Command Phase,

    }


    public void proceed() {

    }
}