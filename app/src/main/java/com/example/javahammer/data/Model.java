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
import java.util.Map;

public class Model implements Parcelable, Cloneable, Serializable {

    // FIELDS
    private String TAG = Unit.TAG +"/ Model Class";
    private Statline statline;
    private String name;
    private ArrayList<Wargear> wargear;
    private HashMap<WargearOption, Integer> wargearOptionActivations;

    public EnumSet<ModelKeywords> getKeywords() {
        return modelKeywords;
    }

    public void setToughness(int parseInt) {
        statline.toughness = parseInt;
    }

    public void setWounds(int parseInt) {
        statline.wounds = parseInt;
    }

    public void setSave(int parseInt) {
        statline.armorSave = parseInt;
    }

    public void setInvulnerableSave(int parseInt) {
        statline.invulnSave = parseInt;
    }

    public enum ModelKeywords {
        INFANTRY, GRENADES, IMPERIUM, CHARACTER, MOUNTED, MONSTER, EPIC_HERO, TECH_PRIEST, BELISARIUS_CAWL, CYBERNETICA_DATASMITH, SKITARII, MARSHAL, DOMINUS, BATTLELINE, RANGERS, ENGINSEER, INTERCESSOR_SQUAD, TACTICUS, DEDICATED_TRANSPORT, TRANSPORT, SKORPIUS_DUNERIDER, SYDONICAN_DRAGOONS, WALKER, ELECTRO_PRIESTS, CORPUSCARII, KASTELAN_ROBOTS, FULGURITE, IRONSTRIDER_BALLISTARII, SMOKE, AIRCRAFT, FLY, ARCHAEOPTER_FUSILAVE, ARCHAEOPTER_TRANSVECTOR, ARCHAEOPTER_STRATORAPTOR, KATAPHRON, DESTROYERS, BREACHERS, TECHNOARCHEOLOGIST, SKORPIUS_DISINTEGRATOR, SICARIAN_INFILTRATORS, SICARIAN_RUSTSTALKERS, QUESTORIS, TOWERING, TITANIC, KNIGHT_GALLANT, KNIGHT_CRUSADER, KNIGHT_WARDEN, KNIGHT_CASTELLAN, KNIGHT_PALADIN, KNIGHT_VALIANT, Battleline, ARMIGER, HELVERIN, WARGLAIVE, VEHICLE, CAPTAIN, ADEPTUS_ASTARTES;
    }

    private EnumSet<ModelKeywords> modelKeywords;

    // CONSTRUCTOR
    public Model (String name, Statline statline, ArrayList<Wargear> wargear, EnumSet<ModelKeywords> modelKeywords) {
        this.name = name;
        this.statline = statline;
        this.wargear = wargear;
        this.modelKeywords = modelKeywords;
        wargearOptionActivations = new HashMap<>();
    }

    // PARCELABLE
    protected Model(Parcel in) {
        name = in.readString();
        statline = new Statline(in);
        wargear = new ArrayList<Wargear>();
        in.readList(wargear, Wargear.class.getClassLoader());
        wargearOptionActivations = new HashMap<>();
        wargearOptionActivations = (HashMap<WargearOption, Integer>) in.readSerializable();
        modelKeywords =  (EnumSet<ModelKeywords>) in.readSerializable();
    }
    public static final Creator<Model> CREATOR = new Creator<Model>() {
    @Override
    public Model createFromParcel(Parcel in) {
        return new Model(in);
    }

    @Override
    public Model[] newArray(int size) {
        return new Model[size];
    }
};
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(@NonNull Parcel out, int i) {
        // Stores model name
        out.writeString(name);
        // Stores Statline
        statline.writeToParcel(out, 0);
        // Stores number of wargear choices
        out.writeList(wargear);
        out.writeSerializable(wargearOptionActivations);
        // Stores size of wargearOptionActivations
        out.writeSerializable(modelKeywords);


    }

    // METHODS
    protected Map<Weapon, Map<Pair<Integer, Integer>, Double>> attack (Unit target, EnumSet<DamageCalculator.AttackFlags> attackFlags) {

        ArrayList<Weapon> equippedWeaponProfiles = new ArrayList<>();
        Map<Weapon, Map<Pair<Integer, Integer>, Double>> results = new HashMap<Weapon, Map<Pair<Integer, Integer>, Double>>();

        for (Weapon firingWeaponProfile : equippedWeaponProfiles) {
     //       double avgModelsKilled = 0.0;
        //    double chanceToKillUnit = 0.0;
         //   int targetKillValue = target.models.size();

            results.put(firingWeaponProfile, firingWeaponProfile.attack(target, attackFlags));

       //     for (Map.Entry<Pair<Integer, Integer>, Double> outcome : firingWeaponProfile.attack(target, attackFlags).entrySet()) {
             //   avgModelsKilled += ( (outcome.getKey().first + (outcome.getKey().second / Integer.parseInt(target.statlines.get(0).wounds))) * outcome.getValue());

     //           if (outcome.getKey().first >= targetKillValue) {
           //         chanceToKillUnit += (outcome.getValue());
              //  }
    //        }
         //   Log.v(TAG, "Expected " +target.name  +"'s killed: " +avgModelsKilled);
          //  Log.v(TAG, "Chance to kill " +target.name  +" unit: " +chanceToKillUnit);

        //    results.put(firingWeaponProfile, new Pair<Double, Double> (avgModelsKilled, chanceToKillUnit));

        }
        Log.v(this.name, "Firing: " + getWargear());
        return results;
    }
    @Override
    public boolean equals(Object o) {
        Model cModel = (Model) o;
        if (!this.name.equals(cModel.name)) {
            return false;
        } else if (!this.wargear.equals(cModel.wargear)) {
            return false;
        }
        return true;
    }

    public String getName() {return name;}
    public Statline getStatline() {return statline;}
    public ArrayList<Wargear> getWargear() {
        return wargear;
    }



    public String toString() {
        String str = String.format("Name = %s \t ObjectId = %s\t Wargear = %s", name, System.identityHashCode(this), wargear.toString());
        return str;
    }
    // CLONEABLE
    public Object clone() {

        ArrayList<Wargear> wargearClone = new ArrayList<>();
        for (Wargear wargear : wargear) {
            wargearClone.add(wargear);
        }

        for (Map.Entry<WargearOption, Integer> entry : wargearOptionActivations.entrySet()) {

        }
        return new Model(this.name, this.statline, wargearClone, modelKeywords);
    }

    public void exchangeWargear(ArrayList<Wargear> toRemove, ArrayList<Wargear> toAdd) {

        try {
            int index = wargear.indexOf(toRemove.get(0));

            for (Wargear wargear : toRemove) {
                this.wargear.remove(wargear);
            }
            for (Wargear weapon : toAdd) {
                wargear.add(index++ ,weapon);
            }
        } catch (Exception e) {
            Log.v("exchangeWargear Error", e.toString());
        }
    }
}
