package com.example.andorid.ersnexus.userprofile.tabs;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.models.AssignmentData;
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


public class Assignments extends Fragment {

    private RecyclerView mAssignmentRecyclerView;
    private List<AssignmentData> mAssignmentData;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_assignments,container,false);

        mAssignmentRecyclerView = (RecyclerView)v.findViewById(R.id.assignment_recyclerView);
        mAssignmentRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        new FetchAssignmentTask().execute();

        return v;
    }

    private class AssignmentHolder extends RecyclerView.ViewHolder{

        private TextView mAssignmentNameTextView, mSubjectCodeTextView, mFacultyCodeTextView,
                mClassTextView,mAssignmentDate;

        private AssignmentData mAssignmentData;

        public AssignmentHolder(LayoutInflater layoutInflater,ViewGroup container){
            super(layoutInflater.inflate(R.layout.list_item_assignment_recycler_view,container,
                    false));

            mAssignmentNameTextView = (TextView)itemView.
                    findViewById(R.id.list_item_assignment_name_textView);

            mSubjectCodeTextView = (TextView)itemView.
                    findViewById(R.id.list_item_subject_code_textView);

            mClassTextView = (TextView)itemView.
                    findViewById(R.id.list_item_class_textView);

            mAssignmentDate = (TextView)itemView.
                    findViewById(R.id.list_item_date_textView);
        }

        public void bindAssignments(AssignmentData assignmentData){
            mAssignmentData = assignmentData;
            mAssignmentNameTextView.setText(mAssignmentData.getAssignmentName());
            mSubjectCodeTextView.setText(mAssignmentData.getSubjectCode());
            mClassTextView.setText(mAssignmentData.getAssignmentClass());
            mAssignmentDate.setText(mAssignmentData.getDate());

        }
    }


    private class AssignmentAdapter extends RecyclerView.Adapter<AssignmentHolder>{
        private List<AssignmentData> mAssignmentDatas;

        public AssignmentAdapter(List<AssignmentData> assignmentDatas){
            mAssignmentDatas = assignmentDatas;
        }

        @Override
        public AssignmentHolder onCreateViewHolder (ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new AssignmentHolder(inflater,parent);
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

    private class FetchAssignmentTask extends AsyncTask<Void,Void,List<AssignmentData>>{
        private HttpURLConnection mHttpURLConnection;

        @Override
        protected List<AssignmentData> doInBackground (Void... params) {
            try {
                //Fetch the username and password from the background method call.

                mHttpURLConnection = URLManager.
                        getConnection(URLManager.FETCH_ASSIGNMENTS);

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
        protected void onPostExecute(List<AssignmentData> items){
            mAssignmentData = items;
            if(mAssignmentData != null){
                mAssignmentRecyclerView.setAdapter(new AssignmentAdapter(mAssignmentData));
            }
        }
    }

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
                assignmentData.setDate(jsonObject.getString("date"));

                assignmentDatas.add(assignmentData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return assignmentDatas;
    }


}
