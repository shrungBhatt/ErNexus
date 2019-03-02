package com.example.andorid.ersnexus.usersignup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.example.andorid.ersnexus.userlogin.UserLoginActivity;
import com.example.andorid.ersnexus.util.BaseActivity;
import com.example.andorid.ersnexus.webservices.URLManager;

import java.util.HashMap;
import java.util.Map;

public class FacultySignUpActivity extends BaseActivity {


    private EditText mFacultyCodeEdtTxt;
    private EditText mFacultyNameEdtTxt;
    private EditText mFacultyPasswordEdtTxt;
    private EditText mConfirmPasswordEdtTxt;

    private Button mSubmitButton;
    private String TAG = "FacultySignUp";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_sign_up);

        mFacultyCodeEdtTxt = (EditText) findViewById(R.id.faculty_code_edit_text);
        mFacultyNameEdtTxt = (EditText) findViewById(R.id.faculty_name_edit_text);
        mFacultyPasswordEdtTxt = (EditText) findViewById(R.id.faculty_password_edit_text);
        mConfirmPasswordEdtTxt = (EditText) findViewById(R.id.faculty_confirm_password_edit_text);

        mSubmitButton = (Button) findViewById(R.id.faculty_register_submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mFacultyCodeEdtTxt.getText().toString().trim().length() > 0) {

                    if (mFacultyNameEdtTxt.getText().toString().trim().length() > 0) {


                        if (mConfirmPasswordEdtTxt.getText().toString().trim().
                                equals(mFacultyPasswordEdtTxt.getText().toString().trim())) {

                            registerFaculty();
                        } else {
                            mConfirmPasswordEdtTxt.setError("Password do not match");
                        }

                    } else {
                        mFacultyNameEdtTxt.setError("Enter name");
                    }
                } else {
                    mFacultyCodeEdtTxt.setError("Enter code");
                }
            }
        });

    }


    private void registerFaculty() {
        showProgressBar(TAG);
        final String facCode = mFacultyCodeEdtTxt.getText().toString().trim();
        final String facName = mFacultyNameEdtTxt.getText().toString().trim();
        final String facPass = mFacultyPasswordEdtTxt.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URLManager.REGISTER_FACULTY,
                response -> {
                    hideProgressBar();
                    if (response.trim().equals("Insert SuccessFul")) {
                        Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();

                    }

                }, error -> {
            hideProgressBar();
            Log.e(TAG, error.getMessage());

        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("fac_code", facCode);
                params.put("fac_name", facName);
                params.put("fac_pass", facPass);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}
