package com.example.andorid.ersnexus.userprofile.homeactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.userlogin.UserLoginActivity;
import com.example.andorid.ersnexus.webservices.BackgroundDbConnector;
import com.example.andorid.ersnexus.util.SharedPreferencesData;

//This is the activity where all the tabs are showed where user interacts with everything.

public class UserProfileHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
        ,TabLayout.OnTabSelectedListener {

    //This is our tabLayout
    private TabLayout mTabLayout;

    //This is our mViewPager
    private ViewPager mViewPager;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_home_activity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Intent intent = new Intent("finish_activity");
        //sendBroadcast(intent);

        if(SharedPreferencesData.getStoredLoginStatus(UserProfileHomeActivity.this)&&
                UserLoginActivity.mActive){
            String userName = SharedPreferencesData.getStoredUsername(UserProfileHomeActivity.this);
            Toast.makeText(UserProfileHomeActivity.this,
                    "Welcome "+userName,Toast.LENGTH_SHORT).show();
            UserLoginActivity.mActivity.finish();
            UserLoginActivity.mActivity = null;
        }

        String type = "enrollmentnumber";
        String username = SharedPreferencesData.getStoredUsername(UserProfileHomeActivity.this);

        BackgroundDbConnector backgroundDbConnector = new
                BackgroundDbConnector(UserProfileHomeActivity.this);

        backgroundDbConnector.execute(type,username);

        //Initializing the tablayout
        mTabLayout = (TabLayout) findViewById(R.id.home_activity_tabLayout);

        //Adding the tabs using addTab() method
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.addTab(mTabLayout.newTab());
        mTabLayout.setTabGravity(TabLayout.MODE_SCROLLABLE);

        //Initializing mViewPager
        mViewPager = (ViewPager) findViewById(R.id.home_activity_viewPager);


        //Creating our pager adapter
        UserProfileHomeActivityViewPager adapter = new UserProfileHomeActivityViewPager
                (getSupportFragmentManager(),
                        mTabLayout.getTabCount());

        //Adding adapter to pager
        mViewPager.setAdapter(adapter);

        //Connecting the viewPager and the tabLayout.
        mTabLayout.setupWithViewPager(mViewPager);

        //Giving the headers of tabs.
        mTabLayout.getTabAt(0).setText(R.string.attendance_tab);
        mTabLayout.getTabAt(1).setText(R.string.assignments_tab);
        mTabLayout.getTabAt(2).setText(R.string.newsfeed_tab);
        mTabLayout.getTabAt(3).setText(R.string.achievements_tab);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed () {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user_profile_home_activty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharedPreferencesData.setStoredLoginStatus(UserProfileHomeActivity.this,false);
            Intent i = new Intent(UserProfileHomeActivity.this, UserLoginActivity.class);
            startActivity(i);
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected (MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTabSelected (TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected (TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected (TabLayout.Tab tab) {

    }
}
