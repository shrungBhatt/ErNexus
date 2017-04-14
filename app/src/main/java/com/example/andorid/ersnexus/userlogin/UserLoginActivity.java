package com.example.andorid.ersnexus.userlogin;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.database.userdata.UserBaseHelper;
import com.example.andorid.ersnexus.userprofile.homeactivity.UserProfileHomeActivity;
import com.example.andorid.ersnexus.usersignup.UserSignUpActivity;
import com.example.andorid.ersnexus.webservices.BackgroundDbConnector;
import com.example.andorid.ersnexus.util.SharedPreferencesData;


//This is the main activity of the app.
//It is the user login screen where users logs in or sign up's.

public class UserLoginActivity extends AppCompatActivity {

    private EditText mUserName;
    private EditText mUserPassword;
    private Button mLoginButton;
    private Button mSignUpButton;
    private UserBaseHelper mHelper;
    private String userName;
    private String pass;
    private String password;
    private String mErno;
    public static Activity mActivity;

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mActivity = this;

        Boolean status = SharedPreferencesData.getStoredLoginStatus(UserLoginActivity.this);
        if(status){
            Intent i = new Intent(UserLoginActivity.this, UserProfileHomeActivity.class);
            startActivity(i);
        }

        mHelper = new UserBaseHelper(this);


        //user UserName editText in activity_user_login
        mUserName = (EditText) findViewById(R.id.login_user_name);


        //PASSWORD editText
        mUserPassword = (EditText) findViewById(R.id.login_user_pass);


        //SignUp button
        mSignUpButton = (Button) findViewById(R.id.sign_up_button);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent i = new Intent(UserLoginActivity.this, UserSignUpActivity.class);
                startActivity(i);

            }
        });


        //Login Button
        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                if(isNetworkAvailableAndConnected()) {
                    String type = "login";
                    userName = mUserName.getText().toString();
                    pass = mUserPassword.getText().toString();
                    password = mHelper.fetchUserPass(userName);
                    mErno = mHelper.fetchErNo(userName);
                    //String fullName = mHelper.fetchFullName(userName);

                    BroadcastReceiver broadcast_reciever = new BroadcastReceiver() {

                        @Override
                        public void onReceive(Context arg0, Intent intent) {
                            String action = intent.getAction();
                            if (action.equals("finish_activity")) {
                                finish();
                                // DO WHATEVER YOU WANT.
                            }
                        }
                    };
                    registerReceiver(broadcast_reciever, new IntentFilter("finish_activity"));

                    BackgroundDbConnector backgroundDbConnector = new
                            BackgroundDbConnector(UserLoginActivity.this);

                    backgroundDbConnector.execute(type, userName, pass);
                    SharedPreferencesData.setStoredUsername(UserLoginActivity.this, userName);
                }else {
                    Toast.makeText(UserLoginActivity.this,
                            "No Internet Connection",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private boolean isNetworkAvailableAndConnected () {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;
        boolean isNetworkConnected = isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();

        return isNetworkConnected;
    }

}
