package com.example.andorid.ersnexus.userprofile.tabs.attendance.userscanattendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.userprofile.homeactivity.UserProfileHomeActivity;
import com.example.andorid.ersnexus.util.SharedPreferencesData;
import com.example.andorid.ersnexus.webservices.URLManager;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//This class is used for scanning the Qr code and submitting the attendance to the database.

public class UserScanAttendanceFragment extends Fragment {

    private static final String TAG = "UserScanFragment";

    private static final String KEY_SUBJECT_CODE = "subjectCode";
    private static final String KEY_FACULTY_CODE = "facultyCode";
    private static final String KEY_DATE = "date";
    private Button mScanButton, mSubmitButton;
    private TextView mErno, mSubjectCode, mFacultyCode, mDate;
    private String erNo, subjectCode, facultyCode;
    private Date date;
    //qr code scanner object
    private IntentIntegrator qrScan;

    DateFormat formatDate = DateFormat.getDateInstance(3);

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //If screen is rotated the textViews will still have the values.
        if (savedInstanceState != null) {
            subjectCode = savedInstanceState.getString(KEY_SUBJECT_CODE, null);
            mSubjectCode.setText(subjectCode);

            facultyCode = savedInstanceState.getString(KEY_FACULTY_CODE, null);
            mFacultyCode.setText(facultyCode);

            mDate.setText(date.getDate());
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_scan_attendance, container, false);


        date = new Date();


        mErno = (TextView) v.findViewById(R.id.enrollment_number_textView);
        mErno.setText(SharedPreferencesData.getStoredErno(getActivity()));
        erNo = mErno.getText().toString();


        mSubjectCode = (TextView) v.findViewById(R.id.subject_code_textView);

        mFacultyCode = (TextView) v.findViewById(R.id.faculty_code_textView);

        mDate = (TextView) v.findViewById(R.id.date_textView);
        mDate.setText(formatDate.format(date));

        mSubmitButton = (Button) v.findViewById(R.id.attendance_submit_button);
        mSubmitButton.setOnClickListener(v1 -> {

            if (mSubjectCode.length() == 0 && mFacultyCode.length() == 0) {
                Toast.makeText(getActivity(), "Scan Attendance First", Toast.LENGTH_SHORT).
                        show();
            } else {

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        URLManager.REGISTER_ATTENDANCE_URL,
                        response -> {

                            if (response.trim().equals("Insert Successful")) {
                                Toast.makeText(getActivity(),
                                        "Submitted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getActivity(),
                                        UserProfileHomeActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }else{
                                Toast.makeText(getActivity(),"Submit attendance failure", Toast.LENGTH_SHORT).show();
                            }

                        },
                        error ->
                                Log.e("VolleyError!",error.getMessage())) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("enrollmentnumber", erNo);
                        params.put("subject_code", subjectCode);
                        params.put("faculty_code", facultyCode);
                        params.put("date", mDate.getText().toString());

                        return params;
                    }


                };

                RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);
            }
        });

        mScanButton = (Button) v.findViewById(R.id.buttonScan);
        mScanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                qrScan.setBeepEnabled(false);
                qrScan.initiateScan();
            }
        });

        qrScan = new IntentIntegrator(getActivity());


        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    mSubjectCode.setText(obj.getString("subject"));
                    subjectCode = mSubjectCode.getText().toString();

                    mFacultyCode.setText(obj.getString("faculty"));
                    facultyCode = mFacultyCode.getText().toString();

                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(getActivity(), result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_SUBJECT_CODE, subjectCode);
        outState.putString(KEY_FACULTY_CODE, facultyCode);
        outState.putString(KEY_DATE, date.toString());
    }

}

