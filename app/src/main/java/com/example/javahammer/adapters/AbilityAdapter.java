package com.example.javahammer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.archived.OnItemClickListener;
import com.example.javahammer.data.Ability;
import com.example.javahammer.data.Roster;
import com.example.javahammer.data.Unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;

public class AbilityAdapter extends RecyclerView.Adapter<AbilityAdapter.ViewHolder> {

    protected Context context;

    protected ArrayList<Ability> abilityArrayList;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public AbilityAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_ability, parent, false);

        // Return a new holder instance
        AbilityAdapter.ViewHolder viewHolder = new AbilityAdapter.ViewHolder(contactView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(AbilityAdapter.ViewHolder holder, int position) {
        Ability ability = abilityArrayList.get(position);

        holder.name.setText(ability.getName());
        holder.desc.setText(ability.getDesc());
    }

    @Override
    public int getItemCount() {
        return abilityArrayList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name, desc;


        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_ability_title_tv);
            desc = itemView.findViewById(R.id.item_ability_desc_tv);
        }
    }

    // Pass in the contact array into the constructor
    public AbilityAdapter(ArrayList<Ability> ability) {
        this.abilityArrayList = ability;
    }


}
