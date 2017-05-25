package com.example.andorid.ersnexus.userprofile.tabs.newsfeed;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;


public class NewsFeed extends Fragment {

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
        //This method is used so that the swipeRefresh does not work when the recyclerView is
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
            new FetchNewsTask().execute();
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
        }


        public void bindNews (NewsData newsData) {
            mNewsData = newsData;
            mNewsTopicTextView.setText(mNewsData.getNewsTopic());
            mNewsDateTextView.setText(mNewsData.getNewsDate());
        }
    }

    private class NewsAdapter extends RecyclerView.Adapter<NewsHolder>{
        private List<NewsData> mNewsDatas;

        public NewsAdapter(List<NewsData> newsDatas){
            mNewsDatas = newsDatas;
        }

        @Override
        public NewsHolder onCreateViewHolder (ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new NewsHolder(inflater,parent);
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



    private class FetchNewsTask extends AsyncTask<Void,Void,List<NewsData>>{
        private HttpURLConnection mHttpURLConnection;

        @Override
        protected List<NewsData> doInBackground(Void... Params){
            try {

                mHttpURLConnection = URLManager.
                        getConnection(URLManager.FETCH_NEWS_URL);

                //Creating an inputStream to fetch the results.
                InputStream inputStream = mHttpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        inputStream, "iso-8859-1"));

                //Getting the results
                String result = "";
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                mHttpURLConnection.disconnect();

                //Returning the results
                return getNewsDatas(result);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<NewsData> items){
            mNewsData = items;
            if(mNewsData != null) {
                mNewsfeedRecyclerView.setAdapter(new NewsAdapter(mNewsData));
                NewsData.setmNewsData(mNewsData);
            }

        }

    }

    private List<NewsData> getNewsDatas (String result) {
        List<NewsData> newsDatas = new ArrayList<>();

        try{
            JSONArray jsonArray = new JSONArray(result);

            for(int i = 0; i < jsonArray.length();i++){
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


