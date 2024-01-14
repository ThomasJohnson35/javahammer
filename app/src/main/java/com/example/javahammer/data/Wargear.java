package com.example.javahammer.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Wargear implements Serializable, Parcelable, Comparable {

    public String name;
    private ArrayList<Weapon> weaponProfiles;

    private Ability ability;
    public Wargear(String name) {
        this.name = name;
        this.weaponProfiles = new ArrayList<>();
    }
    public Wargear(String name, ArrayList<Weapon> weaponProfiles, Ability ability) {
        this.name = name;
        this.weaponProfiles = weaponProfiles;
        this.ability = ability;
    }

    public Wargear(String name, ArrayList<Weapon> weaponProfiles) {
        this.name = name;
        this.weaponProfiles = weaponProfiles;
    }

    public Wargear(String name, Weapon... weaponProfiles) {
        this.name = name;
        this.weaponProfiles = new ArrayList<>(Arrays.asList(weaponProfiles));
    }

    public Wargear(Weapon weapon) {
        this.name = weapon.getName();
        this.weaponProfiles = new ArrayList<>(Collections.singletonList(weapon));
    }

    protected Wargear(Parcel in) {
        this.name = in.readString();
        weaponProfiles = new ArrayList<>();
        in.readList(weaponProfiles, Weapon.class.getClassLoader());
        Log.v("Thomas" , "Wargear(Parcel in) - name = " +name);
    }

    public static final Creator<Wargear> CREATOR = new Creator<Wargear>() {
        @Override
        public Wargear createFromParcel(Parcel in) {
            return new Wargear(in);
        }

        @Override
        public Wargear[] newArray(int size) {
            return new Wargear[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int i) {
        out.writeString(name);
        out.writeList(weaponProfiles);
        Log.v("Thomas" , "WRITE TO PARCEL - WARGEAR");
    }
    @Override
    public boolean equals(Object o) {
        Log.v("Thomas", "Wargear equals");
        if (o instanceof Wargear) {
            Wargear cWargear = (Wargear) o;
            return cWargear.name.equals(this.name);
        }
        return false;
    }

    public String toString() {
        return name;
    }
    @Override
    public int hashCode() {
        Log.v("Thomas", "Wargear hashCode()");
        return name.hashCode();
    }
    @Override
    public int compareTo(Object o) {
        Log.v("Thomas", "Wargear compareTo()");
        Wargear w = (Wargear) o;
        return this.name.compareTo(w.name);
    }

    public Ability getAbility() {
        return ability;
    }
    public static ArrayList<ArrayList<Weapon>> getAllWeaponProfiles(ArrayList<Wargear> wargearArrayList) {
        ArrayList<ArrayList<Weapon>> weaponProfileList = new ArrayList<>();

        for (Wargear wargear : wargearArrayList) {

            try {
                if (wargear.weaponProfiles == null | !wargear.weaponProfiles.isEmpty()) {
                    weaponProfileList.add(wargear.weaponProfiles);
                }
            } catch (NullPointerException e) {
                System.out.println(wargear);
            }

        }
        return weaponProfileList;
    }

    public ArrayList<Weapon> getWeaponProfiles() {
        return weaponProfiles;
    }

}
