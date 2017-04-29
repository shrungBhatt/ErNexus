package com.example.andorid.ersnexus.userprofile.tabs.assignment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.webservices.URLManager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;


public class AssignmentFragment extends Fragment {

    private static final String ARG_ASSIGNMENT_ID = "Assignment_id";


    private int mAssignmentId;
    private TextView mAssignmentDetails;

    public static AssignmentFragment newInstance (int crimeId) {
        Bundle args = new Bundle();
        args.putInt(ARG_ASSIGNMENT_ID, crimeId);
        AssignmentFragment fragment = new AssignmentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mAssignmentId = getArguments().getInt(ARG_ASSIGNMENT_ID);
        new FetchAssignmentDetailsTask().execute(mAssignmentId);
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container,
                             Bundle savedInstanceState){

        View v = layoutInflater.inflate(R.layout.fragment_assignment,container,false);


        mAssignmentDetails = (TextView)v.findViewById(R.id.assignment_details_textview);

        return v;
    }

    private class FetchAssignmentDetailsTask extends AsyncTask<Integer,Void,String>{

        HttpURLConnection mHttpURLConnection;

        @Override
        protected String doInBackground (Integer... params) {
            try {
                //Fetch the username from the background method call.
                int id = params[0];

                mHttpURLConnection = URLManager.
                        getConnection(URLManager.FETCH_ASSIGNMENTS_DETAILS_URL);

                //Creating the outputStream
                OutputStream outputStream = mHttpURLConnection.getOutputStream();
                //Writing in the outputStream.
                BufferedWriter bufferedWriter = new BufferedWriter(new
                        OutputStreamWriter(outputStream, "UTF-8"));

                //This is for connecting the variables in the app and in the php file.
                String postData = URLEncoder.encode("id", "UTF-8") + "=" +//$_POST["id"]
                        URLEncoder.encode(String.valueOf(id), "UTF-8");

                //Feeding the data.
                bufferedWriter.write(postData);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

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
                return result;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            if(result != null){
                mAssignmentDetails.setText(result);
            }
        }
    }

}
