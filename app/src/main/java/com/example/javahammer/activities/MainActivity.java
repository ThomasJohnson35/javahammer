package com.example.javahammer.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.example.javahammer.R;
import com.example.javahammer.fragments.BrowseUnitsFragment;
import com.example.javahammer.fragments.DamageTestFragment;
import com.example.javahammer.fragments.DatasheetBlowupReferenceFragment;
import com.example.javahammer.fragments.EditRosterSettingsFragment;
import com.example.javahammer.fragments.ReferenceFragment;
import com.example.javahammer.fragments.RosterBuilderFragment;
import com.example.javahammer.fragments.RosterLibraryFragment;
import com.example.javahammer.data.Roster;
import com.example.javahammer.data.Unit;
import com.example.javahammer.fragments.DatasheetBlowupFragment;
import com.example.javahammer.fragments.ImportExistingUnitFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Context context;
    public Roster roster;
    public ArrayList<Roster> rosterList;
    public Fragment referenceFragment;
    public Fragment battleForgeFragment;
    public Fragment playingMatchFragment;
    public Fragment damageTestFragment;
    public RosterLibraryFragment rosterLibraryFragment;
    public RosterBuilderFragment rosterBuilderFragment;
    public ImportExistingUnitFragment importExistingUnitFragment;
    public DatasheetBlowupFragment datasheetBlowupFragment;
    FragmentManager fragmentManager;

    FragmentTransaction fragmentTransaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getBaseContext();
        rosterList = new ArrayList<>();
        fragmentManager = getSupportFragmentManager();
        readSavedRosterList();
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.rules_reference:
                   // Toast.makeText(MainActivity.this, "" + (bottomNavigationView.getSelectedItemId() == R.id.reference), Toast.LENGTH_SHORT).show();
                    // Represents a double-tap on bottom nav

                    replaceFragment(
                            (bottomNavigationView.getSelectedItemId() == R.id.rules_reference || referenceFragment == null) ?
                                    new ReferenceFragment() : referenceFragment);
                    break;
                case R.id.roster_editor:
                    replaceFragment(
                            (bottomNavigationView.getSelectedItemId() == R.id.roster_editor || battleForgeFragment == null) ?
                                    new RosterLibraryFragment() : battleForgeFragment);
                    break;

                case R.id.damage_calculator:
                    replaceFragment(
                            (bottomNavigationView.getSelectedItemId() == R.id.damage_calculator || damageTestFragment == null) ?
                                    new DamageTestFragment() : damageTestFragment);
                    break;
                default:
                    break;
            }
            return true;
        });

        bottomNavigationView.setSelectedItemId(R.id.roster_editor);
    }

    @Override
    protected void onPause() {
        writeRostersToFile(rosterList);
        super.onPause();
    }

    public ArrayList<Roster> readSavedRosterList() {
        FileInputStream fin;
        ObjectInputStream ois = null;
        try {
            fin = getApplicationContext().openFileInput("Rosters.rosz");
            ois = new ObjectInputStream(fin);
            rosterList = (ArrayList<Roster>) ois.readObject();
            ois.close();
        } catch (IOException e) {
            Log.v("RosterLibrary", "No File Found");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        return rosterList;
    }
    public void writeRostersToFile(ArrayList<Roster> rosterList) {
        FileOutputStream fos;
        ObjectOutputStream oos = null;

        try {
            fos = openFileOutput("Rosters.rosz", MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject( (ArrayList<Roster>) rosterList);
            oos.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    public Roster getRoster() {
        return roster;
    }

    public Unit getUnit() {
        return null;
    }
    public void replaceFragment(Fragment newFragment) {

        if (newFragment instanceof ReferenceFragment ||
            newFragment instanceof BrowseUnitsFragment ||
            newFragment instanceof DatasheetBlowupReferenceFragment) {
            referenceFragment = newFragment;
        } else if (newFragment instanceof RosterLibraryFragment ||
            newFragment instanceof RosterBuilderFragment ||
            newFragment instanceof ImportExistingUnitFragment ||
            newFragment instanceof DatasheetBlowupFragment ||
            newFragment instanceof EditRosterSettingsFragment) {
            battleForgeFragment = newFragment;
        } else if (newFragment instanceof DamageTestFragment) {
            damageTestFragment = newFragment;
        }

        /*
        if (newFragment instanceof ChooseRosterFragment) {
          //      newFragment instanceof  ||
           //     newFragment instanceof  ||
           //     newFragment instanceof )
            playingMatchFragment = newFragment;
        }
         */

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_activity_fragment_container, newFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {

        List<Fragment> Fraglist = fragmentManager.getFragments();

    }
}