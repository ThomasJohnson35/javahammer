package com.example.javahammer.data;

import android.util.Pair;

import com.example.javahammer.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.function.Predicate;

public class Stratagem implements Serializable {

    private final EnumSet<PlayerTurn> playerTurns;
    private EnumSet<Phase> phases;
    public int commandPointCost;
    public String name;
    public stratagemType stratagemType;
    public String flavourText;
    public ArrayList<Timing> alertTiming;

    public String when;
    public String target;
    public String effect;
    public String restrictions;
    ArrayList<ArrayList<Model.ModelKeywords>> requirements;

    public Predicate<WarhammerGameState> predicate;
    public static ArrayList<Stratagem> coreStratagems = new ArrayList<Stratagem>() {{
        add(new Stratagem(
                "Command Re-roll",
                1,
                com.example.javahammer.data.stratagemType.BATTLE_TACTIC,
                "A great commander can bend even the vagaries of fate and fortune to their will, the better to ensure victory.",
                "",
                "",
                "In any phase, just after you have made a Hit roll, a Wound roll, a Damage roll, a saving throw, an Advance roll, a Charge roll, a Desperate Escape test, a Hazardous test, or just after you have rolled the dice to determine the number of attacks made with a weapon, for an attack, model or unit from your army.",
                "",
                EnumSet.allOf(Phase.class),
                EnumSet.allOf(PlayerTurn.class),
                new ArrayList<Timing>()
                )
        );
    }};
    public Stratagem(String name, int commandPointCost, stratagemType stratagemType, String flavourText, String when, String target, String effect, String restrictions, EnumSet<Phase> phases, EnumSet<PlayerTurn> playerTurns, ArrayList<Timing> alertTiming) {
        this.name = name;
        this.commandPointCost = commandPointCost;
        this.stratagemType = stratagemType;
        this.flavourText = flavourText;
        this.alertTiming = alertTiming;
        this.when = when;
        this.target = target;
        this.effect = effect;
        this.restrictions = restrictions;
        this.phases = phases;
        this.playerTurns = playerTurns;
    }

    public Stratagem(String name, int commandPointCost, stratagemType stratagemType, String flavourText, String when, String target, String effect, String restrictions, EnumSet<Phase> phases, EnumSet<PlayerTurn> playerTurns, Predicate<WarhammerGameState> predicate) {
        this.name = name;
        this.commandPointCost = commandPointCost;
        this.stratagemType = stratagemType;
        this.flavourText = flavourText;
        this.predicate = predicate;
        this.when = when;
        this.target = target;
        this.effect = effect;
        this.restrictions = restrictions;
        this.phases = phases;
        this.playerTurns = playerTurns;
    }

    public Pair<Integer, Integer> getPhasesImageRes() {

        if (phases.containsAll(EnumSet.allOf(Phase.class))) {
            return new Pair(R.drawable.check, null);
        } else if (phases.containsAll(EnumSet.of(Phase.MOVEMENT, Phase.CHARGE))) {
            return new Pair<>(R.drawable.movement_phase, R.drawable.charge_phase);
        } else if (phases.containsAll(EnumSet.of(Phase.SHOOTING, Phase.FIGHT))) {
            return new Pair<>(R.drawable.shooting_phase, R.drawable.fight_phase);
        } else if (phases.contains(Phase.COMMAND)) {
            return new Pair<>(R.drawable.command_phase, null);
        } else if (phases.contains(Phase.MOVEMENT)) {
            return new Pair<>(R.drawable.movement_phase, null);
        }
        else if (phases.contains(Phase.SHOOTING)) {
            return new Pair<>(R.drawable.shooting_phase, null);
        }
        else if (phases.contains(Phase.CHARGE)) {
            return new Pair<>(R.drawable.charge_phase, null);
        }
        else if (phases.contains(Phase.FIGHT)) {
            return new Pair<>(R.drawable.fight_phase, null);
        } else {
            return new Pair<>(null, null);
        }
    }

    public int getTurnColorTint() {

        if (playerTurns.containsAll(EnumSet.allOf(PlayerTurn.class))) {
            return R.color.sea_green;
        } else if (playerTurns.contains(EnumSet.of(PlayerTurn.OWNER))) {
            return R.color.astartes_blue;
        } else if (playerTurns.contains(EnumSet.of(PlayerTurn.OPPONENT))){
            return R.color.red;
        } else {
            throw new RuntimeException("Missing Player Turn");
        }
    }
}
