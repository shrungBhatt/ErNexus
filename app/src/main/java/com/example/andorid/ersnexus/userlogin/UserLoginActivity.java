package com.example.andorid.ersnexus.userlogin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.userprofile.homeactivity.UserProfileHomeActivity;
import com.example.andorid.ersnexus.usersignup.UserSignUpActivity;
import com.example.andorid.ersnexus.util.SharedPreferencesData;
import com.example.andorid.ersnexus.webservices.BackgroundDbConnector;
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
    //private UserBaseHelper mHelper;
    private String userName;
    private String pass;
    private Context mContext;
    //private String password;
    //private String mErno;
    public static Activity mActivity;
    public static Boolean mActive;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        mContext = this;

        mActivity = this;

        Boolean status = SharedPreferencesData.getStoredLoginStatus(UserLoginActivity.this);
        if (status) {
            Intent i = new Intent(UserLoginActivity.this, UserProfileHomeActivity.class);
            startActivity(i);
        }

        //mHelper = new UserBaseHelper(this);


        //user UserName editText in activity_user_login
        mUserName = (EditText) findViewById(R.id.login_user_name);


        //PASSWORD editText
        mUserPassword = (EditText) findViewById(R.id.login_user_pass);


        //SignUp button
        mSignUpButton = (Button) findViewById(R.id.sign_up_button);
        mSignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(UserLoginActivity.this, UserSignUpActivity.class);
                startActivity(i);

            }
        });


        //Login Button
        mLoginButton = (Button) findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailableAndConnected()) {
                    /*try {
                        if(!InetAddress.getByName("192.168.2.3").isReachable(5000)){
                            throw new Exception("Host does not exist::");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(UserLoginActivity.this,
                                "Server Is Down",Toast.LENGTH_SHORT).show();
                    }*/
                    String type = "login";
                    userName = mUserName.getText().toString();
                    pass = mUserPassword.getText().toString();
                    //password = mHelper.fetchUserPass(userName);
                    //mErno = mHelper.fetchErNo(userName);
                    //String fullName = mHelper.fetchFullName(userName);

//                    BackgroundDbConnector backgroundDbConnector = new
//                            BackgroundDbConnector(UserLoginActivity.this);
//
//                    backgroundDbConnector.execute(type, userName, pass);

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

                } else {
                    Toast.makeText(UserLoginActivity.this,
                            "No Internet Connection", Toast.LENGTH_SHORT).show();
                }

            }
        });

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
