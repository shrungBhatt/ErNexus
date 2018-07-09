package com.example.andorid.ersnexus.userprofile.homeactivity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.userlogin.UserLoginActivity;
import com.example.andorid.ersnexus.util.SharedPreferencesData;

public class FacultyHomeScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_homescreen);


        if(SharedPreferencesData.getFacLoginStatus(this)&&
                UserLoginActivity.mActive){
            String userName = SharedPreferencesData.getStoredUsername(this);
            Toast.makeText(this,
                    "Welcome "+userName,Toast.LENGTH_SHORT).show();
            UserLoginActivity.mActivity.finish();
        }
    }
}
