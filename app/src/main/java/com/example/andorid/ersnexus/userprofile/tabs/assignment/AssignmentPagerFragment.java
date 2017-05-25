package com.example.andorid.ersnexus.userprofile.tabs.assignment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.models.AssignmentData;

import java.util.List;


//This class is the Fragment which the AssignmentPagerActivity will hold
//All the modification regarding the assignment screen cen be done here.

public class AssignmentPagerFragment extends Fragment {

    //String key for bundle arguments to pass information between the AssignmentPagerActivity and
    //This class.
    private static final String ARG_ASSIGNMENT_ID = "Assignment_id";
    private static final String ARG_ARRAY_POSITION = "Array_position";


    //To store the value of the id we got from the user after he pressed a particular assignment
    //From the assignment fragment screen.
    private int mAssignmentId;
    private TextView mAssignmentDetails;
    private TextView mAssignmentNameTextView;
    private TextView mAssignmentDate;
    private TextView mSubjectCode;
    private TextView mFacultyCode;
    private int mPosition;
    private List<AssignmentData> mAssignmentDatas;

    //Method called in Assignment tab when the user clicks any assignment(viewHolder).
    public static AssignmentPagerFragment newInstance (int assignmentId, int position) {
        Bundle args = new Bundle();
        args.putInt(ARG_ASSIGNMENT_ID, assignmentId);
        args.putInt(ARG_ARRAY_POSITION, position);
        AssignmentPagerFragment fragment = new AssignmentPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mAssignmentId = getArguments().getInt(ARG_ASSIGNMENT_ID);

        mPosition = getArguments().getInt(ARG_ARRAY_POSITION);

        mAssignmentDatas = AssignmentData.getAssignments();

        //This is used to start the background task to fetch the assignment from the database using
        //the assignmentId.
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container,
                             Bundle savedInstanceState){

        View v = layoutInflater.inflate(R.layout.fragment_assignment,container,false);


        mAssignmentDetails = (TextView)v.findViewById(R.id.assignment_details_textview);
        mAssignmentDetails.setText(mAssignmentDatas.get(mPosition).getDetails());

        mAssignmentNameTextView = (TextView)v.findViewById(R.id.assignment_name);
        mAssignmentNameTextView.setText(mAssignmentDatas.get(mPosition).getAssignmentName());

        mAssignmentDate = (TextView)v.findViewById(R.id.assignment_date);
        mAssignmentDate.setText(mAssignmentDatas.get(mPosition).getDate());

        mFacultyCode = (TextView)v.findViewById(R.id.assignment_faculty_code);
        mFacultyCode.setText(mAssignmentDatas.get(mPosition).getFacultyCode());

        mSubjectCode = (TextView)v.findViewById(R.id.assignment_subject_code);
        mSubjectCode.setText(mAssignmentDatas.get(mPosition).getSubjectCode());

        return v;
    }

}
