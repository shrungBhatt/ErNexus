package com.example.andorid.ersnexus.userprofile.tabs.newsfeed;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.widget.PullRefreshLayout;
import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.models.NewsData;
import com.example.andorid.ersnexus.util.SimpleDividerItemDecoration;
import com.example.andorid.ersnexus.webservices.URLManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


public class NewsFeed extends Fragment {

    private static final String TAG = "Newsfeed";

    private RecyclerView mNewsfeedRecyclerView;
    private List<NewsData> mNewsData;
    private PullRefreshLayout mSwipeRefresh;

    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_newsfeed, container, false);

        mSwipeRefresh = (PullRefreshLayout) v.findViewById(R.id.swipe_refresh_newsfeed_tab);
        mSwipeRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh () {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(NewsFeed.this).attach(NewsFeed.this).commit();

            }
        });

        //Setting the recylcerView.
        mNewsfeedRecyclerView = (RecyclerView) v.findViewById(R.id.newsfeed_recyclerView);
        mNewsfeedRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //This method is used so that the swipeRefresh will not work when the recyclerView is
        //scrolled up.
        mNewsfeedRecyclerView.
                setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled (RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        LinearLayoutManager manager = ((LinearLayoutManager) recyclerView
                                .getLayoutManager());
                        boolean enabled = manager.findFirstCompletelyVisibleItemPosition() == 0;
                        mSwipeRefresh.setEnabled(enabled);
                    }
                });
        mNewsfeedRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));


        if (isNetworkAvailableAndConnected()) {
            //Start the background task if the connection is availabel.
            fetchNews();
        } else {
            Toast.makeText(getActivity(), getString(R.string.no_internet_connection),
                    Toast.LENGTH_SHORT).show();
        }


        return v;
    }


    private class NewsHolder extends RecyclerView.ViewHolder {

        private TextView mNewsTopicTextView;
        private TextView mNewsDateTextView;
        private NewsData mNewsData;

        public NewsHolder (LayoutInflater layoutInflater, ViewGroup container) {
            super(layoutInflater.inflate(R.layout.list_item_newsfeed_recycler_view, container,
                    false));

            mNewsTopicTextView = (TextView) itemView.
                    findViewById(R.id.list_item_newsfeed_name_textView);

            mNewsDateTextView = (TextView) itemView.findViewById(R.id.list_item_news_date_textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick (View v) {
                    Intent i = NewsPagerActivity.newIntent(getActivity(),mNewsData.getId());
                    startActivity(i);
                }
            });
        }


        public void bindNews (NewsData newsData) {
            mNewsData = newsData;
            mNewsTopicTextView.setText(mNewsData.getNewsTopic());
            mNewsDateTextView.setText(mNewsData.getNewsDate());
        }


    }

    private class NewsAdapter extends RecyclerView.Adapter<NewsHolder> {
        private List<NewsData> mNewsDatas;

        public NewsAdapter (List<NewsData> newsDatas) {
            mNewsDatas = newsDatas;
        }

        @Override
        public NewsHolder onCreateViewHolder (ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new NewsHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder (NewsHolder holder, int position) {
            NewsData newsData = mNewsDatas.get(position);
            holder.bindNews(newsData);

        }

        @Override
        public int getItemCount () {
            return mNewsDatas.size();
        }
    }


    //Method using volley to fetch database from the database.
    private void fetchNews(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URLManager.FETCH_NEWS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mNewsData = getNewsDatas(response);
                        if (mNewsData != null) {
                            Collections.reverse(mNewsData);
                            mNewsfeedRecyclerView.setAdapter(new NewsAdapter(mNewsData));
                            NewsData.setmNewsData(mNewsData);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG,error.toString());
                    }
                }){
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    //Method used to parse the json response from the database.
    private List<NewsData> getNewsDatas (String result) {
        List<NewsData> newsDatas = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                NewsData newsData = new NewsData();
                newsData.setId(jsonObject.getInt("id"));
                newsData.setNewsTopic(jsonObject.getString("topic"));
                newsData.setNewsDate(jsonObject.getString("date"));
                newsData.setNewsDetails(jsonObject.getString("details"));

                newsDatas.add(newsData);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return newsDatas;
    }


    private boolean isNetworkAvailableAndConnected () {
        ConnectivityManager cm = (ConnectivityManager) getActivity().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;

        return isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
    }
}


