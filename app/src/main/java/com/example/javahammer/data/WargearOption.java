package com.example.javahammer.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WargearOption implements Parcelable, Serializable {

    // FIELDS
    private String requiredModel;
    private int activationsAllowed;
    private double activationsRatio;
    private ArrayList<ArrayList<Wargear>> exchangeableWargear;
    public ConcurrentHashMap<Model, Integer> activeModels;

    // CONSTRUCTOR
    public WargearOption(String eligibleModel, ArrayList<ArrayList<Wargear>> exchangableWargear) {
        this.exchangeableWargear = exchangableWargear;
        this.requiredModel = eligibleModel;
        this.activeModels = new ConcurrentHashMap<>();
        this.activationsAllowed = 0;
        this.activationsRatio = 0.0;
    }
    public WargearOption(String eligibleModel, ArrayList<ArrayList<Wargear>> exchangableWargear, int activationsAllowed) {
        this(eligibleModel, exchangableWargear);
        this.activationsAllowed = activationsAllowed;
    }
    public WargearOption(String eligibleModel, ArrayList<ArrayList<Wargear>> exchangableWargear, double activationsRatio) {
        this(eligibleModel, exchangableWargear);
        this.activationsRatio = activationsRatio;
    }

    // PARCELABLE
    protected WargearOption(Parcel in) {
        requiredModel = in.readString();
        Log.v("WargearOption (Parcel in)" , "requiredModel = " + requiredModel.toString());
        activationsAllowed = in.readInt();
        Log.v("WargearOption (Parcel in)" , "activationsAllowed = " + activationsAllowed);
        activationsRatio = in.readDouble();
        Log.v("WargearOption (Parcel in)" , "activationsRatio = " + activationsAllowed);
        exchangeableWargear = new ArrayList<>();
        for (int i = in.readInt(); i > 0; i--) {
            ArrayList<Wargear> temp = new ArrayList<>();
            in.readList(temp, Wargear.class.getClassLoader());
            exchangeableWargear.add((ArrayList<Wargear>) temp);
        }
        activeModels = new ConcurrentHashMap<>();
        for (int i = in.readInt(); i > 0; i--) {
            activeModels.put(new Model(in), in.readInt());
        }
    }
    @Override
    public void writeToParcel(@NonNull Parcel out, int i) {

        // Writes name
        out.writeString(requiredModel);
        // Writes activations allowed
        out.writeInt(activationsAllowed);
        // Writes name
        out.writeDouble(activationsRatio);

        // Writes number of items in defaultWargear
        out.writeInt(exchangeableWargear.size());
        for (ArrayList<Wargear> wargearArrayList : exchangeableWargear) {
            out.writeList(wargearArrayList);
        }

        // Writes number of entries in activateModels
        out.writeInt(activeModels.size());
        for (Map.Entry<Model, Integer> activeModel : activeModels.entrySet()) {
            activeModel.getKey().writeToParcel(out, i);
            out.writeInt(activeModel.getValue());
        }

    }
    public static final Creator<WargearOption> CREATOR = new Creator<WargearOption>() {
        @Override
        public WargearOption createFromParcel(Parcel in) {
            return new WargearOption(in);
        }

        @Override
        public WargearOption[] newArray(int size) {
            return new WargearOption[size];
        }
    };
    @Override
    public int describeContents() { return 0; }


    // METHODS
    public ArrayList<ArrayList<Wargear>> getExchangeableWargear() {
        return exchangeableWargear;
    }
    public int getCapacity(int numModels) {

        // Fixed activations case
        if (activationsAllowed != 0) {
            return activationsAllowed;
        } else if (activationsRatio != 0.0) {
            return (int) ((double) numModels * activationsRatio);
        } else {
            return numModels;
        }
    }
    public String getRequiredModel() {
        return requiredModel;
    }
    public int getNumActive(Unit parentUnit) {
        int sum = 0;
        for (Model model : activeModels.keySet()) {
            for (Map.Entry<Model, Integer> entry : parentUnit.getUnitModelComposition().entrySet()) {
                if (model == entry.getKey()) {
                    sum += entry.getValue();
                }
            }
        }

        return sum;
    }
    public void activateOnModel(Model model, int position) {
        ArrayList<Wargear> toRemove;
        ArrayList<Wargear> toAdd = getExchangeableWargear().get(position);

        if (activeModels.containsKey(model)) {         // If the model is already activated, then we want to exchange one exchangeable wargear choice for another
            toRemove = exchangeableWargear.get(activeModels.get(model));
            activeModels.replace(model, position);
        } else {    // Otherwise we will want to exchange the choice for the default wargear
            toRemove = exchangeableWargear.get(0);
            activeModels.put(model, position);
        }
        // Executes the exchange
        model.exchangeWargear(toRemove, toAdd);
    }
    public void deactivateOnModel(Model model) {
        // Ensures Wargear Option Activation has been previously recorded
        if (!activeModels.containsKey(model)) {
            throw new IllegalArgumentException();
        }
        // Determines the wargear that this wargear option activation had previously given
        // which now must be removed
        ArrayList<Wargear> toRemove = exchangeableWargear.get(activeModels.get(model));
        // Determines the wargear to be given back (default wargear)
        ArrayList<Wargear> toAdd = exchangeableWargear.get(0);
        // Removes model from list of activated models for this wargear option
        activeModels.remove(model);
        // Executes the exchange
        model.exchangeWargear(toRemove, toAdd);
    }
    public String toString() {

        StringBuffer stringBuffer = new StringBuffer();

        stringBuffer.append(String.format("\t\u2022 "));
        // AMOUNT OF SWAPS
        if (activationsRatio != 0) {
            //eg. 1 in 3

            // TODO fix
            stringBuffer.append("1 in 3 ");


        } else {
            stringBuffer.append(activationsAllowed +" ");
        }

        // MODEL REQUIREMENT
        if (requiredModel.equals("")) {
            // Any model can replace
            stringBuffer.append("model");
        } else {
            stringBuffer.append(requiredModel);
        }

        // EXCHANGEABLE FIRST
        if (!exchangeableWargear.get(0).equals("")) {
            stringBuffer.append(String.format("'s %s can be replaced with", exchangeableWargear.get(0)));
        } else {
            stringBuffer.append(" can be equipped with");
        }

        if (exchangeableWargear.size() == 2) {
            stringBuffer.append(" " +exchangeableWargear.get(1));
        } else {
            stringBuffer.append(" one of the following:");
            for (int i = 1; i < exchangeableWargear.size(); i++) {
                stringBuffer.append(String.format("\n\t\t\u2022 %s", exchangeableWargear.get(i)));
            }
        }

        return stringBuffer.toString();
    }
}
