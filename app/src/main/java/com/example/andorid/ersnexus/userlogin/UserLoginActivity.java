package com.example.andorid.ersnexus.userlogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.userprofile.homeactivity.FacultyHomeScreenActivity;
import com.example.andorid.ersnexus.userprofile.homeactivity.UserProfileHomeActivity;
import com.example.andorid.ersnexus.usersignup.FacultySignUpActivity;
import com.example.andorid.ersnexus.usersignup.UserSignUpActivity;
import com.example.andorid.ersnexus.util.SharedPreferencesData;
import com.example.andorid.ersnexus.webservices.URLManager;

import java.util.HashMap;
import java.util.Map;


//This is the main activity of the app.
//It is the user login screen where users logs in or sign up's.

public class UserLoginActivity extends AppCompatActivity {

    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private EditText mUserName;
    private EditText mUserPassword;
    private Button mLoginButton;
    private Button mSignUpButton;
    private Button mFacultySignup;
    //private UserBaseHelper mHelper;
    private String userName;
    private String pass;
    private Context mContext;
    //private String password;
    //private String mErno;
    public static Activity mActivity;
    public static Boolean mActive;

    private CheckBox mCheckBox;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);

        mContext = this;

        mActivity = this;



        Boolean status = SharedPreferencesData.getStoredLoginStatus(UserLoginActivity.this);
        if (status) {
            Intent i = new Intent(UserLoginActivity.this, UserProfileHomeActivity.class);
            startActivity(i);
        }else if(SharedPreferencesData.getFacLoginStatus(mContext)){
            startActivity(new Intent(mContext,FacultyHomeScreenActivity.class));
        }

        //mHelper = new UserBaseHelper(this);


        mCheckBox = (CheckBox) findViewById(R.id.sign_in_as_faculty);

        //user UserName editText in activity_user_login
        mUserName = (EditText) findViewById(R.id.login_user_name);


        //PASSWORD editText
        mUserPassword = (EditText) findViewById(R.id.login_user_pass);


        //SignUp button
        mSignUpButton = (Button) findViewById(R.id.sign_up_as_a_student_button);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserLoginActivity.this, UserSignUpActivity.class);
                startActivity(i);

            }
        });

        mFacultySignup = (Button) findViewById(R.id.sign_up_as_a_faculty_button);
        mFacultySignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserLoginActivity.this,
                        FacultySignUpActivity.class));
            }
        });




        //Login Button
        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailableAndConnected()) {

                    userName = mUserName.getText().toString();
                    pass = mUserPassword.getText().toString();


                    if(mCheckBox.isChecked()){
                        loginFaculty(userName,pass);
                    }else{
                        loginStudent(userName,pass);
                    }


                } else {
                    Toast.makeText(UserLoginActivity.this,
                            "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void loginStudent(final String userName, final String pass){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.
                LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null &&
                        !response.equals("Wrong Username or Password")) {
                    SharedPreferencesData.setStoredLoginStatus(mContext, true);
                    SharedPreferencesData.setStoredErno(mContext, response);
                    mContext.startActivity(new Intent(mContext,
                            UserProfileHomeActivity.class));
                } else {
                    Toast.makeText(mContext, "Wrong Username or Password!",
                            Toast.LENGTH_SHORT)
                            .show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserLoginActivity.this, error.toString(),
                        Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(KEY_USERNAME, userName);
                params.put(KEY_PASSWORD, pass);
                return params;
            }
        };

        SharedPreferencesData.setStoredUsername(UserLoginActivity.this, userName);

        RequestQueue requestQueue = Volley.newRequestQueue(UserLoginActivity.this);
        requestQueue.add(stringRequest);
    }

    private void loginFaculty(final String userName, final String pass){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URLManager.LOGIN_FACULTY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response != null &&
                                !response.equals("Wrong Username or Password")) {
//                            SharedPreferencesData.setStoredLoginStatus(mContext, true);
                            SharedPreferencesData.setFacLoginStatus(mContext,true);
                            SharedPreferencesData.setFacCode(mContext, response);
                            mContext.startActivity(new Intent(mContext,
                                    FacultyHomeScreenActivity.class));
                        } else {
                            Toast.makeText(mContext, "Wrong Username or Password!",
                                    Toast.LENGTH_SHORT)
                                    .show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(UserLoginActivity.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put(KEY_USERNAME, userName);
                params.put(KEY_PASSWORD, pass);
                return params;
            }


        };

        SharedPreferencesData.setStoredUsername(UserLoginActivity.this, userName);


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    @Override
    public void onStart() {
        super.onStart();
        mActive = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        mActive = false;
    }

    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;

        return isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
    }

}
