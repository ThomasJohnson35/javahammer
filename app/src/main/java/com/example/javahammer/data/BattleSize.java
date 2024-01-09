package com.example.javahammer.data;

import android.widget.Switch;

public enum BattleSize {
        INCURSION, STRIKEFORCE, ONSLAUGHT;

        public String toString() {
                switch(this) {
                        case INCURSION : return "Incursion (1000 PTS)";
                        case STRIKEFORCE : return "Strikeforce (2000 PTS)";
                        case ONSLAUGHT : return "Onslaught (3000 PTS)";
                        default : throw new RuntimeException();
                }
        }

    public int getPoints() {

                switch (this) {
                        case INCURSION : return 1000;
                        case STRIKEFORCE : return 2000;
                        case ONSLAUGHT : return 3000;
                        default : throw new RuntimeException();                }
    }
}
