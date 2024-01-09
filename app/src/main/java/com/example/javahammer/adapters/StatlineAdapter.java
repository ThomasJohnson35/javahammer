package com.example.javahammer.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.data.Statline;

import java.util.ArrayList;
import java.util.List;

public class StatlineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Statline> statlineList = new ArrayList<Statline>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("StatlineAdapter", "onCreateViewHolder start");
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == 0)  {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_statline, parent, false);
            return new LabelsHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_statline_new, parent, false);
            return new CharacteristicsHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.v("StatlineAdapter", "onBindViewHolder start");

        // If item_add_unit
        if (position == 0) {
            LabelsHolder labelsHolder = (LabelsHolder) holder;

        } else {
            Statline statline = statlineList.get(position-1);
            CharacteristicsHolder characteristicsHolder = (CharacteristicsHolder) holder;
            characteristicsHolder.movement.setText("" +statline.getMovement() +"\"");
            characteristicsHolder.toughness.setText("" +statline.getToughness());
            characteristicsHolder.save.setText(String.format("%d+", statline.getArmorSave()));
            characteristicsHolder.wounds.setText("" +statline.getWounds());
            characteristicsHolder.leadership.setText("" +statline.getLeadership());
            characteristicsHolder.objectiveControl.setText("" +statline.getObjectiveControl());

            if (statline.getInvulnSave() != null) {
                characteristicsHolder.invulnSave.setVisibility(View.VISIBLE);
                characteristicsHolder.itemView.findViewById(R.id.invuln_save_label_tv).setVisibility(View.VISIBLE);
                characteristicsHolder.invulnSave.setText("" +statline.getInvulnSave());
            } else {
                characteristicsHolder.invulnSave.setVisibility(View.GONE);
                characteristicsHolder.itemView.findViewById(R.id.invuln_save_label_tv).setVisibility(View.GONE);
                characteristicsHolder.itemView.findViewById(R.id.pretty_square_invuln_save).setVisibility(View.GONE);
            }
        }

  //      holder.name.setText("" +statline.getName());

        Log.v("StatlineAdapter", "onBindViewHolder done");
    }

    @Override
    public int getItemViewType(int position) {

        if (position == 0) {
            return 0;
        }
        else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return statlineList.size() + 1;
    }

    public class LabelsHolder extends RecyclerView.ViewHolder {

        public TextView movement;
        public TextView toughness;
        public TextView save;
        public TextView wounds;
        public TextView leadership;
        public TextView objectiveControl;

        public LabelsHolder(@NonNull View itemView) {
            super(itemView);

            Log.v("StatlineAdapter.CharacteristicsHolder", "constructor start");
            movement = itemView.findViewById(R.id.movement_tv);
            toughness = itemView.findViewById(R.id.toughness_tv);
            save = itemView.findViewById(R.id.save_tv);
            wounds = itemView.findViewById(R.id.wounds_tv);
            leadership = itemView.findViewById(R.id.leadership_tv);
            objectiveControl = itemView.findViewById(R.id.objective_control_tv);
            Log.v("StatlineAdapter.CharacteristicsHolder", "constructor done");
        }
    }

    public class CharacteristicsHolder extends RecyclerView.ViewHolder {
        public TextView movement;
        public TextView toughness;
        public TextView save;
        public TextView invulnSave;
        public TextView wounds;
        public TextView leadership;
        public TextView objectiveControl;

        public CharacteristicsHolder(View itemView) {
            super(itemView);
            Log.v("StatlineAdapter.CharacteristicsHolder", "constructor start");
            movement = itemView.findViewById(R.id.movement_tv);
            toughness = itemView.findViewById(R.id.toughness_tv);
            save = itemView.findViewById(R.id.save_tv);
            invulnSave = itemView.findViewById(R.id.invuln_save_tv);
            wounds = itemView.findViewById(R.id.wounds_tv);
            leadership = itemView.findViewById(R.id.leadership_tv);
            objectiveControl = itemView.findViewById(R.id.objective_control_tv);
            Log.v("StatlineAdapter.CharacteristicsHolder", "constructor done");
        }

    }

    public StatlineAdapter(List<Statline> statlineList) {
        this.statlineList = statlineList;

        Log.v("StatlineAdapter", "StatlineList size: "+statlineList.size());
    }
}
