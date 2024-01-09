package com.example.javahammer.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class PointsThreshold implements Parcelable, Serializable {
    public int points;
    public int numberModels;

    public PointsThreshold(int points, int numberModels) {
        this.points = points;
        this.numberModels = numberModels;
    }

    protected PointsThreshold(Parcel in) {
        points = in.readInt();
        numberModels = in.readInt();
    }

    public static final Creator<PointsThreshold> CREATOR = new Creator<PointsThreshold>() {
        @Override
        public PointsThreshold createFromParcel(Parcel in) {
            return new PointsThreshold(in);
        }

        @Override
        public PointsThreshold[] newArray(int size) {
            return new PointsThreshold[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel out, int i) {
        out.writeInt(points);
        out.writeInt(numberModels);
    }
}