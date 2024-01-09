package com.example.javahammer.interfaces;

import com.example.javahammer.data.Model;

public interface ProfileAdapterListener {

    void onWeaponClicked();

    void onAttackerPlusClicked();
    void onDefenderPlusClicked();

    void onModelClicked(Model model);
}
