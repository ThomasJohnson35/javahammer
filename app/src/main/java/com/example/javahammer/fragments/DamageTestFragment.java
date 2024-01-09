package com.example.javahammer.fragments;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.activities.DamageCalculator;
import com.example.javahammer.activities.MainActivity;
import com.example.javahammer.adapters.DamageTestAttackerAdapter;
import com.example.javahammer.adapters.DamageTestDefenderAdapter;
import com.example.javahammer.adapters.KeywordAdapter;
import com.example.javahammer.adapters.ResultsExtraAdapter;
import com.example.javahammer.data.Enhancement;
import com.example.javahammer.data.Model;
import com.example.javahammer.data.Statline;
import com.example.javahammer.data.Weapon;
import com.example.javahammer.interfaces.ProfileAdapterListener;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DamageTestFragment extends Fragment implements ProfileAdapterListener {

    String TAG = "DamageTestFragment";
    MainActivity mainActivity;
    View view;
    DamageTestAttackerAdapter damageTestAttackerAdapter;
    DamageTestDefenderAdapter defendingModelAdapter;

    public Weapon weapon;
    public Model defendingModel;
    public ArrayList<Model> defendingModelArrayList;
    public ArrayList<Weapon> weaponArrayList;


    public boolean switchingText = false;

    // UI Elements
    public ConstraintLayout resultsLayout;
    public EditText attacks, bs, strength, ap, damage, toughness, wounds, save, invulnerable;
    public RecyclerView attackerRv, defenderRv;
    public TextView weaponKeywords, attacksResult, hitsResult, woundsResult, unsavedWoundsResult, damageDealtResults, deadModelsResults;
    public RecyclerView attacksResultsExtraRv, hitsResultsExtraRv, woundsResultsExtraRv, unsavedWoundsResultsExtraRv, damageDealtResultsExtraRv, deadModelsResultsExtraRv;
    public Toolbar toolbar;
    public DamageTestFragment() {
        weaponArrayList = new ArrayList<Weapon>();
        if (weaponArrayList.isEmpty()) {
            weaponArrayList.add(new Weapon(String.format("Weapon %d", weaponArrayList.size() + 1), -1, "", -1, -1, -1, ""));
        }
        weapon = weaponArrayList.get(0);


        defendingModelArrayList = new ArrayList<Model>() {{
            add(new Model("Light Infantry", new Statline(6, 3, 5, 1, 6, 6), new ArrayList<>(), EnumSet.of(Model.ModelKeywords.INFANTRY)));
            add(new Model("Medium Infantry", new Statline(6, 4, 3, 2, 6, 6), new ArrayList<>(), EnumSet.of(Model.ModelKeywords.INFANTRY)));
            add(new Model("Heavy Infantry", new Statline(6, 5, 2, 3, 6, 6), new ArrayList<>(), EnumSet.of(Model.ModelKeywords.INFANTRY)));
            add(new Model("Light Vehicle", new Statline(6, 9, 3, 11, 6, 2), new ArrayList<>(), EnumSet.of(Model.ModelKeywords.VEHICLE)));
            add(new Model("Heavy Vehicle", new Statline(6, 12, 3, 16, 6, 6), new ArrayList<>(), EnumSet.of(Model.ModelKeywords.VEHICLE)));
        }};
        defendingModel = defendingModelArrayList.get(0);
    }

    public DamageTestFragment(ArrayList<Weapon> weaponArrayList) {
        this();

        this.weaponArrayList = weaponArrayList;
        this.weapon = weaponArrayList.get(0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_damage_test, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.view = view;
        mainActivity = (MainActivity) getActivity();

        // Binding Toolbar
        toolbar = view.findViewById(R.id.fragment_damage_test_toolbar);
        toolbar.setTitle("Damage Tester");

        // Attacking Weapon Adapter
        damageTestAttackerAdapter = new DamageTestAttackerAdapter(weaponArrayList);
        damageTestAttackerAdapter.setListener(this);
        attackerRv = view.findViewById(R.id.attacker_rv_fragment_damage_test);
        attackerRv.setAdapter(damageTestAttackerAdapter);
        attackerRv.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));

        // Defending Model Adapter
        defendingModelAdapter = new DamageTestDefenderAdapter(defendingModelArrayList);
        defendingModelAdapter.setListener(this);
        defenderRv = view.findViewById(R.id.defender_rv_fragment_damage_test);
        defenderRv.setAdapter(defendingModelAdapter);
        defenderRv.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));

        // Finding Edit Text Elements
        weaponKeywords = view.findViewById(R.id.weapon_keywords_tv_fragment_damage_test);
        attacks = view.findViewById(R.id.attacks_et_damage_test);
        bs = view.findViewById(R.id.bs_et_damage_test);
        strength = view.findViewById(R.id.strength_input_tv_damage_test);
        ap = view.findViewById(R.id.ap_et);
        damage = view.findViewById(R.id.damage_et);
        toughness = view.findViewById(R.id.toughness_et_damage_test);
        wounds = view.findViewById(R.id.wounds_et_damage_test);
        save = view.findViewById(R.id.save_et_damage_test);
        invulnerable = view.findViewById(R.id.invlunerable_et_damage_test);


        // Attacker Edit Text Fields

        weaponKeywords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopWindow();
            }
        });
        attacks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (!switchingText) {
                    weapon.setAttacks(charSequence.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateDamageCalc();
            }
        });

        bs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!switchingText) {
                    try {
                        weapon.setBs(Integer.parseInt(charSequence.toString()));
                    } catch (NumberFormatException e) {

                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateDamageCalc();
            }
        });

        strength.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!switchingText) {
                    try {
                        weapon.setStrength(Integer.parseInt(charSequence.toString()));
                    } catch (NumberFormatException e) {

                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateDamageCalc();
            }
        });

        ap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!switchingText) {
                    try {
                        weapon.setAp(Integer.parseInt(charSequence.toString()));
                    } catch (NumberFormatException e) {

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateDamageCalc();
            }
        });

        damage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!switchingText) {
                    try {
                        weapon.setDamage(charSequence.toString());
                    } catch (NumberFormatException e) {

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateDamageCalc();
            }
        });

        // Defender Edit Text Fields
        toughness.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!switchingText) {
                    try {
                        defendingModel.setToughness(Integer.parseInt(charSequence.toString()));
                    } catch (NumberFormatException e) {

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateDamageCalc();
            }
        });

        wounds.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!switchingText) {
                    try {
                        defendingModel.setWounds(Integer.parseInt(charSequence.toString()));
                    } catch (NumberFormatException e) {

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateDamageCalc();
            }
        });

        save.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!switchingText) {
                    try {
                        defendingModel.setSave(Integer.parseInt(charSequence.toString()));
                    } catch (NumberFormatException e) {

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateDamageCalc();
            }
        });

        invulnerable.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!switchingText) {
                    try {
                        defendingModel.setInvulnerableSave(Integer.parseInt(charSequence.toString()));
                    } catch (NumberFormatException e) {

                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateDamageCalc();
            }
        });

        resultsLayout = view.findViewById(R.id.results_layout);
        attacksResultsExtraRv = view.findViewById(R.id.attacks_results_modifiers_rv);
        attacksResult = view.findViewById(R.id.attacks_results_output_tv);
        hitsResultsExtraRv = view.findViewById(R.id.hits_results_extra_rv);
        hitsResult = view.findViewById(R.id.hits_results_output_tv);
        woundsResultsExtraRv = view.findViewById(R.id.wounds_results_modifiers_rv);
        woundsResult = view.findViewById(R.id.wounds_results_output_tv);
        unsavedWoundsResultsExtraRv = view.findViewById(R.id.unsaved_wounds_result_rv);
        unsavedWoundsResult = view.findViewById(R.id.unsaved_wounds_results_output_tv);
        damageDealtResultsExtraRv = view.findViewById(R.id.damage_dealt_result_rv);
        damageDealtResults = view.findViewById(R.id.damage_dealt_results_output_tv);
        deadModelsResultsExtraRv = view.findViewById(R.id.dead_models_result_rv);
        deadModelsResults = view.findViewById(R.id.dead_models_results_output_tv);

        updateDefenderEditText();
        updateWeaponEditText();
        updateDamageCalc();
    }
    public void updateDamageCalc() {
        // TODO check if weapon is valid

        if (weapon.getName().equals("") ||
            weapon.getAttacks().equals("") ||
            weapon.getAp() == -1 ||
            weapon.getDamage() == "" ||

            defendingModel.getStatline().getToughness() == -1 ||
            defendingModel.getStatline().getWounds() == -1 ||
            defendingModel.getStatline().getArmorSave() == -1
        )
        {
            Toast.makeText(mainActivity, "Weapon Invalid", Toast.LENGTH_SHORT).show();


            // Remove damage calculation
            toolbar.setSubtitle("");
            resultsLayout.setVisibility(View.GONE);
        } else {
            // Add Damage Calculation

            Map<Pair<Integer, Integer>, Double> g = calculateDamage(defendingModel, EnumSet.noneOf(DamageCalculator.AttackFlags.class));
            Log.v("Thomas Update", g.toString());

            double sum = 0;
            double avgWoundsDealt = 0;
            double avgDeadModels = 0;
            for(Map.Entry<Pair<Integer, Integer>, Double> entry : g.entrySet()) {
                sum += entry.getValue();

                avgWoundsDealt += ( (entry.getKey().first * defendingModel.getStatline().getWounds()) + entry.getKey().second)  * entry.getValue();
                avgDeadModels +=  ( (entry.getKey().first * entry.getValue()));


            }
            Log.v("Thomas Update Sum", "" +sum);
            // Mean Wounds Dealt

            toolbar.setSubtitle(String.format("Wounds Dealt: %,.3f \t Dead Models %,.3f", avgWoundsDealt, avgDeadModels));

            damageDealtResults.setText(String.format("%.3f", avgWoundsDealt));
            deadModelsResults.setText(String.format("%.3f", avgDeadModels));
            resultsLayout.setVisibility(View.VISIBLE);
        }
    }
    private Map<Integer, Double> probabilitySplit(Map<Integer, Double> inputProbability, double hitPercentage) {
        Map<Integer, Double> outputProbability = new HashMap<Integer, Double>();
        for (Map.Entry<Integer, Double> entry : inputProbability.entrySet()) {

            // Misses
            outputProbability.put(entry.getKey(),  outputProbability.getOrDefault(entry.getKey(), 0.0) + entry.getValue() * (1 - hitPercentage));
            // Hits
            outputProbability.put(entry.getKey() + 1, entry.getValue() * hitPercentage);
        }
        return outputProbability;
    }
    private Map<Integer, Double> probabilitySplit2(Map<Integer, Double> outputProbability, Map.Entry<Integer, Double> inputProbabilityOutcome, Map<Integer, Double> alterByProbability) {
        for (Map.Entry<Integer, Double> alterByProbabilityOutcome : alterByProbability.entrySet()) {
            outputProbability.put(alterByProbabilityOutcome.getKey(), outputProbability.getOrDefault(alterByProbabilityOutcome.getKey(), 0.0) + inputProbabilityOutcome.getValue() * alterByProbabilityOutcome.getValue());
        }
        return outputProbability;
    }
    public Map<Pair<Integer, Integer>, Double> calculateDamage (Model model, EnumSet<DamageCalculator.AttackFlags> attackFlags) {


        Log.v(TAG, weapon.getName() +" firing at " +model.getName());
        Log.v(TAG, "attack flags " +attackFlags);


        // ATTACK GENERATION SEQUENCE
        Map<Integer, Double> attacks = new HashMap<>();

        ArrayList<String> attacksResultsExtra = new ArrayList<>();
        ArrayList<String> hitsResultsExtra = new ArrayList<>();
        ArrayList<String> woundsResultsExtra = new ArrayList<>();
        ArrayList<String> unsavedWoundsResultsExtra = new ArrayList<>();
        ArrayList<String> damageDealtResultsExtra = new ArrayList<>();
        ArrayList<String> deadModelsResultsExtra = new ArrayList<>();

        try {
            attacks.put(Integer.parseInt(weapon.getAttacks()), 1.0 );
        } catch (NumberFormatException exception) {
            attacks.put(0, 1.0);

            Pattern numberedD6 = Pattern.compile("([0-9]*)(D|d)6");
            Pattern numberedD3 = Pattern.compile("([0-9]*)(D|d)3");

            Matcher matcher = numberedD6.matcher(weapon.getAttacks());

            // Adds avg of 3.5 attacks per D6
            if (matcher.find()) {

                int num = 0;

                // If there is no number present before the D6
                if (matcher.group(1).equals("")) {
                    num = 1;
                } else {
                    num = Integer.parseInt(matcher.group(1));
                }

                for (int i = 0; i < num; i++) {
                    Map<Integer, Double> outputProbability = new HashMap<Integer, Double>();
                    for (Map.Entry<Integer, Double> entry : attacks.entrySet()) {
                        outputProbability.put(entry.getKey() + 1, outputProbability.getOrDefault(entry.getKey() + 1, 0.0) + entry.getValue() * ( (double) 1/6));
                        outputProbability.put(entry.getKey() + 2, outputProbability.getOrDefault(entry.getKey() + 2, 0.0) + entry.getValue() * ( (double) 1/6));
                        outputProbability.put(entry.getKey() + 3, outputProbability.getOrDefault(entry.getKey() + 3, 0.0) + entry.getValue() * ( (double) 1/6));
                        outputProbability.put(entry.getKey() + 4, outputProbability.getOrDefault(entry.getKey() + 4, 0.0) + entry.getValue() * ( (double) 1/6));
                        outputProbability.put(entry.getKey() + 5, outputProbability.getOrDefault(entry.getKey() + 5, 0.0) + entry.getValue() * ( (double) 1/6));
                        outputProbability.put(entry.getKey() + 6, outputProbability.getOrDefault(entry.getKey() + 6, 0.0) + entry.getValue() * ( (double) 1/6));
                    }
                    attacks = outputProbability;
                }
            }
        }


        Log.v(TAG, weapon.getName() +" attacks " +attacks);

        if (weapon.getKeywords().contains(Weapon.Keywords.BLAST)) {
            attacksResultsExtra.add("Blast: %d Extra Attacks");
        }

        double attacksAvg = 0.0;

        for (Map.Entry<Integer, Double> entry : attacks.entrySet()) {
            attacksAvg += (entry.getKey() * entry.getValue());
        }
        attacksResult.setText(String.format("%.3f", attacksAvg));

        attacksResultsExtraRv.setAdapter(new ResultsExtraAdapter(attacksResultsExtra));
        attacksResultsExtraRv.setLayoutManager(new LinearLayoutManager(getContext()));

        // END OF ATTACKS GENERATION SEQUENCE

        // HITS GENERATION SEQUENCE
        Map<Integer, Double> numHits = new HashMap<Integer, Double>();

        if (weapon.getKeywords().stream().anyMatch(n -> n.keyword.equals(Weapon.Keywords.TORRENT))) {
            numHits = attacks;
            hitsResultsExtra.add("Torrent: All Attacks Auto-hit");
        } else {
            Log.v(TAG, "BS " +bs);

            // Parses bs String as a percentage of hits
            Double hitPercentage;
            if (weapon.getBs() == 2) {
                hitPercentage = (double) 5/6;
            } else if (weapon.getBs() == 3) {
                hitPercentage = (double) 4/6;
            } else if (weapon.getBs() == 4) {
                hitPercentage = (double) 3/6;
            } else if (weapon.getBs() == 5) {
                hitPercentage = (double) 2/6;
            } else if (weapon.getBs() == 6) {
                hitPercentage = (double) 1/6;
            } else {
                Log.v(TAG, "FATAL ERROR!!!");
                throw new RuntimeException();
            }

            // Re-calculate's hit percentage due to for  re-rolls
            if (attackFlags.contains(DamageCalculator.AttackFlags.RR_HITS_ALL)) {
                hitPercentage += ((1 - hitPercentage) * hitPercentage);
            } else if (attackFlags.contains(DamageCalculator.AttackFlags.RR_HITS_OF_ONE)) {
                hitPercentage += ( (double) 1/6 * hitPercentage);
            }

            Log.v(TAG, "Hitting " +hitPercentage +" of the time!");

            hitsResultsExtra.add(String.format("Hitting on a %d+", (int) (7 - (6 * hitPercentage))));

            Map<Integer, Double> hitsProbability = new HashMap<Integer, Double>();
            hitsProbability.put(0, 1.0);

            for (Map.Entry<Integer, Double> entry : attacks.entrySet()) {

                // For each outcome in the current wound probability map split into hits and misses
                if (entry.getKey() != 0) {
                    hitsProbability = probabilitySplit(hitsProbability, hitPercentage);
                }

                //   Log.v(TAG, "Wound Probability for this matrix" + woundsProbability);
                numHits = probabilitySplit2(numHits, entry, hitsProbability);

            }
        }

        // Averages out the hits
        double avgHits = 0.0;

        for (Map.Entry<Integer, Double> entry : numHits.entrySet()) {
            avgHits += (entry.getKey() *  entry.getValue());
        }

        hitsResult.setText(String.format("%.3f", avgHits));

        hitsResultsExtraRv.setAdapter(new ResultsExtraAdapter(hitsResultsExtra));
        hitsResultsExtraRv.setLayoutManager(new LinearLayoutManager(getContext()));

        // END OF HITS GENERATION SEQUENCE

        // WOUNDS GENERATION SEQUENCE

        // Determines weapon Strength
        Double strength = (double) weapon.getStrength();
        // Determines average toughness of defending unit
        Double toughness = model.getStatline().getToughness().doubleValue();
        Log.v(TAG, "Strength " +strength +" vs " +toughness);

        // Wound sequence
        double strengthOverToughness = strength / toughness;
        double woundPercentage;
        // If strength is double toughness
        if(strengthOverToughness >= 2) {
            // Wounds on 2's
            woundPercentage = (double) 5/6;
        } else if (strengthOverToughness > 1) {
            woundPercentage = (double) 4/6;
            // Wounds on 3's
        } else if (strengthOverToughness == 1) {
            woundPercentage = (double) 3/6;
            // Wounds on 4's
        } else if (strengthOverToughness > 0.5) {
            woundPercentage = (double) 2/6;
            // Wounds on 5's
        } else {
            woundPercentage = (double) 1/6;
        }

        // Re-calculate's wound percentage due to re-rolls
        if (attackFlags.contains(DamageCalculator.AttackFlags.RR_WOUNDS_ALL)) {
            woundsResultsExtra.add(String.format("Re-rolling all failed wounds"));
            woundPercentage += ((1 - woundPercentage) * woundPercentage);
        } else if (attackFlags.contains(DamageCalculator.AttackFlags.RR_WOUNDS_OF_ONE)) {
            woundsResultsExtra.add(String.format("Re-rolling failed wound rolls of 1"));
            woundPercentage += ( (double) 1/6 * woundPercentage);
        }

        Log.v(TAG, "Wounding on a : " + (int) (7 - (6 * woundPercentage)) +"+");
        woundsResultsExtra.add(String.format("Wounding on a %d+", (int) (7 - (6 * woundPercentage))));

        Map<Integer, Double> woundsProbability = new HashMap<Integer, Double>();
        woundsProbability.put(0, 1.0);

        Map<Integer, Double> numHitAndWound = new HashMap<Integer, Double>();
        for (Map.Entry<Integer, Double> entry : numHits.entrySet()) {

            // For each outcome in the current wound probability map split into hits and misses
            if (entry.getKey() != 0) {
                woundsProbability = probabilitySplit(woundsProbability, woundPercentage);
            }
            //   Log.v(TAG, "Wound Probability for this matrix" + woundsProbability);
            numHitAndWound = probabilitySplit2(numHitAndWound, entry, woundsProbability);
        }

        double avgWounds = 0.0;
        for (Map.Entry<Integer, Double> entry : numHitAndWound.entrySet()) {
            avgWounds += (entry.getKey() * entry.getValue());
        }
        woundsResult.setText(String.format("%.3f", avgWounds));

        woundsResultsExtraRv.setAdapter(new ResultsExtraAdapter(woundsResultsExtra));
        woundsResultsExtraRv.setLayoutManager(new LinearLayoutManager(getContext()));

        // END OF WOUNDS GENRATION SEQUENCE








        // UNSAVED WOUNDS GENERATION SEQUENCE
        Log.v(TAG, "# Wounds Probability:" +numHits);

        // Want damage probability per wound given
        // Get Save_Perc
        Integer sv = model.getStatline().getArmorSave();
        double savePercentage =  ( (double) ( 1 - ( (sv - 1) / 6.0)) );
        Log.v(TAG, "Armour save of " +sv );
        Log.v(TAG, String.format("Saving %s of the time before AP ", +savePercentage) );
        // Determines AP value of weapon
        Log.v(TAG, "AP -"+ap);

        // Applies AP
        savePercentage -=  ((double) 1/6) * weapon.getAp();

        if (attackFlags.contains(DamageCalculator.AttackFlags.IN_COVER)) {
            Log.v(TAG, "In cover ");
            savePercentage += ((double) 1/6);
        }

        // Even if save is modified above a 2+, ensures that rolls of 1 will still fail
        if (savePercentage > ((double) 5/6)) {
            savePercentage = ((double) 5/6);
        } else if (savePercentage < (double) 0) {
            savePercentage = (double) 0;
        }

        Log.v(TAG, "Saving on a " + (int) (7 - (6 * savePercentage)) +"+");

        unsavedWoundsResultsExtra.add(String.format("Saving on a %d+", (int) (7 - (6 * savePercentage))));

        woundsProbability = numHitAndWound;
        Map<Integer, Double> saveProbability =  new HashMap<Integer, Double>();
        saveProbability.put(0, 1.0);
        Map<Integer, Double> numUnsavedWounds = new HashMap<Integer, Double>();
        for (Map.Entry<Integer, Double> entry : woundsProbability.entrySet()) {

            if (entry.getKey() != 0) {
                saveProbability = probabilitySplit(saveProbability, 1 - savePercentage);
            }

            numUnsavedWounds = probabilitySplit2(numUnsavedWounds, entry, saveProbability);

        }

        double avgUnsavedWounds = 0.0;

        for (Map.Entry<Integer, Double> entry : numUnsavedWounds.entrySet()) {
            avgUnsavedWounds += (entry.getKey() * entry.getValue());
        }
        unsavedWoundsResult.setText(String.format("%.3f", avgUnsavedWounds));
        unsavedWoundsResultsExtraRv.setAdapter(new ResultsExtraAdapter(unsavedWoundsResultsExtra));
        unsavedWoundsResultsExtraRv.setLayoutManager(new LinearLayoutManager(getContext()));

        Log.v(TAG, "# Unsaved Wounds " +numUnsavedWounds);

        // Determine damage characteristic
        int damage = Integer.parseInt(weapon.getDamage());
        // Damage parser
        Map<Integer, Double> damageProbMatrix = new HashMap<Integer, Double>();
        // Probability split on damage characteristics
        // TODO
        // Flat Damage
        damageProbMatrix.put(damage, 1.0);
        Map<Pair<Integer, Integer>, Double> woundsDealtProbability = new HashMap<Pair<Integer, Integer>, Double>();
        woundsDealtProbability.put(new Pair<Integer, Integer>(0,0), 1.0);
        Map<Pair<Integer, Integer>, Double> modelsKilledProbability = new HashMap<Pair<Integer, Integer>, Double>();
        for (Map.Entry<Integer, Double> unsavedWoundsOutcome : numUnsavedWounds.entrySet()) {

            // FNP CALC
            // TODO

            if (unsavedWoundsOutcome.getKey() != 0) {

                // Apply damage characteristic to all possible outcomes so far
                Map<Pair<Integer, Integer>, Double> newWoundsDealtProbability = null;
                for (Map.Entry<Pair<Integer, Integer>, Double> woundsDealtOutcome : woundsDealtProbability.entrySet()) {

                    newWoundsDealtProbability = new HashMap<Pair<Integer, Integer>, Double>();

                    for (Map.Entry<Integer, Double> damageOutcome : damageProbMatrix.entrySet()) {
                        Pair<Integer, Integer> deadModelsAndWounds;
                        if (woundsDealtOutcome.getKey().second + damageOutcome.getKey() >= Integer.valueOf(model.getStatline().getWounds())) {
                            // Damage has killed model
                            deadModelsAndWounds = new Pair<Integer, Integer>(woundsDealtOutcome.getKey().first + 1, 0);
                        } else {
                            // Damage has wounded model
                            deadModelsAndWounds = new Pair<Integer, Integer>(woundsDealtOutcome.getKey().first, woundsDealtOutcome.getKey().second + damageOutcome.getKey());
                        }
                        newWoundsDealtProbability.put(deadModelsAndWounds, newWoundsDealtProbability.getOrDefault(deadModelsAndWounds, 0.0) + woundsDealtOutcome.getValue() * damageOutcome.getValue());
                    }

                }
                woundsDealtProbability = newWoundsDealtProbability;
                Log.v(TAG, "WoundsDealtProbability: " +woundsDealtProbability);

                for (Map.Entry<Pair<Integer, Integer>, Double> woundsDealtOutcome : woundsDealtProbability.entrySet()) {
                    modelsKilledProbability.put(woundsDealtOutcome.getKey(), modelsKilledProbability.getOrDefault(woundsDealtOutcome, 0.0) + (unsavedWoundsOutcome.getValue() * woundsDealtOutcome.getValue()));
                }


            }


        }

        Log.v(TAG, "# wounds dealt: " +modelsKilledProbability);

        return modelsKilledProbability;
    }
    public void onWeaponClicked() {
        this.weapon = weaponArrayList.get(damageTestAttackerAdapter.selected);
        updateWeaponEditText();
    }
    public void updateWeaponEditText() {
        switchingText = true;
        weaponKeywords.setText(weapon.getKeywords().toString());
        attacks.setText( (weapon.getAttacks() == "") ? "" : weapon.getAttacks());
        bs.setText( (weapon.getBs() == -1) ? "" : "" +weapon.getBs());
        strength.setText( (weapon.getStrength() == -1) ? "" : "" +weapon.getStrength());
        ap.setText( (weapon.getAp() == -1) ? "" : "" +weapon.getAp());
        damage.setText( (weapon.getDamage() == "") ? "" : weapon.getDamage());
        switchingText = false;
        updateDamageCalc();
    }
    @Override
    public void onAttackerPlusClicked() {
        weaponArrayList.add(new Weapon(String.format("Weapon %d", weaponArrayList.size() + 1), -1, "", -1, -1, -1, ""));
        damageTestAttackerAdapter.notifyDataSetChanged();
    }
    @Override
    public void onDefenderPlusClicked() {
        defendingModelArrayList.add(new Model(String.format("Weapon %d", weaponArrayList.size() + 1), new Statline(-1, -1, -1, -1, -1, -1), null, null));
        damageTestAttackerAdapter.notifyDataSetChanged();
    }
    @Override
    public void onModelClicked(Model model) {
        this.defendingModel = model;
        updateDefenderEditText();
    }

    public void updateDefenderEditText() {
        switchingText = true;
        toughness.setText( (defendingModel.getStatline().getToughness() == -1) ? "" : defendingModel.getStatline().getToughness().toString());
        wounds.setText( (defendingModel.getStatline().getWounds() == -1) ? "" : "" +defendingModel.getStatline().getWounds().toString() );
        save.setText( (defendingModel.getStatline().getArmorSave() == -1) ? "" : "" +defendingModel.getStatline().getArmorSave().toString() );
        //  invulnerable.setText( (defendingModel.getStatline().getInvulnSave() == -1) ? "" : model.getStatline().getInvulnSave().toString());
        switchingText = false;
        updateDamageCalc();
    }

    public void createPopWindow() {
        LayoutInflater inflater = (LayoutInflater)  mainActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_keyword_selection, null);
        ConstraintLayout layout = view.findViewById(R.id.fragment_damage_test_full_page_layout);

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;

        boolean focusable = true;
        PopupWindow popupWindow =  new PopupWindow(popupView, width, height, focusable);

        layout.post(new Runnable() {
            @Override
            public void run() {
                popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);
            }
        });

        KeywordAdapter keywordAdapter = new KeywordAdapter(weapon.getKeywords());
        RecyclerView rv = popupView.findViewById(R.id.rv_popup_keyword_selection);
        rv.setAdapter(keywordAdapter);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                updateWeaponEditText();
                return true;
            }
        });
    }

}
