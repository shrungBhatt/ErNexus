package com.example.andorid.ersnexus.userprofile.tabs;

import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.widget.PullRefreshLayout;
import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.models.AttendanceData;
import com.example.andorid.ersnexus.userscanattendance.UserScanAttendanceActivity;
import com.example.andorid.ersnexus.util.SharedPreferencesData;
import com.example.andorid.ersnexus.webservices.URLManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

//This class is the attendance tab in userProfileHomeActivity screen.


public class Attendance extends Fragment implements AdapterView.OnItemSelectedListener {

    private Button mScanAttendanceButton;
    //private AttendanceAdapter mAdapter;
    private RecyclerView mAttendanceRecyclerView;
    private int mPosition;
    private EditText mSortAttendanceEditText;
    private ImageButton mSortAttendanceButton;
    private String mErno;
    //private AttendanceLab mAttendanceLab;
    private List<AttendanceData> mAttendanceDatas;
    private ImageButton mClearButton;
    private PullRefreshLayout mSwipeRefresh;
    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_attendance, container, false);

        mErno = SharedPreferencesData.getStoredErno(getActivity());

        String type = "sort_erno";
        new FetchAttendanceTask().execute(type,mErno);





        //mAttendanceLab = AttendanceLab.get(getActivity());

        //Button used to start the scanAttendance activity.
        mScanAttendanceButton = (Button) v.findViewById(R.id.scan_attendance_button);
        Long currentTs = System.currentTimeMillis()/1000;
        Long previousTs = SharedPreferencesData.getCurrentTimeStamp(getActivity());

        if(currentTs - previousTs >= 20){
            mScanAttendanceButton.setEnabled(true);
        }else {
            mScanAttendanceButton.setEnabled(false);
            mScanAttendanceButton.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
            mScanAttendanceButton.setText(R.string.attendance_cooldown_text);
        }

        mScanAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {


                Long currentTs = System.currentTimeMillis()/1000;
                SharedPreferencesData.setCurrntTimeStamp(getActivity(),currentTs);

                Intent i = new Intent(getActivity(), UserScanAttendanceActivity.class);
                startActivityForResult(i, 1);
            }
        });

        mClearButton = (ImageButton) v.findViewById(R.id.clear_button);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                mSortAttendanceEditText.getText().clear();
                String type = "sort_erno";
                new FetchAttendanceTask().execute(type,mErno);
                //updateUI();
            }
        });

        mSwipeRefresh = (PullRefreshLayout)v.findViewById(R.id.swipe_refresh_attendance_tab);
        mSwipeRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh () {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(Attendance.this).attach(Attendance.this).commit();

            }
        });

        Spinner spinner = (Spinner) v.findViewById(R.id.sort_attendance_by_spinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.sort_attendance_by, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        mSortAttendanceEditText = (EditText) v.findViewById(R.id.sort_attendance_by_editText);

        mSortAttendanceButton = (ImageButton) v.findViewById(R.id.sort_attendance_by_button);
        mSortAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                switch (mPosition) {
                    case 0:
                        String type = "sort_erno";
                        new FetchAttendanceTask().execute(type,mErno);
                        break;
                    case 1:
                        type = "sort_subject_code";
                        String sortSubject = mSortAttendanceEditText.getText().toString();
                        new FetchAttendanceTask().execute(type,mErno,sortSubject);
                        break;
                    case 2:
                        type = "sort_faculty_code";
                        String sortFaculty = mSortAttendanceEditText.getText().toString();
                        new FetchAttendanceTask().execute(type,mErno,sortFaculty);
                        break;
                    case 3:
                        type = "sort_date";
                        String sortDate = mSortAttendanceEditText.getText().toString();
                        new FetchAttendanceTask().execute(type,mErno,sortDate);
                        break;
                }

            }
        });

        //RecyclerView that displays the attendances after fetching it from the database.
        mAttendanceRecyclerView = (RecyclerView) v.findViewById(R.id.attendance_recyvlerView);
        mAttendanceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAttendanceRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled (RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = ((LinearLayoutManager)recyclerView
                        .getLayoutManager());
                boolean enabled = manager.findFirstCompletelyVisibleItemPosition() == 0;
                mSwipeRefresh.setEnabled(enabled);
            }
        });
        //updateUI();



        return v;
    }

    @Override
    public void onResume () {
        super.onResume();
        //updateUI();// To update the data in recyclerView after editing the data in attendance tab.
    }

    @Override
    public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
        mPosition = position;
    }

    @Override
    public void onNothingSelected (AdapterView<?> parent) {

    }

    /*private void checkAndSetAdapter () {
        if (mAdapter == null) {
            mAdapter = new AttendanceAdapter(mAttendanceDatas);
            mAttendanceRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setAttendances(mAttendanceDatas);
            mAdapter.notifyDataSetChanged();
        }
    }*/


    //ViewHolder class of the recyclerView.
    private class AttendanceHolder extends RecyclerView.ViewHolder {

        private TextView mDateTextView, mErnoTextView, mFacultyTextView, mSubjectTextView;
        private AttendanceData mAttendanceData;

        public AttendanceHolder (LayoutInflater layoutInflater, ViewGroup container) {
            super(layoutInflater.inflate(R.layout.list_item_attendance_recycler_view, container,
                    false));

            mDateTextView = (TextView) itemView.findViewById(R.id.date_list_item_textView);

            mErnoTextView = (TextView) itemView.findViewById(R.id.erNo_code_list_item_textView);

            mFacultyTextView = (TextView) itemView.
                    findViewById(R.id.faculty_code_list_item_textView);

            mSubjectTextView = (TextView) itemView.
                    findViewById(R.id.subject_code_list_item_textView);

        }

        public void bindAttendance (AttendanceData attendancedata) {
            mAttendanceData = attendancedata;
            mDateTextView.setText(mAttendanceData.getDate());
            mFacultyTextView.setText(mAttendanceData.getFacultyCode());
            mSubjectTextView.setText(mAttendanceData.getSubjectCode());
            mErnoTextView.setText(mAttendanceData.getEnrollmentNumber());

        }
    }


    /*//method used to initialise the adpater of the recyclerView.
    private void updateUI () {
        //AttendanceLab attendanceLab = AttendanceLab.get(getActivity());
        //List<AttendanceData> attendances = attendanceLab.getAttendances(mErno);
        if (mAdapter == null) {
            //mAdapter = new AttendanceAdapter(new BackgroundAttendanceFetcher(getActivity())
                    //.getAttendanceDatas());
            mAttendanceRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setAttendances(mAttendanceDatas);
            mAdapter.notifyDataSetChanged();
        }
    }*/


    //Adapter class for the recyclerView.
    private class AttendanceAdapter extends RecyclerView.Adapter<AttendanceHolder> {
        private List<AttendanceData> mAttendanceDatas;

        public AttendanceAdapter (List<AttendanceData> attendanceDatas) {
            mAttendanceDatas = attendanceDatas;
        }

        @Override
        public AttendanceHolder onCreateViewHolder (ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new AttendanceHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder (AttendanceHolder holder, int position) {
            AttendanceData attendanceData = mAttendanceDatas.get(position);
            holder.bindAttendance(attendanceData);

        }

        @Override
        public int getItemCount () {
            return mAttendanceDatas.size();
        }

        /*public void setAttendances (List<AttendanceData> attendances) {
            mAttendanceDatas = attendances;
        }*/
    }

    public class FetchAttendanceTask extends AsyncTask<String, Void, List<AttendanceData>> {

        private HttpURLConnection mHttpURLConnection;

        public FetchAttendanceTask () {
        }

        @Override
        protected List<AttendanceData> doInBackground (String... params) {
            String type = params[0];

            try {
                SocketAddress sockaddr = new InetSocketAddress("192.168.2.3", 80);
                // Create an unbound socket
                Socket sock = new Socket();

                // This method will block no more than timeoutMs.
                // If the timeout occurs, SocketTimeoutException is thrown.
                int timeoutMs = 2000;   // 2 seconds
                sock.connect(sockaddr, timeoutMs);
            } catch (Exception e) {
                Toast.makeText(getActivity(), "Server is Down", Toast.LENGTH_SHORT).show();
            }

            //It is an login call.
            switch (type) {
                case "sort_erno":
                    try {
                        //Fetch the username and password from the background method call.
                        String enrollmentnumber = params[1];

                        mHttpURLConnection = URLManager.
                                getConnection(URLManager.SORT_ERNO_URL);

                        //Creating the outputStream
                        OutputStream outputStream = mHttpURLConnection.getOutputStream();
                        //Writing in the outputStream.
                        BufferedWriter bufferedWriter = new BufferedWriter(new
                                OutputStreamWriter(outputStream, "UTF-8"));

                        //This is for connecting the variables in the app and in the php file.
                        String postData = URLEncoder.encode("enrollmentnumber", "UTF-8") + "=" +//$_POST["enrollmentnumber"]
                                URLEncoder.encode(enrollmentnumber, "UTF-8");

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
                        return getAttendanceDatas(result);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "sort_subject_code": //It is an register POST call.
                    try {
                        //Fetching the values to be registered.
                        String enrollmentnumber = params[1];
                        String subjectCode = params[2];



                        mHttpURLConnection = URLManager.
                                getConnection(URLManager.SORT_SUBJECT_CODE_URL);

                        OutputStream outputStream = mHttpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new
                                OutputStreamWriter(outputStream, "UTF-8"));

                        String postData = URLEncoder.encode("enrollmentnumber", "UTF-8") + "=" +//$_POST["enrollmentnumber"]
                                URLEncoder.encode(enrollmentnumber, "UTF-8") +
                                "&" +
                                URLEncoder.encode("subject_code", "UTF-8") + "=" +//$_POST["username"]
                                URLEncoder.encode(subjectCode, "UTF-8");

                        bufferedWriter.write(postData);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();

                        InputStream inputStream = mHttpURLConnection.getInputStream();

                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                                inputStream, "iso-8859-1"));

                        String result = "";
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            result += line;
                        }
                        bufferedReader.close();
                        inputStream.close();
                        mHttpURLConnection.disconnect();

                        //Returning the results
                        return getAttendanceDatas(result);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "sort_faculty_code":
                    try {
                        //Fetch the username from the background method call.
                        String enrollmentnumber = params[1];
                        String faculty_code = params[2];


                        mHttpURLConnection = URLManager.
                                getConnection(URLManager.SORT_FACULTY_CODE_URL);

                        //Creating the outputStream
                        OutputStream outputStream = mHttpURLConnection.getOutputStream();
                        //Writing in the outputStream.
                        BufferedWriter bufferedWriter = new BufferedWriter(new
                                OutputStreamWriter(outputStream, "UTF-8"));

                        //This is for connecting the variables in the app and in the php file.
                        String postData = URLEncoder.encode("enrollmentnumber", "UTF-8") + "=" +//$_POST["enrollmentnumber"]
                                URLEncoder.encode(enrollmentnumber, "UTF-8") +
                                "&" +
                                URLEncoder.encode("faculty_code", "UTF-8") + "=" +//$_POST["faculty_code"]
                                URLEncoder.encode(faculty_code, "UTF-8");

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

                        //Returning the results
                        return getAttendanceDatas(result);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case "sort_date":
                    try {
                        //Fetching the values to be registered.
                        String enrollmentnumber = params[1];
                        String date = params[2];


                        mHttpURLConnection = URLManager.
                                getConnection(URLManager.SORT_DATE_URL);

                        OutputStream outputStream = mHttpURLConnection.getOutputStream();
                        BufferedWriter bufferedWriter = new BufferedWriter(new
                                OutputStreamWriter(outputStream, "UTF-8"));

                        String postData = URLEncoder.encode("enrollmentnumber", "UTF-8") + "=" +//$_POST["enrollmentnumber"]
                                URLEncoder.encode(enrollmentnumber, "UTF-8") +
                                "&" +
                                URLEncoder.encode("date", "UTF-8") + "=" +//$_POST["date"]
                                URLEncoder.encode(date, "UTF-8");

                        bufferedWriter.write(postData);
                        bufferedWriter.flush();
                        bufferedWriter.close();
                        outputStream.close();

                        InputStream inputStream = mHttpURLConnection.getInputStream();

                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                                inputStream, "iso-8859-1"));

                        String result = "";
                        String line;
                        while ((line = bufferedReader.readLine()) != null) {
                            result += line;
                        }
                        bufferedReader.close();
                        inputStream.close();
                        mHttpURLConnection.disconnect();

                        //Returning the results
                        return getAttendanceDatas(result);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

            }
            return null;
        }

        @Override
        protected void onPostExecute (List<AttendanceData> items) {
            mAttendanceDatas = items;
            mAttendanceRecyclerView.setAdapter(new AttendanceAdapter(mAttendanceDatas));
        }
    }

    private List<AttendanceData> getAttendanceDatas(String result){
        List<AttendanceData> attendanceDatas = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(result);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                AttendanceData attendanceData = new AttendanceData();
                attendanceData.
                        setEnrollmentNumber(jsonObject.getString("enrollmentnumber"));
                attendanceData.setSubjectCode(jsonObject.getString("subjectcode"));
                attendanceData.setFacultyCode(jsonObject.getString("facultycode"));
                attendanceData.setDate(jsonObject.getString("date"));


                attendanceDatas.add(attendanceData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Returning the results
        return attendanceDatas;
    }



}
