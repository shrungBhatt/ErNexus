package com.example.andorid.ersnexus.userprofile.tabs;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.database.attendance.AttendanceLab;
import com.example.andorid.ersnexus.models.AttendanceData;
import com.example.andorid.ersnexus.userscanattendance.UserScanAttendanceActivity;
import com.example.andorid.ersnexus.util.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

//This class is the attendance tab in userProfileHomeActivity screen.


public class Attendance extends Fragment implements AdapterView.OnItemSelectedListener {

    private Button mScanAttendanceButton;
    private AttendanceAdapter mAdapter;
    private RecyclerView mAttendanceRecyclerView;
    private int mPosition;
    private EditText mSortAttendanceEditText;
    private ImageButton mSortAttendanceButton;
    private String mErno;
    private AttendanceLab mAttendanceLab;
    private List<AttendanceData> mAttendanceDatas;
    private ImageButton mClearButton;

    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_attendance, container, false);


        new FetchAttendanceTask().execute();


        mErno = SharedPreferences.getStoredErno(getActivity());
        mAttendanceLab = AttendanceLab.get(getActivity());

        //Button used to start the scanAttendance activity.
        mScanAttendanceButton = (Button) v.findViewById(R.id.scan_attendance_button);
        mScanAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent i = new Intent(getActivity(), UserScanAttendanceActivity.class);
                startActivityForResult(i, 1);
            }
        });

        mClearButton = (ImageButton)v.findViewById(R.id.clear_button);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                mSortAttendanceEditText.getText().clear();
                //updateUI();
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
        /*mSortAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                switch (mPosition) {
                    case 0:
                        updateUI();
                    case 1:
                        String sortSubject = mSortAttendanceEditText.getText().toString();
                        mAttendanceDatas = mAttendanceLab.getAttendances(mErno, sortSubject);
                        checkAndSetAdapter();
                        break;
                    case 2:
                        String sortFaculty = mSortAttendanceEditText.getText().toString();
                        mAttendanceDatas = mAttendanceLab.getAttendances(mErno, null, sortFaculty);
                        checkAndSetAdapter();
                        break;
                    case 3:
                        String sortDate = mSortAttendanceEditText.getText().toString();
                        mAttendanceDatas = mAttendanceLab.getAttendances(mErno, null, null, sortDate);
                        checkAndSetAdapter();
                        break;
                }

            }
        });*/

        //RecyclerView that displays the attendances after fetching it from the database.
        mAttendanceRecyclerView = (RecyclerView) v.findViewById(R.id.attendance_recyvlerView);
        mAttendanceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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

        public void setAttendances (List<AttendanceData> attendances) {
            mAttendanceDatas = attendances;
        }
    }

    public class FetchAttendanceTask extends AsyncTask<Void,Void,List<AttendanceData>>{

        public FetchAttendanceTask(){
        }

        @Override
        protected List<AttendanceData> doInBackground (Void... params) {
            try {

                boolean exists = false;

                try {
                    SocketAddress sockaddr = new InetSocketAddress("192.168.2.3", 80);
                    // Create an unbound socket
                    Socket sock = new Socket();

                    // This method will block no more than timeoutMs.
                    // If the timeout occurs, SocketTimeoutException is thrown.
                    int timeoutMs = 2000;   // 2 seconds
                    sock.connect(sockaddr, timeoutMs);
                    exists = true;
                }catch(Exception e){
                    Toast.makeText(getActivity(), "Server is Down", Toast.LENGTH_SHORT).show();
                }
                //Fetch the username and password from the background method call.

                List<AttendanceData> attendanceDatas = new ArrayList<>();

                //Creating a URL.
                URL url = new URL("http://192.168.2.3/ersnexus/fetchattendance.php");
                //Connecting to the URL.
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //Setting request method POST.
                httpURLConnection.setRequestMethod("POST");
                //This connection include Input and output interaction.
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);


                //Creating an inputStream to fetch the results.
                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        inputStream, "iso-8859-1"));

                //Getting the results
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                try {
                    JSONArray jsonArray = new JSONArray(result);

                    for(int i = 0; i<jsonArray.length();i++){
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

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<AttendanceData> items){
            mAttendanceDatas = items;
            mAttendanceRecyclerView.setAdapter(new AttendanceAdapter(mAttendanceDatas));
        }
    }


}
