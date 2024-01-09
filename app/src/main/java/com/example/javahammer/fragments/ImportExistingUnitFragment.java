package com.example.javahammer.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.activities.DatasheetBlowup;
import com.example.javahammer.activities.MainActivity;
import com.example.javahammer.adapters.DatasheetAdapter;
import com.example.javahammer.archived.OnItemClickListener;
import com.example.javahammer.data.Model;
import com.example.javahammer.data.Roster;
import com.example.javahammer.data.Unit;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.stream.Collectors;

public class ImportExistingUnitFragment extends BrowseUnitsFragment implements OnItemClickListener {

    private Roster roster;
    private HashSet<Unit> units;
    private DatasheetAdapter adapter;
    SearchView searchView;
    public View view;
    public MainActivity mainActivity;
    public int searchType;

    public ImportExistingUnitFragment(Roster roster, int searchType) {
        super(roster.getFaction());
        this.roster = roster;
        this.searchType = searchType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_import_existing_unit, container, false);
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

        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newtext) {
                filterList(newtext);
                return true;
            }
        });

        Toolbar toolbar = view.findViewById(R.id.import_existing_unit_toolbar);
        mainActivity.setSupportActionBar(toolbar);
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                Toast.makeText(mainActivity, "GOING BACK!", Toast.LENGTH_SHORT).show();
                mainActivity.replaceFragment(new RosterBuilderFragment(roster));
            }
        });
        // Lookup the recyclerview in activity layout
        RecyclerView rvUnits = (RecyclerView) view.findViewById(R.id.unitsRecycleView);

        units = new HashSet<Unit>();

        Log.v("Thomas searchType:", ""  +searchType);
            // Filters Units shown by specified Unit Keyword


        switch (searchType) {
            case 0:
                toolbar.setTitle("CHARACTERS");
                units = (HashSet<Unit>) roster.faction.getUnits().stream().filter(n -> n.getKeywords().contains(Model.ModelKeywords.CHARACTER)).collect(Collectors.toSet());
                break;
            case 1:
                toolbar.setTitle("BATTLELINE");
                units = (HashSet<Unit>) roster.faction.getUnits().stream().filter(n -> n.getKeywords().contains(Model.ModelKeywords.BATTLELINE)).collect(Collectors.toSet());
                break;
            case 2:
                toolbar.setTitle("DEDICATED TRANSPORTS");
                units = (HashSet<Unit>) roster.faction.getUnits().stream().filter(n -> n.getKeywords().contains(Model.ModelKeywords.DEDICATED_TRANSPORT)).collect(Collectors.toSet());
                break;
            case 3:
                toolbar.setTitle("OTHER DATASHEETS");
                units = (HashSet<Unit>) roster.faction.getUnits().stream().filter(n -> !n.getKeywords().contains(Model.ModelKeywords.CHARACTER) && !n.getKeywords().contains(Model.ModelKeywords.BATTLELINE) && !n.getKeywords().contains(Model.ModelKeywords.DEDICATED_TRANSPORT)).collect(Collectors.toSet());
                break;
        }

        // Lookup the recyclerview in activity layout
        adapter = new DatasheetAdapter(roster, new ArrayList<>(units));
        adapter.setListener(this);

        // Attach the adapter to the recyclerview to populate items
        rvUnits.setAdapter(adapter);
        // Set layout manager to position the items
        rvUnits.setLayoutManager(new LinearLayoutManager(mainActivity));

        Button finishBtn = view.findViewById(R.id.finishBtn);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO
                // End Protocol
            }
        });
    }
    private void filterList(String text) {
        ArrayList<Unit> filteredList = new ArrayList<>();

        for (Unit d : units) {
            if (d.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(d);
            }
        }

        if (filteredList.isEmpty()) {
            Toast.makeText(mainActivity, "No data", Toast.LENGTH_SHORT).show();
            adapter.setFilteredList(roster, filteredList);
        } else {
            adapter.setFilteredList(roster, filteredList);
        }

    }
    // Protocol when Datasheet is clicked
    @Override
    public void onItemClick(Unit item) {
       // Return protocol
        roster.addUnit(item);
        //mainActivity.replaceFragment(new RosterBuilderFragment(roster));
    }
   @Override
    public void onBlowupDatasheet(Unit datasheet) {
        mainActivity.replaceFragment(new DatasheetBlowupFragment(roster, datasheet, this));
    }
    // Adds defaults
    public ArrayList<Unit> getRosterUnits() {
        return roster.faction.getUnits();
    }


}