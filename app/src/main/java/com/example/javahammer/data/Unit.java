package com.example.javahammer.data;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.example.javahammer.activities.DamageCalculator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Unit implements Parcelable, Serializable, Comparable {
    // FIELDS
    public final static String TAG = "/ Unit Class";
    protected ArrayList<ModelComposition> unitComposition;
    protected ArrayList<WargearOption> wargearOptions;
    protected ArrayList<PointsThreshold> pointsMap;
    protected String name;
    protected boolean isWarlord = false;
    protected EnumSet<Model.ModelKeywords> keywords;
    protected int imageRes;
    protected ArrayList<Ability> abilities;
    protected ArrayList<Ability> wargearAbilities;
    protected ArrayList<String> bodyguardUnitsOptions;
    // CONSTRUCTOR
    public Unit(String name, int imageRes, ArrayList<Ability> abilities, ArrayList<Ability> wargearAbilities, ArrayList<ModelComposition> unitComposition, ArrayList<WargearOption> wargearOption, ArrayList<PointsThreshold> pointsMap, EnumSet<Model.ModelKeywords> keywords) {
        this.name = name;
        this.imageRes = imageRes;
        this.abilities = abilities;
        this.wargearAbilities = wargearAbilities;
        this.unitComposition = unitComposition;
        this.wargearOptions = wargearOption;
        this.pointsMap = pointsMap;
        this.keywords = keywords;
    }
    // CONSTRUCTOR
    public Unit(String name, int imageRes, ArrayList<Ability> abilities, ArrayList<Ability> wargearAbilities, ArrayList<ModelComposition> unitComposition, ArrayList<WargearOption> wargearOption, ArrayList<PointsThreshold> pointsMap, EnumSet<Model.ModelKeywords> keywords, ArrayList<String> bodyguardUnits) {
        this(name, imageRes, abilities,  wargearAbilities, unitComposition, wargearOption, pointsMap, keywords);
        bodyguardUnitsOptions = bodyguardUnits;
    }
    // PARCELABLE
    protected Unit(Parcel in) {
        name = in.readString();
        Log.v("Unit(Parcel in) - name = ", name);
        // Stores model resID
        imageRes = in.readInt();

        unitComposition = in.createTypedArrayList(ModelComposition.CREATOR);
        Log.v("Unit(Parcel in) - unitComposition = ", unitComposition.toString());
        wargearOptions = in.createTypedArrayList(WargearOption.CREATOR);
        Log.v("Unit(Parcel in) - wargearOptions = ", wargearOptions.toString());
        pointsMap = in.createTypedArrayList(PointsThreshold.CREATOR);
        Log.v("Unit(Parcel in) - pointsMap = ", pointsMap.toString());
        reknit();
        isWarlord = in.readBoolean();
        keywords =  (EnumSet<Model.ModelKeywords>) in.readSerializable();
        Log.v("Unit(Parcel in) - keywords = ", keywords.toString());
        imageRes = in.readInt();
    }
    @Override
    public int describeContents() {
        return 0;
    }
    public void writeToParcel(@NonNull Parcel out, int flags) {
        Log.v("UNIT PRINT", "\nPARCEL OUT\n\n" +this + "\n\n");
        out.writeString(name);
        out.writeInt(imageRes);
        out.writeTypedList(unitComposition);
        out.writeTypedList(wargearOptions);
        out.writeTypedList(pointsMap);
        out.writeBoolean(isWarlord);
        out.writeSerializable(keywords);
        out.writeInt(imageRes);
    }
    // METHODS
    public Map<Weapon, Pair<Double, Double>> attack(Unit targetUnit, EnumSet<DamageCalculator.AttackFlags> attackFlags) {
        for (Model model : getUnitModelComposition().keySet()) {

            double pistolChoiceExpectedDamage = 0.0;
            double nonPistolChoiceExpectedDamage = 0.0;

            for (Map.Entry<Weapon, Map<Pair<Integer, Integer>, Double>> weaponAttackResults : model.attack(targetUnit, attackFlags).entrySet()) {

                double expectedDamage = 0.0;
                for (Map.Entry<Pair<Integer, Integer>, Double> weaponAttackOutcome : weaponAttackResults.getValue().entrySet()) {
                    expectedDamage += (weaponAttackOutcome.getKey().first + (weaponAttackOutcome.getKey().second / targetUnit.getModel().getStatline().wounds.doubleValue())) * weaponAttackOutcome.getValue() ;
                }

                if (weaponAttackResults.getKey().getKeywords().contains(Weapon.Keywords.PISTOL)) {
                    pistolChoiceExpectedDamage += expectedDamage;
                }
                else {
                    nonPistolChoiceExpectedDamage += expectedDamage;
                }
            }
        }
        return new HashMap<Weapon, Pair<Double, Double>>();
    }
    public Model getModel() {
        // TODO Make this actually make sense
        Model targetModel = null;
        for (Map.Entry<Model, Integer> entry : getUnitModelComposition().entrySet()) {
            targetModel = entry.getKey();
        }
        return targetModel;
    }
    public ArrayList<Ability> getAbilities() {

        return abilities;
    }
    public ArrayList<PointsThreshold> getPointsMap() {
        return pointsMap;
    }
    public String getName() {
        return this.name;
    }
    public int getNumberModels() {
        int numberOfModels = 0;
        for (ModelComposition modelComposition : unitComposition) {
            numberOfModels += modelComposition.getNumberOfModels();
        }
        return numberOfModels;
    }
    public ArrayList<ModelComposition> getUnitComposition() {
        return unitComposition;
    }
    public ArrayList<Statline> getStatlines() {
        ArrayList<Statline> statlines = new ArrayList<>();
        for (Model model : getUnitModelComposition().keySet()) {
            if (!statlines.contains(model.getStatline())) {
                statlines.add(model.getStatline());
            }
        }
        return statlines;
    }

    /* TODO
    public ArrayList<Weapon> getWeapons() {
        ArrayList<Weapon> weapons = new ArrayList<>();
        for (Model model : getUnitModelComposition().keySet()) {
            weapons.addAll(model.getWargear());
            Log.v(TAG, weapons.toString());
        }
        return weapons;
    }
     */

    public HashMap<Model, Integer> getUnitModelComposition() {
        HashMap<Model, Integer> unitModelComposition = new HashMap<>();
        Log.v(TAG, "Number of distinct Models: " +unitComposition.size());

        for (ModelComposition modelComposition : unitComposition) {
            Log.v(this.toString(), "" +modelComposition.getTemplateModel().getName());

            unitModelComposition.putAll(modelComposition.getLoadoutCompositon());
        }

        return unitModelComposition;
    }
    public static final Creator<Unit> CREATOR = new Creator<Unit>() {
        @Override
        public Unit createFromParcel(Parcel in) {
            return new Unit(in);
        }

        @Override
        public Unit[] newArray(int size) {
            return new Unit[size];
        }
    };
    public ArrayList<WargearOption> getWargearOptions() {
        return wargearOptions;
    }
    public Model mutateModel(Model originalModel, WargearOption mutatingWargearOption, int index) {
        Model newModel = (Model) originalModel.clone();

        for (WargearOption wargearOption : wargearOptions) {
            if (wargearOption.activeModels.containsKey(originalModel)) {
                // newModel Inherits previous wargearOptions from oldModel
                wargearOption.activeModels.put(newModel, wargearOption.activeModels.get(originalModel));
            }
        }

        // Applies the new mutatingWargearOption to the model
        if (index == -1) {
            // Either as a deactivation
            mutatingWargearOption.deactivateOnModel(newModel);
        } else {
            // Or an activation
            mutatingWargearOption.activateOnModel(newModel, index);
        }

        // Checks if this newModel created is identical to any prexisting model in the unit
        for (ModelComposition modelComposition : unitComposition) {
            if (modelComposition.getLoadoutCompositon().containsKey(originalModel)) {
                Log.v("Thomas", String.format("modelComposition %s contains %s",modelComposition, originalModel));
                // If by decrementing this model the model is actually deleted
                if (modelComposition.removeModel(originalModel, 1)) {
                    for (WargearOption wargearOption : wargearOptions) {
                        wargearOption.activeModels.remove(originalModel);
                    }
                }
                // If by adding model the model is only incremented
                Model addModelReturn = modelComposition.addModel(newModel, 1);
                if (!newModel.equals(addModelReturn)) {
                    for (WargearOption wargearOption : wargearOptions) {
                        wargearOption.activeModels.remove(newModel);
                    }
                }
                return addModelReturn;
            }
        }

        return null;
    }
    public void reknit() {
        for (WargearOption wargearOption : wargearOptions) {
            Log.v("reknit()", "wargearOption:" +wargearOption.toString());

            ConcurrentHashMap<Model, Integer> cleanedActiveModels = new ConcurrentHashMap<>();
            for (Map.Entry<Model, Integer> entry : wargearOption.activeModels.entrySet()) {
                // Checks all known existing models
                for (Model exisitingModel : getUnitModelComposition().keySet()) {
                    if (exisitingModel.equals(entry.getKey())) {
                        Log.v("UNIT PRINT", String.format("WargearOptionsModel:%s\nis actually\nExisting Model:%s", entry.getKey(), exisitingModel));
                        cleanedActiveModels.put(exisitingModel, entry.getValue());
                    }
                }
            }
            wargearOption.activeModels = cleanedActiveModels;
        }
    }
    public int getPoints() {
        int modelCount = getNumberModels();
        int points = 10000000;
        for (PointsThreshold pointsThreshold : pointsMap) {
            if ((modelCount <= pointsThreshold.numberModels) && (pointsThreshold.points <= points)) {
                points = pointsThreshold.points;
            }
        }

        return points;
    }
    public String toString() {
        return String.format("Name: %s\n Unit Composition: %s\nWargear Option: %s\nPointsMap: %s", name, unitComposition, wargearOptions, pointsMap);
    }

    public EnumSet<Model.ModelKeywords> getKeywords() {
        return keywords;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setWarlord(boolean status) {
        isWarlord = status;
    }

    public String getKeywordsString() {
        StringBuffer stringBufffer = new StringBuffer();
        stringBufffer.append(String.format("All %s", keywords.toString()));

        // TODO Checks all models for additional Keywords

        // for ()

        return stringBufffer.toString();
    }

    public boolean isWarlord() {
        return isWarlord;
    }

    @Override
    public int compareTo(Object o) {
        Unit cUnit = (Unit) o;

        return this.getName().compareTo(cUnit.getName());
    }


    public ArrayList<Ability> getWargearAbilities() {
        return wargearAbilities;
    }
}