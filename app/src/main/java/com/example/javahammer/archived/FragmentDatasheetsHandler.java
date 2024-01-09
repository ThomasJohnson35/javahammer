package com.example.javahammer.archived;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.data.Unit;

import java.util.ArrayList;
import java.util.List;

public class FragmentDatasheetsHandler extends Fragment implements FragmentDatasheetsHandlerListener {


    RecyclerView recyclerView;
    protected ArrayList<Unit> units;
    protected UnitAdapter adapter;
    protected QuickCalculatorListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v("FragmentDatasheetsHandler", "onCreateView start");
        View view = inflater.inflate(R.layout.fragment_attacker, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.attackerRV);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        adapter = new FragmentAttacker.UnitAdapter();
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        return view;

        //      attackerMsg = (TextView) container.findViewById(R.id.attackerMsg);
        //     rvAttacker = (RecyclerView)  container.findViewById(R.id.attackerRV);

    }
    @Override
    public void onAddUnitClick() {
    }

    @Override
    public void onSelectUnitClick(Unit d) {
    }

    public void setListener(QuickCalculatorListener listener) {
        this.listener = listener;
    }


    public void addUnit(Unit unit) {
        adapter.addUnit(unit);
        adapter.notifyDataSetChanged();
    }

    class UnitAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        protected List<Unit> units;
        protected FragmentDatasheetsHandlerListener listener;
        public void addUnit(Unit unit) {
            units.add(unit);
        }

        public void setListener(FragmentDatasheetsHandlerListener listener) {
            this.listener = listener;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
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
                UnitHolder du = (UnitHolder) holder;

                du.unitName.setText(units.get(position).getName());
                //     du.unitName.setText("" +position);

                du.selectBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                    }
                });
            }
        }


        @Override
        public int getItemViewType(int position) {

            Log.v("FragmentDefender getItemViewType", "position: " +position +"\n"
                    + "ItemCount: " +getItemCount());

            if (position == getItemCount()-1) {
                return 1;
            }
            else {
                return 0;
            }
        }

        @Override
        public int getItemCount() {
            return units.size() + 1;
        }

        class UnitHolder extends RecyclerView.ViewHolder {

            public Button selectBtn;
            public TextView unitName;

            public UnitHolder(@NonNull View itemView) {
                super(itemView);

                unitName = itemView.findViewById(R.id.unitName);

                selectBtn = itemView.findViewById(R.id.selectBtn);
            }
        }

        class RowHolder extends RecyclerView.ViewHolder {

            public Button addBtn;

            public RowHolder(@NonNull View itemView) {
                super(itemView);

                addBtn = (Button) itemView.findViewById(R.id.addUnitBtn);
            }
        }

        public UnitAdapter() {
            units = new ArrayList<Unit>();
            Log.v("FragmentDatasheetsHandler", "UnitAdapter Constructor call");
        }
    }
}
