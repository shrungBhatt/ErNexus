package com.example.andorid.ersnexus.usersignup;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.userlogin.UserLoginActivity;
import com.example.andorid.ersnexus.util.BaseFragment;
import com.example.andorid.ersnexus.webservices.URLManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserSignUpFragmentNew extends BaseFragment implements Validator.ValidationListener {

    private final String TAG = "SignUpNew";

    @NotEmpty
    private EditText mErnoEdt;

    @NotEmpty
    private EditText mUsernameEdt;

    @NotEmpty
    private EditText mFullnameEdt;

    @NotEmpty
    @Password
    private EditText mPasswordEdt;

    @NotEmpty
    @ConfirmPassword
    private EditText mConfirmPassEdt;

    @NotEmpty
    @Email
    private EditText mEmailEdt;

    private Button mSubmitButton;

    private Validator mValidator;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_sign_up_new, container, false);

        initViews(v);

        mValidator = new Validator(this);
        mValidator.setValidationListener(this);

        mSubmitButton.setOnClickListener(view -> mValidator.validate());

        return v;

    }

    private void initViews(View v) {
        mErnoEdt = v.findViewById(R.id.erno_edt);
        mUsernameEdt = v.findViewById(R.id.username_edt);
        mFullnameEdt = v.findViewById(R.id.fullname_edt);
        mPasswordEdt = v.findViewById(R.id.password_edt);
        mConfirmPassEdt = v.findViewById(R.id.confirm_password_edt);
        mEmailEdt = v.findViewById(R.id.email_id_edt);
        mSubmitButton = v.findViewById(R.id.submit_button);
    }

    @Override
    public void onValidationSucceeded() {
        registerUser();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());

            // Display error messages ;)
            if (view instanceof TextInputEditText) {
                ((TextInputEditText) view).setError(message);
            } else {
                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void registerUser() {
        showProgressBar(TAG);
        final String erno = mErnoEdt.getText().toString();
        final String username = mUsernameEdt.getText().toString();
        final String email = mEmailEdt.getText().toString();
        final String fullname = mFullnameEdt.getText().toString();
        final String password = mPasswordEdt.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.
                REGISTER_STUDENT_URL, response -> {
            hideProgressBar();
            if (response.trim().equals("Insert SuccessFul")) {
                Toast.makeText(getActivity(), "Registered", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), UserLoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        }, error -> {
            hideProgressBar();
            Toast.makeText(getActivity(), error.toString(),
                    Toast.LENGTH_SHORT).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("enrollmentnumber", erno);
                params.put("username", username);
                params.put("fullname", fullname);
                params.put("password", password);
                params.put("emailid", email);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
