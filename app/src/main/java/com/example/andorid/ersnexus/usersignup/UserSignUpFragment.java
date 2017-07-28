package com.example.andorid.ersnexus.usersignup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.andorid.ersnexus.database.userdata.UserBaseHelper;
import com.example.andorid.ersnexus.models.UserData;
import com.example.andorid.ersnexus.userlogin.UserLoginActivity;
import com.example.andorid.ersnexus.util.DatePickerFragment;
import com.example.andorid.ersnexus.webservices.URLManager;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class UserSignUpFragment extends Fragment {


    private static final int REQUEST_DATE = 0;
    private static final String DIALOG_DATE = "dialog_date";
    private EditText mErNo1;
    private EditText mErNo2;
    private EditText mErNo3;
    private EditText mErNo4;
    private EditText mErNo5;
    private EditText mErNo6;
    private EditText mErNo7;
    private EditText mErNo8;
    private EditText mErNo9;
    private EditText mErNo10;
    private EditText mErNo11;
    private EditText mErNo12;
    private EditText mUserName;
    private EditText mFullName;
    private EditText mPassword;
    private EditText mEmail;
    private EditText mConfirmPassword;
    private Button mDob;
    private Button mSubmit;
    private UserData mUserData;

    private UserBaseHelper mHelper;

    DateFormat formatDate = DateFormat.getDateInstance();


    @Override
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserData = new UserData();
        mHelper = new UserBaseHelper(getActivity());
    }


    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_sign_up, container, false);


        //enrollmentNumber of user


        mErNo1 = (EditText) v.findViewById(R.id.erno_1);
        mErNo1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                mUserData.setErNo1(s.toString());
                if (mErNo1.getText().length() == 1) {
                    mErNo2.requestFocus();
                }


            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });


        mErNo2 = (EditText) v.findViewById(R.id.erno_2);
        mErNo2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                mUserData.setErNo2(s.toString());
                if (mErNo2.getText().length() == 1) {
                    mErNo3.requestFocus();
                }


            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });


        mErNo3 = (EditText) v.findViewById(R.id.erno_3);
        mErNo3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                mUserData.setErNo3(s.toString());
                if (mErNo3.getText().length() == 1) {
                    mErNo4.requestFocus();
                }


            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });


        mErNo4 = (EditText) v.findViewById(R.id.erno_4);
        mErNo4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                mUserData.setErNo4(s.toString());
                if (mErNo4.getText().length() == 1) {
                    mErNo5.requestFocus();
                }


            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });


        mErNo5 = (EditText) v.findViewById(R.id.erno_5);
        mErNo5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                mUserData.setErNo5(s.toString());
                if (mErNo5.getText().length() == 1) {
                    mErNo6.requestFocus();
                }


            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });


        mErNo6 = (EditText) v.findViewById(R.id.erno_6);
        mErNo6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                mUserData.setErNo6(s.toString());
                if (mErNo6.getText().length() == 1) {
                    mErNo7.requestFocus();
                }


            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });


        mErNo7 = (EditText) v.findViewById(R.id.erno_7);
        mErNo7.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                mUserData.setErNo7(s.toString());
                if (mErNo7.getText().length() == 1) {
                    mErNo8.requestFocus();
                }


            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });


        mErNo8 = (EditText) v.findViewById(R.id.erno_8);
        mErNo8.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                mUserData.setErNo8(s.toString());
                if (mErNo8.getText().length() == 1) {
                    mErNo9.requestFocus();
                }


            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });


        mErNo9 = (EditText) v.findViewById(R.id.erno_9);
        mErNo9.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                mUserData.setErNo9(s.toString());
                if (mErNo9.getText().length() == 1) {
                    mErNo10.requestFocus();
                }


            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });


        mErNo10 = (EditText) v.findViewById(R.id.erno_10);
        mErNo10.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                mUserData.setErNo10(s.toString());
                if (mErNo10.getText().length() == 1) {
                    mErNo11.requestFocus();
                }


            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });


        mErNo11 = (EditText) v.findViewById(R.id.erno_11);
        mErNo11.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                mUserData.setErNo11(s.toString());
                if (mErNo11.getText().length() == 1) {
                    mErNo12.requestFocus();
                }


            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });


        mErNo12 = (EditText) v.findViewById(R.id.erno_12);
        mErNo12.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                mUserData.setErNo12(s.toString());
                if (mErNo12.getText().length() == 1) {
                    mUserName.requestFocus();
                }


            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });


        //USER_NAME editText field in fragment_user_sign_up
        mUserName = (EditText) v.findViewById(R.id.userName_editText);
        mUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                mUserData.setUserName(s.toString());

            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });


        //FULL_NAME editText field in fragment_user_sign_up
        mFullName = (EditText) v.findViewById(R.id.fullName_editText);
        mFullName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                mUserData.setFullName(s.toString());

            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });


        //PASSWORD editText field in fragment_user_sign_up
        mPassword = (EditText) v.findViewById(R.id.passwd_editText);
        mPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                mUserData.setPassword(s.toString());

            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });


        //confirmPassword editText field in fragment_user_sign_up
        mConfirmPassword = (EditText) v.findViewById(R.id.confirm_passwd_editText);
        mConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                mUserData.setCorrectPassword(s.toString());

            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });


        //EMAIL Id of user
        mEmail = (EditText) v.findViewById(R.id.emai_id_editText);
        mEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged (CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged (CharSequence s, int start, int before, int count) {
                mUserData.setEmail(s.toString());

            }

            @Override
            public void afterTextChanged (Editable s) {

            }
        });


        //datOFBirth button
        mDob = (Button) v.findViewById(R.id.dob_button);
        updateDate();
        mDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mUserData.getDob());
                dialog.setTargetFragment(UserSignUpFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });


        //submit button
        mSubmit = (Button) v.findViewById(R.id.submit_button);
        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                if (mUserName.length() == 0) {
                    createSnack("Enter UserName");
                } else if (mFullName.length() == 0) {
                    createSnack("Enter FullName");
                } else if (mPassword.length() == 0) {
                    createSnack("Enter Password");
                } else if (!mUserData.getPassword().equals(mUserData.getCorrectPassword())) {
                    createSnack("Password And Confirm PassWord does not match");
                } else if (mErNo1.length() == 0 &&
                        mErNo2.length() == 0 &&
                        mErNo3.length() == 0 &&
                        mErNo4.length() == 0 &&
                        mErNo5.length() == 0 &&
                        mErNo6.length() == 0 &&
                        mErNo7.length() == 0 &&
                        mErNo8.length() == 0 &&
                        mErNo9.length() == 0 &&
                        mErNo10.length() == 0 &&
                        mErNo11.length() == 0 &&
                        mErNo12.length() == 0) {
                    createSnack("Enter Enrollment Number");
                } else {
                    registerUser();
                }


            }
        });


        return v;
    }

    public void createSnack (String snack) {
        Snackbar.make(getView(), snack,Snackbar.LENGTH_SHORT).show();
    }



    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            mUserData.setDob(date);
            updateDate();
        }
    }

    private void updateDate () {
        mDob.setText(formatDate.format(mUserData.getDob()));

    }


    private void registerUser(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLManager.
                REGISTER_STUDENT_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("Insert SuccessFul")){
                    Toast.makeText(getActivity(), "Registered", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(getActivity(), UserLoginActivity.class);
                    startActivity(i);
                    getActivity().finish();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), error.toString(),
                        Toast.LENGTH_SHORT).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("enrollmentnumber", mUserData.getEnrollmentNumber());
                params.put("username",mUserData.getUserName());
                params.put("fullname",mUserData.getFullName());
                params.put("password",mUserData.getPassword());
                params.put("emailid",mUserData.getEmail());
                params.put("dob",mUserData.getDob().toString());
                return params;
            }
        };



        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }




}
