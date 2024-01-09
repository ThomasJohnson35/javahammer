package com.example.javahammer.fragments;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.media.Image;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.javahammer.R;
import com.example.javahammer.activities.MainActivity;
import com.example.javahammer.adapters.ArmyRuleAdapter;
import com.example.javahammer.adapters.ReferenceAdapter;
import com.example.javahammer.data.Ability;
import com.example.javahammer.data.Enhancement;
import com.example.javahammer.data.Faction;
import com.example.javahammer.interfaces.ArmyRuleAdapterListener;
import com.example.javahammer.interfaces.ReferenceAdapterListener;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class FactionReferenceFragment extends Fragment implements ReferenceAdapterListener, ArmyRuleAdapterListener {

    public MainActivity mainActivity;
    public View view;
    public ReferenceAdapter referenceAdapter;
    public RecyclerView armyRulesRv, detachmentRv;
    public View datasheets;
    public Faction faction;
    public FactionReferenceFragment(Faction faction) {
        // Required empty public constructor
        this.faction = faction;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faction_reference, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        this.view = view;

        mainActivity = (MainActivity) getActivity();

        Toolbar toolbar = view.findViewById(R.id.fragment_reference_toolbar);
        toolbar.setTitle(faction.getName());
        mainActivity.setSupportActionBar(toolbar);
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.replaceFragment( new ReferenceFragment());
            }
        });

        // Faction UltraWide Image
        ImageView imageView = view.findViewById(R.id.fragment_reference_faction_ultrawide_iv);
        imageView.setImageResource(faction.getImageRes());

        // Army Rules
        ArmyRuleAdapter armyRuleAdapter = new ArmyRuleAdapter(faction.getArmyRules());
        armyRuleAdapter.setListener(this);
        armyRulesRv = view.findViewById(R.id.fragment_faction_reference_army_rules_rv);
        armyRulesRv.setAdapter(armyRuleAdapter);
        armyRulesRv.setLayoutManager(new LinearLayoutManager(getContext()));

        // Datasheets
        datasheets = view.findViewById(R.id.fragment_faction_reference_datasheets);
        TextView tv = datasheets.findViewById(R.id.item_reference_name_tv);
        tv.setText(String.format("%s Datasheets", faction.getName()));
        datasheets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO
                mainActivity.replaceFragment(new BrowseUnitsFragment(faction));
            }
        });

        // Detachments
        referenceAdapter = new ReferenceAdapter(new ArrayList<>(faction.getDetachments().stream().map(n -> n.getName()).collect(Collectors.toList())));
        referenceAdapter.setListener(this);
        detachmentRv = view.findViewById(R.id.fragment_faction_reference_detachment_rv);
        detachmentRv.setAdapter(referenceAdapter);
        detachmentRv.setLayoutManager(new LinearLayoutManager(mainActivity));
    }

    @Override
    public void onItemClick(int position) {
        mainActivity.replaceFragment(new DetachmentReferenceFragment(faction.getDetachments().get(position)));
    }

    @Override
    public void onArmyRuleClicked(Ability ability) {
       // Toast.makeText(mainActivity, String.format("Army Rule %s",ability.getName()), Toast.LENGTH_SHORT).show();
        createPopWindow(ability);
    }

    public void createPopWindow(Ability ability) {
        LayoutInflater inflater = (LayoutInflater)  mainActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_army_rule, null);
        ConstraintLayout layout = view.findViewById(R.id.full_page_layout);

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

        TextView nameTv = popupView.findViewById(R.id.popup_name);
        nameTv.setText(ability.getName());

        TextView flavorTv = popupView.findViewById(R.id.popup_flavour_text_tv);
        flavorTv.setText(ability.getFlavourText()
        );

        TextView descTv = popupView.findViewById(R.id.desc_tv);
        descTv.setText(ability.getDesc());

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
