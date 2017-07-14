package com.example.andorid.ersnexus.userprofile.tabs.newsfeed;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.models.NewsData;


import java.util.List;

/**
 * Created by Bhatt on 14-07-2017.
 */

public class NewsPagerFragment extends Fragment {

    private int mPosition;
    private List<NewsData> mNewsDatas;
    private TextView mNewsTopicName;
    private TextView mNewsDate;
    private TextView mNewsDiscription;


    private static final String ARG_NEWS_ID = "newsId";
    private static final String ARG_ARRAY_POSITION = "position of news in newsArray";

    public static NewsPagerFragment newInstance (int assignmentId, int position) {
        Bundle args = new Bundle();
        args.putInt(ARG_NEWS_ID, assignmentId);
        args.putInt(ARG_ARRAY_POSITION, position);
        NewsPagerFragment fragment = new NewsPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mPosition = getArguments().getInt(ARG_ARRAY_POSITION);

        mNewsDatas = NewsData.getmNewsData();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_pager_newsfeed,container,false);

        mNewsTopicName = (TextView)v.findViewById(R.id.news_topic);
        mNewsTopicName.setText(mNewsDatas.get(mPosition).getNewsTopic());

        mNewsDate = (TextView)v.findViewById(R.id.news_date);
        mNewsDate.setText(mNewsDatas.get(mPosition).getNewsDate());

        mNewsDiscription = (TextView)v.findViewById(R.id.news_discription);
        mNewsDiscription.setText(mNewsDatas.get(mPosition).getNewsDetails());





        return v;

    }





}
