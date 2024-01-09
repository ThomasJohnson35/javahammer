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

public class FragmentAttacker extends FragmentDatasheetsHandler {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v("FragmentAttacker", "onCreateView start");
        View view = inflater.inflate(R.layout.fragment_attacker, container, false);
        Log.v("FragmentAttacker", "onCreateView start 2");
        recyclerView = (RecyclerView) view.findViewById(R.id.attackerRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        Log.v("FragmentAttacker", "onCreateView start 3");
        adapter = new AttackerUnitAdapter();
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);
        Log.v("FragmentAttacker", "onCreateView start 4");
        return view;
    }
    @Override
    public void onAddUnitClick() {
        listener.AddAttackerClick();
    }

    @Override
    public void onSelectUnitClick(Unit d) { listener.SelectAttackerClick(d);
    }
    class AttackerUnitAdapter extends UnitAdapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

            if (viewType == 0) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attacker_unit, parent, false);
                return new AttackerUnit(view);
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
                AttackerUnit du = (AttackerUnit) holder;

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
        class AttackerUnit extends UnitHolder {
            public AttackerUnit(@NonNull View itemView) {
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
        public AttackerUnitAdapter() {
            units = new ArrayList<Unit>();
            Log.v("FragmentAttacker", "AttackerUnitAdapter Constructor call");
        }
    }
}