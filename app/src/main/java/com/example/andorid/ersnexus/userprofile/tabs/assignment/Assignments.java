package com.example.andorid.ersnexus.userprofile.tabs.assignment;

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
import com.example.andorid.ersnexus.models.AssignmentData;
import com.example.andorid.ersnexus.util.SimpleDividerItemDecoration;
import com.example.andorid.ersnexus.webservices.URLManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//This class is the Assignment Tab.
//It consists of a recyclerView in which assignments are stored.
//Assignments are fetched from the database using a background task.

public class Assignments extends Fragment {

    private static final String TAG = "Assignments";

    private RecyclerView mAssignmentRecyclerView;
    private List<AssignmentData> mAssignmentData;
    private PullRefreshLayout mSwipeRefresh;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_assignments, container, false);

        //Adding the swipeAndRefresh functionality
        mSwipeRefresh = (PullRefreshLayout) v.findViewById(R.id.swipe_refresh_assignment_tab);
        mSwipeRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(Assignments.this).attach(Assignments.this).commit();

            }
        });

        //Setting the recylcerView.
        mAssignmentRecyclerView = (RecyclerView) v.findViewById(R.id.assignment_recyclerView);
        mAssignmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //This method is used so that the swipeRefresh does not work when the recyclerView is
        //scrolled up.
        mAssignmentRecyclerView.
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
        mAssignmentRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        if (isNetworkAvailableAndConnected()) {
            //Start the background task if the connection is availabel.
//            new FetchAssignmentTask().execute();
            fetchAssignments();
        } else {
            Toast.makeText(getActivity(), getString(R.string.no_internet_connection),
                    Toast.LENGTH_SHORT).show();
        }

        return v;
    }



    //Viewholder class of recyclerView.
    private class AssignmentHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {

        private TextView mAssignmentNameTextView, mSubjectCodeTextView,
                mClassTextView, mAssignmentDate;

        private AssignmentData mAssignmentData;

        //Constructor used to inflate the viewHolder in adapter class.
        public AssignmentHolder(LayoutInflater layoutInflater, ViewGroup container) {
            super(layoutInflater.inflate(R.layout.list_item_assignment_recycler_view, container,
                    false));

            itemView.setOnClickListener(this);

            mAssignmentNameTextView = (TextView) itemView.
                    findViewById(R.id.list_item_newsfeed_name_textView);

            mSubjectCodeTextView = (TextView) itemView.
                    findViewById(R.id.list_item_subject_code_textView);

            mClassTextView = (TextView) itemView.
                    findViewById(R.id.list_item_class_textView);

            mAssignmentDate = (TextView) itemView.
                    findViewById(R.id.list_item_date_textView);
        }

        //Method to bind the data of viewholder called in the adapter class.
        public void bindAssignments(AssignmentData assignmentData) {
            mAssignmentData = assignmentData;
            mAssignmentNameTextView.setText(mAssignmentData.getAssignmentName());
            mSubjectCodeTextView.setText(mAssignmentData.getSubjectCode());
            mClassTextView.setText(mAssignmentData.getAssignmentClass());
            mAssignmentDate.setText(mAssignmentData.getDate());
        }

        @Override
        public void onClick(View v) {
            Intent intent = AssignmentPagerActivity.newIntent(getActivity(),
                    mAssignmentData.getId());
            startActivity(intent);
        }
    }


    //Adapter class of recyclerView.
    private class AssignmentAdapter extends RecyclerView.Adapter<AssignmentHolder> {
        private List<AssignmentData> mAssignmentDatas;

        public AssignmentAdapter(List<AssignmentData> assignmentDatas) {
            mAssignmentDatas = assignmentDatas;
        }

        @Override
        public AssignmentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new AssignmentHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(AssignmentHolder holder, int position) {
            AssignmentData assignmentData = mAssignmentDatas.get(position);
            holder.bindAssignments(assignmentData);

        }

        @Override
        public int getItemCount() {
            return mAssignmentDatas.size();
        }
    }

    //Method using volley to fetch assignments from online database.
    private void fetchAssignments() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URLManager.FETCH_ASSIGNMENTS_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mAssignmentData = getAssignmentDatas(response);
                        Collections.reverse(mAssignmentData);
                        if(mAssignmentData != null){
                            AssignmentData.setAssignments(mAssignmentData);
                            mAssignmentRecyclerView.setAdapter(new AssignmentAdapter(mAssignmentData));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG,"Error: " + error.toString());
                    }
                }) {
        };


        //This is to start the request to the database server.
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    //This method converts the fetched result of array of assignments into an arrayList();
    public static List<AssignmentData> getAssignmentDatas(String result) {
        List<AssignmentData> assignmentDatas = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                AssignmentData assignmentData = new AssignmentData();
                assignmentData.
                        setAssignmentName(jsonObject.getString("assignmentname"));
                assignmentData.setSubjectCode(jsonObject.getString("subjectcode"));
                assignmentData.setFacultyCode(jsonObject.getString("facultycode"));
                assignmentData.setAssignmentClass(jsonObject.getString("class"));
                assignmentData.setId(jsonObject.getInt("id"));
                assignmentData.setDetails(jsonObject.getString("details"));
                assignmentData.setDate(jsonObject.getString("date"));

                assignmentDatas.add(assignmentData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return assignmentDatas;
    }


    //Method used to check that whether internet connection is available or not.
    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;

        return isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
    }
}
