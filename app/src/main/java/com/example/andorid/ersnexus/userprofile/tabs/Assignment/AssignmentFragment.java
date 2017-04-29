package com.example.andorid.ersnexus.userprofile.tabs.Assignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andorid.ersnexus.R;


public class AssignmentFragment extends Fragment {

    private static final String ARG_ASSIGNMENT_ID = "Assignment_id";


    private int mAssignmentId;

    public static AssignmentFragment newInstance (int crimeId) {
        Bundle args = new Bundle();
        args.putInt(ARG_ASSIGNMENT_ID, crimeId);
        AssignmentFragment fragment = new AssignmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mAssignmentId = getArguments().getInt(ARG_ASSIGNMENT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container,
                             Bundle savedInstanceState){

        View v = layoutInflater.inflate(R.layout.fragment_assignment,container,false);



        return v;
    }

}
