package com.example.javahammer.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.example.javahammer.activities.DamageCalculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Weapon implements Parcelable, Comparable, Serializable {


    public enum WeaponEnums{
        BOLT_RIFLE, BOLT_PISTOL, BOLTGUN, CLOSE_COMBAT_WEAPON, INCENDINE_COMBUSTOR, HAND_FLAMER, POWER_WEAPON, POWER_FIST, THUNDER_HAMMER, ASTARTES_CHAINSWORD, ASTARTES_GRENADE_LAUNCHER, PLASMA_PISTOL, COGNIS_HEAVY_STUBBER_ARRAY, COGNIS_HEAVY_STUBBER, TWIN_COGNIS_LASCANNON, GALVANIC_RIFLE, RADIUM_CARBINE, ARCHEOTECH_PISTOL, PLASMA_CALIVER, ARC_RIFLE, TRANSURANIC_ARQUEBUS, SKITARII_RANGER_COMBAT_WEAPON, PHOSOPHOR_SERPENTA, RADIUM_JEZZAIL, IRONSTRIDER_FEET, TASER_LANCE, MACROSTUBBER, ERADICATION_RAY, VOLKITE_BLASTER, OMNISSIAN_AXE, SERVO_ARM, MAGNARAIL_LANCE, TRANSONIC_CANNON, OMNISSIAN_STAFF, SERVO_ARC_CLAW, TWIN_COGNIS_AUTOCANNON, ARMOURED_HULL, HEAVY_PHOSPHOR_BLASTER, ELECTROSTATIC_GAUNTLETS_RANGED, ELECTROSTATIC_GAUNTLETS_MELEE, ELECTROLEECH_STAVE, KASTELAN_PHOSPHOR_BLASTER, TWIN_KASTELAN_PHOSPHOR_BLASTER, KASTELAN_CLOSE_COMBAT_WEAPON, KASTELAN_FIST, TWIN_KASTELAN_FIST, CAWLS_OMNISSIAN_AXE, MECHADENDRITE_HIVE, ARC_SCOURGE, SOLAR_ATOMISER, ARC_CLAW, HYDRAULIC_CLAW, TORSION_CANNON, HEAVY_ARC_RIFLE, COGNIS_FLAMER, HEAVY_GRAV_CANNON, KATAPHRON_CLOSE_COMBAT, PHOSPHOR_BLASTER, FLECHETTE_BLASTER, STUBCARBINE, TASER_GOAD, SICARIAN_POWER_WEAPON, KATAPHRON_PLASMA_CULVERIN, BELLEROS_ENERGY_CANNON, DISRUPTOR_MISSILE_LAUNCHER, FERRUMITE_CANNON, TRANSONIC_RAZOR, TRANSONIC_RAZOR_AND_CHORDCLAW, TRANSONIC_BLADES, TRANSONIC_BLADES_AND_CHORDCLAW, SHIELDBREAKER_MISSILE_LAUNCHER, PLASMA_DECIMATOR_STANDARD, PLASMA_DECIMATOR, TWIN_MELTAGUN, VOLCANO_LANCE, TWIN_SIEGEBREAKER_CANNON, TITANIC_FEET, HEAVY_FLAMER, AVENGER_GATLING_CANNON, ICARUS_AUTOCANNONS, IRONSTORM_MISSILE_PODS, MELTAGUN, QUESTORIS_HEAVY_STUBBER, STORMSPEAR_ROCKET_POD, RAPIDFIRE_BATTLE_CANNON, THERMAL_CANNON, DOMINUS_TITANIC_FEET, REAPER_CHAINSWORD, THUNDERSTRIKE_GAUNTLET, CONFLAGURATION_CANNON, THUNDERCOIL_HARPOON, ARMIGER_AUTOCANNON, ARMIGER_FEET, THERMAL_SPEAR, REAPER_CHAIN_CLEAVER, ENHANCED_DATA_TETHER, OMNISPEX, COMMAND_UPLINK, CHAFF_LAUNCHER, HEAVY_BOLT_RIFLE, HEAVY_BOLT_PISTOL, HEAVY_BOLTER, CAPTAIN_CLOSE_COMBAT_WEAPON, RELIC_SHIELD, CAPTAIN_MASTER_CRAFTED_POWER_WEAPON, CAPTAIN_POWER_FIST, CAPTAIN_HEAVY_BOLT_PISTOL, CAPTAIN_PLASMA_PISTOL, CAPTAIN_NEO_VOLKITE_PISTOL, CAPTAIN_BOLT_PISTOL, CAPTAIN_MASTER_CRAFTED_BOLTGUN, RELIC_FIST, RELIC_CHAINSWORD, RELIC_BLADE, CAPTAIN_BOLTSTORM_GAUNTLET, CAPTAIN_MASTER_CRAFTED_HEAVY_BOLT_RIFLE, ABSOLVER_BOLT_PISTOL, CROZIUS_ARCANUM, LIBRARIAN_FORCE_WEAPON, LIBRARIAN_SMITE, TECHMARINE_OMNISSIAN_AXE, TECHMARINE_SERVO_ARM, TECHMARINE_GRAV_PISTOL, TECHMARINE_FORGE_BOLTER;
    }
    public static final HashMap<WeaponEnums, Wargear> armory = new HashMap<WeaponEnums, Wargear>() {{

        // Astartes

        // Ranged Weapons

        put(WeaponEnums.ASTARTES_GRENADE_LAUNCHER, new Wargear("Astartes Grenade Launcher",
                new Weapon("Astartes Grenade Launcher - Frag", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.BLAST));}},  30, "D3", 3, 4, 0, "1"),
                new Weapon("Astartes Grenade Launcher - Krak", 30, "1", 3, 9, 2, "D3")
        ));
        put(WeaponEnums.ABSOLVER_BOLT_PISTOL, new Wargear(new Weapon("Absolver Bolt Pistol", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.PISTOL));}}, 12, "1", 3, 5, 1, "2")));

        put(WeaponEnums.BOLT_PISTOL, new Wargear(new Weapon("Bolt Pistol", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.PISTOL));}}, 12, "1", 3, 4, 0, "1")));
        put(WeaponEnums.BOLT_RIFLE, new Wargear(new Weapon("Bolt Rifle",  new HashSet<wepAbilities>() {{add(new wepAbilities(Keywords.ASSAULT)); add(new wepAbilities(Keywords.HEAVY));}}, 30, "2", 3, 4, 1, "1")));
        put(WeaponEnums.CAPTAIN_BOLT_PISTOL, new Wargear(new Weapon("Bolt Pistol", 12, "1", 2, 4, 0, "1", new wepAbilities(Keywords.PISTOL))));
        put(WeaponEnums.CAPTAIN_HEAVY_BOLT_PISTOL, new Wargear(new Weapon("Heavy Bolt Pistol", 18, "1", 2, 4, 1, "1", new wepAbilities(Keywords.PISTOL))));
        put(WeaponEnums.CAPTAIN_MASTER_CRAFTED_BOLTGUN, new Wargear(new Weapon("Master-crafted Boltgun", 24, "2", 2, 4, 1, "2")));
        put(WeaponEnums.CAPTAIN_NEO_VOLKITE_PISTOL, new Wargear(new Weapon("Neo-volkite Pistol", 12, "1", 2, 5, 0, "2", new wepAbilities(Keywords.DEVASTATING_WOUNDS) ,new wepAbilities(Keywords.PISTOL))));
        put(WeaponEnums.CAPTAIN_PLASMA_PISTOL, new Wargear("Plasma Pistol", new ArrayList<Weapon>() {{
            add(new Weapon("Plasma Pistol - Standard", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.PISTOL));}}, 12, "1", 2, 7, 2, "1"));
            add(new Weapon("Plasma Pistol - Supercharge", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.PISTOL)); add(new wepAbilities(Keywords.HAZARDOUS));}}, 12, "1", 2, 8, 3, "2"));
        }}));

        put(WeaponEnums.CAPTAIN_BOLTSTORM_GAUNTLET, new Wargear(new Weapon("Boltstorm Gauntlet", 12, "3", 2, 4, 1, "1", new wepAbilities(Keywords.PISTOL))));
        put(WeaponEnums.CAPTAIN_MASTER_CRAFTED_HEAVY_BOLT_RIFLE, new Wargear(new Weapon("Master-crafted Heavy Bolt Rifle", 30, "2", 2, 5, 1, "2")));
        put(WeaponEnums.HAND_FLAMER, new Wargear(new Weapon("Hand Flamer", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.IGNORES_COVER)); add(new wepAbilities(Keywords.TORRENT)); add(new wepAbilities(Keywords.PISTOL));}}, 12, "D6", 3, 4, 1, "1")));
        put(WeaponEnums.HEAVY_BOLT_PISTOL, new Wargear(new Weapon("Heavy Bolt Pistol", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.PISTOL));}}, 12, "1", 3, 4, 1, "1")));
        put(WeaponEnums.HEAVY_BOLT_RIFLE, new Wargear(new Weapon("Heavy Bolt Rifle",  new HashSet<wepAbilities>() {{add(new wepAbilities(Keywords.ASSAULT)); add(new wepAbilities(Keywords.HEAVY));}}, 30, "2", 3, 5, 1, "1")));
        put(WeaponEnums.HEAVY_BOLTER, new Wargear(new Weapon("Heavy Bolter",  new HashSet<wepAbilities>() {{add(new wepAbilities(Keywords.ASSAULT)); add(new wepAbilities(Keywords.HEAVY));}}, 30, "3", 4, 5, 1, "2")));
        put(WeaponEnums.LIBRARIAN_SMITE, new Wargear("Smite",
                new Weapon("Smite - Witchfire", 24, "D6", 3, 5, 1, "D3", new wepAbilities(Keywords.PSYCHIC)),
                new Weapon("Smite - Focused Witchfire", 24, "D6", 3, 6, 2, "D3", new wepAbilities(Keywords.DEVASTATING_WOUNDS), new wepAbilities(Keywords.HAZARDOUS), new wepAbilities(Keywords.PSYCHIC))
        ));
        put(WeaponEnums.PLASMA_PISTOL, new Wargear("Plasma Pistol",
                new Weapon("Plasma Pistol - Standard", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.PISTOL));}}, 12, "1", 3, 7, 2, "1"),
                new Weapon("Plasma Pistol - Supercharge", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.PISTOL));}}, 12, "1", 3, 8, 3, "2")
        ));
        put(WeaponEnums.TECHMARINE_FORGE_BOLTER, new Wargear(new Weapon("Forge Bolter",24, "3", 2, 5, 1, "2")));
        put(WeaponEnums.TECHMARINE_GRAV_PISTOL, new Wargear(new Weapon("Grav-pistol", 12, "1", 2, 4, 1, "2", new wepAbilitiesAnti(Model.ModelKeywords.VEHICLE, 2), new wepAbilities(Keywords.PISTOL))));

        // Melee Weapons
        put(WeaponEnums.ASTARTES_CHAINSWORD, new Wargear(new Weapon("Astartes Chainsword",0, "2", 3, 4, 1, "1")));
        put(WeaponEnums.CAPTAIN_CLOSE_COMBAT_WEAPON, new Wargear(new Weapon("Close Combat Weapon", 0, "6", 2, 4, 0, "1")));
        put(WeaponEnums.CAPTAIN_MASTER_CRAFTED_POWER_WEAPON, new Wargear(new Weapon("Master-crafted Power Weapon", 0, "6", 2, 5, 2, "2")));
        put(WeaponEnums.CAPTAIN_POWER_FIST, new Wargear(new Weapon("Power Fist", 0, "5", 2, 8, 2, "2")));
        put(WeaponEnums.CLOSE_COMBAT_WEAPON, new Wargear(new Weapon("Close Combat Weapon",0,  "3", 3, 4, 0, "1")));
        put(WeaponEnums.CROZIUS_ARCANUM, new Wargear(new Weapon("Crozius Arcanum", 0, "5", 2, 6, 1, "2")));
        put(WeaponEnums.LIBRARIAN_FORCE_WEAPON, new Wargear(new Weapon("Force Weapon", 0, "4", 3, 6, 1, "D3", new wepAbilities(Keywords.PSYCHIC))));
        put(WeaponEnums.POWER_WEAPON, new Wargear(new Weapon("Power Weapon",0, "3", 3, 5, 2, "1")));
        put(WeaponEnums.POWER_FIST, new Wargear(new Weapon("Power Fist", 0, "3", 3, 8, 2, "2")));
        put(WeaponEnums.RELIC_BLADE, new Wargear(new Weapon("Relic Blade", 0, "2", 2, 5, 2, "2", new wepAbilities(Keywords.EXTRA_ATTACKS))));
        put(WeaponEnums.RELIC_CHAINSWORD, new Wargear(new Weapon("Relic Chainsword", 0, "3", 2, 4, 1, "2", new wepAbilities(Keywords.EXTRA_ATTACKS))));
        put(WeaponEnums.RELIC_FIST, new Wargear(new Weapon("Relic Fist", 0, "1", 2, 8, 2, "2", new wepAbilities(Keywords.EXTRA_ATTACKS))));
        put(WeaponEnums.THUNDER_HAMMER, new Wargear(new Weapon("Thunder Hammer", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.DEVASTATING_WOUNDS));}}, 0, "3", 4, 8, 2, "2")));

        put(WeaponEnums.TECHMARINE_OMNISSIAN_AXE, new Wargear(new Weapon("Omnissian Power Axe", 0, "4", 3, 6, 2, "2")));
        put(WeaponEnums.TECHMARINE_SERVO_ARM, new Wargear(new Weapon("Servo-arm", 0, "1", 3, 8, 2, "3", new wepAbilities(Keywords.EXTRA_ATTACKS))));

        put(WeaponEnums.RELIC_SHIELD, new Wargear("Relic Shield"));

        // ADEPTUS MECHANICUS


        // Cawl
        put(WeaponEnums.SOLAR_ATOMISER, new Wargear(new Weapon("Solar Atomiser", 18, "D3", 2, 14, 4, "3")));
        put(WeaponEnums.ARC_SCOURGE, new Wargear(new Weapon("Arc Scourge", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.DEVASTATING_WOUNDS)); add(new wepAbilities(Keywords.EXTRA_ATTACKS));}}, 0, "4", 2, 5, 1, "1")));
        put(WeaponEnums.CAWLS_OMNISSIAN_AXE, new Wargear(new Weapon("Cawl's Omnissian Axe", 0, "4", 2, 8, 2, "2")));
        put(WeaponEnums.MECHADENDRITE_HIVE, new Wargear(new Weapon("Mechadendrite Hive", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.EXTRA_ATTACKS));}}, 0, "2D6", 3, 4 ,0, "1")));

        // Vehicles
        put(WeaponEnums.COGNIS_HEAVY_STUBBER_ARRAY, new Wargear(new Weapon("Cognis Heavy Stubber Array", new HashSet<wepAbilities>(){{add(new wepAbilitiesExtra(Keywords.RAPID_FIRE, 9)); add(new wepAbilitiesExtra(Keywords.SUSTAINED_HITS, 1));}}, 36, "9", 4, 4, 0, "1")));
        put(WeaponEnums.COGNIS_HEAVY_STUBBER, new Wargear(new Weapon("Cognis Heavy Stubber", new HashSet<wepAbilities>(){{add(new wepAbilitiesExtra(Keywords.RAPID_FIRE, 3)); add(new wepAbilitiesExtra(Keywords.SUSTAINED_HITS, 1));}}, 36, "3", 4, 4, 0, "1")));
        put(WeaponEnums.HEAVY_PHOSPHOR_BLASTER, new Wargear(new Weapon("Heavy Phosphor Blaster", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.IGNORES_COVER));}}, 36, "3", 4, 6, 1, "1")));
        put(WeaponEnums.TWIN_COGNIS_LASCANNON, new Wargear(new Weapon("Twin Cognis Lascannon", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.TWIN_LINKED));}},48, "1", 4, 12, 3, "D6+1")));
        put(WeaponEnums.TWIN_COGNIS_AUTOCANNON, new Wargear(new Weapon("Twin Cognis Autocannon", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.TWIN_LINKED));}}, 48, "2", 4, 9, 1, "3")));
        put(WeaponEnums.ARMOURED_HULL, new Wargear(new Weapon("Armoured Hull", 0, "3", 4, 6, 0, "1")));

            // WARGEAR
            put(WeaponEnums.CHAFF_LAUNCHER, new Wargear("Chaff Launcher"));
            put(WeaponEnums.COMMAND_UPLINK, new Wargear("Command Uplink"));

        // Skitarii
        put(WeaponEnums.ARCHEOTECH_PISTOL, new Wargear(new Weapon("Archeotech Pistol",12, "1", 4, 6, 1, "6", new wepAbilities(Keywords.DEVASTATING_WOUNDS), new wepAbilities(Keywords.PISTOL))));
        put(WeaponEnums.ARC_RIFLE, new Wargear(new Weapon("Arc Rifle",30, "1", 4, 8, 1, "6", new wepAbilitiesAnti(Model.ModelKeywords.VEHICLE, 4), new wepAbilities(Keywords.DEVASTATING_WOUNDS), new wepAbilitiesExtra(Keywords.RAPID_FIRE, 1))));
        put(WeaponEnums.GALVANIC_RIFLE, new Wargear(new Weapon("Galvanic Rifle",30, "2", 4, 4, 0, "1")));
        put(WeaponEnums.RADIUM_CARBINE, new Wargear(new Weapon("Radium Carbine",48, "1", 4, 12, 3, "6", new wepAbilitiesAnti(Model.ModelKeywords.INFANTRY, 4))));
        put(WeaponEnums.PLASMA_CALIVER, new Wargear("Plasma Caliver", new ArrayList<Weapon>() {{
            add(new Weapon("Plasma Caliver - Standard" ,30, "2", 4, 7, 2, "1"));
            add(new Weapon("Plasma Caliver - Supercharge" , 30, "2", 4, 8, 2, "2", new wepAbilities(Keywords.HAZARDOUS)));
        }}));
        put(WeaponEnums.TRANSURANIC_ARQUEBUS, new Wargear(new Weapon("Transuranic Arquebus",36, "1", 4, 7, 2, "6", new wepAbilities(Keywords.HEAVY), new wepAbilities(Keywords.PRECISION))));
        put(WeaponEnums.SKITARII_RANGER_COMBAT_WEAPON, new Wargear(new Weapon("Alpha Combat Weapon", 0, "2", 4, 5, 1, "1")));
            // Wargear Abilities
            put(WeaponEnums.ENHANCED_DATA_TETHER, new Wargear("Enhanced Data-Tether"));
            put(WeaponEnums.OMNISPEX, new Wargear("Omnispex"));

        // Kataphron Breachers
        put(WeaponEnums.HEAVY_ARC_RIFLE, new Wargear(new Weapon("Heavy Arc Rifle",36, "2", 4, 8, 2, "3", new wepAbilitiesExtra(Keywords.RAPID_FIRE, 2), new wepAbilitiesAnti(Model.ModelKeywords.VEHICLE, 4))));
        put(WeaponEnums.TORSION_CANNON, new Wargear(new Weapon("Torsion Cannon",48, "D3", 4, 6, 2, "2", new wepAbilitiesAnti(Model.ModelKeywords.INFANTRY, 2))));
        put(WeaponEnums.ARC_CLAW, new Wargear(new Weapon("Arc Claw",0, "4", 4, 5, 1, "1")));
        put(WeaponEnums.HYDRAULIC_CLAW, new Wargear(new Weapon("Hydraulic Claw",0, "2", 4, 8, 2, "3")));

        // Kataphron Destroyers
        put(WeaponEnums.COGNIS_FLAMER, new Wargear(new Weapon("Cognis Flamer", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.IGNORES_COVER)); add(new wepAbilities(Keywords.TORRENT));}}, 12, "D6", 3, 4, 0, "1")));
        put(WeaponEnums.HEAVY_GRAV_CANNON, new Wargear(new Weapon("Heavy Grav-Cannon",30, "4", 4, 6, 1, "2")));
        put(WeaponEnums.KATAPHRON_PLASMA_CULVERIN, new Wargear(new Weapon("Kataphron Plasma Culverin",30, "4", 4, 6, 1, "2")));
        // Plasma Profile
        put(WeaponEnums.PHOSPHOR_BLASTER, new Wargear(new Weapon("Phosphor Blaster", 24, "1", 4, 5, 0, "1")));
        put(WeaponEnums.KATAPHRON_CLOSE_COMBAT, new Wargear(new Weapon("Close Combat Weapon", 0, "2", 4, 5, 0, "1")));

        // Sicarians
        put(WeaponEnums.FLECHETTE_BLASTER, new Wargear(new Weapon("Flechette Blaster", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.PISTOL));}}, 12, "5", 4, 3, 0, "1")));
        put(WeaponEnums.STUBCARBINE, new Wargear(new Weapon("Stubcarbine", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.PISTOL));}}, 12, "3", 4, 4, 0, "1")));

        put(WeaponEnums.SICARIAN_POWER_WEAPON, new Wargear(new Weapon("Power Weapon", 0, "2", 4, 4, 2, "1")));
        put(WeaponEnums.TASER_GOAD, new Wargear(new Weapon("Taser Goad", 0, "2", 4, 6, 0, "1")));

        // Skorpius
        put(WeaponEnums.BELLEROS_ENERGY_CANNON, new Wargear(new Weapon("Belleros Energy Cannon", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.BLAST)); add(new wepAbilities(Keywords.INDIRECT_FIRE));}}, 36, "2D6", 4, 7, 2, "1")));
        put(WeaponEnums.DISRUPTOR_MISSILE_LAUNCHER, new Wargear(new Weapon("Disruptor Missile Launcher", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.TWIN_LINKED));}}, 36, "3", 4, 9, 2, "D6")));
        put(WeaponEnums.FERRUMITE_CANNON, new Wargear(new Weapon("Ferrumite Cannon", 48, "3", 4, 12, 3, "D6")));

        // Kastelan Robots
        put(WeaponEnums.INCENDINE_COMBUSTOR, new Wargear(new Weapon("Indendine Combustor",12, "D6", 0, 6, 1, "1")));
        put(WeaponEnums.KASTELAN_PHOSPHOR_BLASTER, new Wargear(new Weapon("Kastelan Phosphor Blaster",new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.IGNORES_COVER));}}, 24, "3", 4, 6, 0, "2")));
        put(WeaponEnums.TWIN_KASTELAN_PHOSPHOR_BLASTER, new Wargear(new Weapon("Twin Kastelan Phosphor Blaster", new HashSet<wepAbilities>(){{add (new wepAbilities(Keywords.TWIN_LINKED)); add(new wepAbilities(Keywords.IGNORES_COVER));}}, 24, "3", 4, 6, 0, "2")));
        put(WeaponEnums.KASTELAN_CLOSE_COMBAT_WEAPON, new Wargear(new Weapon("Close Combat Weapon", 0, "3", 4, 6, 0, "1")));
        put(WeaponEnums.KASTELAN_FIST, new Wargear(new Weapon("Kastelan Fist", 0, "4", 4, 12, 2, "3")));
        put(WeaponEnums.TWIN_KASTELAN_FIST, new Wargear(new Weapon("Twin Kastelan Phosphor Blaster", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.TWIN_LINKED));}}, 0, "4", 4, 12, 2, "3")));

        // Ironstrider Engines
        put(WeaponEnums.PHOSOPHOR_SERPENTA, new Wargear(new Weapon("Phosphor Serpenta", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.IGNORES_COVER));}}, 18, "1", 4, 5, 1, "2")));
        put(WeaponEnums.RADIUM_JEZZAIL, new Wargear(new Weapon("Radium Jezzail", new HashSet<wepAbilities>(){{add(new wepAbilitiesAnti(Model.ModelKeywords.INFANTRY, 3)); add(new wepAbilities(Keywords.HEAVY)); add(new wepAbilities(Keywords.PRECISION));}},36, "1", 4, 5, 2, "3")));
        put(WeaponEnums.IRONSTRIDER_FEET, new Wargear(new Weapon("Ironstrider Feet", 0, "3", 4, 5, 0, "1")));
        put(WeaponEnums.TASER_LANCE, new Wargear(new Weapon("Taser Lance", new HashSet<wepAbilities>(){{add(new wepAbilitiesAnti(Model.ModelKeywords.WALKER, 2)); add(new wepAbilitiesExtra(Keywords.SUSTAINED_HITS, 2));}}, 0, "4", 4, 7, 2, "2")));

        // Corpuscarii
        put(WeaponEnums.ELECTROSTATIC_GAUNTLETS_RANGED, new Wargear(new Weapon("Electrostatic Gauntlets", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.PISTOL)); add(new wepAbilitiesExtra(Keywords.SUSTAINED_HITS, 2));}}, 12, "3", 3, 5, 0, "1")));
        put(WeaponEnums.ELECTROSTATIC_GAUNTLETS_MELEE, new Wargear(new Weapon("Electrostatic Gauntlets", new HashSet<wepAbilities>(){{add(new wepAbilitiesExtra(Keywords.SUSTAINED_HITS, 2));}}, 0, "3", 4, 5, 0, "1")));
        put(WeaponEnums.ELECTROLEECH_STAVE, new Wargear(new Weapon("Electroleech Stave", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.DEVASTATING_WOUNDS));}}, 0, "2", 3, 6, 1, "2")));

        // Tech Priest Dominus
        put(WeaponEnums.ERADICATION_RAY, new Wargear(new Weapon("Eradication Ray", 24, "2", 3, 6, 1, "2")));
        put(WeaponEnums.MACROSTUBBER, new Wargear(new Weapon("Macrostubber", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.PISTOL));}}, 36, "1", 4, 5, 2, "3")));
        put(WeaponEnums.VOLKITE_BLASTER, new Wargear(new Weapon("Vokite Blaster", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.DEVASTATING_WOUNDS));}}, 0, "4", 4, 7, 2, "2")));
        put(WeaponEnums.OMNISSIAN_AXE, new Wargear(new Weapon("Omnissian Axe", 0, "4", 3, 6, 2, "2")));

        // Tech Priest Enginseer
        put(WeaponEnums.SERVO_ARM, new Wargear(new Weapon("Servo-arm", 0, "1", 4, 6, 2, "2")));

        // Tech Priest Manipulus
        put(WeaponEnums.MAGNARAIL_LANCE, new Wargear(new Weapon("Magnarail Lance", 36, "1", 3, 7, 2, "3")));
        put(WeaponEnums.TRANSONIC_CANNON, new Wargear( new Weapon("Transonic Cannon", 12, "6", 1, 4, 0, "2")));
        put(WeaponEnums.OMNISSIAN_STAFF, new Wargear(new Weapon("Omnissian Staff", 0, "4", 3, 6, 1, "2")));

        // Cybernetica Datasmith
        put(WeaponEnums.SERVO_ARC_CLAW, new Wargear(new Weapon("Servo-arc Claw", 0, "3", 4, 8, 2, "2")));




        // Imperial Knights

        // Ranged Weapons
        put(WeaponEnums.ARMIGER_AUTOCANNON, new Wargear(new Weapon("Armiger Autocannon", 48, "4", 3, 9, 1, "3")));
        put(WeaponEnums.CONFLAGURATION_CANNON, new Wargear(new Weapon("Conflaguration Cannon", 18, "3D6", 0, 8, 1, "2", new wepAbilities(Keywords.IGNORES_COVER), new wepAbilities(Keywords.TORRENT))));
        put(WeaponEnums.AVENGER_GATLING_CANNON, new Wargear(new Weapon("Avenger Gatling Cannon", 36, "18", 3, 6, 2, "2")));
        put(WeaponEnums.DOMINUS_TITANIC_FEET, new Wargear(new Weapon("Titanic Feet", 0, "4", 4, 8, 1, "2")));
        put(WeaponEnums.HEAVY_FLAMER, new Wargear(new Weapon("Heavy Flamer", 12, "D6", 0, 5, 1, "1", new wepAbilities(Keywords.IGNORES_COVER), new wepAbilities(Keywords.TORRENT))));
        put(WeaponEnums.ICARUS_AUTOCANNONS, new Wargear(new Weapon("Icarus Autocannons", 48, "3", 3, 7, 1, "2")));
        put(WeaponEnums.IRONSTORM_MISSILE_PODS, new Wargear(new Weapon("Ironstorm Missile Pods", 48, "D6+1", 3, 5, 0, "1")));
        put(WeaponEnums.MELTAGUN, new Wargear(new Weapon("Meltagun", 12, "1", 3, 9, 4, "D6", new wepAbilitiesExtra(Keywords.MELTA, 2))));
        put(WeaponEnums.PLASMA_DECIMATOR, new Wargear("Plasma Decimator", new ArrayList<Weapon>() {{
            add(new Weapon("Plasma Decimator - Standard", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.BLAST));}}, 48, "D6+3", 3, 8, 2, "2"));
            add(new Weapon("Plasma Decimator - Supercharge", new HashSet<wepAbilities>(){{add(new wepAbilities(Keywords.BLAST)); add(new wepAbilities(Keywords.HAZARDOUS));}}, 48, "D6+3", 3, 9, 3, "3"));
        }}));
        put(WeaponEnums.QUESTORIS_HEAVY_STUBBER, new Wargear(new Weapon("Questoris Heavy Stubber", 36, "3", 3, 4, 1, "1", new wepAbilitiesExtra(Keywords.RAPID_FIRE, 3))));
        put(WeaponEnums.RAPIDFIRE_BATTLE_CANNON, new Wargear(new Weapon("Rapid-fire Battle Cannon", 72, "D6+3", 3, 10, 1, "3", new wepAbilities(Keywords.BLAST), new wepAbilitiesExtra(Keywords.RAPID_FIRE, "D6+3"))));
        put(WeaponEnums.SHIELDBREAKER_MISSILE_LAUNCHER, new Wargear(new Weapon("Servo-arc Claw", 0, "3", 4, 8, 2, "2", new wepAbilitiesAnti(Model.ModelKeywords.TITANIC, 4), new wepAbilities(Keywords.DEVASTATING_WOUNDS))));
        put(WeaponEnums.STORMSPEAR_ROCKET_POD, new Wargear(new Weapon("Stormspear Rocket Pod", 48, "3", 3, 8, 2, "2")));
        put(WeaponEnums.THERMAL_CANNON, new Wargear(new Weapon("Thermal Cannon", 36, "3", 3, 4, 1, "1", new wepAbilities(Keywords.BLAST), new wepAbilitiesExtra(Keywords.MELTA, 6))));
        put(WeaponEnums.THERMAL_SPEAR, new Wargear(new Weapon("Thermal Spear", 24, "2", 3, 12, 4, "D6", new wepAbilitiesExtra(Keywords.MELTA, 4))));
        put(WeaponEnums.THUNDERCOIL_HARPOON, new Wargear(new Weapon("Thundercoil Harpoon", 18, "1", 2, 24, 6, "12", new wepAbilitiesAnti(Model.ModelKeywords.TITANIC, 4), new wepAbilitiesAnti(Model.ModelKeywords.VEHICLE, 4), new wepAbilities(Keywords.DEVASTATING_WOUNDS))));
        put(WeaponEnums.TWIN_MELTAGUN, new Wargear(new Weapon("Twin Meltagun", 12, "1", 3, 9, 4, "D6", new wepAbilitiesExtra(Keywords.MELTA, "D6"), new wepAbilities(Keywords.TWIN_LINKED))));
        put(WeaponEnums.TWIN_SIEGEBREAKER_CANNON, new Wargear(new Weapon("Twin Siegebreaker Cannon", 36, "D6", 3, 6, 0, "1", new wepAbilities(Keywords.BLAST), new wepAbilities(Keywords.TWIN_LINKED))));
        put(WeaponEnums.VOLCANO_LANCE, new Wargear(new Weapon("Volcano Lance", 72, "D3", 3, 18, 5, "D6+8", new wepAbilities(Keywords.BLAST))));

        // Melee Weapons
        put(WeaponEnums.ARMIGER_FEET, new Wargear(new Weapon("Armoured Feet", 0, "4", 3, 6, 0, "1")));
        put(WeaponEnums.REAPER_CHAINSWORD, new Wargear("Reaper Chainsword", new ArrayList<Weapon>() {{
            add(new Weapon("Reaper Chainsword - Strike", 0, "4", 3, 14, 4, "6"));
            add(new Weapon("Reaper Chainsword - Sweep",  0, "12", 3, 9, 3, "2"));
        }}));
        put(WeaponEnums.REAPER_CHAIN_CLEAVER, new Wargear("Reaper Chain Cleaver", new ArrayList<Weapon>() {{
            add(new Weapon("Reaper Chain Cleaver - Strike", 0, "4", 3, 10, 3, "8"));
            add(new Weapon("Reaper Chain Cleaver - Sweep",  0, "8", 3, 8, 2, "1"));
        }}));
        put(WeaponEnums.TITANIC_FEET, new Wargear(new Weapon("Titanic Feet", 0, "4", 3, 8, 1, "2")));
        put(WeaponEnums.THUNDERSTRIKE_GAUNTLET, new Wargear("Thunderstrike Gauntlet", new ArrayList<Weapon>(){{
            add(new Weapon("Thunderstrike Gauntlet - Strike", 0, "4", 3, 20, 3, "8"));
            add(new Weapon("Thunderstrike Gauntlet - Sweep",  0, "8", 3, 10, 2, "3"));
        }}));


    }};

    public static final String TAG = Unit.TAG + "/ Weapon Class";

    private String name;
    private Integer range;
    private String attacks;
    private Integer bs;
    private Integer strength;
    private Integer ap;
    private String damage;
    private HashSet<wepAbilities> keywords;
    public static HashSet<wepAbilities> staticKeywordsList = new HashSet<wepAbilities>() {{
       // add()
    }};
    public enum Keywords {
        ASSAULT, BLAST, CONVERSION, DEVASTATING_WOUNDS, HAZARDOUS, HEAVY, INDIRECT_FIRE,
        IGNORES_COVER, LETHAL_HITS, LINKED_FIRE, MELTA, PISTOL, PRECISION, PSYCHIC, RAPID_FIRE,
        SUSTAINED_HITS, TORRENT, TWIN_LINKED, EXTRA_ATTACKS, ANTI;

        public String getDescription() {
            switch (this) {
                case ASSAULT:
                    return "Can be shot even if the bearer’s unit Advanced.";
                case BLAST:
                    return "Add 1 to the Attacks characteristic for every five models in the target unit";
                case CONVERSION:
                    return "";
                case DEVASTATING_WOUNDS:
                    return "Saving throws cannot be made against Critical Wounds scored by this weapon (including invulnerable saving throws).";
                case HAZARDOUS:
                    return "After a unit shoots or fights, roll one Hazardous test (one D6) for each Hazardous weapon used. For each 1, one model equipped with a Hazardous weapon is destroyed (CHARACTERS, MONSTERS and VEHICLES suffer 3 mortal wounds instead).";
                case HEAVY:
                    return "Add 1 to Hit rolls if the bearer’s unit Remained Stationary this turn.";
                case INDIRECT_FIRE:
                    return "Can target and make attacks against units that are not visible to the attacking unit";
                case IGNORES_COVER:
                    return "Each time an attack is made with such a weapon, the target cannot have the Benefit of Cover against that attack.";
                case LETHAL_HITS:
                    return "Critical Hits automatically wound the target.";
                case LINKED_FIRE:
                    return "";
                case MELTA:
                    return "[MELTA X]: Increase the Damage by ‘x’ when targeting units within half range.";
                case PISTOL:
                    return "Can be shot even if the bearer’s unit is within Engagement Range of enemy units, but must target one of those enemy units.";
                case PRECISION:
                    return "Can Target Character Models directly";
                case PSYCHIC:
                    return "";
                case RAPID_FIRE:
                    return "[RAPID FIRE X]: Increase the Attacks by ‘x’ when targeting units within half range.";
                case SUSTAINED_HITS:
                    return "[SUSTAINED HITS X]: Each Critical Hit scores ‘x’ additional hits on the target.";
                case TORRENT:
                    return "Attacks automatically hits the target.";
                case TWIN_LINKED:
                    return "Re-roll Failed Wound Rolls";
                case EXTRA_ATTACKS:
                    return "The bearer can attack with this weapon in addition to any other weapons it can make attacks with.";
                case ANTI:
                    return "[ANTI-KEYWORD X+]: An unmodified Wound roll of ‘x+’ against a target with the matching keyword scores a Critical Wound.";


            }
            return "";
        }

    }
    public static class wepAbilities implements Serializable {
        //         ASSAULT, BLAST, DEVASTATING_WOUNDS, HAZARDOUS, HEAVY, INDIRECT_FIRE,
        //        IGNORES_COVER, LETHAL_HITS, PISTOL, PRECISION, PSYCHIC,
        //        TORRENT, TWIN_LINKED, EXTRA_ATTACKS;

        public Keywords keyword;

        // CONVERSION, RAPID_FIRE, SUSTAINED_HITS
        public wepAbilities(Keywords keyword) {

            this.keyword = keyword;
            // Ex Sustained Hits 2
        }

        public String toString() {

            return  keyword.toString();
        }
    }

    public static class wepAbilitiesExtra extends wepAbilities {
        // Ex Sustained Hits 2


        // CONVERSION, RAPID_FIRE, SUSTAINED_HITS
        String triggerAmount;
        public wepAbilitiesExtra(Keywords keyword, int triggerAmount) {
            super(keyword);
            this.triggerAmount = String.valueOf(triggerAmount);

        }
        public wepAbilitiesExtra(Keywords keyword, String triggerAmount) {
            super(keyword);
            this.triggerAmount = triggerAmount;

        }
    }
    public static class wepAbilitiesAnti extends wepAbilities {

        // ex. Anti-Infantry 3+

        Model.ModelKeywords triggerKeyword;
        int triggerOn;
        public wepAbilitiesAnti(Model.ModelKeywords triggerKeyword, int triggerOn) {
            super(Keywords.ANTI);
            this.triggerKeyword = triggerKeyword;
            this.triggerOn = triggerOn;
        }
    }

    public Weapon(String name, Integer range, String attacks, Integer bs, Integer strength, Integer ap, String damage) {
        this.name = name;
        this.keywords = new HashSet<wepAbilities>();
        this.range = range;
        this.attacks = attacks;
        this.bs = bs;
        this.strength = strength;
        this.ap = ap;
        this.damage = damage;
    }

    public Weapon(String name, HashSet<wepAbilities> keywords, Integer range, String attacks, Integer bs, Integer strength, Integer ap, String damage) {
        this(name, range, attacks, bs, strength, ap, damage);
        this.keywords = keywords;
    }

    public Weapon(String rawString) {

        Pattern regexp = Pattern.compile("/(.+) (\\[.+\\]) (\\d+\\\"|N\\/A) (\\d+) (\\d\\+) (.*) (.*) (.*)/gm");

    }

    public Weapon(String name, Integer range, String attacks, Integer bs, Integer strength, Integer ap, String damage, wepAbilities... wepAbilities) {
        this(name, range, attacks, bs, strength, ap, damage);

        this.keywords = new HashSet<>();
        for (wepAbilities wepAbility : wepAbilities) {
            keywords.add(wepAbility);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeSerializable(keywords);
        parcel.writeInt(range);
        parcel.writeString(attacks);
        parcel.writeInt(bs);
        parcel.writeInt(strength);
        parcel.writeInt(ap);
        parcel.writeString(damage);
    }
    protected Weapon(Parcel in) {
        this.name = in.readString();
        Log.v("Weapon (Parcel in)" , "Name = " + name.toString());
        this.keywords = new HashSet<wepAbilities>();
        this.keywords = (HashSet<wepAbilities>) in.readSerializable();
        Log.v("Weapon (Parcel in)" , "Weapon = " + keywords.toString());
        this.range = in.readInt();
        Log.v("Weapon (Parcel in)" , "Range = " +range);
        this.attacks = in.readString();
        Log.v("Weapon (Parcel in)" , "Attacks = " +attacks);
        this.bs = in.readInt();
        Log.v("Weapon (Parcel in)" , "BS/WS = " +bs);
        this.strength = in.readInt();
        Log.v("Weapon (Parcel in)" , "Strength = " +strength);
        this.ap = in.readInt();
        Log.v("Weapon (Parcel in)"  , "AP = " +ap);
        this.damage = in.readString();
        Log.v("Weapon (Parcel in)"  , "Damage = " +damage);
       // this.quanitiy = 0;
    }
    public static final Creator<Weapon> CREATOR = new Creator<Weapon>() {
        @Override
        public Weapon createFromParcel(Parcel in) {
            return new Weapon(in);
        }

        @Override
        public Weapon[] newArray(int size) {
            return new Weapon[size];
        }
    };
    public void addKeyword() {

    }
    public Map<Pair<Integer, Integer>, Double> attack(Unit target, EnumSet<DamageCalculator.AttackFlags> attackFlags) {
        Log.v(TAG, this.name +" firing at " +target.name);
        Log.v(TAG, this.name +" attack flags " +attackFlags);


        // ATTACK GENERATION SEQUENCE
        //
        //
        Double attacks = (double) Integer.parseInt(getAttacks());
        Log.v(TAG, this.name +" attacks " +attacks);

        if (keywords.contains(Keywords.BLAST)) {
       //     attacks
        }

        Log.v(TAG, "BS " +bs);

        // Parses bs String as a percentage of hits
        Double hitPercentage;
        if (bs.equals("2+")) {
            hitPercentage = (double) 5/6;
        } else if (bs.equals("3+")) {
            hitPercentage = (double) 4/6;
        } else if (bs.equals("4+")) {
            hitPercentage = (double) 3/6;
        } else if (bs.equals("5+")) {
            hitPercentage = (double) 2/6;
        } else if (bs.equals("6+")) {
            hitPercentage = (double) 1/6;
        } else {
            Log.v(TAG, "FATAL ERROR!!!");
            throw new RuntimeException();
        }

        // Re-calculate's hit percentage due to for  re-rolls
        if (attackFlags.contains(DamageCalculator.AttackFlags.RR_HITS_ALL)) {
            hitPercentage += ((1 - hitPercentage) * hitPercentage);
        } else if (attackFlags.contains(DamageCalculator.AttackFlags.RR_HITS_OF_ONE)) {
            hitPercentage += ( (double) 1/6 * hitPercentage);
        }

        Log.v(TAG, "Hitting " +hitPercentage +" of the time!");

        Map<Integer, Double> numHits = new HashMap<Integer, Double>();

        numHits.put(0, 1.0);

        for (int i = 0; i < attacks; i++) {
            numHits = probabilitySplit(numHits, hitPercentage);
        }
        Log.v(TAG, " " +numHits);

        // Performs any re-rolls that need to happen

        // Determines weapon Strength
        Double strength = (double) getStrength();
        // Determines average toughness of defending unit
        Double toughness = target.getModel().getStatline().getToughness().doubleValue();
        Log.v(TAG, "Strength " +strength +" vs " +toughness);

        // Wound sequence
        double strengthOverToughness = strength / toughness;
        double woundPercentage;
        // If strength is double toughness
        if(strengthOverToughness >= 2) {
            // Wounds on 2's
            woundPercentage = (double) 5/6;
        } else if (strengthOverToughness > 1) {
            woundPercentage = (double) 4/6;
            // Wounds on 3's
        } else if (strengthOverToughness == 1) {
            woundPercentage = (double) 3/6;
            // Wounds on 4's
        } else if (strengthOverToughness > 0.5) {
            woundPercentage = (double) 2/6;
            // Wounds on 5's
        } else {
            woundPercentage = (double) 1/6;
        }

        // Re-calculate's wound percentage due to re-rolls
        if (attackFlags.contains(DamageCalculator.AttackFlags.RR_WOUNDS_ALL)) {
            woundPercentage += ((1 - woundPercentage) * woundPercentage);
        } else if (attackFlags.contains(DamageCalculator.AttackFlags.RR_WOUNDS_OF_ONE)) {
            woundPercentage += ( (double) 1/6 * woundPercentage);
        }

        Log.v(TAG, "Wounding on a : " + (int) (7 - (6 * woundPercentage)) +"+");


        Map<Integer, Double> woundsProbability = new HashMap<Integer, Double>();
        woundsProbability.put(0, 1.0);

        Map<Integer, Double> numHitAndWound = new HashMap<Integer, Double>();
        for (Map.Entry<Integer, Double> entry : numHits.entrySet()) {

            // For each outcome in the current wound probability map split into hits and misses
            if (entry.getKey() != 0) {
                woundsProbability = probabilitySplit(woundsProbability, woundPercentage);
            }

         //   Log.v(TAG, "Wound Probability for this matrix" + woundsProbability);
            numHitAndWound = probabilitySplit2(numHitAndWound, entry, woundsProbability);

        }

        numHits = numHitAndWound;

        Log.v(TAG, "# Wounds Probability:" +numHits);

        // Want damage probability per wound given
        // Get Save_Perc
        Integer sv = target.getModel().getStatline().getArmorSave();
        double savePercentage;
        if (sv.equals("2+")) {
            savePercentage = (double) 5/6;
        } else if (sv.equals("3+")) {
            savePercentage = (double) 4/6;
        } else if (sv.equals("4+")) {
            savePercentage = (double) 3/6;
        } else if (sv.equals("5+")) {
            savePercentage = (double) 2/6;
        } else if (sv.equals("6+")) {
            savePercentage = (double) 1/6;
        } else {
            savePercentage = (double) 0;
        }
        Log.v(TAG, "Armour save of " +sv );


        // Determines AP value of weapon
        int ap;
        if (this.ap.equals("0")) {
            ap = 0;
        } else if (this.ap.equals("-1")) {
            ap = 1;
        } else if (this.ap.equals("-2")) {
            ap = 2;
        } else if (this.ap.equals("-3")) {
            ap = 3;
        } else if (this.ap.equals("-4")) {
            ap = 4;
        } else {
            ap = 5;
        }
        Log.v(TAG, "AP -"+ap);

        // Applies AP
        savePercentage -=  ((double) 1/6) * ap;

        if (attackFlags.contains(DamageCalculator.AttackFlags.IN_COVER)) {
            Log.v(TAG, "In cover ");
            savePercentage += ((double) 1/6);
        }

        // Even if save is modified above a 2+, ensures that rolls of 1 will still fail
        if (savePercentage > ((double) 5/6)) {
            savePercentage = ((double) 5/6);
        }

        Log.v(TAG, "Saving on a " + (int) (7 - (6 * savePercentage)) +"+");


        woundsProbability = numHits;
        Map<Integer, Double> saveProbability =  new HashMap<Integer, Double>();
        saveProbability.put(0, 1.0);
        Map<Integer, Double> numUnsavedWounds = new HashMap<Integer, Double>();
        for (Map.Entry<Integer, Double> entry : woundsProbability.entrySet()) {

            if (entry.getKey() != 0) {
                saveProbability = probabilitySplit(saveProbability, 1 - savePercentage);
            }

            numUnsavedWounds = probabilitySplit2(numUnsavedWounds, entry, saveProbability);

        }

        Log.v(TAG, "# Unsaved Wounds " +numUnsavedWounds);

        // Determine damage characteristic
        int damage = Integer.parseInt(this.damage);
        // Damage parser
        Map<Integer, Double> damageProbability = new HashMap<Integer, Double>();
        // Probability split on damage characteristics
        // TODO
        // Flat Damage
        damageProbability.put(damage, 1.0);
        Map<Pair<Integer, Integer>, Double> woundsDealtProbability = new HashMap<Pair<Integer, Integer>, Double>();
        woundsDealtProbability.put(new Pair<Integer, Integer>(0,0), 1.0);
        Map<Pair<Integer, Integer>, Double> modelsKilledProbability = new HashMap<Pair<Integer, Integer>, Double>();
        for (Map.Entry<Integer, Double> unsavedWoundsOutcome : numUnsavedWounds.entrySet()) {

            // FNP CALC
            // TODO

            // Apply damage characteristic to all possible outcomes so far
            Map<Pair<Integer, Integer>, Double> newWoundsDealtProbability = new HashMap<Pair<Integer, Integer>, Double>();
            for (Map.Entry<Pair<Integer, Integer>, Double> woundsDealtOutcome : woundsDealtProbability.entrySet()) {

                for (Map.Entry<Integer, Double> damageOutcome : damageProbability.entrySet()) {
                    Pair<Integer, Integer> deadModelsAndWounds;
                    if (woundsDealtOutcome.getKey().second + damageOutcome.getKey() >= Integer.valueOf(target.getModel().getStatline().wounds)) {
                        // Damage has killed model
                        deadModelsAndWounds = new Pair<Integer, Integer>(woundsDealtOutcome.getKey().first + 1, 0);
                    } else {
                        // Damage has wounded model
                        deadModelsAndWounds = new Pair<Integer, Integer>(woundsDealtOutcome.getKey().first, woundsDealtOutcome.getKey().second + damageOutcome.getKey());
                    }
                    newWoundsDealtProbability.put(deadModelsAndWounds, newWoundsDealtProbability.getOrDefault(deadModelsAndWounds, 0.0) + woundsDealtOutcome.getValue() * damageOutcome.getValue());
                }

            }
            woundsDealtProbability = newWoundsDealtProbability;
            Log.v(TAG, "WoundsDealtProbability: " +woundsDealtProbability);

            for (Map.Entry<Pair<Integer, Integer>, Double> woundsDealtOutcome : woundsDealtProbability.entrySet()) {
                modelsKilledProbability.put(woundsDealtOutcome.getKey(), modelsKilledProbability.getOrDefault(woundsDealtOutcome, 0.0) + (unsavedWoundsOutcome.getValue() * woundsDealtOutcome.getValue()));
            }
        }

        Log.v(TAG, "# wounds dealt: " +modelsKilledProbability);

        return modelsKilledProbability;
    }
    public Map<Pair<Integer, Integer>, Double> attackModel(Model model, EnumSet<DamageCalculator.AttackFlags> attackFlags) {


        Log.v(TAG, this.name +" firing at " +model.getName());
        Log.v(TAG, this.name +" attack flags " +attackFlags);


        // ATTACK GENERATION SEQUENCE
        Double attacks;
        try {
            attacks = (double) Integer.parseInt(getAttacks());
        } catch (NumberFormatException exception) {
            attacks = 0.0;

            Pattern numberedD6 = Pattern.compile("([0-9])D|d6");
            Pattern numberedD3 = Pattern.compile("([0-9])D|d3");

            Matcher matcher = numberedD6.matcher(this.attacks);

            // Adds avg of 3.5 attacks per D6
            if (matcher.find()) {

                int num = 0;

                // If there is no number present before the D6
                if (matcher.group(1).equals("")) {
                    num = 1;
                } else {
                    num = Integer.parseInt(matcher.group(1));
                }

                attacks += (3.5) * num;
            }

            matcher = numberedD3.matcher(this.attacks);

            // Adds avg of 3.5 attacks per D6
            if (matcher.find()) {

                int num = 0;

                // If there is no number present before the D6
                if (matcher.group(1).equals("")) {
                    num = 1;
                } else {
                    num = Integer.parseInt(matcher.group(1));
                }

                attacks += (2) * num;
            }


            // Adds avg of 2 attacks per D6
            // Adds flat amount of attacks
        }


        Log.v(TAG, this.name +" attacks " +attacks);

        if (keywords.contains(Keywords.BLAST)) {
            //     attacks
        }

        Log.v(TAG, "BS " +bs);

        // Parses bs String as a percentage of hits
        Double hitPercentage;
        if (bs == 2) {
            hitPercentage = (double) 5/6;
        } else if (bs == 3) {
            hitPercentage = (double) 4/6;
        } else if (bs == 4) {
            hitPercentage = (double) 3/6;
        } else if (bs == 5) {
            hitPercentage = (double) 2/6;
        } else if (bs == 6) {
            hitPercentage = (double) 1/6;
        } else {
            Log.v(TAG, "FATAL ERROR!!!");
            throw new RuntimeException();
        }

        // Re-calculate's hit percentage due to for  re-rolls
        if (attackFlags.contains(DamageCalculator.AttackFlags.RR_HITS_ALL)) {
            hitPercentage += ((1 - hitPercentage) * hitPercentage);
        } else if (attackFlags.contains(DamageCalculator.AttackFlags.RR_HITS_OF_ONE)) {
            hitPercentage += ( (double) 1/6 * hitPercentage);
        }

        Log.v(TAG, "Hitting " +hitPercentage +" of the time!");

        Map<Integer, Double> numHits = new HashMap<Integer, Double>();

        numHits.put(0, 1.0);

        for (int i = 0; i < attacks; i++) {
            numHits = probabilitySplit(numHits, hitPercentage);
        }
        Log.v(TAG, " " +numHits);

        // Performs any re-rolls that need to happen

        // Determines weapon Strength
        Double strength = (double) getStrength();
        // Determines average toughness of defending unit
        Double toughness = model.getStatline().getToughness().doubleValue();
        Log.v(TAG, "Strength " +strength +" vs " +toughness);

        // Wound sequence
        double strengthOverToughness = strength / toughness;
        double woundPercentage;
        // If strength is double toughness
        if(strengthOverToughness >= 2) {
            // Wounds on 2's
            woundPercentage = (double) 5/6;
        } else if (strengthOverToughness > 1) {
            woundPercentage = (double) 4/6;
            // Wounds on 3's
        } else if (strengthOverToughness == 1) {
            woundPercentage = (double) 3/6;
            // Wounds on 4's
        } else if (strengthOverToughness > 0.5) {
            woundPercentage = (double) 2/6;
            // Wounds on 5's
        } else {
            woundPercentage = (double) 1/6;
        }

        // Re-calculate's wound percentage due to re-rolls
        if (attackFlags.contains(DamageCalculator.AttackFlags.RR_WOUNDS_ALL)) {
            woundPercentage += ((1 - woundPercentage) * woundPercentage);
        } else if (attackFlags.contains(DamageCalculator.AttackFlags.RR_WOUNDS_OF_ONE)) {
            woundPercentage += ( (double) 1/6 * woundPercentage);
        }

        Log.v(TAG, "Wounding on a : " + (int) (7 - (6 * woundPercentage)) +"+");


        Map<Integer, Double> woundsProbability = new HashMap<Integer, Double>();
        woundsProbability.put(0, 1.0);

        Map<Integer, Double> numHitAndWound = new HashMap<Integer, Double>();
        for (Map.Entry<Integer, Double> entry : numHits.entrySet()) {

            // For each outcome in the current wound probability map split into hits and misses
            if (entry.getKey() != 0) {
                woundsProbability = probabilitySplit(woundsProbability, woundPercentage);
            }

            //   Log.v(TAG, "Wound Probability for this matrix" + woundsProbability);
            numHitAndWound = probabilitySplit2(numHitAndWound, entry, woundsProbability);

        }

        numHits = numHitAndWound;

        Log.v(TAG, "# Wounds Probability:" +numHits);

        // Want damage probability per wound given
        // Get Save_Perc
        Integer sv = model.getStatline().getArmorSave();
        double savePercentage =  ( (double) ( 1 - ( (sv - 1) / 6.0)) );
        Log.v(TAG, "Armour save of " +sv );
        Log.v(TAG, String.format("Saving %s of the time before AP ", +savePercentage) );
        // Determines AP value of weapon
        Log.v(TAG, "AP -"+ap);

        // Applies AP
        savePercentage -=  ((double) 1/6) * ap;

        if (attackFlags.contains(DamageCalculator.AttackFlags.IN_COVER)) {
            Log.v(TAG, "In cover ");
            savePercentage += ((double) 1/6);
        }

        // Even if save is modified above a 2+, ensures that rolls of 1 will still fail
        if (savePercentage > ((double) 5/6)) {
            savePercentage = ((double) 5/6);
        } else if (savePercentage < (double) 0) {
            savePercentage = (double) 0;
        }

        Log.v(TAG, "Saving on a " + (int) (7 - (6 * savePercentage)) +"+");

        woundsProbability = numHits;
        Map<Integer, Double> saveProbability =  new HashMap<Integer, Double>();
        saveProbability.put(0, 1.0);
        Map<Integer, Double> numUnsavedWounds = new HashMap<Integer, Double>();
        for (Map.Entry<Integer, Double> entry : woundsProbability.entrySet()) {

            if (entry.getKey() != 0) {
                saveProbability = probabilitySplit(saveProbability, 1 - savePercentage);
            }

            numUnsavedWounds = probabilitySplit2(numUnsavedWounds, entry, saveProbability);

        }

        Log.v(TAG, "# Unsaved Wounds " +numUnsavedWounds);

        // Determine damage characteristic
        int damage = Integer.parseInt(this.damage);
        // Damage parser
        Map<Integer, Double> damageProbability = new HashMap<Integer, Double>();
        // Probability split on damage characteristics
        // TODO
        // Flat Damage
        damageProbability.put(damage, 1.0);
        Map<Pair<Integer, Integer>, Double> woundsDealtProbability = new HashMap<Pair<Integer, Integer>, Double>();
        woundsDealtProbability.put(new Pair<Integer, Integer>(0,0), 1.0);
        Map<Pair<Integer, Integer>, Double> modelsKilledProbability = new HashMap<Pair<Integer, Integer>, Double>();
        for (Map.Entry<Integer, Double> unsavedWoundsOutcome : numUnsavedWounds.entrySet()) {

            // FNP CALC
            // TODO

            if (unsavedWoundsOutcome.getKey() != 0) {

                // Apply damage characteristic to all possible outcomes so far
                Map<Pair<Integer, Integer>, Double> newWoundsDealtProbability = null;
                for (Map.Entry<Pair<Integer, Integer>, Double> woundsDealtOutcome : woundsDealtProbability.entrySet()) {

                    newWoundsDealtProbability = new HashMap<Pair<Integer, Integer>, Double>();

                    for (Map.Entry<Integer, Double> damageOutcome : damageProbability.entrySet()) {
                        Pair<Integer, Integer> deadModelsAndWounds;
                        if (woundsDealtOutcome.getKey().second + damageOutcome.getKey() >= Integer.valueOf(model.getStatline().wounds)) {
                            // Damage has killed model
                            deadModelsAndWounds = new Pair<Integer, Integer>(woundsDealtOutcome.getKey().first + 1, 0);
                        } else {
                            // Damage has wounded model
                            deadModelsAndWounds = new Pair<Integer, Integer>(woundsDealtOutcome.getKey().first, woundsDealtOutcome.getKey().second + damageOutcome.getKey());
                        }
                        newWoundsDealtProbability.put(deadModelsAndWounds, newWoundsDealtProbability.getOrDefault(deadModelsAndWounds, 0.0) + woundsDealtOutcome.getValue() * damageOutcome.getValue());
                    }

                }
                woundsDealtProbability = newWoundsDealtProbability;
                Log.v(TAG, "WoundsDealtProbability: " +woundsDealtProbability);

                for (Map.Entry<Pair<Integer, Integer>, Double> woundsDealtOutcome : woundsDealtProbability.entrySet()) {
                    modelsKilledProbability.put(woundsDealtOutcome.getKey(), modelsKilledProbability.getOrDefault(woundsDealtOutcome, 0.0) + (unsavedWoundsOutcome.getValue() * woundsDealtOutcome.getValue()));
                }


            }


        }

        Log.v(TAG, "# wounds dealt: " +modelsKilledProbability);

        return modelsKilledProbability;
    }

    public Map<Integer, Double> probabilitySplit(Map<Integer, Double> inputProbability, double hitPercentage) {
        Map<Integer, Double> outputProbability = new HashMap<Integer, Double>();
        for (Map.Entry<Integer, Double> entry : inputProbability.entrySet()) {

            // Misses
            outputProbability.put(entry.getKey(),  outputProbability.getOrDefault(entry.getKey(), 0.0) + entry.getValue() * (1 - hitPercentage));
            // Hits
            outputProbability.put(entry.getKey() + 1, entry.getValue() * hitPercentage);
        }
        return outputProbability;
    }
    private Map<Integer, Double> probabilitySplit2(Map<Integer, Double> outputProbability, Map.Entry<Integer, Double> inputProbabilityOutcome, Map<Integer, Double> alterByProbability) {
        for (Map.Entry<Integer, Double> alterByProbabilityOutcome : alterByProbability.entrySet()) {
            outputProbability.put(alterByProbabilityOutcome.getKey(), outputProbability.getOrDefault(alterByProbabilityOutcome.getKey(), 0.0) + inputProbabilityOutcome.getValue() * alterByProbabilityOutcome.getValue());
        }
        return outputProbability;
    }

    public String getName() {
        return this.name;
    }
    public HashSet<wepAbilities> getKeywords() {
        return keywords;
    }
    public int getRange() { return this.range;}
    public String getAttacks() {
        return this.attacks;
    }
    public int getBs() {
        return bs;
    }
    public int getStrength() {
        return this.strength;
    }
    public int getAp() {
        return this.ap;
    }
    public String getDamage() {
        return this.damage;
    }

    public String toString() {
        /*
        return String.format("%s\t%s\t%s\t%s\t%s\n", getName(), getAttacks(), getRange(), getStrength(), getAp());
         */
        return getName();
    }
    @Override
    public boolean equals(Object o) {
        Weapon cWeaponProfile = (Weapon) o;

        if (!this.name.equals(cWeaponProfile.name)) {
            return false;
        }
        return true;
    }
    @Override
    public int compareTo(Object o) {
        Weapon cWeaponProfile = (Weapon) o;
        return this.name.compareTo(cWeaponProfile.name);
    }

    public void setAttacks(String attacks) {
        this.attacks = attacks;
    }
    public void setBs(int bs) {
        this.bs = bs;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public void setAp(int ap) {
        this.ap = ap;
    }
    public void setDamage(String damage) {
        this.damage = damage;
    }

}

