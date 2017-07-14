package com.example.andorid.ersnexus.userprofile.tabs.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.models.NewsData;

import java.util.List;


public class NewsPagerActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private List<NewsData> mNewsDatas;

    private static final String EXTRA_NEWS_ID = "NewsId";

    public static Intent newIntent(Context context, int newsId){
        Intent i = new Intent(context,NewsPagerActivity.class);
        i.putExtra(EXTRA_NEWS_ID,newsId);
        return i;
    }


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsfeed_pager);

        final int newsId = (int)getIntent().getSerializableExtra(EXTRA_NEWS_ID);

        mViewPager = (ViewPager)findViewById(R.id.activity_newsfeed_viewpager);

        mNewsDatas = NewsData.getmNewsData();

        FragmentManager fm = getSupportFragmentManager();

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem (int position) {
                NewsData newsData =  mNewsDatas.get(position);
                return NewsPagerFragment.newInstance(newsData.getId(),position);
            }

            @Override
            public int getCount () {
                return mNewsDatas.size();
            }
        });

        for(int i = 0;i<mNewsDatas.size();i++){
            if(mNewsDatas.get(i).getId() == newsId){
                mViewPager.setCurrentItem(i);
                break;
            }
        }


    }



}
