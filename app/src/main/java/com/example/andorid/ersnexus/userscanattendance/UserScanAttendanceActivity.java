package com.example.andorid.ersnexus.userscanattendance;


import android.support.v4.app.Fragment;


import com.example.andorid.ersnexus.util.SingleFragmentActivity;

public class UserScanAttendanceActivity extends SingleFragmentActivity {


    @Override
    protected Fragment createFragment(){
        return new UserScanAttendanceFragment();
    }
}

