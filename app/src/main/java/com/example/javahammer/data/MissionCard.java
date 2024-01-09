package com.example.javahammer.data;

import com.example.javahammer.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class MissionCard {

    public String name;
    public int imageRes;
    HashSet<Alert> alertHashSet;
    public static ArrayList<MissionCard> missionPrimaryList = new ArrayList<>(
            Arrays.asList(
            new MissionCard("Scorched Earth",R.drawable.scorched_earth),
            new MissionCard("Sites of Power", R.drawable.sites_of_power,
                    new HashSet<Alert>(Arrays.asList(
                            new Alert("Primary Scoring",
                                    "Score 3VP for each Objective Marker you Control\nScore 3VP for each Objective Marker you Control",
                                    new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.COMMAND_END, Timing.BattleRound.NONE))))),
            new MissionCard("Purge The Foe", R.drawable.purge_the_foe,
                    new HashSet<Alert>(Arrays.asList(
                            new Alert("Primary Scoring",
                                    "Score 3VP for each Objective Marker you Control\nScore 3VP for each Objective Marker you Control",
                                    new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.COMMAND_END, Timing.BattleRound.NONE))))),
            new MissionCard("Take And Hold", R.drawable.take_and_hold,
                    new HashSet<Alert>(Arrays.asList(
                            new Alert("Primary Scoring",
                                    "Score 3VP for each Objective Marker you Control\nScore 3VP for each Objective Marker you Control",
                                    new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.COMMAND_END, Timing.BattleRound.NONE))))),
            new MissionCard("Supply Drop", R.drawable.supply_drop,
                    new HashSet<Alert>(Arrays.asList(
                            new Alert("Primary Scoring", "Score 3VP for each Objective Marker you Control\nScore 3VP for each Objective Marker you Control", new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.COMMAND_END, Timing.BattleRound.NONE))))),
            new MissionCard("The Ritual", R.drawable.the_ritual,
                    new HashSet<Alert>(Arrays.asList(
                            new Alert("Primary Scoring", "Score 3VP for each Objective Marker you Control\nScore 3VP for each Objective Marker you Control", new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.COMMAND_END, Timing.BattleRound.NONE))))),
            new MissionCard("Vital Ground", R.drawable.vital_ground,
                    new HashSet<Alert>(Arrays.asList(
                            new Alert("Primary Scoring", "Score 3VP for each Objective Marker you Control\nScore 3VP for each Objective Marker you Control", new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.COMMAND_END, Timing.BattleRound.NONE)))))
                        //new MissionCard("Priority Targets", R.drawable.priority_targets));
                        //new MissionCard("Deploy Servo-Skulls")
                )
                        );

    public static ArrayList<MissionCard> missionDeploymentList = new ArrayList<>(
            Arrays.asList(
            new MissionCard("Crucible of Battle", R.drawable.crucible_of_battle),
                        new MissionCard("Sweep and Clear", R.drawable.sweep_and_clear),
                        new MissionCard("Dawn of War", R.drawable.dawn_of_war)
                )
                        );

    public static ArrayList<MissionCard> missionRuleList = new ArrayList<>(
            Arrays.asList(
            new MissionCard("Chilling Rain", R.drawable.chilling_rain),
                        new MissionCard("Minefields", R.drawable.minefields),
                        new MissionCard("Scrambler Fields", R.drawable.scrambler_fields)
                )
                        );


    public MissionCard(String name, int res) {
        this.name = name;
        this.imageRes = res;
    }

    public MissionCard(String name, int res, HashSet<Alert> alert) {
        this.name = name;
        this.imageRes = res;
        this.alertHashSet = alert;
    }
}
