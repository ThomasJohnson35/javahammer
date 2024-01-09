package com.example.javahammer.activities;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.Button;

import com.example.javahammer.DetermineMission;
import com.example.javahammer.R;
import com.example.javahammer.adapters.ViewPagerFragmentAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class GameSetup extends AppCompatActivity {

    //private final ArrayList<Fragment> fragmentsArrayList = new ArrayList<>();
    DetermineMission determineMission;
    FragmentManager fragmentManager;
    Button nextStep;

    private String[] titles = new String[]{"Muster Armies", "Determine Mission", ""};
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_setup_alt);

        getSupportActionBar().hide();

        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        ViewPagerFragmentAdapter viewPagerFragmentAdapter = new ViewPagerFragmentAdapter(this);
        tabLayout = findViewById(R.id.tab_layout);
        viewPager2.setAdapter(viewPagerFragmentAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> tab.setText(titles[position]))).attach();


        /*
        fragmentManager = getSupportFragmentManager();
        nextStep = findViewById(R.id.next_step_btn);
        fragmentManager.beginTransaction().add(R.id.fragmentContainerView5, new MusterArmies()).commit();
        determineMission = new DetermineMission();
        nextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentManager.beginTransaction()
                        .add(R.id.fragmentContainerView5, determineMission, null)
                        .commit();
            }
        });


         */

        /*
     //   FragmentManager fragmentManager = getSupportFragmentManager();

     //   determineMission = new DetermineMission();
     //   fragmentManager.beginTransaction().add(R.id.fragmentContainerView2, determineMission).commit();

    //    ArrayList<MissionCard> missionCardList = new ArrayList<MissionCard>();
     //   missionCardList.add(new MissionCard("Crucible of Battle"));

     //   PrimaryMissionAdapter primaryMissionAdapter = new PrimaryMissionAdapter(missionCardList);


         */
    }



}