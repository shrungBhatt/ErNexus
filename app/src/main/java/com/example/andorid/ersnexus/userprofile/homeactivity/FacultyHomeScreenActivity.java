package com.example.andorid.ersnexus.userprofile.homeactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.faculty_dashboard.QrCodeGeneratorActivity;
import com.example.andorid.ersnexus.userlogin.UserLoginActivity;
import com.example.andorid.ersnexus.util.SharedPreferencesData;


public class FacultyHomeScreenActivity extends AppCompatActivity {

    private CardView mGenerateQRCodeCardView;
    private CardView mAchievementsRecordCardView;


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

        mGenerateQRCodeCardView = (CardView) findViewById(R.id.generate_qr_code_card_view);
        mAchievementsRecordCardView = (CardView) findViewById(R.id.achievement_record_card_view);


        mGenerateQRCodeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacultyHomeScreenActivity.this,
                        QrCodeGeneratorActivity.class));
            }
        });

    }
}
