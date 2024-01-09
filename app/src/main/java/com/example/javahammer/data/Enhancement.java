package com.example.javahammer.data;

import android.util.Pair;

import java.io.Serializable;
import java.security.Key;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.function.Predicate;

public class Enhancement implements Serializable {

    String name;
    int points;
    public String flavourText;
    String description;


    private Predicate<EnumSet<Model.ModelKeywords>> condition;

    private ArrayList< Pair<EnumSet<Model.ModelKeywords>, EnumSet<Model.ModelKeywords> > > keywordRequirements;

    public Enhancement(String name, int points, String flavourText, String description, Pair<EnumSet<Model.ModelKeywords>, EnumSet<Model.ModelKeywords>>... keywordRequirements) {
        this.name = name;
        this.points = points;
        this.flavourText = flavourText;
        this.description = description;

        this.keywordRequirements = new ArrayList<>();

    }

    public Enhancement(String name, int points, String flavourText, String description, Predicate<EnumSet<Model.ModelKeywords>> condition) {
        this.name = name;
        this.points = points;
        this.flavourText = flavourText;
        this.description = description;

        this.keywordRequirements = new ArrayList<>();

        this.condition = condition;
    }

    public String getName() {
        return name;
    }

    public String getFlavourText() {
        return flavourText;
    }

    public int getPoints() {
        return points;
    }

    public String getDescription() {
        return description;
    }

    public boolean testElligibility(Unit unit) {

        if (!unit.getKeywords().contains(Model.ModelKeywords.CHARACTER)) {
            return false;
        }

        if (condition == null) {
            return true;
        }

        return condition.test(unit.getKeywords());

    }

}
