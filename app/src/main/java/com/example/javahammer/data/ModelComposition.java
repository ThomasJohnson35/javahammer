package com.example.javahammer.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ModelComposition implements Parcelable, Serializable {

    private Model templateModel;                        // Intercessor
    private Integer min;                                // 4
    private Integer max;                                // 9
    private HashMap<Model, Integer> loadoutCompositon;  // Intercessor BR 3, Intercessor AG 1
    public ModelComposition(Model model, Integer min, Integer max) {
        this.templateModel = model;
        this.min = min;
        this.max = max;
        loadoutCompositon = new HashMap<>();
        loadoutCompositon.put(model, min);
    }
    protected ModelComposition(Parcel in) {
        // Read templateModel
        templateModel = new Model(in);
        Log.v("ModelComposition(Parcel in) - templateModel = ", templateModel.toString());
        // Read min
        min = in.readInt();
        Log.v("ModelComposition(Parcel in) - min = ", min.toString());
        // Read max
        max = in.readInt();
        Log.v("ModelComposition(Parcel in) - max = ", max.toString());
        // Read loadoutCompositon size
        int hashMapSize = in.readInt();
        // Read next hashMapSize # of entries into loadoutCompositon
        loadoutCompositon = new HashMap<>();

        for (int i = 0; i < hashMapSize; i++) {

            // Determine if model is template model
            Model model = new Model(in);
            if (model.equals(templateModel)) {
                loadoutCompositon.put(templateModel, in.readInt());
            } else {
                loadoutCompositon.put(model, in.readInt());
            }
        }

    }
    @Override
    public void writeToParcel(@NonNull Parcel out, int i) {
        // Write templateModel
        templateModel.writeToParcel(out, i);
        // Write min
        out.writeInt(min);
        // Write max
        out.writeInt(max);
        // Write size of loadoutComposition
        out.writeInt(loadoutCompositon.size());
        // Write all entries in loadoutComposition
        for (Map.Entry<Model, Integer> entry : loadoutCompositon.entrySet()) {
            entry.getKey().writeToParcel(out, i);
            out.writeInt(entry.getValue());
        }

    }
    public static final Creator<ModelComposition> CREATOR = new Creator<ModelComposition>() {
        @Override
        public ModelComposition createFromParcel(Parcel in) {
            return new ModelComposition(in);
        }

        @Override
        public ModelComposition[] newArray(int size) {
            return new ModelComposition[size];
        }
    };
    public Integer getMin() {
        return min;
    }
    public Integer getMax() {
        return max;
    }

    // Returns true of the model is actually added to unit composition and false if it is only incremented
    public Model addModel(Model model, int count) {
        Log.v("Thomas", String.format("addModel | model = %s, count = %d", model.toString(), count));

        for (HashMap.Entry<Model, Integer> entry : loadoutCompositon.entrySet()) {

            if (entry.getKey().equals(model)) {
                Log.v("Thomas", String.format("Found existing identical model %s", entry.getValue()));
                entry.setValue(entry.getValue() + count);

                return entry.getKey();
            }
        }
        Log.v("Thomas", String.format("New model entirely"));
        loadoutCompositon.put(model, count);
        return model;
    }

    // Returns true if the model is actually removed and false if the model is only decremented
    public boolean removeModel(Model model, int count) {
        Log.v("Thomas", String.format("removeModel | model = %s, count = %d from \n %s", model.toString(), count, this));
        for (HashMap.Entry<Model, Integer> entry : loadoutCompositon.entrySet()) {
            if (entry.getKey().equals(model)) {
                Log.v("Thomas", model +" equals " +entry.getKey() +"found!");

                if (loadoutCompositon.get(entry.getKey()) > 1) {
                    entry.setValue(entry.getValue() - count);
                    return false;
                } else {
                    // Performs a clean removal of the item
                    loadoutCompositon.remove(entry.getKey());
                    return true;
                }

            }
        }
        throw new IllegalArgumentException();
    }

    public HashMap<Model, Integer> getLoadoutCompositon() {
        return loadoutCompositon;
    }
    public Model getTemplateModel() {
        return templateModel;
    }
    public Integer getNumberOfModels() {
        //   Log.v("Thomas", "getNumberOfModels: " +loadoutCompositon);
        Integer numberOfModels = 0;
        for (Map.Entry<Model , Integer> entry : loadoutCompositon.entrySet()) {
            //        Log.v("Thomas", entry.getKey() +" " +entry.getValue());
            numberOfModels += entry.getValue();
        }
        return numberOfModels;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public String toString() {
        return String.format("LoadoutComposition:%s", loadoutCompositon);
    }
}
