package com.example.javahammer.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Predicate;

public class Ability implements Serializable {
    private String name, flavourText, desc;
    ArrayList<Timing> alertTiming;

    private Predicate<WarhammerGameState> condtion;

    public Ability(String name, String desc, Timing... alertTiming) {
        this.name = name;
        //this.desc = desc;

        this.alertTiming = new ArrayList<Timing>();
        for (Timing timing : alertTiming) {
            this.alertTiming.add(timing);
        }

    }

    public Ability(String name, String flavourText, String desc, Predicate<WarhammerGameState> condition) {
        this.name = name;
        this.flavourText = flavourText;
        this.desc = desc;
        this.alertTiming = new ArrayList<Timing>();

        this.condtion = condition;


    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getFlavourText() {
        return flavourText;
    }
}
