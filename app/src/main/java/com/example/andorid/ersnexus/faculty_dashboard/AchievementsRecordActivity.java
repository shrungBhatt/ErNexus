package com.example.andorid.ersnexus.faculty_dashboard;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.adapters.Adapter_AchievementsList;
import com.example.andorid.ersnexus.interfaces.Interface_StatusChangeListener;
import com.example.andorid.ersnexus.models.AchievementDataGSON;

import com.example.andorid.ersnexus.webservices.URLManager;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;


public class AchievementsRecordActivity extends AppCompatActivity implements Interface_StatusChangeListener {

    private RecyclerView mRecyclerView;
    private ArrayList<AchievementDataGSON.List> mAchievementDatas;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements_record);

        mRecyclerView = (RecyclerView) findViewById(R.id.activity_achievements_record_recy);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));


    }


    @Override
    protected void onResume() {
        super.onResume();
        fetchAchievements();
    }

    private void fetchAchievements() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URLManager.FETCH_ALL_ACHIEVEMENT_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject;
                        AchievementDataGSON achievementDataGSON = null;

                        try {
                            jsonObject = new JSONObject(response);
                            Gson gson = new Gson();
                            achievementDataGSON = gson.fromJson(jsonObject.toString(), AchievementDataGSON.class);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        if (achievementDataGSON != null) {
                            mAchievementDatas = achievementDataGSON.getList();
                            Collections.reverse(mAchievementDatas);
                            if (mAchievementDatas != null) {
                                mRecyclerView.
                                        setAdapter(new Adapter_AchievementsList(AchievementsRecordActivity.this,
                                                mAchievementDatas,
                                                AchievementsRecordActivity.this));
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),
                                "Something Went Wrong!",Toast.LENGTH_SHORT).show();
                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }


    @Override
    public void updateStatus(String activityId, int statusId) {
        Toast.makeText(getApplicationContext(),"Click received",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(AchievementDataGSON.List achievementDataGSON, int position) {
        startActivity(ViewAchievementActivity.newIntent(this,achievementDataGSON,position));

    }
}
