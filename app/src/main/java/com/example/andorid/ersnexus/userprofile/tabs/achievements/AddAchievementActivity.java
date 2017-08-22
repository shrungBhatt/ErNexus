package com.example.andorid.ersnexus.userprofile.tabs.achievements;

import android.support.v4.app.Fragment;

import com.example.andorid.ersnexus.util.SingleFragmentActivity;



public class AddAchievementActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new AddAchievementFragment();
    }
}
