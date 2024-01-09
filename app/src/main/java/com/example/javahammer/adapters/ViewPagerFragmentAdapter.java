package com.example.javahammer.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.javahammer.DetermineMission;
import com.example.javahammer.MusterArmies;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewPagerFragmentAdapter extends FragmentStateAdapter {

    private String[] titles = new String[]{"Muster Armies", "Determine Mission"};

    public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
      //  return fragmentsArrayList.get(position);\

        switch (position) {
            case 0 :
                return new MusterArmies();
            case 1 :
                return new DetermineMission();
        }
        return new MusterArmies();
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }
}
