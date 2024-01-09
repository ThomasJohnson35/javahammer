package com.example.javahammer.fragments;

import androidx.fragment.app.Fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.javahammer.R;
import com.example.javahammer.activities.MainActivity;
import com.example.javahammer.data.BattleSize;
import com.example.javahammer.data.Detachment;
import com.example.javahammer.data.Faction;
import com.example.javahammer.data.Roster;

import java.util.ArrayList;

public class EditRosterSettingsFragment extends Fragment {

    ImageView backgroundImage;
    Roster roster;
    EditText editText;
    TextView rosterNameTv;
    TextView battleSizeNameTv;
    TextView battleSizePointsTv;
    TextView detachmentNameTv;
    Button editRosterSaveBtn;
    ArrayList<Detachment> detachmentArrayList;
    ArrayAdapter<Detachment> detachmentArrayAdapter;
    Spinner factionSpinner;
    Spinner battleSizeSpinner;
    Spinner detachmentSpinner;
    MainActivity mainActivity;
    ScrollView sv;
    ImageView iv;

    public EditRosterSettingsFragment(Roster roster) {
        this.roster = roster;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_edit_roster_settings, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mainActivity = (MainActivity) getActivity();

        // Reads in data from intent


        // Finds UI elements
        iv = view.findViewById(R.id.edit_roster_settings_faction_iv);
        editText = view.findViewById(R.id.edit_roster_name_tv);
        factionSpinner = view.findViewById(R.id.edit_roster_faction_spinner);
        battleSizeSpinner = view.findViewById(R.id.edit_roster_battle_size_spinner);
        detachmentSpinner = view.findViewById(R.id.edit_roster_detachment_spinner);
        editRosterSaveBtn = view.findViewById(R.id.edit_roster_save_btn);

        // Initializes List of Factions for Spinner
        ArrayList<Faction> factionList = Faction.getFactionList();

        // Sets Faction Adapter and Spinner
        ArrayAdapter<Faction> factionAdapter = new ArrayAdapter<Faction>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, factionList);
        factionSpinner.setAdapter(factionAdapter);
        factionSpinner.setOnItemSelectedListener(new FactionSpinner());

        // Sets BattleSize Adapter and Spinner
        ArrayList<BattleSize> battleSizeArrayList = new ArrayList<BattleSize>()
        {{
            add(BattleSize.INCURSION);
            add(BattleSize.STRIKEFORCE);
            add(BattleSize.ONSLAUGHT);
        }};
        ArrayAdapter<BattleSize> battleSizeArrayAdapter;
        battleSizeArrayAdapter = new ArrayAdapter<BattleSize>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, battleSizeArrayList);
        battleSizeSpinner.setAdapter(battleSizeArrayAdapter);
        battleSizeSpinner.setOnItemSelectedListener(new BattleSizeSpinner());

        // Sets Detachment Adapter and Spinner
        detachmentArrayList = new ArrayList<>();
        detachmentArrayList = Faction.getFactionList().get(0).getDetachments();
        detachmentArrayAdapter = new ArrayAdapter<Detachment>(mainActivity, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, detachmentArrayList);
        detachmentSpinner.setAdapter(detachmentArrayAdapter);
        detachmentSpinner.setOnItemSelectedListener(new DetachmentSpinner());

        // Sets Roster Name if Present
        if (roster.getName() != null) {
            editText.setText(roster.getName());
        }
        // Sets Roster Army if Present
        if (roster.getFaction() != null) {
            factionSpinner.setSelection(factionAdapter.getPosition(roster.faction));
        } else {
            factionSpinner.setSelection(0);
            roster.faction = (Faction) factionSpinner.getSelectedItem();
        }
        // Sets Battle Size if Present
        if (roster.getBattleSize() != null) {
            battleSizeSpinner.setSelection(battleSizeArrayAdapter.getPosition(roster.getBattleSize()));
        }
        // Sets Detachment if present
        if (roster.getDetachment() != null) {
            detachmentSpinner.setSelection(detachmentArrayAdapter.getPosition(roster.getDetachment()));
        }

        editRosterSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Checks validity of Roster
                if (String.valueOf(editText.getText()).equals("")) {
                    Toast.makeText(mainActivity, "Please Enter a Roster Name", Toast.LENGTH_SHORT).show();

                    // TODO Flash red on editTExt
                } else {
                    // Sets Roster Settings
                    roster.name = String.valueOf(editText.getText());
                    roster.faction = (Faction) factionSpinner.getSelectedItem();
                    roster.battleSize = (BattleSize) battleSizeSpinner.getSelectedItem();
                    roster.detachment = (Detachment) detachmentSpinner.getSelectedItem();
                    mainActivity.roster = roster;
                    mainActivity.replaceFragment(new RosterBuilderFragment(roster));
                }
            }
        });

        sv = view.findViewById(R.id.edit_roster_setttings_sv);
        update();

    }

    public void update() {
        iv.setImageResource(roster.getFaction().getImageRes());
    }

    public class FactionSpinner implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            String name = adapterView.getAdapter().getItem(i).toString();
            Toast.makeText(view.getContext(), name, Toast.LENGTH_LONG).show();

            roster.faction = (Faction) adapterView.getAdapter().getItem(i);

            // Set DetachmentAdapter
            detachmentArrayList = roster.faction.getDetachments();
            detachmentArrayAdapter = new ArrayAdapter<Detachment>(mainActivity, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, detachmentArrayList);
            detachmentArrayAdapter.notifyDataSetChanged();
            detachmentSpinner.setAdapter(detachmentArrayAdapter);

            update();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    public class DetachmentSpinner implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            String name = adapterView.getAdapter().getItem(i).toString();
          //  Toast.makeText(view.getContext(), name, Toast.LENGTH_LONG).show();

            roster.detachment = (Detachment) adapterView.getAdapter().getItem(i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }

    public class BattleSizeSpinner implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            String name = adapterView.getAdapter().getItem(i).toString();
     //       Toast.makeText(view.getContext(), name, Toast.LENGTH_LONG).show();

            roster.battleSize = (BattleSize) adapterView.getAdapter().getItem(i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}