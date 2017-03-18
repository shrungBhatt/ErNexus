package com.example.andorid.ersnexus.userlogin;

import android.content.Intent;
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
import com.example.andorid.ersnexus.util.SharedPreferences;


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

    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

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
                userName = mUserName.getText().toString();
                pass = mUserPassword.getText().toString();
                password = mHelper.fetchUserPass(userName);
                mErno = mHelper.fetchErNo(userName);
                SharedPreferences.setStoredErno(UserLoginActivity.this,mErno);
                //String fullName = mHelper.fetchFullName(userName);

                if (pass.equals(password)) {
                    //Intent i = UserProfileActivity.newIntent(UserLoginActivity.this, userName, fullName, erNo);
                    Intent i = new Intent(UserLoginActivity.this, UserProfileHomeActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(UserLoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
