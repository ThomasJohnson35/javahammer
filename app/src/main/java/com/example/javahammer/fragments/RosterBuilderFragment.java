package com.example.javahammer.fragments;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javahammer.R;
import com.example.javahammer.activities.DatasheetBlowup;
import com.example.javahammer.activities.ImportExistingUnit;
import com.example.javahammer.activities.MainActivity;
import com.example.javahammer.data.Faction;
import com.example.javahammer.data.Model;
import com.example.javahammer.data.ModelComposition;
import com.example.javahammer.data.Roster;
import com.example.javahammer.data.Unit;


import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Map;

public class RosterBuilderFragment extends Fragment implements PopupMenu.OnMenuItemClickListener {
    Roster roster;
    Unit unitExtra;
    Faction faction;
    ImageView factionIv;
    TextView points;
    ImageButton characterBtn, battlelineBtn, otherDatasheetsBtn, dedicatedTransportBtn;
    RecyclerView characterRv, battlelineRv, dedicatedTransportRv, otherDatasheetRv;
    RosterUnitAdapter characterAdapter, battlelineAdapter, dedicatedTransportAdapter, otherDatasheetAdapter;
    CardView rosterValidationCardView;
    Boolean rosterIsValid;
    StringBuffer problems;
    ConstraintLayout layout;
    View view;

    MainActivity mainActivity;

    Toolbar toolbar;
    public RosterBuilderFragment(Roster roster) {
        this.roster = roster;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_roster_builder, container, false);
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

        faction = roster.getFaction();
        factionIv = view.findViewById(R.id.faction_iv);
        factionIv.setImageResource(faction.getImageRes());

        toolbar = view.findViewById(R.id.roster_builder_toolbar);
        toolbar.setTitle(roster.getName());
        toolbar.inflateMenu(R.menu.toolbar_menu);

