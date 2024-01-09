package com.example.javahammer.archived;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.javahammer.R;
import com.example.javahammer.data.Unit;

import java.util.ArrayList;

public class FragmentDefender extends FragmentDatasheetsHandler {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("FragmentDefender", "onCreateView start");
        View view = inflater.inflate(R.layout.fragment_defender, container, false);
        Log.v("FragmentDefender", "onCreateView start 2");
        recyclerView = (RecyclerView) view.findViewById(R.id.attackerRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        Log.v("FragmentDefender", "onCreateView start 3");
        adapter = new DefenderUnitAdapter();
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        Log.v("FragmentDefender", "onCreateView start 4");
        // Inflate the layout for this fragment
        return view;
    }
    @Override
    public void onAddUnitClick() {
        listener.AddDefenderClick();
    }

    @Override
    public void onSelectUnitClick(Unit unit) { listener.SelectDefenderClick(unit);
    }

    class DefenderUnitAdapter extends UnitAdapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            if (viewType == 0) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_unit, parent, false);
                return new DefenderUnit(view);
            } else {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_unit, parent, false);
                return new RowHolder(view);
            }

        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            // If item_add_unit
            if (position == getItemCount() - 1) {

                RowHolder rh = (RowHolder) holder;

                rh.addBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        listener.onAddUnitClick();
                    }
                });

            } else {
                DefenderUnit du = (DefenderUnit) holder;

                du.unitName.setText(units.get(position).getName());
           //     du.unitName.setText("" +position);

                du.selectBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        listener.onSelectUnitClick(units.get(holder.getAdapterPosition()));
                    }
                });
            }
        }

        class DefenderUnit extends UnitHolder {

            public DefenderUnit(@NonNull View itemView) {
                super(itemView);
            }
        }
        class RowHolder extends RecyclerView.ViewHolder {

            public Button addBtn;

            public RowHolder(@NonNull View itemView) {
                super(itemView);

                addBtn = (Button) itemView.findViewById(R.id.addUnitBtn);
            }
        }

        public DefenderUnitAdapter() {
            units = new ArrayList<Unit>();
            Log.v("FragmentDefender", "DefenderUnitAdapter Constructor call");
        }
    }
}