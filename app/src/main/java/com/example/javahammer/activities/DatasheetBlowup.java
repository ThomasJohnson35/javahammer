package com.example.javahammer.activities;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.javahammer.adapters.ModelCompositionAdapter;
import com.example.javahammer.adapters.PointsAdapter;
import com.example.javahammer.adapters.StratagemAdapter;
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

public abstract class DatasheetBlowup extends AppCompatActivity implements ModelAdapterListener, StratagemListener {
    public StatlineAdapter statlineAdapter;
    public ImageView unitIv;
    public RecyclerView statlineRv;
    public WeaponAdapter rangedWeaponAdapter;
    public RecyclerView rangedWeaponRv;
    public WeaponAdapter meleeWeaponAdapter;
    public RecyclerView meleeWeaponRv;
    public WargearOptionAdapter wargearOptionAdapter;
    public RecyclerView wargearOptionsRv;
    public RecyclerView pointsMapRv;
    public PointsAdapter pointsAdapter;
    public TextView unitCompositonTv;
    public ModelCompositionAdapter modelAdapter;
    public RecyclerView modelRv;
    public StratagemAdapter stratagemAdapter;
    public RecyclerView stratagemRv;
    public FloatingActionButton firingRangeBtn;
    public FloatingActionButton saveAndReturnBtn;
    public TextView keywordsTv;
    public Roster roster;
    public Unit unit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v("Thomas", this.toString());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datasheet_blowup);
        roster = getIntent().getParcelableExtra(String.valueOf(R.string.roster_parcelable));
        unit = getIntent().getParcelableExtra(String.valueOf(R.string.unit_parcelable));

        // Finds unit name textview and sets name of unit
        TextView unitName = findViewById(R.id.datasheet_toolbar);
        unitName.setText(unit.getName());

        ImageView unitIv = findViewById(R.id.datasheet_blowup_unit_iv);
        unitIv.setImageResource(unit.getImageRes());
        // Initializes Statline Adapter and RecyclerView
        statlineAdapter = new StatlineAdapter(unit.getStatlines());
        statlineRv = findViewById(R.id.statline_rv);
        statlineRv.setAdapter(statlineAdapter);
        statlineRv.setLayoutManager(new LinearLayoutManager(this));

        // Crude method for obtaining unique Weapons
        ArrayList<ArrayList<Weapon>> allWeaponProfiles = new ArrayList<ArrayList<Weapon>>();

        for (ModelComposition modelComposition : unit.getUnitComposition()) {
            allWeaponProfiles.addAll(Wargear.getAllWeaponProfiles(modelComposition.getTemplateModel().getWargear()));

        }
        for (WargearOption wargearOption : unit.getWargearOptions()) {
            for (ArrayList<Wargear> wargear : wargearOption.getExchangeableWargear()) {
                allWeaponProfiles.addAll(Wargear.getAllWeaponProfiles(wargear));
            }

        }
        ArrayList<ArrayList<Weapon>> uniqueWeaponProfile = new ArrayList<>();

        for (ArrayList<Weapon> weapon : allWeaponProfiles) {
            if (!uniqueWeaponProfile.contains(weapon)) {
                uniqueWeaponProfile.add(weapon);
            }
        }

        // Sort Maybe?

        // Splits into melee and ranged weapons
        ArrayList<ArrayList<Weapon>> rangedWeaponListProfile = new ArrayList<>();
        ArrayList<ArrayList<Weapon>> meleeWeaponListProfile = new ArrayList<>();
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

        // Initializes Ranged Weapons Adapter and RecyclerView
        rangedWeaponAdapter = new WeaponAdapter(rangedWeaponListProfile);
        rangedWeaponRv = findViewById(R.id.ranged_weapon_rv);
        rangedWeaponRv.setAdapter(rangedWeaponAdapter);
        rangedWeaponRv.setLayoutManager(new LinearLayoutManager(this));

        // Initializes Melee Weapons Adapter and RecyclerView
        meleeWeaponAdapter = new WeaponAdapter(meleeWeaponListProfile);
        meleeWeaponRv = findViewById(R.id.melee_weapon_rv);
        meleeWeaponRv.setAdapter(meleeWeaponAdapter);
        meleeWeaponRv.setLayoutManager(new LinearLayoutManager(this));

        ConstraintLayout rangedWeaponsPreviewLayout = findViewById(R.id.ranged_weapons_preview_layout);


        if (rangedWeaponListProfile.isEmpty()) {
            findViewById(R.id.ranged_weapons_preview_layout).setVisibility(View.GONE);
            findViewById(R.id.ranged_weapons_expandable_layout).setVisibility(View.GONE);
        } else {
            setExpandableOnClick(
                    findViewById(R.id.ranged_weapons_preview_layout),
                    findViewById(R.id.ranged_weapons_expandable_layout),
                    findViewById(R.id.ranged_weapons_dropdown_iv));
        }

        if (meleeWeaponListProfile.isEmpty()) {
            findViewById(R.id.melee_weapons_preview_layout).setVisibility(View.GONE);
            findViewById(R.id.melee_weapons_expandable_layout).setVisibility(View.GONE);
        } else {
            setExpandableOnClick(
                    findViewById(R.id.melee_weapons_preview_layout),
                    findViewById(R.id.melee_weapons_expandable_layout),
                    findViewById(R.id.melee_weapons_dropdown_iv));
        }

        if (unit.getAbilities().isEmpty()) {
            findViewById(R.id.abilities_preview_layout).setVisibility(View.GONE);
            findViewById(R.id.abilities_expandable_layout).setVisibility(View.GONE);
        } else {
            setExpandableOnClick(
                    findViewById(R.id.abilities_preview_layout),
                    findViewById(R.id.abilities_expandable_layout),
                    findViewById(R.id.abilities_preview_dropdown_iv));
        }

        if (unit.getWargearOptions().isEmpty()) {
            findViewById(R.id.wargear_options_preview_layout).setVisibility(View.GONE);
            findViewById(R.id.wargear_options_expandable_layout).setVisibility(View.GONE);
        } else {
            setExpandableOnClick(
                    findViewById(R.id.wargear_options_preview_layout),
                    findViewById(R.id.wargear_options_expandable_layout),
                    findViewById(R.id.wargear_options_preview_dropdown_iv));
        }

        setExpandableOnClick(
                findViewById(R.id.unit_composition_preview_layout),
                findViewById(R.id.unit_composition_expandable_layout),
                findViewById(R.id.unit_composition_preview_dropdown_iv));

        setExpandableOnClick(
                findViewById(R.id.keywords_preview_layout),
                findViewById(R.id.keywords_expandable_layout),
                findViewById(R.id.keywords_preview_dropdown_iv));



        // Initializes WargearOptionsAdapter and RecyclerView
        wargearOptionAdapter = new WargearOptionAdapter(unit.getWargearOptions());
        wargearOptionsRv = findViewById(R.id.wargear_options_rv);
        wargearOptionsRv.setAdapter(wargearOptionAdapter);
        wargearOptionsRv.setLayoutManager(new LinearLayoutManager(this));
        //  wargearOptionAdapter.setListener(this);

        pointsMapRv = findViewById(R.id.points_map_rv);

        pointsAdapter = new PointsAdapter(unit);
        pointsMapRv.setAdapter(pointsAdapter);
        pointsMapRv.setLayoutManager(new LinearLayoutManager(this));

        // Initializes TextView fields
        StringBuffer stringBuffer = new StringBuffer();
        for (ModelComposition modelComposition : unit.getUnitComposition()) {
            if (modelComposition.getMin() == modelComposition.getMax()) {
                stringBuffer.append(String.format("\u2022 %d %s\n",modelComposition.getMin(), modelComposition.getTemplateModel().getName()));
            } else {
                stringBuffer.append(String.format("\u2022 %d-%d %s\n",modelComposition.getMin(), modelComposition.getMax(), modelComposition.getTemplateModel().getName()));
            }
        }

        unitCompositonTv = findViewById(R.id.model_distribution_tv);
        unitCompositonTv.setText(stringBuffer.toString());

        // Initializes  Models Adapter and RecyclerView
        modelAdapter = new ModelCompositionAdapter(unit);
        modelAdapter.setListener(this);

        modelRv = findViewById(R.id.models_rv);
        modelRv.setAdapter(modelAdapter);
        modelRv.setLayoutManager(new LinearLayoutManager(this));

        // Formats keywords into a pretty string
        keywordsTv = findViewById(R.id.unit_keywords_tv);
        keywordsTv.setText(unit.getKeywordsString());


        ArrayList<Stratagem> elligableStratagems = roster.detachment.stratagems;

     //   stratagemAdapter = new StratagemAdapter(elligableStratagems, this);
        stratagemRv = findViewById(R.id.stratagem_rv);
        stratagemRv.setAdapter(stratagemAdapter);
        stratagemRv.setLayoutManager(new LinearLayoutManager(this));

        setExpandableOnClick(
                findViewById(R.id.stratagem_preview_layout),
                findViewById(R.id.stratagem_expandable_layout),
                findViewById(R.id.stratagem_preview_dropdown_iv));

        firingRangeBtn = findViewById(R.id.firingRangeButton);
        firingRangeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchActivity(unit);
            }
        });
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
    }

    // StratagemListener Implementation Methods
    @Override
    public Roster getRoster() {
        return roster;
    }

    @Override
    public void onStratagemClick(Stratagem stratagem) {
        createPopWindow(stratagem);
    }

    public void createPopWindow(Stratagem stratagem) {
        LayoutInflater inflater = (LayoutInflater)  getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.stratagem_window, null);
        ConstraintLayout layout = findViewById(R.id.datasheet_blowup_full_page_layout);

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

        TextView nameTv = popupView.findViewById(R.id.enhancement_window_name_tv);
        nameTv.setText(stratagem.name);
        TextView detachmentAndTypeTv = popupView.findViewById(R.id.stratagem_window_detachment_and_type_tv);
        detachmentAndTypeTv.setText(String.format("%s - %s", roster.getDetachment().name, stratagem.stratagemType));
        TextView flavorTv = popupView.findViewById(R.id.enhancement_window_flavour_tv);
        flavorTv.setText(stratagem.flavourText);
        TextView when = popupView.findViewById(R.id.stratagem_window_when_tv);
        when.setText("WHEN: " +stratagem.when);
        TextView targetTv = popupView.findViewById(R.id.enhancement_window_target_tv);
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
        Intent intent = new Intent(this, WargearOptionChooseModels.class);
        intent.putExtra("Unit", (Parcelable) unit);
        intent.putExtra("WargearOption", (Parcelable) wargearOption);
        startActivity(intent);
    }

    public void launchActivity(Unit unit) {
        Intent intent = new Intent(this, DamageCalculator.class);
        intent.putExtra("Unit", (Parcelable) unit);
        startActivity(intent);
    }

    public void onModelClick(Model model) {
      //  modelWargearOptionsResultLauncher.launch(new Pair<>(unit, model));
    }

    public void launchActivityModel(Model model) {
        Intent intent = new Intent(this, DamageCalculator.class);
        intent.putExtra("Model", (Parcelable) model);
        startActivity(intent);
    }

    public class EditModelWargearOptions extends ActivityResultContract<Pair<Unit, Model>, Unit> {
        @NonNull
        @Override
        public Intent createIntent(@NonNull Context context, Pair<Unit, Model> unitModelPair) {
            Intent intent = new Intent(context, WargearOptionsBlowup.class);
            Log.v("UNIT PRINT", "\nDatasheetBlowup Sending: \n\n" +unitModelPair.first + "\n\n");
            intent.putExtra("Unit", (Parcelable) unitModelPair.first);
            intent.putExtra("Model", (Parcelable) unitModelPair.second);
            return intent;
        }

        @Override
        public Unit parseResult(int resultCode, @Nullable Intent result) {
            return result.getParcelableExtra("Unit");
        }
    }
    ActivityResultLauncher<Pair<Unit, Model>> modelWargearOptionsResultLauncher =
            registerForActivityResult(
                    new EditModelWargearOptions(),
                    new ActivityResultCallback<Unit>() {
                        @Override
                        public void onActivityResult(Unit result) {
                            Log.v("Thomas", "onActivityResult");
                            unit = result;
                            Log.v("UNIT PRINT", "\nDatasheetBlowup RECEIVING: \n\n" +unit + "\n\n");
                            Log.v("Thomas", unit.getUnitModelComposition().toString());
                            modelAdapter = new ModelCompositionAdapter(unit);
                            modelRv.setAdapter(modelAdapter);
                            modelAdapter.setListener(DatasheetBlowup.this);
                         //   Toast.makeText(DatasheetBlowup.this, unit.unitComposition.toString(), Toast.LENGTH_LONG).show();
                        }
                    });

}