package com.example.andorid.ersnexus.userprofile.tabs.attendance.userscanattendance;


import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.userprofile.homeactivity.UserProfileHomeActivity;
import com.example.andorid.ersnexus.util.SingleFragmentActivity;

public class UserScanAttendanceActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment(){
        return new UserScanAttendanceFragment();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed(){
        UserProfileHomeActivity.mActivity.finish();
    }
}

