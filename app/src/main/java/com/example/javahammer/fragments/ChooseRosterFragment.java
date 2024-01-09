package com.example.javahammer.fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.example.javahammer.DetermineMission;
import com.example.javahammer.R;
import com.example.javahammer.activities.MainActivity;
import com.example.javahammer.adapters.RosterLibraryAdapter;
import com.example.javahammer.data.Roster;
import com.example.javahammer.interfaces.RosterAdapterListener;

import java.util.ArrayList;

public class ChooseRosterFragment extends Fragment implements RosterAdapterListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public View view;

    public ArrayList<Roster> rosterList;
    public MainActivity mainActivity;
    public ChooseRosterFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_roster, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        this.view = view;
        mainActivity = (MainActivity) getActivity();

        Toolbar toolbar = view.findViewById(R.id.fragment_choose_roster_toolbar);
        toolbar.setTitle("Choose Roster");

        this.rosterList = mainActivity.rosterList;

        RecyclerView rv = view.findViewById(R.id.fragment_choose_roster_rv);
        RosterLibraryAdapter rosterLibraryAdapter = new RosterLibraryAdapter(rosterList);
        rosterLibraryAdapter.setListener(this);
        rv.setAdapter(rosterLibraryAdapter);
        rv.setLayoutManager(new LinearLayoutManager(mainActivity));

    }

    @Override
    public void onItemClick(Roster roster) {
        mainActivity.replaceFragment(new DetermineMission());
    }

    @Override
    public void onEllipsisClick(Roster roster, PopupMenu popupMenu) {
        // Intentionally does nothing
    }
}