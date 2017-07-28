package com.example.andorid.ersnexus.userprofile.tabs;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
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
import com.example.andorid.ersnexus.models.AttendanceData;
import com.example.andorid.ersnexus.userscanattendance.UserScanAttendanceActivity;
import com.example.andorid.ersnexus.util.SharedPreferencesData;
import com.example.andorid.ersnexus.util.SimpleDividerItemDecoration;
import com.example.andorid.ersnexus.webservices.URLManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_attendance, container, false);

        mErno = SharedPreferencesData.getStoredErno(getActivity());

        if (isNetworkAvailableAndConnected()) {
            sortAttendance(URLManager.SORT_ERNO_URL, null, null);
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
        }

        //mAttendanceLab = AttendanceLab.get(getActivity());

        //Button used to start the scanAttendance activity.
        mScanAttendanceButton = (Button) v.findViewById(R.id.scan_attendance_button);

        //Giving the timer functionality to the scanAttendance Button
        Long currentTs = System.currentTimeMillis() / 1000;
        Long previousTs = SharedPreferencesData.getCurrentTimeStamp(getActivity());
        if (currentTs - previousTs >= 20) {
            mScanAttendanceButton.setEnabled(true);
        } else {
            mScanAttendanceButton.setEnabled(false);
            mScanAttendanceButton.setBackgroundColor(getResources().
                    getColor(android.R.color.holo_red_dark));
            mScanAttendanceButton.setText(R.string.attendance_cooldown_text);
        }
        mScanAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Long currentTs = System.currentTimeMillis() / 1000;
                SharedPreferencesData.setCurrntTimeStamp(getActivity(), currentTs);

                Intent i = new Intent(getActivity(), UserScanAttendanceActivity.class);
                startActivityForResult(i, 1);
            }
        });

        mSortAttendanceEditText = (EditText) v.findViewById(R.id.sort_attendance_by_editText);
        //Button used to clear the sortBy editText filed and repopulate the recyclerView.
        mClearButton = (ImageButton) v.findViewById(R.id.clear_button);
        mClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSortAttendanceEditText.getText().clear();
                sortAttendance(URLManager.SORT_ERNO_URL, null, null);
                //updateUI();
            }
        });

        //The Swipe and refresh functionality.
        mSwipeRefresh = (PullRefreshLayout) v.findViewById(R.id.swipe_refresh_attendance_tab);
        mSwipeRefresh.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

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

        //To search the database using the sort type and the string value.
        mSortAttendanceButton = (ImageButton) v.findViewById(R.id.sort_attendance_by_button);
        mSortAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mPosition) {
                    case 0:
                        //sort using erno
                        sortAttendance(URLManager.SORT_ERNO_URL, null, null);
                        break;
                    case 1:
                        //sort using subject_code
                        String sortSubject = mSortAttendanceEditText.getText().toString();
                        sortAttendance(URLManager.SORT_SUBJECT_CODE_URL, "subject_code", sortSubject);
                        break;
                    case 2:
                        //sort using faculty_code
                        String sortFaculty = mSortAttendanceEditText.getText().toString();
                        sortAttendance(URLManager.SORT_FACULTY_CODE_URL, "faculty_code", sortFaculty);
                        break;
                    case 3:
                        //sort using date.
                        String sortDate = mSortAttendanceEditText.getText().toString();
                        sortAttendance(URLManager.SORT_DATE_URL, "date", sortDate);
                        break;
                }

            }
        });

        //RecyclerView that displays the attendances after fetching it from the database.
        mAttendanceRecyclerView = (RecyclerView) v.findViewById(R.id.attendance_recyvlerView);
        mAttendanceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //This to seprate the the recyclerView and swipeToRefresh the scroll up function.
        mAttendanceRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = ((LinearLayoutManager) recyclerView
                        .getLayoutManager());
                boolean enabled = manager.findFirstCompletelyVisibleItemPosition() == 0;
                mSwipeRefresh.setEnabled(enabled);
            }
        });
        mAttendanceRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));


        return v;
    }

    //This method is used for the spinner to get the position of the item selected.
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mPosition = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onResume() {
        super.onResume();

    }


    //ViewHolder class of the recyclerView.
    private class AttendanceHolder extends RecyclerView.ViewHolder {

        private TextView mDateTextView, mFacultyTextView, mSubjectTextView;
        private AttendanceData mAttendanceData;

        public AttendanceHolder(LayoutInflater layoutInflater, ViewGroup container) {
            super(layoutInflater.inflate(R.layout.list_item_attendance_recycler_view, container,
                    false));

            mDateTextView = (TextView) itemView.findViewById(R.id.date_list_item_textView);


            mFacultyTextView = (TextView) itemView.
                    findViewById(R.id.faculty_code_list_item_textView);

            mSubjectTextView = (TextView) itemView.
                    findViewById(R.id.subject_code_list_item_textView);

        }

        public void bindAttendance(AttendanceData attendancedata) {
            mAttendanceData = attendancedata;
            mDateTextView.setText(mAttendanceData.getDate());
            mFacultyTextView.setText(mAttendanceData.getFacultyCode());
            mSubjectTextView.setText(mAttendanceData.getSubjectCode());

        }
    }


    //Adapter class for the recyclerView.
    private class AttendanceAdapter extends RecyclerView.Adapter<AttendanceHolder> {
        private List<AttendanceData> mAttendanceDatas;

        public AttendanceAdapter(List<AttendanceData> attendanceDatas) {
            mAttendanceDatas = attendanceDatas;
        }

        @Override
        public AttendanceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            return new AttendanceHolder(inflater, parent);
        }

        @Override
        public void onBindViewHolder(AttendanceHolder holder, int position) {
            AttendanceData attendanceData = mAttendanceDatas.get(position);
            holder.bindAttendance(attendanceData);

        }

        @Override
        public int getItemCount() {
            return mAttendanceDatas.size();
        }
    }


    //Connecting to the server database using volley.
    private void sortAttendance(String url, final String key, final String searchText) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mAttendanceDatas = getAttendanceDatas(response);
                        setUpRecyclerViewAdapter();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Attendance", error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("enrollmentnumber", mErno);
                if (key != null && searchText != null) {
                    params.put(key, searchText);
                }
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }

    //This method is used to seprate the JSONArray we got in the result of background task.
    private List<AttendanceData> getAttendanceDatas(String result) {
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


    //Method used to check that whether phone is connected to the internet or not
    private boolean isNetworkAvailableAndConnected() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().
                getSystemService(Context.CONNECTIVITY_SERVICE);

        boolean isNetworkAvailable = cm.getActiveNetworkInfo() != null;

        return isNetworkAvailable &&
                cm.getActiveNetworkInfo().isConnected();
    }

    //Method used to set the RecyclerView's adapter
    private void setUpRecyclerViewAdapter() {
        if (mAttendanceDatas != null) {
            mAttendanceRecyclerView.
                    setAdapter(new AttendanceAdapter(mAttendanceDatas));
        }
    }


}
