package com.example.javahammer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.data.Ability;
import com.example.javahammer.fragments.FactionReferenceFragment;
import com.example.javahammer.interfaces.ArmyRuleAdapterListener;
import com.example.javahammer.interfaces.ReferenceAdapterListener;

import java.util.ArrayList;

public class ArmyRuleAdapter extends RecyclerView.Adapter<ArmyRuleAdapter.ViewHolder> {

    public ArmyRuleAdapterListener listener;

    public Context context;
    public ArrayList<Ability> abilityArrayList;
    public ArmyRuleAdapter(ArrayList<Ability> abilityArrayList) {
        this.abilityArrayList = abilityArrayList;
    }

    public void setListener(ArmyRuleAdapterListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_reference, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArmyRuleAdapter.ViewHolder holder, int position) {

        Ability ability = abilityArrayList.get(position);
        holder.name.setText(ability.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onArmyRuleClicked(ability);
            }
        });
    }

    @Override
    public int getItemCount() {
        return abilityArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.item_reference_name_tv);
        }
    }

}
