package com.example.javahammer.fragments;

import com.example.javahammer.R;
import com.example.javahammer.adapters.AbilityAdapter;
import com.example.javahammer.data.Ability;
import com.example.javahammer.data.Roster;
import com.example.javahammer.data.Unit;

import java.util.ArrayList;

public class WargearAbilityAdapter extends AbilityAdapter {
    Unit unit;
    public WargearAbilityAdapter(Unit unit) {
        super(unit.getWargearAbilities());
        this.unit = unit;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        Ability ability = abilityArrayList.get(position);

        if (unit.getUnitModelComposition().keySet().stream().anyMatch(n -> n.getWargear().stream().anyMatch(wargear -> wargear.name.equals(ability.getName())))) {

            holder.name.setBackgroundColor(context.getResources().getColor(R.color.red));
        }
    }
}
