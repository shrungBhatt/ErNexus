package com.example.andorid.ersnexus.userprofile.homeactivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andorid.ersnexus.R;


public class UserProfileFragment extends Fragment {


    private static final String ARG_USER_NAME ="USER_NAME" ;
    private static final String ARG_FULL_NAME = "FULL_NAME";
    private static final String ARGS_ERNO = "erno";


    private ImageView mProfilePicImgView;
    private TextView mUserNameTextView;
    private TextView mFullNameTextView;
    private TextView mEnrollmentNoTextView;
    private TextView mFieldTextView;
    private TextView mCollegeTextView;
    private String mUserName;
    private String mFullName;
    private String mErNo;



    public static UserProfileFragment newInstance(String userName,
                                                  String fullName,String enrollmentNumber){
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER_NAME,userName);
        args.putSerializable(ARG_FULL_NAME,fullName);
        args.putSerializable(ARGS_ERNO,enrollmentNumber);

        UserProfileFragment fragment = new UserProfileFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mUserName = (String)getArguments().getSerializable(ARG_USER_NAME);
        mFullName = (String)getArguments().getSerializable(ARG_FULL_NAME);
        mErNo = (String)getArguments().getSerializable(ARGS_ERNO);



    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.activity_user_profile,container,false);

        //ProfilePicture ImageView
        mProfilePicImgView = (ImageView) v.findViewById(R.id.profile_picture);


        //UserName textView
        mUserNameTextView = (TextView) v.findViewById(R.id.userName_textView);
        mUserNameTextView.setText(mUserName);



        //Full Name textView
        mFullNameTextView = (TextView)v.findViewById(R.id.name_textView);
        mFullNameTextView.setText(mFullName);





        //EnrollmentNumber textView
        mEnrollmentNoTextView = (TextView)v.findViewById(R.id.er_no__textView);
        mEnrollmentNoTextView.setText(mErNo);


        //Field textView
        mFieldTextView = (TextView)v.findViewById(R.id.field_textView);

        //College textView
        mCollegeTextView = (TextView)v.findViewById(R.id.college_textView);

        return v;
    }
}
