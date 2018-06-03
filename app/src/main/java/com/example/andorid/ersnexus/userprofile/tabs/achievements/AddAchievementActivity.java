package com.example.andorid.ersnexus.userprofile.tabs.achievements;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.andorid.ersnexus.util.SingleFragmentActivity;



public class AddAchievementActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new AddAchievementFragment();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
    }
}
