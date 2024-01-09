package com.example.javahammer.archived;

import com.example.javahammer.data.Unit;

public interface QuickCalculatorListener {

    public void SelectAttackerClick(Unit model);
    public void SelectDefenderClick(Unit model);

    public void AddAttackerClick();
    public void AddDefenderClick();
}
