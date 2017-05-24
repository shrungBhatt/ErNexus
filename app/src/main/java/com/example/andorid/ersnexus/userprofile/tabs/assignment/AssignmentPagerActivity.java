package com.example.andorid.ersnexus.userprofile.tabs.assignment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.models.AssignmentData;

import java.util.List;

//This class is the viewPager for the Assignment fragment class.

public class AssignmentPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<AssignmentData> mAssignmentData;

    private static final String EXTRA_ASSIGNMENT_ID = "Assignment_id";

    //Method used to pass the assignmentId of the field that the user clicked in the Assignment tab
    public static Intent newIntent(Context packageContext, int assignmentId){
        Intent intent = new Intent(packageContext,AssignmentPagerActivity.class);
        intent.putExtra(EXTRA_ASSIGNMENT_ID,assignmentId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_pager);


        int assignmentId = (int)getIntent().getSerializableExtra(EXTRA_ASSIGNMENT_ID);

        mViewPager = (ViewPager)findViewById(R.id.assignment_viewPager);

        //To get an array to feed the viewPager adapter.
        //This array is given its value from the Assignmet tab AssignmentData.setAssignment()method.
        mAssignmentData = AssignmentData.getAssignments();

        FragmentManager fm = getSupportFragmentManager();
        //Setting the viewPager's adapter.
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            //Method used to start the AssignmentPagerFragment
            //One value is fetched from the mAssignmentData array and that values are passed to
            //the fragment to set the values of the views in the AssignmentPagerFragment.
            @Override
            public Fragment getItem (int position) {
                AssignmentData assignmentData = mAssignmentData.get(position);
                return AssignmentPagerFragment.newInstance(assignmentData.getId(),position);
            }

            @Override
            public int getCount () {
                return mAssignmentData.size();
            }
        });

        //This loop is used, so that when the user selectes any assignment in the Assignment Tab's,
        //recyclerView viewPager sets the selected item as the current page.
        for(int i = 0;i<mAssignmentData.size();i++){
            if(mAssignmentData.get(i).getId() == assignmentId){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
