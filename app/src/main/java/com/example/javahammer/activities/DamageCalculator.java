package com.example.javahammer.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.javahammer.adapters.ModelAdapter;
import com.example.javahammer.R;
import com.example.javahammer.data.Unit;
import com.example.javahammer.data.Weapon;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Map;

public class DamageCalculator extends AppCompatActivity {

    String TAG = "DamageCalculator";
    public DefenderAdapter defenderAdapter;
    public RecyclerView defenderRv;
    public ModelAdapter modelAdapter;
    public RecyclerView modelRv;

    public EnumSet<AttackFlags> attackFlags;

    public enum AttackFlags {
        RR_HITS_OF_ONE, RR_HITS_ALL, RR_WOUNDS_OF_ONE, IN_COVER, RR_WOUNDS_ALL
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damage_calculator);

        Unit attackerUnit = getIntent().getParcelableExtra("Unit");
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
        modelAdapter = new ModelAdapter(attackerUnit.getUnitComposition());
        modelRv = findViewById(R.id.models_rv_damage);
        modelRv.setAdapter(modelAdapter);
        modelRv.setLayoutManager(new LinearLayoutManager(this));

//        modelAdapter.setListener(this);

        Button rrOnesBtn = findViewById(R.id.rr_hits_ones_btn);
        rrOnesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSetting(rrOnesBtn, AttackFlags.RR_HITS_OF_ONE);
            }
        });

        Button rrHitsAllBtn = findViewById(R.id.rr_hits_all_btn);
        rrHitsAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSetting(rrHitsAllBtn, AttackFlags.RR_HITS_ALL);
            }
        });

        Button rrWoundsOneBtn = findViewById(R.id.rr_wounds_ones_btn);
        rrWoundsOneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSetting(rrWoundsOneBtn, AttackFlags.RR_WOUNDS_OF_ONE);
            }
        });

        Button rrWoundsAllBtn = findViewById(R.id.rr_wounds_all_btn);
        rrWoundsAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSetting(rrWoundsAllBtn, AttackFlags.RR_WOUNDS_ALL);
            }
        });

        Button inCoverBtn = findViewById(R.id.in_cover);
        inCoverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSetting(inCoverBtn, AttackFlags.IN_COVER);
            }
        });

    }

    public void unitUpdated() {
        defenderAdapter.notifyDataSetChanged();
    }
    public void updateSetting(Button btn, AttackFlags setting) {
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