package com.example.javahammer.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.activities.MainActivity;
//import com.example.javahammer.adapters.ModelAdapter;
import com.example.javahammer.data.Unit;
import com.example.javahammer.data.Weapon;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Map;

public class DamageCalculatorFragment extends Fragment {

    String TAG = "DamageCalculator";
    public com.example.javahammer.activities.DamageCalculator.DefenderAdapter defenderAdapter;
    public RecyclerView defenderRv;
 //   public ModelAdapter modelAdapter;
    public RecyclerView modelRv;

    public MainActivity mainActivity;

    public Unit attackerUnit;
    public EnumSet<com.example.javahammer.activities.DamageCalculator.AttackFlags> attackFlags;

    public enum AttackFlags {
        RR_HITS_OF_ONE, RR_HITS_ALL, RR_WOUNDS_OF_ONE, IN_COVER, RR_WOUNDS_ALL
    };

    public DamageCalculatorFragment(Unit attackerUnit) {
        this.attackerUnit = attackerUnit;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_damage_calculator, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        MainActivity mainActivity = (MainActivity) getActivity();

     /*
        Unit termagaunts = new Unit("Termagaunts",
                new HashMap<Model, Triple<Integer, Integer,Integer>>() {{
                        put(new Model ("Termagaunt", new Statline(6, 4, 5, 1, 8, 2), new ArrayList<>()), new Triple<>(1, 20, 20));
        }});


        Unit intercessor = new Unit("Intercessor Squad",
                new HashMap<Model, Triple<Integer, Integer, Integer>>() {{
                        put(new Model("Inew Statline(6, 4, 3, 2, 6, 2))
                }});



        Unit terminator = new Unit("Terminator Squad",
                new HashMap<Model, Triple<Integer, Integer, Integer>>() {{
                    put(new Model("Terminator", new Statline(5, 5, 2, 3, 6, 1), new ArrayList<>()), new Triple<>(5, 5, 9));
                }});


      //  terminator.addModel("Terminator", Arrays.asList(), 5);

        ArrayList<Unit> defenderUnits = new ArrayList<Unit>();
    //    attackerUnit.models.get(0).attack(defender);
        defenderUnits.add(termagaunts);
     //   defenderUnits.add(intercessor);
        defenderUnits.add(terminator);

        attackFlags = EnumSet.noneOf(AttackFlags.class);

        defenderAdapter = new DefenderAdapter(defenderUnits, attackerUnit);
        defenderRv = findViewById(R.id.defending_units_rv);
        defenderRv.setAdapter(defenderAdapter);
        defenderRv.setLayoutManager(new LinearLayoutManager(this));
   */
    //    modelAdapter = new ModelAdapter(attackerUnit.getUnitComposition());
        modelRv = view.findViewById(R.id.models_rv_damage);
     //   modelRv.setAdapter(modelAdapter);
        modelRv.setLayoutManager(new LinearLayoutManager(mainActivity));

//        modelAdapter.setListener(this);

        Button rrOnesBtn = view.findViewById(R.id.rr_hits_ones_btn);
        rrOnesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSetting(rrOnesBtn, com.example.javahammer.activities.DamageCalculator.AttackFlags.RR_HITS_OF_ONE);
            }
        });

        Button rrHitsAllBtn = view.findViewById(R.id.rr_hits_all_btn);
        rrHitsAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSetting(rrHitsAllBtn, com.example.javahammer.activities.DamageCalculator.AttackFlags.RR_HITS_ALL);
            }
        });

        Button rrWoundsOneBtn = view.findViewById(R.id.rr_wounds_ones_btn);
        rrWoundsOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSetting(rrWoundsOneBtn, com.example.javahammer.activities.DamageCalculator.AttackFlags.RR_WOUNDS_OF_ONE);
            }
        });

        Button rrWoundsAllBtn = view.findViewById(R.id.rr_wounds_all_btn);
        rrWoundsAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSetting(rrWoundsAllBtn, com.example.javahammer.activities.DamageCalculator.AttackFlags.RR_WOUNDS_ALL);
            }
        });

        Button inCoverBtn = view.findViewById(R.id.in_cover);
        inCoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSetting(inCoverBtn, com.example.javahammer.activities.DamageCalculator.AttackFlags.IN_COVER);
            }
        });

    }
    public void unitUpdated() {
        defenderAdapter.notifyDataSetChanged();
    }
    public void updateSetting(Button btn, com.example.javahammer.activities.DamageCalculator.AttackFlags setting) {
        if (attackFlags.contains(setting)) {
            btn.setText(btn.getText().subSequence(0, btn.getText().length() - 1));
            attackFlags.remove(setting);
        }
        else {
            btn.append("✓");
            attackFlags.add(setting);
        }
        defenderAdapter.notifyDataSetChanged();
    }
    public class DefenderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context context;
        private Unit attackerUnit;
        private ArrayList<Unit> units;

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View contactView = inflater.inflate(R.layout.item_defenders, parent, false);
            return new DefenderUnitHolder(contactView);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            Unit defenderUnit = units.get(position);
//
            DefenderUnitHolder unitHolder = (DefenderUnitHolder) holder;

            unitHolder.name.setText(defenderUnit.getNumberModels() +"x " + defenderUnit.getName());
            unitHolder.modelsKilled.setText("" +0);
            unitHolder.probToKillUnit.setText("" +0);

            Map<Weapon, Pair<Double, Double>> results = attackerUnit.attack(defenderUnit, attackFlags);
            double modelsKilled = 0.0;
            for (Map.Entry<Weapon, Pair<Double, Double>> resultForWeapon : results.entrySet()) {

                if (!resultForWeapon.getKey().getKeywords().contains(Weapon.Keywords.PISTOL)) {

                    modelsKilled += resultForWeapon.getValue().first;
                    unitHolder.probToKillUnit.setText(String.format("%.2f", (100 * resultForWeapon.getValue().second)) +"%");
                }
            }
            unitHolder.modelsKilled.setText(String.format("%.2f", modelsKilled));
        }
        public void updateDamage() {

        }
        @Override
        public int getItemViewType(int position) {
            return 0;
        }

        @Override
        public int getItemCount() {
            return units.size();
        }

        public void setDamageProjection(Unit attackerUnit) {
        }

        class DefenderUnitHolder extends RecyclerView.ViewHolder {
            public TextView name;
            public TextView modelsKilled;
            public TextView probToKillUnit;

            public DefenderUnitHolder(View itemView) {
                super(itemView);
                name = itemView.findViewById(R.id.defending_unit_name_tv);
                modelsKilled = itemView.findViewById(R.id.defending_unit_models_killed);
                probToKillUnit = itemView.findViewById(R.id.defending_unit_wiped_prob);
            }
        }

        public DefenderAdapter(ArrayList<Unit> units, Unit attackerUnit) {
            this.units = units;
            this.attackerUnit = attackerUnit;
        }
    }
}