        mainActivity.setSupportActionBar(toolbar);
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                Toast.makeText(mainActivity, "GOING BACK!", Toast.LENGTH_SHORT).show();
                mainActivity.replaceFragment(new RosterLibraryFragment());
            }
        });

        points = view.findViewById(R.id.roster_points_tv);
        characterBtn = view.findViewById(R.id.characters_add_btn);
        battlelineBtn = view.findViewById(R.id.battline_add_btn);
        dedicatedTransportBtn = view.findViewById(R.id.dedicated_transport_add_btn);
        otherDatasheetsBtn = view.findViewById(R.id.other_datasheets_add_btn);
        rosterValidationCardView = view.findViewById(R.id.roster_validation_card_view);

        characterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.replaceFragment(new ImportExistingUnitFragment(roster, 0));
            }
        });
        battlelineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.replaceFragment(new ImportExistingUnitFragment(roster, 1));            }
        });
        dedicatedTransportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.replaceFragment(new ImportExistingUnitFragment(roster, 2));            }
        });
        otherDatasheetsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainActivity.replaceFragment(new ImportExistingUnitFragment(roster, 3));            }
        });

        // Layout params
        layout = view.findViewById(R.id.full_page_layout);

        subRosters();
    }
    public void onBlowupDatasheet(Unit datasheet) {
        mainActivity.replaceFragment(new DatasheetBlowupFragment(roster, datasheet, this));
    }
    public void subRosters() {
        ArrayList<Unit> characterUnitArrayList = new ArrayList<>();
        ArrayList<Unit> battlelineUnitArrayList = new ArrayList<>();
        ArrayList<Unit> dedicatedTransportUnitArrayList = new ArrayList<>();
        ArrayList<Unit> otherDatasheetUnitArrayList = new ArrayList<>();

        for (Unit unit : roster.getUnits()) {

            Log.v("subRosters", "Unit Print:\n" +unit.toString());

            EnumSet<Model.ModelKeywords> allKeywords = unit.getKeywords();
            // If unit is character
            if (allKeywords.contains(Model.ModelKeywords.CHARACTER)) {
                characterUnitArrayList.add(unit);
            } else if (allKeywords.contains(Model.ModelKeywords.BATTLELINE)) {
                battlelineUnitArrayList.add(unit);
            } else if (allKeywords.contains(Model.ModelKeywords.DEDICATED_TRANSPORT)) {
                dedicatedTransportUnitArrayList.add(unit);
            } else {
                otherDatasheetUnitArrayList.add(unit);
            }
        }

        characterAdapter = new RosterUnitAdapter(characterUnitArrayList);
        battlelineAdapter = new RosterUnitAdapter(battlelineUnitArrayList);
        dedicatedTransportAdapter = new RosterUnitAdapter(dedicatedTransportUnitArrayList);
        otherDatasheetAdapter = new RosterUnitAdapter(otherDatasheetUnitArrayList);

        characterRv = view.findViewById(R.id.characters_rv);
        characterRv.setAdapter(characterAdapter);
        characterRv.setLayoutManager(new LinearLayoutManager(getContext()));

        battlelineRv = view.findViewById(R.id.battleline_rv);
        battlelineRv.setAdapter(battlelineAdapter);
        battlelineRv.setLayoutManager(new LinearLayoutManager(getContext()));

        dedicatedTransportRv = view.findViewById(R.id.dedicated_transport_rv);
        dedicatedTransportRv.setAdapter(dedicatedTransportAdapter);
        dedicatedTransportRv.setLayoutManager(new LinearLayoutManager(getContext()));

        otherDatasheetRv = view.findViewById(R.id.other_datasheets_rv);
        otherDatasheetRv.setAdapter(otherDatasheetAdapter);
        otherDatasheetRv.setLayoutManager(new LinearLayoutManager(getContext()));
        //  characterAdapter = new RosterUnitAdapter(new ArrayList<Unit> (roster.getUnits().stream().filter(n -> n.getKeyword().contains(Model.ModelKeywords.CHARACTER)).collect(Collectors.toList())));


        toolbar.setSubtitle(String.format("%d/%d Points", roster.getPoints(), roster.getBattleSize().getPoints()));
        points.setText("" +roster.getPoints() +" PTS");
        rosterValidation();

        rosterValidationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (rosterIsValid) {

                } else {
//                    startActivity(new Intent(RosterBuilder.this, Pop.class));
                    createPopWindow();
                }

            }
        });
    }
    public void createPopWindow() {
        LayoutInflater inflater = (LayoutInflater)  getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.pop_window, null);

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

        TextView errors = popupView.findViewById(R.id.validation_error_string_tv);
        errors.setText(problems.toString());

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
    public void rosterValidation() {

        ImageView imageView = view.findViewById(R.id.roster_check_or_x_iv);
        problems = new StringBuffer();


        if (!roster.getUnits().stream().anyMatch(n -> n.isWarlord())) {
            problems.append(String.format("\u2022Need to choose Warlord\n"));
        }
        if (roster.getPoints() > roster.getBattleSize().getPoints()) {
            problems.append(String.format("\u2022Roster is over points limit. %d/%d PTS\n", roster.getPoints(), roster.getBattleSize().getPoints()));
        }

        imageView.setImageResource(problems.toString().equals("")? R.drawable.green_check_box : R.drawable.red_x_mark_box);

        rosterIsValid = problems.toString().equals("");
        //
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.edit_popup_option:
                Toast.makeText(getContext(), "EDIT", Toast.LENGTH_LONG).show();
                return true;
            case R.id.duplicate_popup_option:
                // TODO Check if safe to duplicate this Unit
                if (true) {
                    roster.addUnit(unitExtra);
                }
                subRosters();
                return true;
            case R.id.delete_popup_option:
                roster.removeUnit(unitExtra);
                subRosters();
                return true;
            default:
                Toast.makeText(getContext(), "WHAT!!!", Toast.LENGTH_LONG).show();
                return false;
        }
    }
    public class RosterUnitAdapter extends RecyclerView.Adapter<RosterUnitAdapter.ViewHolder> {
        ArrayList<Unit> unitArrayList;
        public RosterUnitAdapter(ArrayList<Unit> unitArrayList) {
            this.unitArrayList = unitArrayList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View contactView = inflater.inflate(R.layout.item_roster_unit, parent, false);
            return new ViewHolder(contactView);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Unit unit = unitArrayList.get(position);
            holder.name.setText("" +unit.getName());

            holder.imageView.setImageResource(unit.getImageRes());

            StringBuffer stringBuffer = new StringBuffer();
            if (unit.isWarlord()) {
                stringBuffer.append("Warlord\n");
            }
            for (ModelComposition modelComposition : unit.getUnitComposition()) {
                stringBuffer.append(String.format("\u2022 %s\n", modelComposition.getTemplateModel().getName()));
                for (Map.Entry<Model, Integer> loadoutComposition : modelComposition.getLoadoutCompositon().entrySet()) {
                    stringBuffer.append(String.format("\t\u2022 %d %s\n", loadoutComposition.getValue(), loadoutComposition.getKey().getWargear()));
                }
            }
            holder.unitCompositon.setText(stringBuffer.toString());

            holder.points.setText(String.format("%d Points",unit.getPoints()));

            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBlowupDatasheet(unit);
                }
            });

            /*
            // If unit is a character (i.e. eligible to be warlord)
            if (unit.getKeywords().contains(Model.ModelKeywords.CHARACTER)) {
                holder.warlord.setVisibility(View.VISIBLE);

                holder.warlord.setImageResource(unit.isWarlord() ? R.drawable.warlord_symbol_true : R.drawable.warlord_symbol_false);
            } else {
                holder.warlord.setVisibility(View.GONE);
            }
            holder.warlord.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (unit.isWarlord()) {
                        unit.setWarlord(false);
                    } else {
                        unit.setWarlord(true);
                    }

                    subRosters();
                }
            });
             */

            holder.ellipsis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    unitExtra = unit;
                    PopupMenu popup = new PopupMenu(getContext(), view);
                    popup.setOnMenuItemClickListener(RosterBuilderFragment.this);
                    popup.inflate(R.menu.roster_popup);
                    popup.show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return unitArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            ConstraintLayout layout;
            ImageView imageView;
            TextView name;
            TextView unitCompositon;
            TextView points;
            ImageButton ellipsis;

            public ViewHolder(View itemView) {
                super(itemView);
                layout = itemView.findViewById(R.id.roster_unit_layout);
                imageView = itemView.findViewById(R.id.roster_unit_iv);
                name = itemView.findViewById(R.id.roster_unit_name_tv);
                unitCompositon = itemView.findViewById(R.id.roster_unit_composition_tv);
                points = itemView.findViewById(R.id.roster_unit_points_tv);
                ellipsis = itemView.findViewById(R.id.roster_unit_ellipsis_btn);
            }
        }
    }
}