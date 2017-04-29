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


public class AssignmentPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<AssignmentData> mAssignmentData;

    private static final String EXTRA_ASSIGNMENT_ID = "Assignment_id";

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

        mAssignmentData = AssignmentData.getAssignments();

        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem (int position) {
                AssignmentData assignmentData = mAssignmentData.get(position);
                return AssignmentFragment.newInstance(assignmentData.getId());
            }

            @Override
            public int getCount () {
                return mAssignmentData.size();
            }
        });

        for(int i = 0;i<mAssignmentData.size();i++){
            if(mAssignmentData.get(i).getId() == assignmentId){
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
