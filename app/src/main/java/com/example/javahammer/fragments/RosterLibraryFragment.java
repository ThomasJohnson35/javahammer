package com.example.javahammer.fragments;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.javahammer.R;
import com.example.javahammer.activities.MainActivity;
import com.example.javahammer.adapters.RosterLibraryAdapter;
import com.example.javahammer.data.Roster;
import com.example.javahammer.interfaces.RosterAdapterListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RosterLibraryFragment extends Fragment implements RosterAdapterListener, PopupMenu.OnMenuItemClickListener {

    ConstraintLayout layout;
    Roster originalRoster;
    Roster rosterExtra;  // Variable used to easily pass an instance of a roster of interest between methods
    ArrayList<Roster> rosterList;
    RecyclerView recyclerView;
    RosterLibraryAdapter rosterLibraryAdapter;
    FloatingActionButton newRosterFab;
    View view;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_roster_library, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        mainActivity = (MainActivity) getActivity();
        this.view = view;
        // Load in rosters
        rosterList = mainActivity.rosterList;

        recyclerView = getView().findViewById(R.id.roster_rv);
        newRosterFab = view.findViewById(R.id.create_new_roster_fab);

        rosterLibraryAdapter = new RosterLibraryAdapter(rosterList);
        rosterLibraryAdapter.setListener(this);
        recyclerView.setAdapter(rosterLibraryAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        Toolbar toolbar = view.findViewById(R.id.roster_library_toolbar);
        toolbar.setTitle("Battle Forge");
        newRosterFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Roster roster = new Roster(null, null, null);
                rosterList.add(roster);
                mainActivity.roster = roster;
                mainActivity.replaceFragment(new EditRosterSettingsFragment(roster));
                //  modifyRosterLauncher.launch(roster);
            }
        });
        layout = view.findViewById(R.id.library_layout);
    }

    public void update() {

        // Cleans up RosterList
        for (Roster roster : rosterList) {
            if (roster == null) {
                rosterList.remove(roster);
            }
        }

        rosterLibraryAdapter.notifyDataSetChanged();

    }

    public void createPopWindow() {
        LayoutInflater inflater = (LayoutInflater)  mainActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.library_no_rosters_alert, null);

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

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    @Override
    public void onItemClick(Roster roster) {
        mainActivity.replaceFragment(new RosterBuilderFragment(roster));
    }

    @Override
    public void onEllipsisClick(Roster roster, PopupMenu popup) {

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.edit_popup_option:
                        Toast.makeText(view.getContext(), "EDIT", Toast.LENGTH_LONG).show();
                        mainActivity.replaceFragment(new EditRosterSettingsFragment(roster));
                        return true;
                    case R.id.duplicate_popup_option:
                        Toast.makeText(view.getContext(), "DELETE", Toast.LENGTH_LONG).show();
                         rosterList.add((Roster) roster.clone());
                         update();
                        return true;
                    case R.id.delete_popup_option:
                        Toast.makeText(view.getContext(), "DELETE", Toast.LENGTH_LONG).show();
                        rosterList.remove(roster);
                        update();
                        return true;
                    default:
                        Toast.makeText(view.getContext(), "WHAT!!!", Toast.LENGTH_LONG).show();
                        return false;
                }
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.edit_popup_option:
                Toast.makeText(getContext(), "EDIT", Toast.LENGTH_LONG).show();
                mainActivity.replaceFragment(new EditRosterSettingsFragment(rosterExtra));
                return true;
            case R.id.duplicate_popup_option:
                Toast.makeText(getContext(), "DELETE", Toast.LENGTH_LONG).show();
                rosterList.add(rosterExtra);
                update();
                return true;
            case R.id.delete_popup_option:
                Toast.makeText(getContext(), "DELETE", Toast.LENGTH_LONG).show();
                rosterList.remove(rosterExtra);
                update();
                return true;
            default:
                Toast.makeText(getContext(), "WHAT!!!", Toast.LENGTH_LONG).show();
                return false;
        }
    }
}