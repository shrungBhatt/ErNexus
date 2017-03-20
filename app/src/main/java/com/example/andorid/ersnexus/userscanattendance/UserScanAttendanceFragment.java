package com.example.andorid.ersnexus.userscanattendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.database.attendance.AttendanceLab;
import com.example.andorid.ersnexus.models.AttendanceData;
import com.example.andorid.ersnexus.util.SharedPreferences;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;


public class UserScanAttendanceFragment extends Fragment {

    private static final String KEY_SUBJECT_CODE = "subjectCode";
    private static final String KEY_FACULTY_CODE = "facultyCode";
    private static final String KEY_DATE = "date";
    private Button buttonScan, mSubmitButton;
    private TextView mErno, mSubjectCode, mFacultyCode, mDate;
    private String erNo, subjectCode, facultyCode, date;
    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            subjectCode = savedInstanceState.getString(KEY_SUBJECT_CODE,null);
            facultyCode = savedInstanceState.getString(KEY_FACULTY_CODE,null);
            date = savedInstanceState.getString(date,null);

            mSubjectCode.setText(subjectCode);
            mFacultyCode.setText(facultyCode);
            mDate.setText(date);
        }
    }


    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_user_scan_attendance, container, false);


        buttonScan = (Button) v.findViewById(R.id.buttonScan);
        mErno = (TextView) v.findViewById(R.id.enrollment_number_textView);
        mSubjectCode = (TextView) v.findViewById(R.id.subject_code_textView);
        mFacultyCode = (TextView) v.findViewById(R.id.faculty_code_textView);
        mDate = (TextView) v.findViewById(R.id.date_textView);

        erNo = mErno.getText().toString();


        mErno.setText(SharedPreferences.getStoredErno(getActivity()));

        mSubmitButton = (Button) v.findViewById(R.id.attendance_submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                AttendanceData attendanceData = new AttendanceData(subjectCode, facultyCode,
                        date, erNo);


                attendanceData.setEnrollmentNumber(SharedPreferences.getStoredErno(getActivity()));
                attendanceData.getEnrollmentNumber();
                attendanceData.getSubjectCode();
                attendanceData.getFacultyCode();
                AttendanceLab.get(getActivity()).addAttendance(attendanceData);
            }
        });

        qrScan = new IntentIntegrator(getActivity());

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                qrScan.setBeepEnabled(false);
                qrScan.initiateScan();
            }
        });
        return v;
    }

    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
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


                    mDate.setText(obj.getString("date"));
                    date = mDate.getText().toString();

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
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString(KEY_SUBJECT_CODE,subjectCode);
        outState.putString(KEY_FACULTY_CODE,facultyCode);
        outState.putString(KEY_DATE,date);
    }
}

