package com.example.javahammer.fragments;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.javahammer.data.Faction;
import com.example.javahammer.interfaces.StratagemListener;
import com.example.javahammer.adapters.EnhancementAdapter;
import com.example.javahammer.adapters.ReferenceAdapter;
import com.example.javahammer.adapters.StratagemAdapter;
import com.example.javahammer.data.Detachment;
import com.example.javahammer.data.Enhancement;
import com.example.javahammer.data.Roster;
import com.example.javahammer.data.Stratagem;
import com.example.javahammer.interfaces.EnhancementAdapterListener;

import java.util.Optional;

public class DetachmentReferenceFragment extends Fragment implements EnhancementAdapterListener, StratagemListener {
    public MainActivity mainActivity;
    public View view;
    public View datasheets;
    public Detachment detachment;
    public DetachmentReferenceFragment(Detachment detachment) {
        // Required empty public constructor
        this.detachment = detachment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detachment_reference, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        this.view = view;
        mainActivity = (MainActivity) getActivity();

        Toolbar toolbar = view.findViewById(R.id.fragment_reference_toolbar);
        toolbar.setTitle(detachment.getName());

        mainActivity.setSupportActionBar(toolbar);
        mainActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Optional<Faction> rootFaction = detachment.getFaction();

                if (rootFaction.isPresent()) {
                    mainActivity.replaceFragment( new FactionReferenceFragment(detachment.getFaction().get()));
                } else {
                    Toast.makeText(mainActivity, "Error: Could not Find " +detachment.toString() +"'s faction", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Enhancements
        EnhancementAdapter enhancementAdapter = new EnhancementAdapter(detachment.getEnhancements());
        enhancementAdapter.setListener(this);
        RecyclerView enhancementRv = view.findViewById(R.id.fragment_detachment_reference_enhancement_rv);
        enhancementRv.setAdapter(enhancementAdapter);
        enhancementRv.setLayoutManager(new LinearLayoutManager(mainActivity));

        // Stratagems
        StratagemAdapter stratagemAdapter = new StratagemAdapter(detachment.getStratagems());
        stratagemAdapter.setListener(this);
        RecyclerView stratagemRv = view.findViewById(R.id.fragment_detachment_reference_stratagem_rv);
        stratagemRv.setAdapter(stratagemAdapter);
        stratagemRv.setLayoutManager(new LinearLayoutManager(mainActivity));

        // Detachment Rules
    }

    @Override
    public Roster getRoster() {
        return null;
    }

    @Override
    public Detachment getDetachment() {
        return detachment;
    }

    @Override
    public void onEnhancementClicked(Enhancement enhancement) {
        createPopWindow(enhancement);
    }
    @Override
    public void onStratagemClick(Stratagem stratagem) {
        createPopWindow(stratagem);
    }
    public void createPopWindow(Stratagem stratagem) {
        LayoutInflater inflater = (LayoutInflater)  mainActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.stratagem_window, null);
        ConstraintLayout layout = view.findViewById(R.id.fragment_detachment_full_page_layout);

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

        TextView nameTv = popupView.findViewById(R.id.stratagem_window_name_tv);
        nameTv.setText(stratagem.name);
        TextView detachmentAndTypeTv = popupView.findViewById(R.id.stratagem_window_detachment_and_type_tv);
        detachmentAndTypeTv.setText(String.format("%s - %s", detachment.getName(), stratagem.stratagemType));
        TextView flavorTv = popupView.findViewById(R.id.stratagem_window_flavour_tv);
        flavorTv.setText(stratagem.flavourText);
        TextView when = popupView.findViewById(R.id.stratagem_window_when_tv);
        when.setText("WHEN: " +stratagem.when);
        TextView targetTv = popupView.findViewById(R.id.stratagem_window_target_tv);
        targetTv.setText("TARGET: " +stratagem.target);
        TextView effectTv = popupView.findViewById(R.id.stratagem_window_effect_tv);
        effectTv.setText("EFFECT: " +stratagem.effect);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public void createPopWindow(Enhancement enhancement) {
        LayoutInflater inflater = (LayoutInflater)  mainActivity.getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.enhancement_window, null);
        ConstraintLayout layout = view.findViewById(R.id.fragment_detachment_full_page_layout);

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

        TextView nameTv = popupView.findViewById(R.id.enhancement_window_name_tv);
        nameTv.setText(enhancement.getName());
        TextView pointsTv = popupView.findViewById(R.id.enhancement_window_pts_tv);
        pointsTv.setText(String.format("%d pts", enhancement.getPoints()));
        TextView flavorTv = popupView.findViewById(R.id.enhancement_window_flavour_tv);
        flavorTv.setText(enhancement.getFlavourText());
        TextView desc = popupView.findViewById(R.id.enhancement_window_target_tv);
        desc.setText(enhancement.getDescription());

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}
