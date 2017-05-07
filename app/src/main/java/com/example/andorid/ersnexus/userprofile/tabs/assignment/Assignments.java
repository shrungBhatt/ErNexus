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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.models.AssignmentData;
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

//This class is the Assignment Tab.
//It consits of a recyclerView in which assignments are stored.
//Assignments are fetched from the database using a background task.

public class Assignments extends Fragment {

    private RecyclerView mAssignmentRecyclerView;
    private List<AssignmentData> mAssignmentData;
    private PullRefreshLayout mSwipeRefresh;


    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_assignments, container, false);

        //Adding the swipeAndRefresh functionality
        mSwipeRefresh = (PullRefreshLayout) v.findViewById(R.id.swipe_refresh_assignment_tab);
        mSwipeRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh () {

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
                    public void onScrolled (RecyclerView recyclerView, int dx, int dy) {
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
            new FetchAssignmentTask().execute();
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

        public AssignmentHolder (LayoutInflater layoutInflater, ViewGroup container) {
            super(layoutInflater.inflate(R.layout.list_item_assignment_recycler_view, container,
                    false));

            itemView.setOnClickListener(this);

            mAssignmentNameTextView = (TextView) itemView.
                    findViewById(R.id.list_item_assignment_name_textView);

            mSubjectCodeTextView = (TextView) itemView.
                    findViewById(R.id.list_item_subject_code_textView);

            mClassTextView = (TextView) itemView.
                    findViewById(R.id.list_item_class_textView);

            mAssignmentDate = (TextView) itemView.
                    findViewById(R.id.list_item_date_textView);
        }

        public void bindAssignments (AssignmentData assignmentData) {
            mAssignmentData = assignmentData;
            mAssignmentNameTextView.setText(mAssignmentData.getAssignmentName());
            mSubjectCodeTextView.setText(mAssignmentData.getSubjectCode());
            mClassTextView.setText(mAssignmentData.getAssignmentClass());
            mAssignmentDate.setText(mAssignmentData.getDate());
        }

        @Override
        public void onClick (View v) {
            Intent intent = AssignmentPagerActivity.newIntent(getActivity(),
                    mAssignmentData.getId());
            startActivity(intent);
        }
    }


    //Adapter class of recyclerView.
    private class AssignmentAdapter extends RecyclerView.Adapter<AssignmentHolder> {
        private List<AssignmentData> mAssignmentDatas;

        public AssignmentAdapter (List<AssignmentData> assignmentDatas) {
            mAssignmentDatas = assignmentDatas;
        }

        @Override
        public AssignmentHolder onCreateViewHolder (ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new AssignmentHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder (AssignmentHolder holder, int position) {
            AssignmentData assignmentData = mAssignmentDatas.get(position);
            holder.bindAssignments(assignmentData);

        }

        @Override
        public int getItemCount () {
            return mAssignmentDatas.size();
        }
    }


    //Background AsyncTask to fetch the assignments from the database.
    private class FetchAssignmentTask extends AsyncTask<Void, Void, List<AssignmentData>> {
        private HttpURLConnection mHttpURLConnection;

        @Override
        protected List<AssignmentData> doInBackground (Void... params) {
            try {

                mHttpURLConnection = URLManager.
                        getConnection(URLManager.FETCH_ASSIGNMENTS_URL);

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
                return getAssignmentDatas(result);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute (List<AssignmentData> items) {
            mAssignmentData = items;
            if (mAssignmentData != null) {
                AssignmentData.setAssignments(mAssignmentData);
                mAssignmentRecyclerView.setAdapter(new AssignmentAdapter(mAssignmentData));
            }
        }
    }

    //This method converts the fetched result of array of assignments into an arrayList();
    private List<AssignmentData> getAssignmentDatas (String result) {
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
                assignmentData.setDate(jsonObject.getString("date"));

                assignmentDatas.add(assignmentData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return assignmentDatas;
    }


    private boolean isNetworkAvailableAndConnected () {
        ConnectivityManager cm = (ConnectivityManager) getActivity().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;

        return isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
    }
}
