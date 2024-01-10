package com.example.javahammer.data;

import android.app.GameState;
import android.content.res.Resources;

import com.example.javahammer.App;
import com.example.javahammer.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.function.Predicate;

public class Faction implements Serializable {
    private String name;
    private int imageRes;
    private ArrayList<Detachment> detachments;
    private int portraitImageRes;
    private ArrayList<Unit> units;
    private ArrayList<Ability> armyRules;
    private static ArrayList<Faction> factionList = new ArrayList<Faction>() {{
        add(new Faction(
                "Adeptus Astartes",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "Gladius Task Force",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "Artificer Armour",
                                        10,
                                        "Crafted by the Chapter’s finest artificers, this suit of armour provides superior protection.",
                                        "ADEPTUS ASTARTES model only. The bearer has a Save characteristic of 2+ and the Feel No Pain 5+ ability."
                                ));
                                add(new Enhancement(
                                        "The Honour Vehement",
                                        15,
                                        "This stanza is inscribed on thrice-blessed parchment and affixed to its bearers’ wargear with a purity seal, there to inspire them to heroic acts of martial prowess.",
                                        "ADEPTUS ASTARTES model only. Add 1 to the Attacks and Strength characteristics of the bearer’s melee weapons. While the bearer is under the effects of the Assault Doctrine, add 2 to the Attacks and Strength characteristics of the bearers melee weapons instead."
                                ));
                                add(new Enhancement(
                                        "Adept of the Codex",
                                        20,
                                        "An ardent student of the Codex Astartes, this commander epitomises its tactical genius, and the wisdom gleaned from its teachings guides their measured strategic responses amidst the fiercest battle.",
                                        "CAPTAIN model only. At the start of your Command phase, if the bearer is on the battlefield, instead of selecting a Combat Doctrine to be active for your army, you can select the Tactical Doctrine. If you do, until the start of your next Command phase, that Combat Doctrine is active for the bearer’s unit only, even if you have already selected that Combat Doctrine to be active for your army this battle."
                                ));
                                add(new Enhancement(
                                        "Fire Discipline",
                                        30,
                                        "This commander drills his warriors relentlessly; combined with the Adeptus Astartes’ incredible reflexes, they produce a devastating rate of fire.",
                                        "ADEPTUS ASTARTES model only. While the bearer is leading a unit, ranged weapons equipped by models in that unit have the [SUSTAINED HITS 1] ability. In addition, while the bearer’s unit is under the effects of the Devastator Doctrine, each time a model in that unit makes a ranged attack, a successful unmodified Hit roll of 5+ scores a Critical Hit."
                                ));

                            }},
                            new ArrayList<Stratagem>() {{
                                add(new Stratagem(
                                        "Armour of Contempt",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "The belligerency of the Adeptus Astartes, combined with their transhuman physiology, makes them unyielding foes to face.",
                                        "Your opponent’s Shooting phase or the Fight phase, just after an enemy unit has selected its targets.",
                                        "One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time an attack targets your unit, worsen the Armour Penetration characteristic of that attack by 1.",
                                        "",
                                        EnumSet.of(Phase.SHOOTING, Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new Predicate<WarhammerGameState>() {
                                            @Override
                                            public boolean test(WarhammerGameState warhammerGameState) {
                                                return true;
                                            }
                                        }
                                ));

                                add(new Stratagem(
                                        "Adaptive Strategy",
                                        1,
                                        stratagemType.STRATEGIC_PLOY,
                                        "The tenets of the Codex Astartes allow for unorthodox use of combat tactics and the employment of divergent strategic doctrines if doing so will lead to victory.",
                                        "Your Command phase.",
                                        "One ADEPTUS ASTARTES unit from your army.",
                                        "Select the Devastator Doctrine, Tactical Doctrine or Assault Doctrine. Until the start of your next Command phase, that Combat Doctrine is active for that unit instead of any other Combat Doctrine that is active for your army, even if you have already selected that doctrine this battle.",
                                        "",
                                        EnumSet.of(Phase.COMMAND),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.PLAYER_TURN, Timing.TimingEnum.COMMAND_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Only in Death Does Duty End",
                                        2,
                                        stratagemType.EPIC_DEED,
                                        "Imminent death does not prevent a Space Marine from enacting his final justice upon the enemies of the Imperium.",
                                        "Fight phase, just after an enemy unit has selected its targets.",
                                        " One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time a model in your unit is destroyed, if that model has not fought this phase, do not remove it from play. The destroyed model can fight after the attacking model’s unit has finished making its attacks, and is then removed from play.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER, PlayerTurn.OPPONENT),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Storm of Fire",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "There is no escaping the wrath of the Space Marines, and they use their weapons to bring swift death to their foes wherever they may hide.",
                                        " Your Shooting phase.",
                                        "One ADEPTUS ASTARTES unit from your army.",
                                        " Until the end of the phase, ranged weapons equipped by models in your unit have the [IGNORES COVER] ability. If your unit is under the effects of the Devastator Doctrine, until the end of the phase, improve the Armour Penetration characteristic of such weapons by 1 as well.",
                                        "",
                                        EnumSet.of(Phase.SHOOTING),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.PLAYER_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Honour the Chapter",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "The belligerency of the Adeptus Astartes, combined with their transhuman physiology, makes them unyielding foes to face.",
                                        "Your opponent’s Shooting phase or the Fight phase, just after an enemy unit has selected its targets.",
                                        "One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time an attack targets your unit, worsen the Armour Penetration characteristic of that attack by 1.",
                                        "",
                                        EnumSet.of(Phase.FIGHT, Phase.SHOOTING),
                                        EnumSet.of(PlayerTurn.OWNER, PlayerTurn.OPPONENT),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Squad Tactics",
                                        1,
                                        stratagemType.STRATEGIC_PLOY,
                                        "Space Marines know precisely when to give ground in order to leave their enemies floundering, before surging back into the fight and driving them from the field in disarray.",
                                        "Your opponent’s Movement phase, just after an enemy unit ends a Normal, Advance or Fall Back move.",
                                        "One ADEPTUS ASTARTES INFANTRY or ADEPTUS ASTARTES MOUNTED unit from your army that is within 9\" of the enemy unit that just ended that move.",
                                        "Your unit can make a Normal move of up to D6\", or a Normal move of up to 6\" instead if it is under the effects of the Tactical Doctrine.",
                                        "You cannot select a unit that is within Engagement Range of one or more enemy units.",
                                        EnumSet.of(Phase.MOVEMENT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.MOVEMENT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }},
                            new Ability("Combat Doctrines", App.getContext().getResources().getString(R.string.combat_doctrine_flavour_text), App.getContext().getResources().getString(R.string.combat_doctrine_desc), new Predicate<WarhammerGameState>() {
                                @Override
                                public boolean test(WarhammerGameState warhammerGameState) {
                                    return (warhammerGameState.getPhase() == Phase.COMMAND);
                                }
                            })
                    ));

                    add(new Detachment(
                            "Anvil Siege Force",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "Artificer Armour",
                                        10,
                                        "Crafted by the Chapter’s finest artificers, this suit of armour provides superior protection.",
                                        "ADEPTUS ASTARTES model only. The bearer has a Save characteristic of 2+ and the Feel No Pain 5+ ability."
                                ));
                                add(new Enhancement(
                                        "The Honour Vehement",
                                        15,
                                        "This stanza is inscribed on thrice-blessed parchment and affixed to its bearers’ wargear with a purity seal, there to inspire them to heroic acts of martial prowess.",
                                        "ADEPTUS ASTARTES model only. Add 1 to the Attacks and Strength characteristics of the bearer’s melee weapons. While the bearer is under the effects of the Assault Doctrine, add 2 to the Attacks and Strength characteristics of the bearers melee weapons instead."
                                ));
                                add(new Enhancement(
                                        "Adept of the Codex",
                                        20,
                                        "An ardent student of the Codex Astartes, this commander epitomises its tactical genius, and the wisdom gleaned from its teachings guides their measured strategic responses amidst the fiercest battle.",
                                        "CAPTAIN model only. At the start of your Command phase, if the bearer is on the battlefield, instead of selecting a Combat Doctrine to be active for your army, you can select the Tactical Doctrine. If you do, until the start of your next Command phase, that Combat Doctrine is active for the bearer’s unit only, even if you have already selected that Combat Doctrine to be active for your army this battle."
                                ));
                                add(new Enhancement(
                                        "Fire Discipline",
                                        30,
                                        "This commander drills his warriors relentlessly; combined with the Adeptus Astartes’ incredible reflexes, they produce a devastating rate of fire.",
                                        "ADEPTUS ASTARTES model only. While the bearer is leading a unit, ranged weapons equipped by models in that unit have the [SUSTAINED HITS 1] ability. In addition, while the bearer’s unit is under the effects of the Devastator Doctrine, each time a model in that unit makes a ranged attack, a successful unmodified Hit roll of 5+ scores a Critical Hit."
                                ));

                            }},
                            new ArrayList<Stratagem>() {{
                                add(new Stratagem(
                                        "Armour of Contempt",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "The belligerency of the Adeptus Astartes, combined with their transhuman physiology, makes them unyielding foes to face.",
                                        "Your opponent’s Shooting phase or the Fight phase, just after an enemy unit has selected its targets.",
                                        "One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time an attack targets your unit, worsen the Armour Penetration characteristic of that attack by 1.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Adaptive Strategy",
                                        1,
                                        stratagemType.STRATEGIC_PLOY,
                                        "Your Command phase.",
                                        "One ADEPTUS ASTARTES unit from your army.",
                                        "One ADEPTUS ASTARTES unit from your army.",
                                        "Select the Devastator Doctrine, Tactical Doctrine or Assault Doctrine. Until the start of your next Command phase, that Combat Doctrine is active for that unit instead of any other Combat Doctrine that is active for your army, even if you have already selected that doctrine this battle.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.PLAYER_TURN, Timing.TimingEnum.COMMAND_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Only in Death Does Duty End",
                                        2,
                                        stratagemType.EPIC_DEED,
                                        "Imminent death does not prevent a Space Marine from enacting his final justice upon the enemies of the Imperium.",
                                        "Fight phase, just after an enemy unit has selected its targets.",
                                        " One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time a model in your unit is destroyed, if that model has not fought this phase, do not remove it from play. The destroyed model can fight after the attacking model’s unit has finished making its attacks, and is then removed from play.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Storm of Fire",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "There is no escaping the wrath of the Space Marines, and they use their weapons to bring swift death to their foes wherever they may hide.",
                                        " Your Shooting phase.",
                                        "One ADEPTUS ASTARTES unit from your army.",
                                        " Until the end of the phase, ranged weapons equipped by models in your unit have the [IGNORES COVER] ability. If your unit is under the effects of the Devastator Doctrine, until the end of the phase, improve the Armour Penetration characteristic of such weapons by 1 as well.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.PLAYER_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Honour the Chapter",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "The belligerency of the Adeptus Astartes, combined with their transhuman physiology, makes them unyielding foes to face.",
                                        "Your opponent’s Shooting phase or the Fight phase, just after an enemy unit has selected its targets.",
                                        "One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time an attack targets your unit, worsen the Armour Penetration characteristic of that attack by 1.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Squad Tactics",
                                        1,
                                        stratagemType.STRATEGIC_PLOY,
                                        "Space Marines know precisely when to give ground in order to leave their enemies floundering, before surging back into the fight and driving them from the field in disarray.",
                                        "Your opponent’s Movement phase, just after an enemy unit ends a Normal, Advance or Fall Back move.",
                                        "One ADEPTUS ASTARTES INFANTRY or ADEPTUS ASTARTES MOUNTED unit from your army that is within 9\" of the enemy unit that just ended that move.",
                                        "Your unit can make a Normal move of up to D6\", or a Normal move of up to 6\" instead if it is under the effects of the Tactical Doctrine.",
                                        "You cannot select a unit that is within Engagement Range of one or more enemy units.",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.MOVEMENT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }},
                            new Ability("Combat Doctrines", App.getContext().getResources().getString(R.string.combat_doctrine_flavour_text), App.getContext().getResources().getString(R.string.combat_doctrine_desc), new Predicate<WarhammerGameState>() {
                                @Override
                                public boolean test(WarhammerGameState warhammerGameState) {
                                    return (warhammerGameState.getPhase() == Phase.COMMAND);
                                }
                            })
                    ));

