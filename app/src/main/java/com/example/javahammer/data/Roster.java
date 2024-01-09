package com.example.javahammer.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Map;
import java.util.stream.Collectors;

public class Roster implements Parcelable, Serializable {

    public String name;
    public ArrayList<Unit> units;
    public Faction faction;
    public Detachment detachment;
    public BattleSize battleSize;

    public Roster(String name, Faction faction, Detachment detachment) {
        this.name = name;
        units = new ArrayList<>();
        this.faction = faction;
        this.detachment = detachment;
        this.battleSize = BattleSize.INCURSION;
    }

    protected Roster(Parcel in) {

        name = null;
        if (in.readByte() == 1) {
            name = in.readString();
        }

        faction = null;
        // Byte code for non-null detachment
        if (in.readByte() == 1) {
            faction = (Faction) in.readSerializable();
        }

        battleSize = null;
        // Byte code for non-null detachment
        if (in.readByte() == 1) {
            battleSize = (BattleSize) in.readSerializable();
        }


        detachment = null;
        // Byte code for non-null detachment
        if (in.readByte() == 1) {
            detachment = (Detachment) in.readSerializable();
        }

        units = in.createTypedArrayList(Unit.CREATOR);
    }

    public static final Creator<Roster> CREATOR = new Creator<Roster>() {
        @Override
        public Roster createFromParcel(Parcel in) {
            return new Roster(in);
        }

        @Override
        public Roster[] newArray(int size) {
            return new Roster[size];
        }
    };

    public void setDetatchment(Detachment detachment) {
        this.detachment = detachment;
    }

    public boolean addUnit(Unit unit) {

        if (getUnitOccurance(unit.getName()) >= getUnitMaxOccurances(unit)) {
            return false;
        } else {
            units.add(unit);
            return true;
        }
    }

    public int getUnitMaxOccurances(Unit unit) {
        if (unit.getKeywords().contains(Model.ModelKeywords.EPIC_HERO)) {
            return 1;
        } else if (unit.getKeywords().contains(Model.ModelKeywords.BATTLELINE)) {
            return 6;
        } else {
            return 3;
        }
    }
    public int getPoints() {
        int sum = 0;
        for (Unit unit : units) {
            sum += unit.getPoints();
        }
        return sum;
    }
    public String getName() {
        return name;
    }
    public Faction getFaction() {
        return faction;
    }
    public Detachment getDetachment() {
        return detachment;
    }
    public ArrayList<Unit> getUnits () {
        return units;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int i) {
        out.writeByte((byte) (name == null ? 0 : 1));
        if (name != null) {
            out.writeString(name);
        }

        // Writes 0 if detachment is null and 1 otherwise
        out.writeByte((byte) (faction == null ? 0 : 1));
        if (faction != null) {
            out.writeSerializable(faction);
        }

        // Writes 0 if detachment is null and 1 otherwise
        out.writeByte((byte) (battleSize == null ? 0 : 1));
        if (battleSize != null) {
            out.writeSerializable(battleSize);
        }

        // Writes 0 if detachment is null and 1 otherwise
        out.writeByte((byte) (detachment == null ? 0 : 1));
        if (detachment != null) {
            out.writeSerializable(detachment);
           // detachment.writeToParcel(out, i);
        }

        out.writeTypedList(units);
    }

    public int getUnitOccurance(String name) {
        return units.stream().filter(n -> n.getName().equals(name)).collect(Collectors.toList()).size();
    }
    public void replace(Unit first, Unit second) {
        units.set(units.indexOf(first), second);
    }

    public BattleSize getBattleSize() {
        return battleSize;
    }

    public void removeUnit(Unit unit) {
        // TODO implement a safe delete for units from Roster
        units.remove(unit);
    }

    // CLONEABLE
    public Object clone() {

        Roster newRoster = new Roster(name, faction, detachment);
        newRoster.battleSize = this.battleSize;

        newRoster.units = (ArrayList<Unit>) units.clone();

        return newRoster;
    }
}
