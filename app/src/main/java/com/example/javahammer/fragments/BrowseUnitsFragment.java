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
import com.example.javahammer.data.Faction;
import com.example.javahammer.data.Model;
import com.example.javahammer.data.Roster;
import com.example.javahammer.data.Unit;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.stream.Collectors;

public class BrowseUnitsFragment extends Fragment implements OnItemClickListener {

    private Faction faction;
    private HashSet<Unit> units;
    private DatasheetAdapter adapter;
    SearchView searchView;
    public View view;
    public MainActivity mainActivity;

    public BrowseUnitsFragment(Faction faction) {
        this.faction = faction;
        this.units = (HashSet<Unit>) faction.getUnits().stream().collect(Collectors.toSet());
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
        toolbar.setTitle(String.format("%s Datasheets", faction.getName()));
        mainActivity.setSupportActionBar(toolbar);
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                mainActivity.replaceFragment(new FactionReferenceFragment(faction));
            }
        });
        // Lookup the recyclerview in activity layout
        RecyclerView rvUnits = (RecyclerView) view.findViewById(R.id.unitsRecycleView);

        // Lookup the recyclerview in activity layout
        adapter = new DatasheetAdapter(null, new ArrayList<>(units));
        adapter.setListener(this);

        // Attach the adapter to the recyclerview to populate items
        rvUnits.setAdapter(adapter);
        // Set layout manager to position the items
        rvUnits.setLayoutManager(new LinearLayoutManager(mainActivity));
    }
    private void filterList(String text) {
        ArrayList<Unit> filteredList = new ArrayList<>();

        for (Unit d : units) {
            if (d.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(d);
            }
        }

    }
    // Protocol when Datasheet is clicked
    @Override
    public void onItemClick(Unit item) {
        // Doesn't exist
    }
    @Override
    public void onBlowupDatasheet(Unit datasheet) {
        mainActivity.replaceFragment(new DatasheetBlowupReferenceFragment(datasheet, this));
    }


}