package com.example.andorid.ersnexus.userprofile;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.andorid.ersnexus.util.SingleFragmentActivity;


public class UserProfileActivity extends SingleFragmentActivity {

    private static final String EXTRA_USERNAME = "USER_NAME";
    private static final String EXTRA_FULLNAME = "FULL_NAME";
    private static final String EXTRA_ERNO = "erno";

    @Override
    protected Fragment createFragment(){
        String userName = (String)getIntent().getSerializableExtra(EXTRA_USERNAME);
        String fullName = (String)getIntent().getSerializableExtra(EXTRA_FULLNAME);
        String erNo = (String)getIntent().getSerializableExtra(EXTRA_ERNO);
        return UserProfileFragment.newInstance(userName,fullName,erNo);
    }

    public static Intent newIntent(Context packageContext,String userName,String fullName,String enrollmentNumber){
        Intent i = new Intent(packageContext,UserProfileActivity.class);
        i.putExtra(EXTRA_USERNAME,userName);
        i.putExtra(EXTRA_FULLNAME,fullName);
        i.putExtra(EXTRA_ERNO,enrollmentNumber);
        return i;
    }
}
