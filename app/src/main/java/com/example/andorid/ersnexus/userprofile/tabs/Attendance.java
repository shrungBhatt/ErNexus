package com.example.andorid.ersnexus.userprofile.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.userscanattendance.UserScanAttendanceActivity;


public class Attendance extends Fragment {

    private Button mScanAttendanceButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_attendance,container,false);

        mScanAttendanceButton = (Button)v.findViewById(R.id.scan_attendance_button);
        mScanAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent i = new Intent(getActivity(), UserScanAttendanceActivity.class);
                startActivity(i);
            }
        });

        return v;
    }
}