                    add(new Detachment(
                            "Ironstrom Spearhead",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "Artificer Armour",
                                        10,
                                        "Crafted by the Chapter’s finest artificers, this suit of armour provides superior protection.",
                                        "ADEPTUS ASTARTES model only. The bearer has a Save characteristic of 2+ and the Feel No Pain 5+ ability."
                                ));
                                add(new Enhancement(
                                        "The Honour Vehement",
                                        15,
                                        "This stanza is inscribed on thrice-blessed parchment and affixed to its bearers’ wargear with a purity seal, there to inspire them to heroic acts of martial prowess.",
                                        "ADEPTUS ASTARTES model only. Add 1 to the Attacks and Strength characteristics of the bearer’s melee weapons. While the bearer is under the effects of the Assault Doctrine, add 2 to the Attacks and Strength characteristics of the bearers melee weapons instead."
                                ));
                                add(new Enhancement(
                                        "Adept of the Codex",
                                        20,
                                        "An ardent student of the Codex Astartes, this commander epitomises its tactical genius, and the wisdom gleaned from its teachings guides their measured strategic responses amidst the fiercest battle.",
                                        "CAPTAIN model only. At the start of your Command phase, if the bearer is on the battlefield, instead of selecting a Combat Doctrine to be active for your army, you can select the Tactical Doctrine. If you do, until the start of your next Command phase, that Combat Doctrine is active for the bearer’s unit only, even if you have already selected that Combat Doctrine to be active for your army this battle."
                                ));
                                add(new Enhancement(
                                        "Fire Discipline",
                                        30,
                                        "This commander drills his warriors relentlessly; combined with the Adeptus Astartes’ incredible reflexes, they produce a devastating rate of fire.",
                                        "ADEPTUS ASTARTES model only. While the bearer is leading a unit, ranged weapons equipped by models in that unit have the [SUSTAINED HITS 1] ability. In addition, while the bearer’s unit is under the effects of the Devastator Doctrine, each time a model in that unit makes a ranged attack, a successful unmodified Hit roll of 5+ scores a Critical Hit."
                                ));

                            }},
                            new ArrayList<Stratagem>() {{
                                add(new Stratagem(
                                        "Armour of Contempt",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "The belligerency of the Adeptus Astartes, combined with their transhuman physiology, makes them unyielding foes to face.",
                                        "Your opponent’s Shooting phase or the Fight phase, just after an enemy unit has selected its targets.",
                                        "One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time an attack targets your unit, worsen the Armour Penetration characteristic of that attack by 1.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Adaptive Strategy",
                                        1,
                                        stratagemType.STRATEGIC_PLOY,
                                        "Your Command phase.",
                                        "One ADEPTUS ASTARTES unit from your army.",
                                        "One ADEPTUS ASTARTES unit from your army.",
                                        "Select the Devastator Doctrine, Tactical Doctrine or Assault Doctrine. Until the start of your next Command phase, that Combat Doctrine is active for that unit instead of any other Combat Doctrine that is active for your army, even if you have already selected that doctrine this battle.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.PLAYER_TURN, Timing.TimingEnum.COMMAND_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Only in Death Does Duty End",
                                        2,
                                        stratagemType.EPIC_DEED,
                                        "Imminent death does not prevent a Space Marine from enacting his final justice upon the enemies of the Imperium.",
                                        "Fight phase, just after an enemy unit has selected its targets.",
                                        " One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time a model in your unit is destroyed, if that model has not fought this phase, do not remove it from play. The destroyed model can fight after the attacking model’s unit has finished making its attacks, and is then removed from play.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Storm of Fire",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "There is no escaping the wrath of the Space Marines, and they use their weapons to bring swift death to their foes wherever they may hide.",
                                        " Your Shooting phase.",
                                        "One ADEPTUS ASTARTES unit from your army.",
                                        " Until the end of the phase, ranged weapons equipped by models in your unit have the [IGNORES COVER] ability. If your unit is under the effects of the Devastator Doctrine, until the end of the phase, improve the Armour Penetration characteristic of such weapons by 1 as well.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.PLAYER_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Honour the Chapter",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "The belligerency of the Adeptus Astartes, combined with their transhuman physiology, makes them unyielding foes to face.",
                                        "Your opponent’s Shooting phase or the Fight phase, just after an enemy unit has selected its targets.",
                                        "One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time an attack targets your unit, worsen the Armour Penetration characteristic of that attack by 1.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Squad Tactics",
                                        1,
                                        stratagemType.STRATEGIC_PLOY,
                                        "Space Marines know precisely when to give ground in order to leave their enemies floundering, before surging back into the fight and driving them from the field in disarray.",
                                        "Your opponent’s Movement phase, just after an enemy unit ends a Normal, Advance or Fall Back move.",
                                        "One ADEPTUS ASTARTES INFANTRY or ADEPTUS ASTARTES MOUNTED unit from your army that is within 9\" of the enemy unit that just ended that move.",
                                        "Your unit can make a Normal move of up to D6\", or a Normal move of up to 6\" instead if it is under the effects of the Tactical Doctrine.",
                                        "You cannot select a unit that is within Engagement Range of one or more enemy units.",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.MOVEMENT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }},
                            new Ability("Combat Doctrines", App.getContext().getResources().getString(R.string.combat_doctrine_flavour_text), App.getContext().getResources().getString(R.string.combat_doctrine_desc), new Predicate<WarhammerGameState>() {
                                @Override
                                public boolean test(WarhammerGameState warhammerGameState) {
                                    return (warhammerGameState.getPhase() == Phase.COMMAND);
                                }
                            })
                    ));

                    add(new Detachment(
                            "Firestorm Assault Force",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "Artificer Armour",
                                        10,
                                        "Crafted by the Chapter’s finest artificers, this suit of armour provides superior protection.",
                                        "ADEPTUS ASTARTES model only. The bearer has a Save characteristic of 2+ and the Feel No Pain 5+ ability."
                                ));
                                add(new Enhancement(
                                        "The Honour Vehement",
                                        15,
                                        "This stanza is inscribed on thrice-blessed parchment and affixed to its bearers’ wargear with a purity seal, there to inspire them to heroic acts of martial prowess.",
                                        "ADEPTUS ASTARTES model only. Add 1 to the Attacks and Strength characteristics of the bearer’s melee weapons. While the bearer is under the effects of the Assault Doctrine, add 2 to the Attacks and Strength characteristics of the bearers melee weapons instead."
                                ));
                                add(new Enhancement(
                                        "Adept of the Codex",
                                        20,
                                        "An ardent student of the Codex Astartes, this commander epitomises its tactical genius, and the wisdom gleaned from its teachings guides their measured strategic responses amidst the fiercest battle.",
                                        "CAPTAIN model only. At the start of your Command phase, if the bearer is on the battlefield, instead of selecting a Combat Doctrine to be active for your army, you can select the Tactical Doctrine. If you do, until the start of your next Command phase, that Combat Doctrine is active for the bearer’s unit only, even if you have already selected that Combat Doctrine to be active for your army this battle."
                                ));
                                add(new Enhancement(
                                        "Fire Discipline",
                                        30,
                                        "This commander drills his warriors relentlessly; combined with the Adeptus Astartes’ incredible reflexes, they produce a devastating rate of fire.",
                                        "ADEPTUS ASTARTES model only. While the bearer is leading a unit, ranged weapons equipped by models in that unit have the [SUSTAINED HITS 1] ability. In addition, while the bearer’s unit is under the effects of the Devastator Doctrine, each time a model in that unit makes a ranged attack, a successful unmodified Hit roll of 5+ scores a Critical Hit."
                                ));

                            }},
                            new ArrayList<Stratagem>() {{
                                add(new Stratagem(
                                        "Armour of Contempt",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "The belligerency of the Adeptus Astartes, combined with their transhuman physiology, makes them unyielding foes to face.",
                                        "Your opponent’s Shooting phase or the Fight phase, just after an enemy unit has selected its targets.",
                                        "One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time an attack targets your unit, worsen the Armour Penetration characteristic of that attack by 1.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Adaptive Strategy",
                                        1,
                                        stratagemType.STRATEGIC_PLOY,
                                        "Your Command phase.",
                                        "One ADEPTUS ASTARTES unit from your army.",
                                        "One ADEPTUS ASTARTES unit from your army.",
                                        "Select the Devastator Doctrine, Tactical Doctrine or Assault Doctrine. Until the start of your next Command phase, that Combat Doctrine is active for that unit instead of any other Combat Doctrine that is active for your army, even if you have already selected that doctrine this battle.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.PLAYER_TURN, Timing.TimingEnum.COMMAND_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Only in Death Does Duty End",
                                        2,
                                        stratagemType.EPIC_DEED,
                                        "Imminent death does not prevent a Space Marine from enacting his final justice upon the enemies of the Imperium.",
                                        "Fight phase, just after an enemy unit has selected its targets.",
                                        " One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time a model in your unit is destroyed, if that model has not fought this phase, do not remove it from play. The destroyed model can fight after the attacking model’s unit has finished making its attacks, and is then removed from play.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Storm of Fire",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "There is no escaping the wrath of the Space Marines, and they use their weapons to bring swift death to their foes wherever they may hide.",
                                        " Your Shooting phase.",
                                        "One ADEPTUS ASTARTES unit from your army.",
                                        " Until the end of the phase, ranged weapons equipped by models in your unit have the [IGNORES COVER] ability. If your unit is under the effects of the Devastator Doctrine, until the end of the phase, improve the Armour Penetration characteristic of such weapons by 1 as well.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.PLAYER_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Honour the Chapter",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "The belligerency of the Adeptus Astartes, combined with their transhuman physiology, makes them unyielding foes to face.",
                                        "Your opponent’s Shooting phase or the Fight phase, just after an enemy unit has selected its targets.",
                                        "One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time an attack targets your unit, worsen the Armour Penetration characteristic of that attack by 1.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Squad Tactics",
                                        1,
                                        stratagemType.STRATEGIC_PLOY,
                                        "Space Marines know precisely when to give ground in order to leave their enemies floundering, before surging back into the fight and driving them from the field in disarray.",
                                        "Your opponent’s Movement phase, just after an enemy unit ends a Normal, Advance or Fall Back move.",
                                        "One ADEPTUS ASTARTES INFANTRY or ADEPTUS ASTARTES MOUNTED unit from your army that is within 9\" of the enemy unit that just ended that move.",
                                        "Your unit can make a Normal move of up to D6\", or a Normal move of up to 6\" instead if it is under the effects of the Tactical Doctrine.",
                                        "You cannot select a unit that is within Engagement Range of one or more enemy units.",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.MOVEMENT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }},
                            new Ability("Combat Doctrines", App.getContext().getResources().getString(R.string.combat_doctrine_flavour_text), App.getContext().getResources().getString(R.string.combat_doctrine_desc), new Predicate<WarhammerGameState>() {
                                @Override
                                public boolean test(WarhammerGameState warhammerGameState) {
                                    return (warhammerGameState.getPhase() == Phase.COMMAND);
                                }
                            })
                    ));

                    add(new Detachment(
                            "Stormlance Task Force",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "Artificer Armour",
                                        10,
                                        "Crafted by the Chapter’s finest artificers, this suit of armour provides superior protection.",
                                        "ADEPTUS ASTARTES model only. The bearer has a Save characteristic of 2+ and the Feel No Pain 5+ ability."
                                ));
                                add(new Enhancement(
                                        "The Honour Vehement",
                                        15,
                                        "This stanza is inscribed on thrice-blessed parchment and affixed to its bearers’ wargear with a purity seal, there to inspire them to heroic acts of martial prowess.",
                                        "ADEPTUS ASTARTES model only. Add 1 to the Attacks and Strength characteristics of the bearer’s melee weapons. While the bearer is under the effects of the Assault Doctrine, add 2 to the Attacks and Strength characteristics of the bearers melee weapons instead."
                                ));
                                add(new Enhancement(
                                        "Adept of the Codex",
                                        20,
                                        "An ardent student of the Codex Astartes, this commander epitomises its tactical genius, and the wisdom gleaned from its teachings guides their measured strategic responses amidst the fiercest battle.",
                                        "CAPTAIN model only. At the start of your Command phase, if the bearer is on the battlefield, instead of selecting a Combat Doctrine to be active for your army, you can select the Tactical Doctrine. If you do, until the start of your next Command phase, that Combat Doctrine is active for the bearer’s unit only, even if you have already selected that Combat Doctrine to be active for your army this battle."
                                ));
                                add(new Enhancement(
                                        "Fire Discipline",
                                        30,
                                        "This commander drills his warriors relentlessly; combined with the Adeptus Astartes’ incredible reflexes, they produce a devastating rate of fire.",
                                        "ADEPTUS ASTARTES model only. While the bearer is leading a unit, ranged weapons equipped by models in that unit have the [SUSTAINED HITS 1] ability. In addition, while the bearer’s unit is under the effects of the Devastator Doctrine, each time a model in that unit makes a ranged attack, a successful unmodified Hit roll of 5+ scores a Critical Hit."
                                ));

                            }},
                            new ArrayList<Stratagem>() {{
                                add(new Stratagem(
                                        "Armour of Contempt",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "The belligerency of the Adeptus Astartes, combined with their transhuman physiology, makes them unyielding foes to face.",
                                        "Your opponent’s Shooting phase or the Fight phase, just after an enemy unit has selected its targets.",
                                        "One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time an attack targets your unit, worsen the Armour Penetration characteristic of that attack by 1.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Adaptive Strategy",
                                        1,
                                        stratagemType.STRATEGIC_PLOY,
                                        "Your Command phase.",
                                        "One ADEPTUS ASTARTES unit from your army.",
                                        "One ADEPTUS ASTARTES unit from your army.",
                                        "Select the Devastator Doctrine, Tactical Doctrine or Assault Doctrine. Until the start of your next Command phase, that Combat Doctrine is active for that unit instead of any other Combat Doctrine that is active for your army, even if you have already selected that doctrine this battle.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.PLAYER_TURN, Timing.TimingEnum.COMMAND_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Only in Death Does Duty End",
                                        2,
                                        stratagemType.EPIC_DEED,
                                        "Imminent death does not prevent a Space Marine from enacting his final justice upon the enemies of the Imperium.",
                                        "Fight phase, just after an enemy unit has selected its targets.",
                                        " One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time a model in your unit is destroyed, if that model has not fought this phase, do not remove it from play. The destroyed model can fight after the attacking model’s unit has finished making its attacks, and is then removed from play.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Storm of Fire",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "There is no escaping the wrath of the Space Marines, and they use their weapons to bring swift death to their foes wherever they may hide.",
                                        " Your Shooting phase.",
                                        "One ADEPTUS ASTARTES unit from your army.",
                                        " Until the end of the phase, ranged weapons equipped by models in your unit have the [IGNORES COVER] ability. If your unit is under the effects of the Devastator Doctrine, until the end of the phase, improve the Armour Penetration characteristic of such weapons by 1 as well.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.PLAYER_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Honour the Chapter",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "The belligerency of the Adeptus Astartes, combined with their transhuman physiology, makes them unyielding foes to face.",
                                        "Your opponent’s Shooting phase or the Fight phase, just after an enemy unit has selected its targets.",
                                        "One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time an attack targets your unit, worsen the Armour Penetration characteristic of that attack by 1.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Squad Tactics",
                                        1,
                                        stratagemType.STRATEGIC_PLOY,
                                        "Space Marines know precisely when to give ground in order to leave their enemies floundering, before surging back into the fight and driving them from the field in disarray.",
                                        "Your opponent’s Movement phase, just after an enemy unit ends a Normal, Advance or Fall Back move.",
                                        "One ADEPTUS ASTARTES INFANTRY or ADEPTUS ASTARTES MOUNTED unit from your army that is within 9\" of the enemy unit that just ended that move.",
                                        "Your unit can make a Normal move of up to D6\", or a Normal move of up to 6\" instead if it is under the effects of the Tactical Doctrine.",
                                        "You cannot select a unit that is within Engagement Range of one or more enemy units.",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.MOVEMENT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }},
                            new Ability("Combat Doctrines", App.getContext().getResources().getString(R.string.combat_doctrine_flavour_text), App.getContext().getResources().getString(R.string.combat_doctrine_desc), new Predicate<WarhammerGameState>() {
                                @Override
                                public boolean test(WarhammerGameState warhammerGameState) {
                                    return (warhammerGameState.getPhase() == Phase.COMMAND);
                                }
                            })
                    ));

                    add(new Detachment(
                            "Vanguard Spearhead",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "Artificer Armour",
                                        10,
                                        "Crafted by the Chapter’s finest artificers, this suit of armour provides superior protection.",
                                        "ADEPTUS ASTARTES model only. The bearer has a Save characteristic of 2+ and the Feel No Pain 5+ ability."
                                ));
                                add(new Enhancement(
                                        "The Honour Vehement",
                                        15,
                                        "This stanza is inscribed on thrice-blessed parchment and affixed to its bearers’ wargear with a purity seal, there to inspire them to heroic acts of martial prowess.",
                                        "ADEPTUS ASTARTES model only. Add 1 to the Attacks and Strength characteristics of the bearer’s melee weapons. While the bearer is under the effects of the Assault Doctrine, add 2 to the Attacks and Strength characteristics of the bearers melee weapons instead."
                                ));
                                add(new Enhancement(
                                        "Adept of the Codex",
                                        20,
                                        "An ardent student of the Codex Astartes, this commander epitomises its tactical genius, and the wisdom gleaned from its teachings guides their measured strategic responses amidst the fiercest battle.",
                                        "CAPTAIN model only. At the start of your Command phase, if the bearer is on the battlefield, instead of selecting a Combat Doctrine to be active for your army, you can select the Tactical Doctrine. If you do, until the start of your next Command phase, that Combat Doctrine is active for the bearer’s unit only, even if you have already selected that Combat Doctrine to be active for your army this battle."
                                ));
                                add(new Enhancement(
                                        "Fire Discipline",
                                        30,
                                        "This commander drills his warriors relentlessly; combined with the Adeptus Astartes’ incredible reflexes, they produce a devastating rate of fire.",
                                        "ADEPTUS ASTARTES model only. While the bearer is leading a unit, ranged weapons equipped by models in that unit have the [SUSTAINED HITS 1] ability. In addition, while the bearer’s unit is under the effects of the Devastator Doctrine, each time a model in that unit makes a ranged attack, a successful unmodified Hit roll of 5+ scores a Critical Hit."
                                ));

                            }},
                            new ArrayList<Stratagem>() {{
                                add(new Stratagem(
                                        "Armour of Contempt",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "The belligerency of the Adeptus Astartes, combined with their transhuman physiology, makes them unyielding foes to face.",
                                        "Your opponent’s Shooting phase or the Fight phase, just after an enemy unit has selected its targets.",
                                        "One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time an attack targets your unit, worsen the Armour Penetration characteristic of that attack by 1.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Adaptive Strategy",
                                        1,
                                        stratagemType.STRATEGIC_PLOY,
                                        "Your Command phase.",
                                        "One ADEPTUS ASTARTES unit from your army.",
                                        "One ADEPTUS ASTARTES unit from your army.",
                                        "Select the Devastator Doctrine, Tactical Doctrine or Assault Doctrine. Until the start of your next Command phase, that Combat Doctrine is active for that unit instead of any other Combat Doctrine that is active for your army, even if you have already selected that doctrine this battle.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.PLAYER_TURN, Timing.TimingEnum.COMMAND_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Only in Death Does Duty End",
                                        2,
                                        stratagemType.EPIC_DEED,
                                        "Imminent death does not prevent a Space Marine from enacting his final justice upon the enemies of the Imperium.",
                                        "Fight phase, just after an enemy unit has selected its targets.",
                                        " One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time a model in your unit is destroyed, if that model has not fought this phase, do not remove it from play. The destroyed model can fight after the attacking model’s unit has finished making its attacks, and is then removed from play.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Storm of Fire",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "There is no escaping the wrath of the Space Marines, and they use their weapons to bring swift death to their foes wherever they may hide.",
                                        " Your Shooting phase.",
                                        "One ADEPTUS ASTARTES unit from your army.",
                                        " Until the end of the phase, ranged weapons equipped by models in your unit have the [IGNORES COVER] ability. If your unit is under the effects of the Devastator Doctrine, until the end of the phase, improve the Armour Penetration characteristic of such weapons by 1 as well.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.PLAYER_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Honour the Chapter",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "The belligerency of the Adeptus Astartes, combined with their transhuman physiology, makes them unyielding foes to face.",
                                        "Your opponent’s Shooting phase or the Fight phase, just after an enemy unit has selected its targets.",
                                        "One ADEPTUS ASTARTES unit from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, each time an attack targets your unit, worsen the Armour Penetration characteristic of that attack by 1.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Squad Tactics",
                                        1,
                                        stratagemType.STRATEGIC_PLOY,
                                        "Space Marines know precisely when to give ground in order to leave their enemies floundering, before surging back into the fight and driving them from the field in disarray.",
                                        "Your opponent’s Movement phase, just after an enemy unit ends a Normal, Advance or Fall Back move.",
                                        "One ADEPTUS ASTARTES INFANTRY or ADEPTUS ASTARTES MOUNTED unit from your army that is within 9\" of the enemy unit that just ended that move.",
                                        "Your unit can make a Normal move of up to D6\", or a Normal move of up to 6\" instead if it is under the effects of the Tactical Doctrine.",
                                        "You cannot select a unit that is within Engagement Range of one or more enemy units.",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.MOVEMENT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }},
                            new Ability("Combat Doctrines", App.getContext().getResources().getString(R.string.combat_doctrine_flavour_text), App.getContext().getResources().getString(R.string.combat_doctrine_desc), new Predicate<WarhammerGameState>() {
                                @Override
                                public boolean test(WarhammerGameState warhammerGameState) {
                                    return (warhammerGameState.getPhase() == Phase.COMMAND);
                                }
                            })
                    ));
                }},
                new ArrayList<Unit>(){{

                    // Characters

                    add(new Unit(
                            "Intercessor Squad",
                            R.drawable.intercessor,

                            new ArrayList<Ability>() {{
                                add(new Ability("Broad Spectrum Data-tether", "Each time you select this unit as the target of a Stratagem, roll one D6: on a 5+, "));
                            }},
                            new ArrayList<Ability>() {{
                            }},
                            new ArrayList<ModelComposition>() {{
                                add(new ModelComposition(
                                        new Model("Intercessor Sergeant", new Statline( 6, 4, 3, 2, 6, 2),
                                                new ArrayList<>(Arrays.asList(
                                                        Weapon.armory.get(Weapon.WeaponEnums.BOLT_PISTOL),
                                                        Weapon.armory.get(Weapon.WeaponEnums.BOLT_RIFLE),
                                                        Weapon.armory.get(Weapon.WeaponEnums.CLOSE_COMBAT_WEAPON))),
                                                EnumSet.noneOf(Model.ModelKeywords.class)),
                                        1, 1));
                                add(new ModelComposition(
                                        new Model("Intercessor",new Statline( 6, 4, 3, 2, 6, 2),
                                                new ArrayList<>(Arrays.asList(
                                                        Weapon.armory.get(Weapon.WeaponEnums.BOLT_PISTOL),
                                                        Weapon.armory.get(Weapon.WeaponEnums.BOLT_RIFLE),
                                                        Weapon.armory.get(Weapon.WeaponEnums.CLOSE_COMBAT_WEAPON))),
                                                EnumSet.noneOf(Model.ModelKeywords.class)),
                                        4, 9));
                            }},
                            new ArrayList<WargearOption>() {{
                                add(new WargearOption(
                                        "Intercessor Sergeant",
                                        new ArrayList<>(Arrays.asList(
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.CLOSE_COMBAT_WEAPON))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.ASTARTES_CHAINSWORD))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.POWER_WEAPON))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.POWER_FIST))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.THUNDER_HAMMER)))
                                        )),
                                        1
                                ));
                                add(new WargearOption(
                                        "Intercessor Sergeant",
                                        new ArrayList<>(Arrays.asList(
                                                new ArrayList<>(Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.BOLT_PISTOL))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.ASTARTES_CHAINSWORD))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.HAND_FLAMER))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.PLASMA_PISTOL))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.POWER_WEAPON)))
                                        )),
                                        1
                                ));
                                add(new WargearOption(
                                        "",
                                        new ArrayList<>(Arrays.asList(
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.BOLT_RIFLE))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.ASTARTES_GRENADE_LAUNCHER)))
                                        )),
                                        0.2
                                ));
                            }},
                            new ArrayList<PointsThreshold>() {{
                                add(new PointsThreshold(75, 5));
                                add(new PointsThreshold(150, 10));
                            }},
                            EnumSet.of(Model.ModelKeywords.INFANTRY, Model.ModelKeywords.BATTLELINE, Model.ModelKeywords.GRENADES, Model.ModelKeywords.TACTICUS, Model.ModelKeywords.INTERCESSOR_SQUAD)
                    ));

                    add(new Unit(
                            // Name
                            "Heavy Intercessor Squad",
                            // Image
                            R.drawable.heavy_intercessor,
                            // Abilities
                            new ArrayList<Ability>() {{
                                add(new Ability("Broad Spectrum Data-tether", "Each time you select this unit as the target of a Stratagem, roll one D6: on a 5+, "));
                            }},
                            // Wargear Abilities
                            new ArrayList<Ability>() {{
                            }},
                            // Unit Composition
                            new ArrayList<ModelComposition>() {{
                                add(new ModelComposition(
                                        new Model("Heavy Intercessor Sergeant", new Statline( 5, 5, 3, 3, 6, 2),
                                                new ArrayList<>(Arrays.asList(
                                                        Weapon.armory.get(Weapon.WeaponEnums.BOLT_PISTOL),
                                                        Weapon.armory.get(Weapon.WeaponEnums.HEAVY_BOLT_RIFLE),
                                                        Weapon.armory.get(Weapon.WeaponEnums.CLOSE_COMBAT_WEAPON))),
                                                EnumSet.noneOf(Model.ModelKeywords.class)),
                                        1, 1));
                                add(new ModelComposition(
                                        new Model("Heavy Intercessor",new Statline( 6, 4, 3, 2, 6, 2),
                                                new ArrayList<>(Arrays.asList(
                                                        Weapon.armory.get(Weapon.WeaponEnums.BOLT_PISTOL),
                                                        Weapon.armory.get(Weapon.WeaponEnums.HEAVY_BOLT_RIFLE),
                                                        Weapon.armory.get(Weapon.WeaponEnums.CLOSE_COMBAT_WEAPON))),
                                                EnumSet.noneOf(Model.ModelKeywords.class)),
                                        4, 9));
                            }},
                            // Wargear Options
                            new ArrayList<WargearOption>() {{
                                add(new WargearOption(
                                        "Heavy Intercessor",
                                        new ArrayList<>(Arrays.asList(
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.HEAVY_BOLT_RIFLE))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.HEAVY_BOLTER)))
                                        )),
                                        1
                                ));
                            }},
                            // Unit Points
                            new ArrayList<PointsThreshold>() {{
                                add(new PointsThreshold(75, 5));
                                add(new PointsThreshold(150, 10));
                            }},
                            // Keywords
                            EnumSet.of(Model.ModelKeywords.INFANTRY, Model.ModelKeywords.BATTLELINE, Model.ModelKeywords.GRENADES, Model.ModelKeywords.TACTICUS, Model.ModelKeywords.INTERCESSOR_SQUAD)
                    ));
                    add(new Unit(
                            // Name
                            "Assault Intercessor Squad",
                            // Image
                            R.drawable.assault_intercessor,
                            // Abilities
                            new ArrayList<Ability>() {{
                                add(new Ability("Shock Assault", ""));
                            }},
                            // Wargear Abilities
                            new ArrayList<Ability>() {{
                            }},
                            // Unit Composition
                            new ArrayList<ModelComposition>() {{
                                add(new ModelComposition(
                                        new Model("Assault Intercessor Sergeant", new Statline( 6, 4, 3, 2, 6, 2),
                                                new ArrayList<>(Arrays.asList(
                                                        Weapon.armory.get(Weapon.WeaponEnums.BOLT_PISTOL),
                                                        Weapon.armory.get(Weapon.WeaponEnums.ASTARTES_CHAINSWORD))),
                                                EnumSet.noneOf(Model.ModelKeywords.class)),
                                        1, 1));
                                add(new ModelComposition(
                                        new Model("Assault Intercessor",new Statline( 6, 4, 3, 2, 6, 2),
                                                new ArrayList<>(Arrays.asList(
                                                        Weapon.armory.get(Weapon.WeaponEnums.BOLT_PISTOL),
                                                        Weapon.armory.get(Weapon.WeaponEnums.ASTARTES_CHAINSWORD))),
                                                EnumSet.noneOf(Model.ModelKeywords.class)),
                                        4, 9));
                            }},

                            new ArrayList<WargearOption>() {{
                                add(new WargearOption(
                                        "Assault Intercessor Sergeant",
                                        new ArrayList<>(Arrays.asList(
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.CLOSE_COMBAT_WEAPON))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.ASTARTES_CHAINSWORD))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.POWER_WEAPON))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.POWER_FIST))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.THUNDER_HAMMER)))
                                        )),
                                        1
                                ));
                                add(new WargearOption(
                                        "Assault Intercessor Sergeant",
                                        new ArrayList<>(Arrays.asList(
                                                new ArrayList<>(Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.BOLT_PISTOL))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.ASTARTES_CHAINSWORD))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.HAND_FLAMER))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.PLASMA_PISTOL))),
                                                new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.POWER_WEAPON)))
                                        )),
                                        1
                                ));
                            }},
                            new ArrayList<PointsThreshold>() {{
                                add(new PointsThreshold(75, 5));
                                add(new PointsThreshold(150, 10));
                            }},
                            EnumSet.of(Model.ModelKeywords.INFANTRY, Model.ModelKeywords.BATTLELINE, Model.ModelKeywords.GRENADES, Model.ModelKeywords.TACTICUS, Model.ModelKeywords.INTERCESSOR_SQUAD)
                    ));


                }},
                // Replace
                R.drawable.space_marine_ultrawide,
                R.drawable.space_marine_phone,
                new Ability("Oath of Moment", App.getContext().getResources().getString(R.string.oath_of_moment_flavour_text), App.getContext().getResources().getString(R.string.oath_of_moment_desc), new Predicate<WarhammerGameState>() {
                        @Override
                        public boolean test(WarhammerGameState warhammerGameState) {
                            return (warhammerGameState.getPhase() == Phase.COMMAND);
                        }
                })
        ));

        add(new Faction(
                "Adepta Sororitas",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "Hallowed Martyrs",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                                add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }},
                            new Ability("Combat Doctrines", App.getContext().getResources().getString(R.string.the_blood_of_martyrs_flavour_text), App.getContext().getResources().getString(R.string.the_blood_of_martyrs_desc), new Predicate<WarhammerGameState>() {
                                @Override
                                public boolean test(WarhammerGameState warhammerGameState) {
                                    return (warhammerGameState.getPhase() == Phase.COMMAND);
                                }
                            })
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.adepta_sororitas_ultrawide,
                R.drawable.adepta_sororitas_phone,
                new Ability("Acts of Faith", App.getContext().getResources().getString(R.string.acts_of_faith_flavour_text), App.getContext().getResources().getString(R.string.acts_of_fatith_desc), new Predicate<WarhammerGameState>() {
                    @Override
                    public boolean test(WarhammerGameState warhammerGameState) {
                        return (warhammerGameState.getPhase() == Phase.COMMAND);
                    }
                })

        ));

        add(new Faction(
                "Adeptus Custodes",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                                add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.adeptus_custodes_wide,
                R.drawable.adeptus_custodes_portrait
        ));


        add(new Faction(
                "Aeldari",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.aeldari_ultrawide,
                R.drawable.aeldari_phone
        ));

        add(new Faction(
                "Agents of the Imperium",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.crimson_fists_wide,
                R.drawable.sapce_marine_phone_art
        ));

        add(new Faction(
                "Astra Militarum",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.astra_militarum_wide,
                R.drawable.astra_militarum_tall
        ));

        add(new Faction(
                "Grey Knights",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.crimson_fists_wide,
                R.drawable.sapce_marine_phone_art
        ));

        add(new Faction(
                "Chaos Demons",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.chaos_demons_ultrawide,
                R.drawable.chaos_demons_phone
        ));

        add(new Faction(
                "Chaos Knights",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.chaos_knights_ultrawide,
                R.drawable.chaos_knights_phone
        ));

        add(new Faction(
                "Chaos Space Marines",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.chaos_space_marine_ultrawide,
                R.drawable.chaos_space_marine_phone
        ));

        add(new Faction(
                "Death Guard",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.deathguard_wide,
                R.drawable.deathguard_phone
        ));

        add(new Faction(
                "Drukhari",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.drukhari_ultrawide,
                R.drawable.drukhari_phone
        ));

        add(new Faction(
                "Genestealer Cults",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.genestealer_cults_ultrawide,
                R.drawable.genestealer_cults_phone
        ));


        add(new Faction(
                "Imperial Knights",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "Noble Lance",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "Revered Knight (Aura)",
                                        20,
                                        "Never hesitating before tyrannical invaders, this Knight is beheld as the very epitome of honour – a monolithic incarnation of the Imperium. While they stand, courage is inviolate and victory is assured.",
                                        "IMPERIAL KNIGHTS model only. While a friendly IMPERIAL KNIGHTS model is within 6\" of the bearer, improve that model’s Leadership characteristic by 1. If your army is Honoured, increase the range of this Aura ability to 12\"."
                                ));
                                add(new Enhancement(
                                        "Excoriating Emanation",
                                        35,
                                        "Manifesting as though from nowhere, this Noble’s titanic steed is an elemental force of vengeance against the Emperor of Mankind’s foes. At battle’s end, it disappears as mysteriously as it arrived.",
                                        "IMPERIAL KNIGHTS model only. The bearer has the Deep Strike ability. Once per battle, at the end of your opponent’s turn, if the bearer is not within Engagement Range of any enemy units, it can fade away. If it does, remove it from the battlefield then, in the Reinforcements step of your subsequent Movement phase, set it up anywhere on the battlefield that is more than 9\" horizontally away from all enemy models. If the battle ends and the bearer is not on the battlefield, it is destroyed."
                                ));
                                add(new Enhancement(
                                        "Mythic Hero",
                                        25,
                                        "This warrior is a legendary figure who embodies the Code Chivalric, and tales of their deeds have spread far and wide. To be a Bondsman to such a warrior is a great honour indeed.",
                                        "QUESTORIS model with a Bondsman ability only. Each time the bearer uses its Bondsman ability, you can select one additional friendly ARMIGER model within 12\" of the bearer that is not already being affected by a Bondsman ability. Until the start of your next Command phase, that model is also affected by that Bondsman ability.",
                                        new Predicate<EnumSet<Model.ModelKeywords>>() {
                                            @Override
                                            public boolean test(EnumSet<Model.ModelKeywords> keywords) {
                                                return keywords.contains(Model.ModelKeywords.QUESTORIS);
                                            }
                                        }

                                ));
                                add(new Enhancement(
                                        "Banner of Macharius Triumphant",
                                        30,
                                        "A gift from Lord Solar Macharius himself to honour the noble houses that accompanied his crusade, legend has it that this banner has never seen defeat: every time a Knight has carried it to war, a great victory has been won.",
                                        "IMPERIAL KNIGHTS model only. If you control an objective marker at the end of your Command phase and the bearer is within range of that objective marker, that objective marker remains under your control even if you have no models within range of it, until your opponent controls it at the start or end of any turn."
                                ));
                                add(new Enhancement(
                                        "Unyielding Paragon",
                                        40,
                                        "This Knight is an unyielding army unto themselves, capable of engaging entire enemy forces and holding their ground, emerging through the fiercest firestorms bloodied, but unbowed.",
                                        "QUESTORIS model only. Each time an attack is allocated to the bearer, worsen the Armour Penetration characteristic of that attack by 1."
                                ));

                            }},
                            new ArrayList<Stratagem>() {{
                                add(new Stratagem(
                                        "Squire's Duty",
                                        1,
                                        stratagemType.BATTLE_TACTIC,
                                        "Under the scrutiny and judgement of their Noble betters, Armiger pilots will redouble their efforts, attacking as one to smash aside their foes.",
                                        "The start of your Shooting phase or the start of the Fight phase.",
                                        "Two or more ARMIGER models from your army and one enemy unit that is an eligible target for all of those ARMIGER models.",
                                        "Until the end of the phase, improve the Strength and Armour Penetration characteristics of weapons equipped by those ARMIGER models by 1. If your army is Honoured, until the end of the phase, add 1 to the Damage characteristic of those weapons as well.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Rotate Ion Shields",
                                        1,
                                        stratagemType.WARGEAR,
                                        "Veteran Knight pilots can swiftly angle their ion shields to better deflect incoming fire.",
                                        "Your opponent's Shooting phase, just after an enemy unit has selected its targets",
                                        "One IMPERIAL KNIGHTS model from your army that was selected as the target of one or more of the attacking unit’s attacks.",
                                        "Until the end of the phase, that IMPERIAL KNIGHTS model has a 4+ invulnerable save against ranged attacks.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.PLAYER_TURN, Timing.TimingEnum.COMMAND_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Thunderstomp",
                                        1,
                                        stratagemType.EPIC_DEED,
                                        "The Noble brings their Knight suit’s full weight crashing down with the force of an industrial piledriver. Few can survive such a blow.",
                                        "Fight phase.",
                                        "One IMPERIAL KNIGHTS model from your army that has not been selected to fight this phase.",
                                        "Until the end of the phase, your model cannot target MONSTER or VEHICLE units, but all melee weapons equipped by your model have the [DEVASTATING WOUNDS] ability.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Valiant Last Stand",
                                        2,
                                        stratagemType.EPIC_DEED,
                                        "Badly wounded, their Knight’s generator on the verge of overload, still the Noble fights on, drawing upon their reserves of chivalric heroism to sell their life as dearly as they can.",
                                        "Fight phase.",
                                        "One IMPERIAL KNIGHTS model from your army that was just destroyed and that is eligible to fight but has not been selected to fight this phase. You can use this Stratagem on that model even though it was just destroyed.",
                                        "Before rolling to see if this model deals any mortal wounds as a result of its Deadly Demise ability, it can fight; when doing so, it is assumed to have 1 wound remaining, or all its wounds remaining if your army is Honoured. After it has finished resolving its attacks, resolve its Deadly Demise ability as normal.",
                                        "You cannot target SIR HEKHTUR with this Stratagem.",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.PLAYER_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Trophy Claim",
                                        2,
                                        stratagemType.EPIC_DEED,
                                        "Once a mighty foe is laid low, the victorious Knight’s emitters blare its triumph, announcing the glory brought to the Imperium, but shame awaits those who fail in such confrontations.",
                                        "Your Shooting phase or the Fight phase.",
                                        "One IMPERIAL KNIGHTS model from your army that has not been selected to shoot or fight this phase, and one enemy MONSTER or VEHICLE unit.",
                                        "Until the end of the phase, each time your model makes an attack that targets that enemy unit, add 1 to the Wound roll. If your model destroys that enemy unit this phase, you gain 1CP, but if your model does not destroy that enemy unit this phase, you cannot use this Stratagem again for the rest of the battle.",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.PLAYER_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                                add(new Stratagem(
                                        "Shoulder the Burden",
                                        2,
                                        stratagemType.BATTLE_TACTIC,
                                        "When faced with their darkest hour, knightly Nobles rise to the challenge, for nothing shall deter them from fulfilling their duty.",
                                        "Your Command phase.",
                                        "One IMPERIAL KNIGHTS model from your army that has lost one or more wounds.",
                                        "Until the start of your next Command phase, improve your model’s Move, Toughness, Save, Leadership and Objective Control characteristics by 1 and each time your model makes an attack, add 1 to the Hit roll.",
                                        "You can only use this Stratagem once per battle. If your army is Honoured, you can use this Stratagem one additional time.",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.MOVEMENT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>() {{

                    // Imperial Knight Units

                    add(
                            new Unit(
                                    "Knight Castellan",
                                    R.drawable.knight_castellan,
                                    new ArrayList<Ability>() {{
                                        add(new Ability("Ion Aegis (Aura)", "While a friendly ARMIGER model is within 6\" of this model, that ARMIGER models has the Benefits of Cover."));
                                        add(new Ability("Titan Hunter", "Each time a ranged attack made by this model is allocated to a MONSTER or VEHICLE model, re-roll a Damage roll of 1."));
                                    }},
                                    new ArrayList<Ability>() {{
                                    }},
                                    new ArrayList<ModelComposition>() {{
                                        add(new ModelComposition(
                                                new Model("Knight Crusader", new Statline(8, 13, 2, 5, 24, 6, 10),
                                                        new ArrayList<Wargear>(){{
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.PLASMA_DECIMATOR));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.SHIELDBREAKER_MISSILE_LAUNCHER));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.SHIELDBREAKER_MISSILE_LAUNCHER));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.TWIN_MELTAGUN));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.TWIN_MELTAGUN));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.TWIN_SIEGEBREAKER_CANNON));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.VOLCANO_LANCE));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.DOMINUS_TITANIC_FEET));

                                                        }},
                                                        EnumSet.noneOf(Model.ModelKeywords.class)),
                                                1,
                                                1));
                                    }},

                                    new ArrayList<WargearOption>() {{

                                        add(new WargearOption(
                                                "",
                                                new ArrayList<>(Arrays.asList(
                                                        new ArrayList<Wargear>() {{
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.SHIELDBREAKER_MISSILE_LAUNCHER));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.SHIELDBREAKER_MISSILE_LAUNCHER));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.TWIN_SIEGEBREAKER_CANNON));
                                                        }},

                                                        new ArrayList<Wargear>() {{
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.SHIELDBREAKER_MISSILE_LAUNCHER));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.TWIN_SIEGEBREAKER_CANNON));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.TWIN_SIEGEBREAKER_CANNON));
                                                        }}
                                                ))
                                        ));

                                    }},

                                    new ArrayList<PointsThreshold>() {{
                                        add(new PointsThreshold(565, 1));
                                    }},

                                    EnumSet.of(Model.ModelKeywords.VEHICLE, Model.ModelKeywords.WALKER, Model.ModelKeywords.TITANIC, Model.ModelKeywords.TOWERING, Model.ModelKeywords.DOMINUS, Model.ModelKeywords.CHARACTER ,Model.ModelKeywords.IMPERIUM, Model.ModelKeywords.KNIGHT_CASTELLAN)


                            )
                    );

                    add(
                            new Unit(
                                    "Knight Crusader",
                                    R.drawable.knight_crusader,
                                    new ArrayList<Ability>() {{
                                        add(new Ability("Crusader's Duty (Bondsman)", " While a model is affected by this ability, each time that model makes a ranged attack, add 1 to the Hit roll."));
                                        add(new Ability("Punishing Salvoes","In your Movement phase, if this model Remains Stationary, until the start of your next Movement phase, this model’s ranged weapons have the [SUSTAINED HITS 1] ability."));


                                    }},
                                    new ArrayList<Ability>() {{
                                    }},
                                    new ArrayList<ModelComposition>() {{
                                        add(new ModelComposition(
                                                new Model("Knight Crusader", new Statline(10, 12, 3, 5, 22, 6, 10),
                                                        new ArrayList<Wargear>(){{
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.AVENGER_GATLING_CANNON));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.HEAVY_FLAMER));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.MELTAGUN));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.THERMAL_CANNON));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.TITANIC_FEET));

                                                        }},
                                                        EnumSet.noneOf(Model.ModelKeywords.class)),
                                                1,
                                                1));
                                    }},

                                    new ArrayList<WargearOption>() {{
                                        add(new WargearOption(
                                                "",
                                                new ArrayList<>(Arrays.asList(
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.MELTAGUN))),
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.QUESTORIS_HEAVY_STUBBER)))
                                                ))
                                        ));

                                        add(new WargearOption(
                                                "",
                                                new ArrayList<>(Arrays.asList(
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.THERMAL_CANNON))),
                                                        new ArrayList<Wargear>() {{
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.RAPIDFIRE_BATTLE_CANNON));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.QUESTORIS_HEAVY_STUBBER));
                                                        }}
                                                ))
                                        ));

                                        add(new WargearOption(
                                                "",
                                                new ArrayList<>(Arrays.asList(
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.ICARUS_AUTOCANNONS))),
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.IRONSTORM_MISSILE_PODS))),
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.STORMSPEAR_ROCKET_POD)))
                                                ))
                                        ));

                                    }},

                                    new ArrayList<PointsThreshold>() {{
                                        add(new PointsThreshold(400, 1));
                                    }},

                                    EnumSet.of(Model.ModelKeywords.VEHICLE, Model.ModelKeywords.WALKER, Model.ModelKeywords.TITANIC, Model.ModelKeywords.TOWERING, Model.ModelKeywords.QUESTORIS, Model.ModelKeywords.CHARACTER ,Model.ModelKeywords.IMPERIUM, Model.ModelKeywords.KNIGHT_CRUSADER)


                            )
                    );

                    add(
                            new Unit(
                                    "Knight Gallant",
                                    R.drawable.knight_gallant,
                                    new ArrayList<Ability>() {{
                                        add(new Ability("", ""));
                                    }},
                                    new ArrayList<Ability>() {{
                                    }},
                                    new ArrayList<ModelComposition>() {{
                                        add(new ModelComposition(
                                                new Model("Knight Gallant", new Statline(12, 12, 3, 5, 22, 6, 10),
                                                        new ArrayList<Wargear>(){{
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.MELTAGUN));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.THUNDERSTRIKE_GAUNTLET));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.REAPER_CHAINSWORD));

                                                        }},
                                                        EnumSet.noneOf(Model.ModelKeywords.class)),
                                                1,
                                                1));
                                    }},

                                    new ArrayList<WargearOption>() {{
                                        add(new WargearOption(
                                                "",
                                                new ArrayList<>(Arrays.asList(
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.MELTAGUN))),
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.QUESTORIS_HEAVY_STUBBER)))
                                                ))
                                        ));

                                        add(new WargearOption(
                                                "",
                                                new ArrayList<>(Arrays.asList(
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.ICARUS_AUTOCANNONS))),
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.IRONSTORM_MISSILE_PODS))),
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.STORMSPEAR_ROCKET_POD)))
                                                ))
                                        ));

                                    }},

                                    new ArrayList<PointsThreshold>() {{
                                        add(new PointsThreshold(400, 1));
                                    }},

                                    EnumSet.of(Model.ModelKeywords.VEHICLE, Model.ModelKeywords.WALKER, Model.ModelKeywords.TITANIC, Model.ModelKeywords.TOWERING, Model.ModelKeywords.QUESTORIS, Model.ModelKeywords.CHARACTER ,Model.ModelKeywords.IMPERIUM, Model.ModelKeywords.KNIGHT_GALLANT)


                            )
                    );

                    add(
                            new Unit(
                                    "Knight Paladin",
                                    R.drawable.knight_paladin,
                                    new ArrayList<Ability>() {{
                                        add(new Ability("Paladin's Duty (Bondsman)", "While a model is affected by this ability, its weapons have the [LETHAL HITS] and [LANCE] abilities."));
                                        add(new Ability("Seasoned Noble", "Once per phase, you can re-roll one Hit roll, one Wound roll or one saving throw made for this model."));
                                    }},
                                    new ArrayList<Ability>() {{
                                    }},
                                    new ArrayList<ModelComposition>() {{
                                        add(new ModelComposition(
                                                new Model("Knight Paladin", new Statline(10, 12, 3, 5, 22, 6, 10),
                                                        new ArrayList<Wargear>(){{
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.AVENGER_GATLING_CANNON));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.HEAVY_FLAMER));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.MELTAGUN));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.THERMAL_CANNON));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.TITANIC_FEET));

                                                        }},
                                                        EnumSet.noneOf(Model.ModelKeywords.class)),
                                                1,
                                                1));
                                    }},

                                    new ArrayList<WargearOption>() {{
                                        add(new WargearOption(
                                                "",
                                                new ArrayList<>(Arrays.asList(
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.MELTAGUN))),
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.QUESTORIS_HEAVY_STUBBER)))
                                                ))
                                        ));

                                        add(new WargearOption(
                                                "",
                                                new ArrayList<>(Arrays.asList(
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.THERMAL_CANNON))),
                                                        new ArrayList<Wargear>() {{
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.RAPIDFIRE_BATTLE_CANNON));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.QUESTORIS_HEAVY_STUBBER));
                                                        }}
                                                ))
                                        ));

                                        add(new WargearOption(
                                                "",
                                                new ArrayList<>(Arrays.asList(
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.ICARUS_AUTOCANNONS))),
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.IRONSTORM_MISSILE_PODS))),
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.STORMSPEAR_ROCKET_POD)))
                                                ))
                                        ));

                                    }},

                                    new ArrayList<PointsThreshold>() {{
                                        add(new PointsThreshold(400, 1));
                                    }},

                                    EnumSet.of(Model.ModelKeywords.VEHICLE, Model.ModelKeywords.WALKER, Model.ModelKeywords.TITANIC, Model.ModelKeywords.TOWERING, Model.ModelKeywords.QUESTORIS, Model.ModelKeywords.CHARACTER ,Model.ModelKeywords.IMPERIUM, Model.ModelKeywords.KNIGHT_PALADIN)


                            )
                    );

                    add(
                            new Unit(
                                    "Knight Valiant",
                                    R.drawable.knight_valiant,
                                    new ArrayList<Ability>() {{
                                        add(new Ability("Ion Aegis (Aura):", "While a friendly ARMIGER model is within 6\" of this model, that ARMIGER model has the Benefit of Cover."));
                                        add(new Ability("Overwhelming Firestorm:", "Overwhelming Firestorm: In your Shooting phase, after this model has shot, select one enemy unit hit by this model this phase. That unit must take a Battle-shock test."));
                                    }},
                                    new ArrayList<Ability>() {{
                                    }},
                                    new ArrayList<ModelComposition>() {{
                                        add(new ModelComposition(
                                                new Model("Knight Valiant", new Statline(8, 13, 2, 5, 24, 6, 10),
                                                        new ArrayList<Wargear>(){{
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.CONFLAGURATION_CANNON));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.SHIELDBREAKER_MISSILE_LAUNCHER));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.SHIELDBREAKER_MISSILE_LAUNCHER));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.THUNDERCOIL_HARPOON));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.TWIN_MELTAGUN));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.TWIN_MELTAGUN));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.DOMINUS_TITANIC_FEET));

                                                        }},
                                                        EnumSet.noneOf(Model.ModelKeywords.class)),
                                                1,
                                                1));
                                    }},

                                    new ArrayList<WargearOption>() {{

                                        add(new WargearOption(
                                                "",
                                                new ArrayList<>(Arrays.asList(
                                                        new ArrayList<Wargear>() {{
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.SHIELDBREAKER_MISSILE_LAUNCHER));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.SHIELDBREAKER_MISSILE_LAUNCHER));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.TWIN_SIEGEBREAKER_CANNON));
                                                        }},

                                                        new ArrayList<Wargear>() {{
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.SHIELDBREAKER_MISSILE_LAUNCHER));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.TWIN_SIEGEBREAKER_CANNON));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.TWIN_SIEGEBREAKER_CANNON));
                                                        }}
                                                ))
                                        ));

                                    }},

                                    new ArrayList<PointsThreshold>() {{
                                        add(new PointsThreshold(565, 1));
                                    }},

                                    EnumSet.of(Model.ModelKeywords.VEHICLE, Model.ModelKeywords.WALKER, Model.ModelKeywords.TITANIC, Model.ModelKeywords.TOWERING, Model.ModelKeywords.DOMINUS, Model.ModelKeywords.CHARACTER ,Model.ModelKeywords.IMPERIUM, Model.ModelKeywords.KNIGHT_VALIANT)


                            )
                    );

                    add(
                            new Unit(
                                    "Knight Warden",
                                    R.drawable.knight_warden,
                                    new ArrayList<Ability>() {{
                                        add(new Ability("", ""));
                                    }},
                                    new ArrayList<Ability>() {{
                                    }},
                                    new ArrayList<ModelComposition>() {{
                                        add(new ModelComposition(
                                                new Model("Knight Warden", new Statline(10, 12, 3, 5, 22, 6, 10),
                                                        new ArrayList<Wargear>(){{
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.AVENGER_GATLING_CANNON));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.HEAVY_FLAMER));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.MELTAGUN));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.REAPER_CHAINSWORD));
                                                        }},
                                                        EnumSet.noneOf(Model.ModelKeywords.class)),
                                                1,
                                                1));
                                    }},

                                    new ArrayList<WargearOption>() {{
                                        add(new WargearOption(
                                                "",
                                                new ArrayList<>(Arrays.asList(
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.MELTAGUN))),
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.QUESTORIS_HEAVY_STUBBER)))
                                                ))
                                        ));

                                        add(new WargearOption(
                                                "",
                                                new ArrayList<>(Arrays.asList(
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.REAPER_CHAINSWORD))),
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.THUNDERSTRIKE_GAUNTLET)))
                                                ))
                                        ));

                                        add(new WargearOption(
                                                "",
                                                new ArrayList<>(Arrays.asList(
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.ICARUS_AUTOCANNONS))),
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.IRONSTORM_MISSILE_PODS))),
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.STORMSPEAR_ROCKET_POD)))
                                                ))
                                        ));

                                    }},

                                    new ArrayList<PointsThreshold>() {{
                                        add(new PointsThreshold(480, 1));
                                    }},

                                    EnumSet.of(Model.ModelKeywords.VEHICLE, Model.ModelKeywords.WALKER, Model.ModelKeywords.TITANIC, Model.ModelKeywords.TOWERING, Model.ModelKeywords.QUESTORIS, Model.ModelKeywords.CHARACTER ,Model.ModelKeywords.IMPERIUM, Model.ModelKeywords.KNIGHT_WARDEN)


                            )
                    );

                    //  Battleline

                    add(
                            new Unit(
                                    "Armiger Helverin",
                                    R.drawable.armiger_helverin,
                                    new ArrayList<Ability>() {{
                                        add(new Ability("Skyfire Protocols", "While this model is either wholly within your deployment zone or within range of an objective marker you control, its Armiger autocannons have the [ANTI-FLY 2+] ability."));
                                    }},
                                    new ArrayList<Ability>() {{
                                    }},
                                    new ArrayList<ModelComposition>() {{
                                        add(new ModelComposition(
                                                new Model("Knight Warden", new Statline(12, 10, 3, 5, 12, 7, 8),
                                                        new ArrayList<Wargear>(){{
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.ARMIGER_AUTOCANNON));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.ARMIGER_AUTOCANNON));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.QUESTORIS_HEAVY_STUBBER));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.ARMIGER_FEET));
                                                        }},
                                                        EnumSet.noneOf(Model.ModelKeywords.class)),
                                                1,
                                                1));
                                    }},

                                    new ArrayList<WargearOption>() {{
                                        add(new WargearOption(
                                                "",
                                                new ArrayList<>(Arrays.asList(
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.QUESTORIS_HEAVY_STUBBER))),
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.MELTAGUN)))
                                                ))
                                        ));
                                    }},

                                    new ArrayList<PointsThreshold>() {{
                                        add(new PointsThreshold(150, 1));
                                    }},

                                    EnumSet.of(Model.ModelKeywords.VEHICLE, Model.ModelKeywords.WALKER, Model.ModelKeywords.BATTLELINE, Model.ModelKeywords.TOWERING, Model.ModelKeywords.IMPERIUM, Model.ModelKeywords.ARMIGER, Model.ModelKeywords.HELVERIN)


                            )
                    );
                    add(
                            new Unit(
                                    "Armiger Warglaive",
                                    R.drawable.armiger_warglaive,
                                    new ArrayList<Ability>() {{
                                        add(new Ability("Impetuous Glory", "Each time this model makes a Charge move, until the end of the turn, melee weapons equipped by this model have the [SUSTAINED HITS 1] ability."));
                                    }},
                                    new ArrayList<Ability>() {{
                                    }},
                                    new ArrayList<ModelComposition>() {{
                                        add(new ModelComposition(
                                                new Model("Armiger Warglaive", new Statline(12, 10, 3, 5, 12, 7, 8),
                                                        new ArrayList<Wargear>(){{
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.THERMAL_SPEAR));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.REAPER_CHAIN_CLEAVER));
                                                            add(Weapon.armory.get(Weapon.WeaponEnums.QUESTORIS_HEAVY_STUBBER));
                                                        }},
                                                        EnumSet.noneOf(Model.ModelKeywords.class)),
                                                1,
                                                1));
                                    }},

                                    new ArrayList<WargearOption>() {{
                                        add(new WargearOption(
                                                "",
                                                new ArrayList<>(Arrays.asList(
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.QUESTORIS_HEAVY_STUBBER))),
                                                        new ArrayList<> (Collections.singletonList(Weapon.armory.get(Weapon.WeaponEnums.MELTAGUN)))
                                                ))
                                        ));
                                    }},

                                    new ArrayList<PointsThreshold>() {{
                                        add(new PointsThreshold(150, 1));
                                    }},

                                    EnumSet.of(Model.ModelKeywords.VEHICLE, Model.ModelKeywords.WALKER, Model.ModelKeywords.BATTLELINE, Model.ModelKeywords.TOWERING, Model.ModelKeywords.IMPERIUM, Model.ModelKeywords.ARMIGER, Model.ModelKeywords.WARGLAIVE)


                            )
                    );

                }},
                // Replace
                R.drawable.imperial_knights_wide,
                R.drawable.imperial_knights_portrait
        ));

        add(new Faction(
                "Leagues of Votann",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.leagues_of_votann_ultawide,
                R.drawable.leagues_of_votann_phone
        ));


        add(new Faction(
                "Necrons",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.necrons_ultrawide,
                R.drawable.necrons_phone
        ));

        add(new Faction(
                "Orks",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.orks_ultrawide,
                R.drawable.orks_phone
        ));

        add(new Faction(
                "Tau Empire",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.tau_empire_wide,
                R.drawable.tau_empire_vertical
        ));
        // Template
        add(new Faction(
                "Thousand Sons",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.thousand_sons_ultrawide,
                R.drawable.thousand_sons_phone
        ));

        add (new Faction(
                "Tyranids",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.tyranid_horizontal_super_wide,
                R.drawable.tyranid_portrait
        ));

        add(new Faction(
                "World Eaters",
                new ArrayList<Detachment>() {{
                    add(new Detachment(
                            "",
                            new ArrayList<Enhancement>() {{
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        "."
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));
                                add(new Enhancement(
                                        "",
                                        0,
                                        "",
                                        ""
                                ));

                            }},
                            new ArrayList<Stratagem>() {{

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                           add(new Stratagem(
                                        "",
                                        0,
                                        stratagemType.BATTLE_TACTIC,
                                        "",
                                        "",
                                        "",
                                        "",
                                        "",
                                        EnumSet.of(Phase.FIGHT),
                                        EnumSet.of(PlayerTurn.OWNER),
                                        new ArrayList<Timing>() {{
                                            add(new Timing(Timing.PlayerTurn.OPPONENT_TURN, Timing.TimingEnum.SHOOTING_MIDDLE, Timing.BattleRound.NONE));
                                            add(new Timing(Timing.PlayerTurn.NONE, Timing.TimingEnum.FIGHT_MIDDLE, Timing.BattleRound.NONE));
                                        }}
                                ));

                            }}
                    ));
                }},
                new ArrayList<Unit>(){{}},
                // Replace
                R.drawable.crimson_fists_wide,
                R.drawable.sapce_marine_phone_art
        ));
    }};

    public Faction(String name, ArrayList<Detachment> detachments, ArrayList<Unit> units, int imageRes, int portraitImageRes, Ability... armyRules) {
        this.name = name;
        this.detachments = detachments;
        this.units = units;
        Collections.sort(units);
        this.imageRes = imageRes;
        this.portraitImageRes = portraitImageRes;

        this.armyRules = new ArrayList<Ability>(Arrays.asList(armyRules));
    }

    public String getName() {
        return name;
    }
    public int getImageRes() {
        return imageRes;
    }
    public ArrayList<Detachment> getDetachments () {
        return detachments;
    }

    public String toString() {
        return name.toString();
    }
    public int getPortraitImageRes() {
        return portraitImageRes;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }
    public ArrayList<Ability> getArmyRules() {

        return armyRules;
    }
    public static ArrayList<Faction> getFactionList() {
        return factionList;
    }
}
