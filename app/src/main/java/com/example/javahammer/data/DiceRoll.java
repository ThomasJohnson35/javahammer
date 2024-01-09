package com.example.javahammer.data;

import android.util.Log;
import android.util.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DiceRoll {

    public enum Dice {
        D3, D6
    }

  //  Pair<Integer, Dice> dicePair;
    Integer d3Amount;
    Integer d6Amount;
    Integer flatAmount;

    public DiceRoll(String str) {
        Pattern p = Pattern.compile("^([1-9])?(D6)?\\+?([1-9])?$");

        Matcher m = p.matcher(str);


        if (m.find()) {


            for (int i = 0; i <= m.groupCount(); i++) {
                if (m.group(i) != null) {
                    Log.v("Regex " + i, m.group(i));
                }
            }

            if (m.group(2) != null) {
                d6Amount = ( m.group(1) != null) ? Integer.parseInt(m.group(1)) : 1;
            } else {
                d6Amount = 0;
            }

            // Set Flat
            flatAmount = ( m.group(3) != null) ? Integer.parseInt(m.group(3)) : 0;
/*
            Log.v("Regex Size", String.valueOf(m.groupCount()));

            Log.v("Regex 0", m.group(0));
            Log.v("Regex 0", m.group(2));

 */
        }

    }

    public String toString() {

        StringBuffer stringBuffer = new StringBuffer();

        // Handles D6 generation
        stringBuffer.append(d6Amount > 0 ? (d6Amount == 1 ? "D6" : String.format("%dD6", d6Amount) ) : "");

        // Handels FlatAmount generation
        stringBuffer.append(flatAmount > 0 ? (d6Amount > 0 ? String.format("+%d", flatAmount) : String.format("%d", flatAmount)) : "");

        return stringBuffer.toString();
    }


    /*
    public DiceRoll (Integer d3Amount, Integer d6Amount) {
        this.dice = dice;
    }
    public DiceRoll (Dice dice, int flatAmount) {
        this.dice = dice;
        this.flatAmount = flatAmount;
    }


     */


}
