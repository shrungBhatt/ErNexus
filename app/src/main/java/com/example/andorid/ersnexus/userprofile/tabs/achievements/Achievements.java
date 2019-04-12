package com.example.andorid.ersnexus.userprofile.tabs.achievements;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.widget.PullRefreshLayout;
import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.models.AchievementData;
import com.example.andorid.ersnexus.util.Const;
import com.example.andorid.ersnexus.util.SharedPreferencesData;
import com.example.andorid.ersnexus.webservices.URLManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Achievements extends Fragment {

    private static final String TAG = "Achievements";

    private FloatingActionButton mAddAchievementsFAB;
    private RecyclerView mAchievementRecyclerView;

    private TextView mActivityNameTextView;
    private TextView mSubActivityNameTextView;
    private TextView mActivityDescriptionTextView;
    private TextView mActivityDateTextView;
    private TextView mPointsTextView;
    private TextView mActivityLevelTextView;
    private TextView mTotalPointsTextView;
    private PullRefreshLayout mSwipeRefresh;

    private List<AchievementData> mAchievementDatas;
    private int mTotalPoints;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_achievements, container, false);

        //Floating action button events when it is clicked.
        mAddAchievementsFAB = (FloatingActionButton) v.findViewById(R.id.add_achievements_fab);
        mAddAchievementsFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getActivity(), AddAchievementActivity.class);
                startActivity(i);

            }
        });

        //The achievement recyclerView.
        mAchievementRecyclerView = (RecyclerView) v.findViewById(R.id.achievement_recyclerView);
        mAchievementRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        mTotalPointsTextView = (TextView) v.findViewById(R.id.show_total_points_textView);


        mSwipeRefresh = (PullRefreshLayout) v.findViewById(R.id.swipe_refresh_achievements_tab);
        mSwipeRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(Achievements.this).attach(Achievements.this).commit();

            }
        });
        mAchievementRecyclerView.
                setOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                        super.onScrolled(recyclerView, dx, dy);
                        LinearLayoutManager manager = ((LinearLayoutManager) recyclerView
                                .getLayoutManager());
                        boolean enabled = manager.findFirstCompletelyVisibleItemPosition() == 0;
                        mSwipeRefresh.setEnabled(enabled);
                    }
                });


        return v;
    }

    //method using volley to fetch the achievements data from the database and set up in
    //the recyclerView.
    private void fetchAchievements() {

        final String erno = SharedPreferencesData.getStoredErno(getActivity());

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URLManager.FETCH_ACHIEVEMENT_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        mAchievementDatas = getAssignmentDatas(response);
                        Collections.reverse(mAchievementDatas);
                        if (mAchievementDatas != null) {
                            mAchievementRecyclerView.
                                    setAdapter(new AchievementAdapter(mAchievementDatas));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {

            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("enrollmentnumber", erno);
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


    }

    @Override
    public void onResume() {
        super.onResume();
        fetchAchievements();

    }

    //method used to parse the JSON which we get from the fetchActivityResults() method.
    private List<AchievementData> getAssignmentDatas(String result) {

        int points = 0;

        List<AchievementData> achievementDatas = new ArrayList<>();

        List<Integer> totalPoints = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                AchievementData achievementData = new AchievementData();

                achievementData.setActivity(jsonObject.getString("activity_name"));
                achievementData.setSubActivity(jsonObject.getString("sub_activity"));
                achievementData.setDescription(jsonObject.getString("activity_description"));
                achievementData.setId(jsonObject.getInt("id"));
                achievementData.setActivityLevel(jsonObject.getString("activity_level"));
                achievementData.setDate(jsonObject.getString("activity_date"));
                achievementData.setPoints(jsonObject.getInt("activity_points"));
                achievementData.setStatus(jsonObject.getString("status"));
                achievementData.setImage(jsonObject.getString("activity_image"));

                achievementDatas.add(achievementData);

                int point = jsonObject.getInt("activity_points");
                totalPoints.add(point);

            }

            for (int i = 0; i < totalPoints.size(); i++) {

                points = points + totalPoints.get(i);
            }
            mTotalPoints = points;
            Log.v(TAG, "Total Points: " + mTotalPoints);
            mTotalPointsTextView.setText(Integer.toString(mTotalPoints));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return achievementDatas;
    }

    //Class used for recyclerView's viewHolder.
    private class AchievementHolder extends RecyclerView.ViewHolder {

        private AchievementData mAchievementData;
        private LinearLayout mStatusBg;
        private TextView mStatus;

        AchievementHolder(LayoutInflater inflater, ViewGroup container) {
            super(inflater.inflate(R.layout.list_item_achievement_recycler_view, container,
                    false));

            mActivityNameTextView = (TextView) itemView.findViewById(R.id.activity_name_textView);

            mSubActivityNameTextView = (TextView) itemView.
                    findViewById(R.id.sub_activity_name_textView);

            mActivityDescriptionTextView = (TextView) itemView.
                    findViewById(R.id.activity_description_name_textView);

            mActivityDateTextView = (TextView) itemView.
                    findViewById(R.id.activity_date_textView);

            mPointsTextView = (TextView) itemView.findViewById(R.id.activity_points_textView);

            mActivityLevelTextView = (TextView) itemView.findViewById(R.id.activity_level_textView);

            mStatusBg = (LinearLayout) itemView.findViewById(R.id.status_bg);

            mStatus = (TextView) itemView.findViewById(R.id.status_tv);

        }

        //method used to bind the data using AchievementData model class to the viewHolder.
        private void bindAchievements(AchievementData achievementData) {

            mAchievementData = achievementData;

            mActivityNameTextView.setText(mAchievementData.getActivity());
            mSubActivityNameTextView.setText(mAchievementData.getSubActivity());
            mActivityDescriptionTextView.setText(mAchievementData.getDescription());
            mActivityDateTextView.setText(mAchievementData.getDate());
            mPointsTextView.setText(Integer.toString(mAchievementData.getPoints()));
            mActivityLevelTextView.setText(mAchievementData.getActivityLevel());

        }


    }

    //adapter class for recyclerView, used to set up the adpater of the recyclerView.
    private class AchievementAdapter extends RecyclerView.Adapter<AchievementHolder> {

        private List<AchievementData> mAchievementDatas;

        AchievementAdapter(List<AchievementData> achievementDatas) {
            mAchievementDatas = achievementDatas;
        }


        @Override
        public AchievementHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new AchievementHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(AchievementHolder holder, int position) {
            AchievementData achievementData = mAchievementDatas.get(position);

            appointmentStatusDisplay(holder,position,getActivity());

            holder.bindAchievements(achievementData);

        }

        @Override
        public int getItemCount() {
            return mAchievementDatas.size();
        }


        private void appointmentStatusDisplay(AchievementHolder holder, int position, Context context) {

            Drawable icon = ContextCompat.getDrawable(context, R.drawable.token_textview_background).mutate();

            int statusId = Integer.valueOf(mAchievementDatas.get(position).getStatus());

            switch (statusId) {
                case Const.Pending:
                    setData(holder,context.getResources().getColor(R.color.pending),
                            context.getResources().getColor(R.color.pending),
                            "Pending",icon);
                    break;
                case Const.Approved:
                    setData(holder,context.getResources().getColor(R.color.approved),
                            context.getResources().getColor(R.color.approved),
                            "Approved",icon);
                    break;
            }


        }

        private void setData(AchievementHolder holder, int colorBg, int colorText, String stringId, Drawable icon) {
            icon.setColorFilter(new PorterDuffColorFilter(colorBg, PorterDuff.Mode.SRC_IN));
            holder.mStatusBg.setBackground(icon);
            holder.mStatusBg.setVisibility(View.VISIBLE);
            holder.mStatus.setText(stringId);
            holder.mStatus.setTextColor(colorText);
        }
    }




}
