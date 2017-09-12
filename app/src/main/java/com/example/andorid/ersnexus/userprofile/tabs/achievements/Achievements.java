package com.example.andorid.ersnexus.userprofile.tabs.achievements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.models.AchievementData;
import com.example.andorid.ersnexus.util.ActivitiesHashMap;
import com.example.andorid.ersnexus.util.SimpleDividerItemDecoration;

import java.net.Inet4Address;
import java.util.List;


public class Achievements extends Fragment {

    private FloatingActionButton mAddAchievementsFAB;
    private RecyclerView mAchievementRecyclerView;

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

        mAchievementRecyclerView = (RecyclerView)v.findViewById(R.id.achievement_recyclerView);
        mAchievementRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAchievementRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        return v;
    }

    private class AchievementHolder extends RecyclerView.ViewHolder{

        public AchievementHolder(LayoutInflater inflater,ViewGroup container){
            super(inflater.inflate(R.layout.list_item_achievement_recycler_view,container,
                    false));


        }


    }

    private class AchievementAdapter extends RecyclerView.Adapter<AchievementHolder>{

        private List<AchievementData> mAchievementDatas;

        public AchievementAdapter(List<AchievementData> achievementDatas){
            mAchievementDatas = achievementDatas;
        }


        @Override
        public AchievementHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new AchievementHolder(inflater,parent);
        }

        @Override
        public void onBindViewHolder(AchievementHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mAchievementDatas.size();
        }
    }


}
