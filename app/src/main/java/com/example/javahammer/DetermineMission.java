package com.example.javahammer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.example.javahammer.activities.MainActivity;
import com.example.javahammer.data.MissionCard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class DetermineMission extends Fragment {

    ArrayList<MissionCard> missionPrimaryList;
    ArrayList<MissionCard> missionDeploymentList;
    ArrayList<MissionCard> missionRuleList;
    MissionCard primaryCard;
    MissionCard deploymentCard;
    MissionCard ruleCard;
    ImageView missionPrimaryIv;
    ImageView missionDeploymentIv;
    ImageView missionRuleIv;
    ImageView expandedIv;
    ImageButton rollMissionBtn;
    Button chooseMissionBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_determine_mission, container, false);
        Log.v("Thomas", "DetermineMission");

            /*
            missionPrimaryAdapter = new PrimaryMissionAdapter(null);
            missionPrimaryRv = view.findViewById(R.id.primary_mission_single_rv);
            missionPrimaryRv.setLayoutManager(new LinearLayoutManager(this.getContext()));
            missionPrimaryRv.setAdapter(missionPrimaryAdapter);
                        /*
                    missionPrimaryAdapter = new PrimaryMissionAdapter(new ArrayList<MissionCard>(Arrays.asList(missionCard)));
                    missionPrimaryRv.setAdapter(missionPrimaryAdapter);

             */
        missionPrimaryIv = view.findViewById(R.id.mission_card_iv);
        missionDeploymentIv = view.findViewById(R.id.mission_deployment_iv);
        missionRuleIv = view.findViewById(R.id.mission_rule_iv);
        expandedIv = view.findViewById(R.id.expanded_image);
        rollMissionBtn = view.findViewById(R.id.rollForMissionBtn);


        missionPrimaryIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (primaryCard != null) {
                    expandedIv.setImageResource(primaryCard.imageRes);
                    expandedIv.bringToFront();
                    expandedIv.setVisibility(View.VISIBLE);
                }
                //  zoomImageFromThumb(view, primaryCard.imageRes);
            }
        });
        missionDeploymentIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deploymentCard != null) {
                    expandedIv.setImageResource(deploymentCard.imageRes);
                    expandedIv.bringToFront();
                    expandedIv.setVisibility(View.VISIBLE);

                }
            }
        });

        missionRuleIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ruleCard != null) {
                    expandedIv.setImageResource(ruleCard.imageRes);
                    expandedIv.bringToFront();
                    expandedIv.setVisibility(View.VISIBLE);
                }
            }
        });

                    /*
                    ImageBlowup imageBlowup = new ImageBlowup(deploymentCard.imageRes);
                    getParentFragmentManager().beginTransaction()
                            .add(R.id.fragmentContainerView5, imageBlowup)
                            .commit();

                     */

        expandedIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                expandedIv.setVisibility(View.GONE);
            }
        });
        rollMissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random r = new Random();

                primaryCard = MissionCard.missionPrimaryList.get( r.nextInt(MissionCard.missionPrimaryList.size()));
                missionPrimaryIv.setImageResource(primaryCard.imageRes);
                Log.v(this.getClass().toString(), "" + primaryCard.name);

                deploymentCard = MissionCard.missionDeploymentList.get(r.nextInt(MissionCard.missionDeploymentList.size()));
                missionDeploymentIv.setImageResource(deploymentCard.imageRes);

                ruleCard = MissionCard.missionRuleList.get(r.nextInt(MissionCard.missionRuleList.size()));
                missionRuleIv.setImageResource(ruleCard.imageRes);
            }
        });
        chooseMissionBtn = view.findViewById(R.id.chooseMissionBtn);
        chooseMissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

        return view;
    }
}