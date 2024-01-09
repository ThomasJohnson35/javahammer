package com.example.javahammer.interfaces;

import com.example.javahammer.data.Model;
import com.example.javahammer.data.Wargear;

import java.util.ArrayList;
import java.util.HashMap;

public interface AdapterListener {
    public ArrayList<Wargear> getExchangeableWargear();


    HashMap<Model, Integer> activateWargearOption(Model model);
    HashMap<Model, Integer> deactivateWargearOption(Model model);
}
