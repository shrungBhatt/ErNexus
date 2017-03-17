package com.example.andorid.ersnexus.userprofile.homeactivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.andorid.ersnexus.userprofile.tabs.Achievements;
import com.example.andorid.ersnexus.userprofile.tabs.Assignments;
import com.example.andorid.ersnexus.userprofile.tabs.Attendance;
import com.example.andorid.ersnexus.userprofile.tabs.NewsFeed;


public class UserProfileHomeActivityViewPager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    private int mTabCount;

    //Constructor to the class
    public UserProfileHomeActivityViewPager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.mTabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                return new Attendance();
            case 1:
                return new Assignments();
            case 2:
                return new NewsFeed();
            case 3:
                return new Achievements();
            default:
                return null;
        }
    }

    //Overiden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return mTabCount;
    }
}

