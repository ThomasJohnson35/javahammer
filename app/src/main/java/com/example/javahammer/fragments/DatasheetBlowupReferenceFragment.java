package com.example.javahammer.fragments;

import androidx.fragment.app.Fragment;

import com.example.javahammer.data.Roster;
import com.example.javahammer.data.Unit;

public class DatasheetBlowupReferenceFragment extends DatasheetBlowupFragment{

    BrowseUnitsFragment prevFragment;
    public DatasheetBlowupReferenceFragment(Unit unit, BrowseUnitsFragment prevFragment) {
        super(null, unit, prevFragment);
        this.prevFragment = prevFragment;
    }

    @Override
    public void setToolbar() {
       toolbar.setTitle(unit.getName());
    }

    @Override
    protected void setStratagems() {

        prevFragment.faction.getDetachments().forEach(detachment -> elligableStratagems.addAll(detachment.getStratagems()));
    }
}
