package com.example.andorid.ersnexus.faculty_dashboard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.models.AchievementDataGSON;
import com.example.andorid.ersnexus.util.Const;
import com.example.andorid.ersnexus.webservices.URLManager;
import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ViewAchievementActivity extends AppCompatActivity {

    private static final String EXTRA_ACHIEVEMENTDATA = "achievement_data";
    private static final String EXTRA_POSITION = "position";
    private final String TAG = getClass().getSimpleName();


    private ZoomageView mAchievementDataImageView;
    private Button mApproveButton;
    private Button mRejectButton;
    private AchievementDataGSON.List mAchievementData;
    private int mPosition;

    public static Intent newIntent(Context context, AchievementDataGSON.List achievementData, int position) {
        Intent intent = new Intent(context, ViewAchievementActivity.class);
        intent.putExtra(EXTRA_ACHIEVEMENTDATA, achievementData);
        intent.putExtra(EXTRA_POSITION, position);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_achievement);

        mAchievementDataImageView = findViewById(R.id.achievement_image_holder);
        mApproveButton = findViewById(R.id.approve_button);
        mRejectButton = findViewById(R.id.reject_button);

        mAchievementData = (AchievementDataGSON.List) getIntent().getSerializableExtra(EXTRA_ACHIEVEMENTDATA);
        mPosition = getIntent().getIntExtra(EXTRA_POSITION, 0);


        if (mAchievementData != null) {
            if (mAchievementData.getActivityImage() != null && !mAchievementData.getActivityImage().equalsIgnoreCase("")) {
                Picasso.with(this).load(mAchievementData.getActivityImage())
                        .into(mAchievementDataImageView);
            }



            mApproveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendUpdateStatusRequest(Const.Approved, mAchievementData.getId());
                }
            });

            mRejectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendUpdateStatusRequest(Const.Rejected, mAchievementData.getId());
                }
            });

        }
    }

    private void sendUpdateStatusRequest(final int status, final String id) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URLManager.UPDATE_ACHIEVEMENT_STATUS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equalsIgnoreCase("Updated status")) {
                            Toast.makeText(getApplicationContext(), "Status Updated!",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, error.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("status_id", String.valueOf(status));
                params.put("id", id);
                return params;
            }


        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
