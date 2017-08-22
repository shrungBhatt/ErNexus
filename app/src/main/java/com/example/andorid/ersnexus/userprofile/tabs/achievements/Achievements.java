package com.example.andorid.ersnexus.userprofile.tabs.achievements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andorid.ersnexus.R;

import java.net.Inet4Address;


public class Achievements extends Fragment {

    private FloatingActionButton mAddAchievementsFAB;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_achievements,container,false);

        mAddAchievementsFAB = (FloatingActionButton)v.findViewById(R.id.add_achievements_fab);
        mAddAchievementsFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(),AddAchievementActivity.class);
                startActivity(i);

            }
        });

        return v;
    }


}
