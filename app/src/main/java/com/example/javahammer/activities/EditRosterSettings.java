package com.example.javahammer.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
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
import com.example.javahammer.data.BattleSize;
import com.example.javahammer.data.Detachment;
import com.example.javahammer.data.Faction;
import com.example.javahammer.data.Roster;

import java.util.ArrayList;

public class EditRosterSettings extends AppCompatActivity {


    ScrollView sv;
    Roster roster;
    EditText editText;
    Button editRosterSaveBtn;
    ArrayList<Detachment> detachmentArrayList;
    ArrayAdapter<Detachment> detachmentArrayAdapter;
    Spinner factionSpinner;
    Spinner battleSizeSpinner;
    Spinner detachmentSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_roster_settings);

        // Reads in data from intent
        roster = getIntent().getParcelableExtra(String.valueOf(R.string.roster_parcelable));

        // Finds UI elements
        editText = findViewById(R.id.edit_roster_name_tv);
        factionSpinner = findViewById(R.id.edit_roster_faction_spinner);
        battleSizeSpinner = findViewById(R.id.edit_roster_battle_size_spinner);
        detachmentSpinner = findViewById(R.id.edit_roster_detachment_spinner);
        editRosterSaveBtn = findViewById(R.id.edit_roster_save_btn);


        // Initializes List of Factions for Spinner
        ArrayList<Faction> factionList = Faction.getFactionList();

        // Sets Faction Adapter and Spinner
        ArrayAdapter<Faction> factionAdapter = new ArrayAdapter<Faction>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, factionList);
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
        battleSizeArrayAdapter = new ArrayAdapter<BattleSize>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, battleSizeArrayList);
        battleSizeSpinner.setAdapter(battleSizeArrayAdapter);
        battleSizeSpinner.setOnItemSelectedListener(new BattleSizeSpinner());

        // Sets Detachment Adapter and Spinner
        detachmentArrayList = new ArrayList<>();
        detachmentArrayList = Faction.getFactionList().get(0).getDetachments();
        detachmentArrayAdapter = new ArrayAdapter<Detachment>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, detachmentArrayList);
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
                roster.name = String.valueOf(editText.getText());
                roster.faction = (Faction) factionSpinner.getSelectedItem();
                roster.battleSize = (BattleSize) battleSizeSpinner.getSelectedItem();
                roster.detachment = (Detachment) detachmentSpinner.getSelectedItem();

                Intent returnIntent = new Intent();
                returnIntent.putExtra(String.valueOf(R.string.roster_parcelable), (Parcelable) roster);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

        sv = findViewById(R.id.edit_roster_setttings_sv);

        update();

    }

    public void update() {
        sv.setBackgroundResource(roster.getFaction().getPortraitImageRes());
    }

    public class FactionSpinner implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            String name = adapterView.getAdapter().getItem(i).toString();
            Toast.makeText(view.getContext(), name, Toast.LENGTH_LONG).show();

            roster.faction = (Faction) adapterView.getAdapter().getItem(i);

            // Set DetachmentAdapter
            detachmentArrayList = roster.faction.getDetachments();
            detachmentArrayAdapter = new ArrayAdapter<Detachment>(EditRosterSettings.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, detachmentArrayList);
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