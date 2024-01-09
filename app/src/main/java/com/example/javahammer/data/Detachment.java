package com.example.javahammer.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Detachment implements Parcelable, Serializable {

    public ArrayList<Stratagem> stratagems;
    public String name;
    public ArrayList<Enhancement> enhancements;
    public ArrayList<Ability> abilities;

    // ArrayList<Stratagem> stratagemList;
    // ArrayList<Enhancement> enhancementList;
    public Detachment(String name, ArrayList<Enhancement> enhancements, ArrayList<Stratagem> stratagems, Ability... abilities) {
        this.name = name;
        this.enhancements = enhancements;
        this.stratagems = stratagems;

        this.abilities = new ArrayList<>(Arrays.asList(abilities));
    }

    protected Detachment(Parcel in) {
        name = in.readString();
    }

    public static final Creator<Detachment> CREATOR = new Creator<Detachment>() {
        @Override
        public Detachment createFromParcel(Parcel in) {
            return new Detachment(in);
        }

        @Override
        public Detachment[] newArray(int size) {
            return new Detachment[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return name;
    }
    public ArrayList<Stratagem> getStratagems() {
        return stratagems;
    }
    public ArrayList<Enhancement> getEnhancements() {
        return enhancements;
    }

    public Optional<Faction> getFaction() {

        return Faction.getFactionList().stream().filter((n -> n.getDetachments().contains(this))).findFirst();
    }

}
