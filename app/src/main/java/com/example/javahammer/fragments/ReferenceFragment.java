package com.example.javahammer.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.javahammer.R;
import com.example.javahammer.activities.MainActivity;
import com.example.javahammer.adapters.ReferenceAdapter;
import com.example.javahammer.data.Faction;
import com.example.javahammer.interfaces.ReferenceAdapterListener;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class ReferenceFragment extends Fragment implements ReferenceAdapterListener {
    public MainActivity mainActivity;
    public View view;
    public ReferenceAdapter referenceAdapter;
    public RecyclerView factionRv;

    public View coreRules;
    public ReferenceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reference, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        mainActivity = (MainActivity) getActivity();

        // Core Rules
        coreRules = view.findViewById(R.id.fragment_faction_reference_one);
        TextView tv = coreRules.findViewById(R.id.item_reference_name_tv);
        tv.setText("Core Rules");
        coreRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
            }
        });

        // Key Documents
        View keyDocuments = view.findViewById(R.id.fragment_reference_two);
        tv = keyDocuments.findViewById(R.id.item_reference_name_tv);
        tv.setText("Key Documents");
        keyDocuments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO
            }
        });


        // Uses Default List of all Factions
        referenceAdapter = new ReferenceAdapter(new ArrayList<>(Faction.getFactionList().stream().map(n -> n.getName()).collect(Collectors.toList())));
        referenceAdapter.setListener(this);
        factionRv = view.findViewById(R.id.fragment_faction_reference_detachment_rv);
        factionRv.setAdapter(referenceAdapter);
        factionRv.setLayoutManager(new LinearLayoutManager(mainActivity));


    }


    @Override
    public void onItemClick(int position) {

        mainActivity.replaceFragment(new FactionReferenceFragment(Faction.getFactionList().get(position)));
    }
}