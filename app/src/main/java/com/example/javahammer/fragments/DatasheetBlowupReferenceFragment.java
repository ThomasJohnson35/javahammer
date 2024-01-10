package com.example.javahammer.fragments;

import androidx.fragment.app.Fragment;

import com.example.javahammer.data.Roster;
import com.example.javahammer.data.Unit;

public class DatasheetBlowupReferenceFragment extends DatasheetBlowupFragment{
    public DatasheetBlowupReferenceFragment(Unit unit, Fragment prevFragment) {
        super(null, unit, prevFragment);
    }

    @Override
    public void setToolbar() {
       toolbar.setTitle(unit.getName());
    }
}
