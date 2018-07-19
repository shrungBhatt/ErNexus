package com.example.andorid.ersnexus.userprofile.homeactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.faculty_dashboard.AchievementsRecordActivity;
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
        mAchievementsRecordCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacultyHomeScreenActivity.this,
                        AchievementsRecordActivity.class));
            }
        });


        mGenerateQRCodeCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FacultyHomeScreenActivity.this,
                        QrCodeGeneratorActivity.class));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_profile_home_activty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            if(isNetworkAvailableAndConnected()){
                SharedPreferencesData.setStoredLoginStatus(this,false);
                SharedPreferencesData.setFacLoginStatus(this,false);
                Intent i = new Intent(this, UserLoginActivity.class);
                startActivity(i);
                Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
                finish();
            }else {
                Toast.makeText(this,"No Internet Connection",Toast.LENGTH_SHORT).show();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isNetworkAvailableAndConnected () {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;

        return isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
    }
}
