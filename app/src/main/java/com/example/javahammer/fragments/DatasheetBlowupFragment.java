package com.example.javahammer.fragments;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javahammer.activities.MainActivity;
import com.example.javahammer.activities.WargearOptionChooseModels;
import com.example.javahammer.adapters.AbilityAdapter;
import com.example.javahammer.adapters.ModelCompositionAdapter;
import com.example.javahammer.adapters.PointsAdapter;
import com.example.javahammer.adapters.StratagemAdapter;
import com.example.javahammer.data.Detachment;
import com.example.javahammer.data.Enhancement;
import com.example.javahammer.data.Model;
import com.example.javahammer.data.ModelComposition;
import com.example.javahammer.data.Roster;
import com.example.javahammer.data.Stratagem;
import com.example.javahammer.data.Wargear;
import com.example.javahammer.data.Weapon;
import com.example.javahammer.interfaces.ModelAdapterListener;
import com.example.javahammer.R;
import com.example.javahammer.adapters.StatlineAdapter;
import com.example.javahammer.data.Unit;
import com.example.javahammer.data.WargearOption;
import com.example.javahammer.adapters.WargearOptionAdapter;
import com.example.javahammer.adapters.WeaponAdapter;
import com.example.javahammer.interfaces.StratagemListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DatasheetBlowupFragment extends Fragment implements ModelAdapterListener, StratagemListener, Toolbar.OnMenuItemClickListener {


    // UI Elements
    public Toolbar toolbar;
    public FloatingActionButton firingRangeBtn;
    public ImageView unitIv;
    public TextView unitCompositonTv, keywordsTv, unitName;
    public RecyclerView abilityRv, wargearAbilitiyRv, meleeWeaponRv, modelRv, pointsMapRv, rangedWeaponRv, statlineRv, stratagemRv, wargearOptionsRv;

    // Adapters
    public AbilityAdapter abilityAdapter;
    public WargearAbilityAdapter wargearAbilitiyAdapter;
    public ModelCompositionAdapter modelAdapter;
    public PointsAdapter pointsAdapter;
    public StatlineAdapter statlineAdapter;
    public StratagemAdapter stratagemAdapter;
    public WargearOptionAdapter wargearOptionAdapter;
    public WeaponAdapter meleeWeaponAdapter, rangedWeaponAdapter;

    public ArrayList<Enhancement> enhancements;

    // Roster Variables
    public Roster roster;
    public Unit unit;
    public MainActivity mainActivity;

    public View view;
    public Fragment prevFragment;
    ArrayList<Stratagem> elligableStratagems = new ArrayList<>();
    private ArrayList<ArrayList<Weapon>> allWeaponProfiles, uniqueWeaponProfile, rangedWeaponListProfile, meleeWeaponListProfile;

    public DatasheetBlowupFragment(Roster roster, Unit unit, Fragment prevFragment) {
        this.roster = roster;
        this.unit = unit;
        this.prevFragment = prevFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_datasheet_blowup, container, false);
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

        findViews();

        enhancements = new ArrayList<>();

        if (roster != null) {
            int unitOccuranceInArmy = roster.getUnitOccurance(unit.getName());

            toolbar.setTitle(unit.getName());
            toolbar.setSubtitle((unitOccuranceInArmy == 0) ? "" : String.format("\n%dX Unit in Army", unitOccuranceInArmy));

            enhancements = new ArrayList<>(roster.getDetachment().enhancements.stream().filter(n -> n.testElligibility(unit)).collect(Collectors.toList()));
        }

        initWeapons();
        initAdapters();

        ConstraintLayout testDamageLayout = view.findViewById(R.id.test_damage_layout);
        ConstraintLayout restoreDefaultsLayout = view.findViewById(R.id.restore_defaults_layout);
        ConstraintLayout addUnitsLayout = view.findViewById(R.id.add_units_layout);
        addUnitsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                roster.addUnit(unit);
                setToolbar();
            }
        });

        if (roster == null) {
            addUnitsLayout.setVisibility(View.GONE);
        }

        testDamageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.replaceFragment(new DamageTestFragment(new ArrayList<>(uniqueWeaponProfile.stream().flatMap(List::stream).collect(Collectors.toList()))));
            }
        });

        mainActivity.setSupportActionBar(toolbar);
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                Toast.makeText(mainActivity, "GOING BACK!", Toast.LENGTH_SHORT).show();

                if (prevFragment instanceof RosterBuilderFragment) {
                    mainActivity.replaceFragment(new RosterBuilderFragment(roster));
                } else {
                    mainActivity.replaceFragment(prevFragment);
                }

            }
        });

        // Finds unit name textview and sets name of unit
        unitIv.setImageResource(unit.getImageRes());

        // Initializes TextView fields
        StringBuffer stringBuffer = new StringBuffer();
        for (ModelComposition modelComposition : unit.getUnitComposition()) {
            if (modelComposition.getMin() == modelComposition.getMax()) {
                stringBuffer.append(String.format("\u2022 %d %s\n",modelComposition.getMin(), modelComposition.getTemplateModel().getName()));
            } else {
                stringBuffer.append(String.format("\u2022 %d-%d %s\n",modelComposition.getMin(), modelComposition.getMax(), modelComposition.getTemplateModel().getName()));
            }
        }

        unitCompositonTv.setText(stringBuffer.toString());

        // Formats keywords into a pretty string
        keywordsTv.setText(unit.getKeywordsString());

        setToolbar();
    }
    private void initAdapters() {
        // Initializes Statline Adapter and RecyclerView
        statlineAdapter = new StatlineAdapter(unit.getStatlines());
        statlineRv.setAdapter(statlineAdapter);
        statlineRv.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initializes Ranged Weapons Adapter and RecyclerView
        rangedWeaponAdapter = new WeaponAdapter(rangedWeaponListProfile);
        rangedWeaponRv.setAdapter(rangedWeaponAdapter);
        rangedWeaponRv.setLayoutManager(new LinearLayoutManager(mainActivity));

        // Initializes Melee Weapons Adapter and RecyclerView
        meleeWeaponAdapter = new WeaponAdapter(meleeWeaponListProfile);
        meleeWeaponRv.setAdapter(meleeWeaponAdapter);
        meleeWeaponRv.setLayoutManager(new LinearLayoutManager(mainActivity));

        if (rangedWeaponListProfile.isEmpty()) {
            view.findViewById(R.id.ranged_weapons_preview_layout).setVisibility(View.GONE);
            view.findViewById(R.id.ranged_weapons_expandable_layout).setVisibility(View.GONE);
        } else {
            setExpandableOnClick(
                    view.findViewById(R.id.ranged_weapons_preview_layout),
                    view.findViewById(R.id.ranged_weapons_expandable_layout),
                    view.findViewById(R.id.ranged_weapons_dropdown_iv));
        }

        if (meleeWeaponListProfile.isEmpty()) {
            view.findViewById(R.id.melee_weapons_preview_layout).setVisibility(View.GONE);
            view.findViewById(R.id.melee_weapons_expandable_layout).setVisibility(View.GONE);
        } else {
            setExpandableOnClick(
                    view.findViewById(R.id.melee_weapons_preview_layout),
                    view.findViewById(R.id.melee_weapons_expandable_layout),
                    view.findViewById(R.id.melee_weapons_dropdown_iv));
        }

        if (unit.getAbilities().isEmpty()) {
            view.findViewById(R.id.abilities_preview_layout).setVisibility(View.GONE);
            view.findViewById(R.id.abilities_expandable_layout).setVisibility(View.GONE);
        } else {
            setExpandableOnClick(
                    view.findViewById(R.id.abilities_preview_layout),
                    view.findViewById(R.id.abilities_expandable_layout),
                    view.findViewById(R.id.abilities_preview_dropdown_iv));
        }
        if (unit.getWargearAbilities().isEmpty()) {
            view.findViewById(R.id.wargear_abilities_preview_layout).setVisibility(View.GONE);
            view.findViewById(R.id.wargear_abilities_expandable_layout).setVisibility(View.GONE);
        } else {
            setExpandableOnClick(
                    view.findViewById(R.id.wargear_abilities_preview_layout),
                    view.findViewById(R.id.wargear_abilities_expandable_layout),
                    view.findViewById(R.id.wargear_abilities_preview_dropdown_iv));
        }

        if (unit.getWargearOptions().isEmpty()) {
            view.findViewById(R.id.wargear_options_preview_layout).setVisibility(View.GONE);
            view.findViewById(R.id.wargear_options_expandable_layout).setVisibility(View.GONE);
        } else {
            setExpandableOnClick(
                    view.findViewById(R.id.wargear_options_preview_layout),
                    view.findViewById(R.id.wargear_options_expandable_layout),
                    view.findViewById(R.id.wargear_options_preview_dropdown_iv));
        }

        setExpandableOnClick(
                view.findViewById(R.id.unit_composition_preview_layout),
                view.findViewById(R.id.unit_composition_expandable_layout),
                view.findViewById(R.id.unit_composition_preview_dropdown_iv));


        setExpandableOnClick(
                view.findViewById(R.id.keywords_preview_layout),
                view.findViewById(R.id.keywords_expandable_layout),
                view.findViewById(R.id.keywords_preview_dropdown_iv));


        if (enhancements.isEmpty()) {
            view.findViewById(R.id.enhancements_preview_layout).setVisibility(View.GONE);
            view.findViewById(R.id.enhancements_preview_layout).setVisibility(View.GONE);
        } else {
            setExpandableOnClick(
                    view.findViewById(R.id.enhancements_preview_layout),
                    view.findViewById(R.id.enhancements_expandable_layout),
                    view.findViewById(R.id.enhancements_preview_dropdown_iv));
        }

        if (unit.getWargearOptions().isEmpty()) {
            view.findViewById(R.id.wargear_options_preview_layout).setVisibility(View.GONE);
            view.findViewById(R.id.wargear_options_expandable_layout).setVisibility(View.GONE);
        } else {
            setExpandableOnClick(
                    view.findViewById(R.id.wargear_options_preview_layout),
                    view.findViewById(R.id.wargear_options_expandable_layout),
                    view.findViewById(R.id.wargear_options_preview_dropdown_iv));
        }

        // TODO make include all stratagems when Reference version

        setStratagems();

        if (elligableStratagems.isEmpty()) {
            view.findViewById(R.id.stratagem_preview_layout).setVisibility(View.GONE);
            view.findViewById(R.id.stratagem_expandable_layout).setVisibility(View.GONE);
        } else {
            stratagemAdapter = new StratagemAdapter(elligableStratagems);
            stratagemAdapter.setListener(this);
            stratagemRv = view.findViewById(R.id.stratagem_rv);
            stratagemRv.setAdapter(stratagemAdapter);
            stratagemRv.setLayoutManager(new LinearLayoutManager(mainActivity));

            setExpandableOnClick(
                    view.findViewById(R.id.stratagem_preview_layout),
                    view.findViewById(R.id.stratagem_expandable_layout),
                    view.findViewById(R.id.stratagem_preview_dropdown_iv));
        }

        // Initializes AbilityAdapter and RecyclerView
        abilityAdapter = new AbilityAdapter(unit.getAbilities());
        abilityRv.setAdapter(abilityAdapter);
        abilityRv.setLayoutManager(new LinearLayoutManager(mainActivity));

        wargearAbilitiyAdapter = new WargearAbilityAdapter(unit);
        wargearAbilitiyRv.setAdapter(wargearAbilitiyAdapter);
        wargearAbilitiyRv.setLayoutManager(new LinearLayoutManager(mainActivity));

        // Initializes WargearOptionsAdapter and RecyclerView
        wargearOptionAdapter = new WargearOptionAdapter(unit.getWargearOptions());
        wargearOptionsRv.setAdapter(wargearOptionAdapter);
        wargearOptionsRv.setLayoutManager(new LinearLayoutManager(mainActivity));
        wargearOptionAdapter.setListener(this);

        pointsAdapter = new PointsAdapter(unit);
        pointsMapRv.setAdapter(pointsAdapter);
        pointsMapRv.setLayoutManager(new LinearLayoutManager(mainActivity));

        // Initializes  Models Adapter and RecyclerView
        modelAdapter = new ModelCompositionAdapter(unit);
        modelAdapter.setListener(this);
        modelRv = view.findViewById(R.id.models_rv);
        modelRv.setAdapter(modelAdapter);
        modelRv.setLayoutManager(new LinearLayoutManager(mainActivity));


    }

    protected void setStratagems() {
        elligableStratagems = new ArrayList<>();

        elligableStratagems = roster.getDetachment().getStratagems();

    }
    private void initWeapons() {
        // Crude method for obtaining unique Weapons
        allWeaponProfiles = new ArrayList<ArrayList<Weapon>>();
        for (ModelComposition modelComposition : unit.getUnitComposition()) {
            allWeaponProfiles.addAll(Wargear.getAllWeaponProfiles(modelComposition.getTemplateModel().getWargear()));
        }
        for (WargearOption wargearOption : unit.getWargearOptions()) {
            for (ArrayList<Wargear> wargear : wargearOption.getExchangeableWargear()) {
                allWeaponProfiles.addAll(Wargear.getAllWeaponProfiles(wargear));
            }

        }
        uniqueWeaponProfile = new ArrayList<>();

        for (ArrayList<Weapon> weapon : allWeaponProfiles) {
            if (!uniqueWeaponProfile.contains(weapon)) {
                uniqueWeaponProfile.add(weapon);
            }
        }

        // Splits into melee and ranged weapons
        rangedWeaponListProfile = new ArrayList<>();
        meleeWeaponListProfile = new ArrayList<>();
        for (ArrayList<Weapon> weaponProfiles : uniqueWeaponProfile) {
            ArrayList<Weapon> currRangedWeaponProfiles  = new ArrayList<>();
            ArrayList<Weapon> currMeleeWeaponProfiles = new ArrayList<>();


            for (Weapon weapon : weaponProfiles) {
                if (weapon.getRange() == 0) {
                    currMeleeWeaponProfiles.add(weapon);
                } else {
                    currRangedWeaponProfiles.add(weapon);
                }
            }

            if (currMeleeWeaponProfiles.size() != 0) {
                meleeWeaponListProfile.add(currMeleeWeaponProfiles);
            }

            if (currRangedWeaponProfiles.size() != 0) {
                rangedWeaponListProfile.add(currRangedWeaponProfiles);
            }
        }
    }
    private void findViews() {
        toolbar = view.findViewById(R.id.datasheet_toolbar);
        unitIv = view.findViewById(R.id.datasheet_blowup_unit_iv);
        statlineRv = view.findViewById(R.id.statline_rv);
        abilityRv = view.findViewById(R.id.abilities_rv);
        wargearAbilitiyRv = view.findViewById(R.id.wargear_abilities_rv);
        wargearOptionsRv = view.findViewById(R.id.wargear_options_rv);
        rangedWeaponRv = view.findViewById(R.id.ranged_weapon_rv);
        meleeWeaponRv = view.findViewById(R.id.melee_weapon_rv);
        pointsMapRv = view.findViewById(R.id.points_map_rv);
        firingRangeBtn = view.findViewById(R.id.firingRangeButton);
        unitCompositonTv = view.findViewById(R.id.model_distribution_tv);
        keywordsTv = view.findViewById(R.id.unit_keywords_tv);
    }

    // Method for turning a preview view into a collapsable or expandable view
    public void setExpandableOnClick(View preview, View expandableLayout, ImageView dropdownBtn) {
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  LinearLayout expandableLayout = findViewById(R.id.ranged_weapons_expandable_layout);
                expandableLayout.setVisibility((expandableLayout.getVisibility() == View.VISIBLE) ? View.GONE : View.VISIBLE);
             //   ImageView dropdownBtn = findViewById(R.id.ranged_weapons_dropdown_iv);
                dropdownBtn.setRotation(dropdownBtn.getRotation() == 0 ? dropdownBtn.getRotation() + 180 : 0);
            }
        });
    }
    // Everytime a part of the unit is modified, this method is called in order to ensure that all
    // other information about the unit is updated accordingly
    public void unitDataChanged() {
        rangedWeaponAdapter.notifyDataSetChanged();
        meleeWeaponAdapter.notifyDataSetChanged();
        pointsAdapter.notifyDataSetChanged();
        modelAdapter.notifyDataSetChanged();
        setToolbar();
    }
    // StratagemListener Implementation Methods
    public Roster getRoster() {
        return roster;
    }

    @Override
    public Detachment getDetachment() {return roster.getDetachment();}
    public void onStratagemClick(Stratagem stratagem) {
        createPopWindow(stratagem);
    }
    public void createPopWindow(Stratagem stratagem) {
        LayoutInflater inflater = (LayoutInflater)  mainActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.stratagem_window, null);
        ConstraintLayout layout = view.findViewById(R.id.datasheet_blowup_full_page_layout);

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

        TextView nameTv = popupView.findViewById(R.id.stratagem_window_name_tv);
        nameTv.setText(stratagem.name);
        TextView detachmentAndTypeTv = popupView.findViewById(R.id.stratagem_window_detachment_and_type_tv);
        detachmentAndTypeTv.setText(String.format("%s - %s", roster.getDetachment().name, stratagem.stratagemType));
        TextView flavorTv = popupView.findViewById(R.id.stratagem_window_flavour_tv);
        flavorTv.setText(stratagem.flavourText);
        TextView when = popupView.findViewById(R.id.stratagem_window_when_tv);
        when.setText("WHEN: " +stratagem.when);
        TextView targetTv = popupView.findViewById(R.id.stratagem_window_target_tv);
        targetTv.setText("TARGET: " +stratagem.target);
        TextView effectTv = popupView.findViewById(R.id.stratagem_window_effect_tv);
        effectTv.setText("EFFECT: " +stratagem.effect);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
    public void wargearOptionOnCLick(WargearOption wargearOption) {
        Intent intent = new Intent(mainActivity, WargearOptionChooseModels.class);
        intent.putExtra("Unit", (Parcelable) unit);
        intent.putExtra("WargearOption", (Parcelable) wargearOption);
        startActivity(intent);
    }
    public void removeModel(ModelComposition modelComposition, Model model) {
        modelComposition.removeModel(model, 1);
        unitDataChanged();
    }

    public Unit getUnit() {
        return unit;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()) {
            default:
                break;
        }

        return true;
    }

    public void setToolbar() {
        toolbar.setTitle(unit.getName());
        toolbar.setSubtitle(String.format("%d", roster.getUnitOccurance(unit.getName())));
    }

}