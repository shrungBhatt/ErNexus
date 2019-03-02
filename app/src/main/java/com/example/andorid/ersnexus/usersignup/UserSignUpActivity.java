package com.example.andorid.ersnexus.usersignup;

import android.support.v4.app.Fragment;

import com.example.andorid.ersnexus.util.SingleFragmentActivity;


public class UserSignUpActivity extends SingleFragmentActivity {



    @Override
    protected Fragment createFragment(){
        return new UserSignUpFragmentNew();
    }
}
