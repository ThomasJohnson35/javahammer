package com.example.javahammer.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.activities.MainActivity;
import com.example.javahammer.adapters.WeaponAdapter;
import com.example.javahammer.data.Weapon;
import com.example.javahammer.interfaces.WeaponAdapterListener;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ImportWeaponFragment extends Fragment implements WeaponAdapterListener {
    private ArrayList<ArrayList<Weapon>> weaponArrayList;
    SearchView searchView;
    public View view;
    public MainActivity mainActivity;

    public WeaponAdapter adapter;
    public DamageTestFragment prevFragment;

    public LinearLayout weaponLabelsLayout;
    public ImportWeaponFragment(DamageTestFragment prevFragment) {
        this.prevFragment = prevFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_import_existing_item, container, false);
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

        view.findViewById(R.id.include_ranged_weapon_labels).setVisibility(View.VISIBLE);

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

        Toolbar toolbar = view.findViewById(R.id.import_existing_item_toolbar);
        mainActivity.setSupportActionBar(toolbar);
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // What to do on back clicked
                Toast.makeText(mainActivity, "GOING BACK!", Toast.LENGTH_SHORT).show();

                mainActivity.replaceFragment(prevFragment);
            }
        });
        // Lookup the recyclerview in activity layout
        RecyclerView rvUnits = (RecyclerView) view.findViewById(R.id.unitsRecycleView);

        weaponArrayList = new ArrayList<ArrayList<Weapon>>();

        Weapon.armory.values().forEach(wargear -> weaponArrayList.add(new ArrayList<>(wargear.getWeaponProfiles())));

        // Lookup the recyclerview in activity layout
        adapter = new WeaponAdapter(weaponArrayList);
        adapter.setListener(this);

        // Attach the adapter to the recyclerview to populate items
        rvUnits.setAdapter(adapter);
        // Set layout manager to position the items
        rvUnits.setLayoutManager(new LinearLayoutManager(mainActivity));
    }
    private void filterList(String text) {
        adapter.setFilteredList(new ArrayList<>(weaponArrayList.stream().filter(weapons -> weapons.stream().anyMatch(weapon -> weapon.getName().toLowerCase().contains(text.toLowerCase()))).collect(Collectors.toList())));
    }

    @Override
    public void onWeaponClick(Weapon weapon) {
        prevFragment.weaponArrayList.add(weapon);
        mainActivity.replaceFragment(prevFragment);
    }
}