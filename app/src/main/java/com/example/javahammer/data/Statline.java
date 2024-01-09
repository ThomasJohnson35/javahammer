package com.example.javahammer.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Statline implements Parcelable, Serializable {
    protected Integer movement;
    protected Integer toughness;
    protected Integer armorSave;
    protected Integer invulnSave;
    protected Integer wounds;
    protected Integer leadership;
    protected Integer objectiveControl;

    public Statline(Integer movement, Integer toughness, Integer save, Integer wounds, Integer leadership,
                    Integer objectiveControl) {

        this.movement = movement;
        this.toughness = toughness;
        this.armorSave = save;
        this.invulnSave = null;
        this.wounds = wounds;
        this.leadership = leadership;
        this.objectiveControl = objectiveControl;
    }

    public Statline(Integer movement, Integer toughness, Integer save, Integer invulnSave, Integer wounds, Integer leadership,
                    Integer objectiveControl) {

       this(movement, toughness, save, wounds, leadership, objectiveControl);
       this.invulnSave = invulnSave;
    }

    protected Statline(Parcel in) {
        movement = in.readInt();
        toughness = in.readInt();
        armorSave = in.readInt();
        invulnSave = (in.readInt() == 0? null : in.readInt());
        wounds = in.readInt();
        leadership = in.readInt();
        objectiveControl = in.readInt();
    }

    public static final Creator<Statline> CREATOR = new Creator<Statline>() {
        @Override
        public Statline createFromParcel(Parcel in) {
            return new Statline(in);
        }

        @Override
        public Statline[] newArray(int size) {
            return new Statline[size];
        }
    };

    public Integer getMovement() {
        return this.movement;
    }
    public Integer getToughness() {
        return this.toughness;
    }
    public Integer getArmorSave() {
        return this.armorSave;
    }
    public Integer getInvulnSave() {
        return this.invulnSave;
    }
    public Integer getWounds() {
        return  this.wounds;
    }
    public Integer getObjectiveControl() {
        return this.objectiveControl;
    }
    public Integer getLeadership() {
        return this.leadership;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(movement);
        parcel.writeInt(toughness);
        parcel.writeInt(armorSave);
        if (invulnSave == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeInt(invulnSave);
        }
        parcel.writeInt(wounds);
        parcel.writeInt(objectiveControl);
        parcel.writeInt(leadership);
    }

    @Override
    public boolean equals(Object o) {
        Statline cStatline = (Statline) o;
        if (!this.movement.equals(cStatline.movement)) {
            return false;
        } else if (!this.toughness.equals(cStatline.toughness)) {
            return false;
        } else if (!this.wounds.equals(cStatline.wounds)) {
            return false;
        } else if (!this.objectiveControl.equals(cStatline.objectiveControl)) {
            return false;
        } else if (!this.leadership.equals(cStatline.leadership)) {
            return false;
        } else if (!this.armorSave.equals(cStatline.armorSave)) {
            return false;
        }
        return true;
    }

}
