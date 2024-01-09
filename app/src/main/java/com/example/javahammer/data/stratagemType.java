package com.example.javahammer.data;

import android.widget.Switch;

import java.io.Serializable;

public enum stratagemType implements Serializable {
    BATTLE_TACTIC, STRATEGIC_PLOY, EPIC_DEED, WARGEAR;

    public String toString() {

        switch (this) {
            case BATTLE_TACTIC:
                return "BATTLE TACTIC";

            case STRATEGIC_PLOY:
                return "STRATEGIC PLOY";
            case EPIC_DEED:
                return "EPIC DEED";
            default:
                return "ERROR";
        }
    }
}
