package com.example.javahammer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.example.javahammer.R;
import com.example.javahammer.adapters.DatasheetAdapter;
import com.example.javahammer.archived.OnItemClickListener;
import com.example.javahammer.data.Model;
import com.example.javahammer.data.Roster;
import com.example.javahammer.data.Unit;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashSet;

public class ImportExistingUnit extends AppCompatActivity implements OnItemClickListener {

    private Roster roster;
    private ArrayList<Unit> savedUnits;
    private HashSet<Unit> units;
    private DatasheetAdapter adapter;
    SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_existing_item);

        roster = (Roster) getIntent().getSerializableExtra(String.valueOf(R.string.roster_parcelable));

        searchView = findViewById(R.id.searchView);
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

        // Lookup the recyclerview in activity layout
        RecyclerView rvUnits = (RecyclerView) findViewById(R.id.unitsRecycleView);

        units = new HashSet<Unit>();

        int searchType = getIntent().getIntExtra(String.valueOf(R.string.keywords_parcelable), 0);

        Log.v("Thomas searchType:", ""  +searchType);
            // Filters Units shown by specified Unit Keyword
        for (Unit unit : roster.faction.getUnits()) {

            EnumSet<Model.ModelKeywords> allKeywords = unit.getKeywords();

            // If unit is character
            if (allKeywords.contains(Model.ModelKeywords.CHARACTER)) {
                if (searchType == 0) {
                    units.add(unit);
                }
            } else if (allKeywords.contains(Model.ModelKeywords.BATTLELINE)) {
                if (searchType == 1) {
                    units.add(unit);
                }
            } else if (allKeywords.contains(Model.ModelKeywords.DEDICATED_TRANSPORT)) {
                if (searchType == 2) {
                    units.add(unit);
                }
            } else {
                if (searchType == 3) {
                    units.add(unit);
                }
            }
        }

        // Lookup the recyclerview in activity layout
        adapter = new DatasheetAdapter(roster, new ArrayList<>(units));
        adapter.setListener(this);

        // Initalize savedModels
        savedUnits = new ArrayList<Unit>();

        // Attach the adapter to the recyclerview to populate items
        rvUnits.setAdapter(adapter);
        // Set layout manager to position the items
        rvUnits.setLayoutManager(new LinearLayoutManager(this));

        Button finishBtn = findViewById(R.id.finishBtn);
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("Thomas", "finishBtn.onClick() start");
                Log.v("Thomas", "savedModels size:" + savedUnits.size());

                Intent returnIntent = new Intent();
              //  returnIntent.putExtra("savedModels", sample);

                if (savedUnits.isEmpty()) {
                    Log.v("Thomas", "savedModels empty");
                    setResult(AppCompatActivity.RESULT_CANCELED, returnIntent);

                } else {
                    Log.v("Thomas", "savedModels non-empty");
                  //  returnIntent.putExtra("savedModels", savedModels);
                  //   Model sample = new Model("Skitarii");
                      returnIntent.putExtra("savedModels", (Parcelable) savedUnits.get(0));
                    setResult(AppCompatActivity.RESULT_OK, returnIntent);
                }
                Log.v("Thomas", "finishBtn.onClick() end");
               finish();
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

        adapter.setFilteredList(roster, filteredList);
    }
    @Override
    public void onItemClick(Unit item) {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent);
        returnIntent.putExtra(String.valueOf(R.string.unit_parcelable), (Parcelable) item);
        finish();
    }
   @Override
    public void onBlowupDatasheet(Unit datasheet) {
        Intent intent = new Intent(this, DatasheetBlowup.class);
       intent.putExtra(String.valueOf(R.string.roster_parcelable), (Parcelable) roster);
        intent.putExtra(String.valueOf(R.string.unit_parcelable), (Parcelable) datasheet);
        startActivity(intent);
    }
}