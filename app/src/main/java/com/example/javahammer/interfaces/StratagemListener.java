package com.example.javahammer.interfaces;

import android.view.View;

import com.example.javahammer.data.Detachment;
import com.example.javahammer.data.Roster;
import com.example.javahammer.data.Stratagem;

public interface StratagemListener {

    Roster getRoster();

    Detachment getDetachment();
    void onStratagemClick(Stratagem stratagem);
}
