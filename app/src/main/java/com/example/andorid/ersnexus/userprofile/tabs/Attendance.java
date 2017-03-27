package com.example.andorid.ersnexus.userprofile.tabs;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.database.attendance.AttendanceLab;
import com.example.andorid.ersnexus.models.AttendanceData;
import com.example.andorid.ersnexus.userscanattendance.UserScanAttendanceActivity;
import com.example.andorid.ersnexus.util.SharedPreferences;

import java.util.List;

//This class is the attendance tab in userProfileHomeActivity screen.


public class Attendance extends Fragment {

    private Button mScanAttendanceButton;
    private AttendanceAdapter mAdapter;
    private RecyclerView mAttendanceRecyclerView;

    @Override
    public View onCreateView (LayoutInflater inflater, @Nullable ViewGroup container,
                              @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_attendance, container, false);

        //Button used to start the scanAttendance activity.
        mScanAttendanceButton = (Button) v.findViewById(R.id.scan_attendance_button);
        mScanAttendanceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {
                Intent i = new Intent(getActivity(), UserScanAttendanceActivity.class);
                startActivityForResult(i, 1);
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

        //RecyclerView that displays the attendances after fetching it from the database.
        mAttendanceRecyclerView = (RecyclerView) v.findViewById(R.id.attendance_recyvlerView);
        mAttendanceRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();

        return v;
    }

    @Override
    public void onResume () {
        super.onResume();
        updateUI();// To update the data in recyclerView after editing the data in attendance tab.
    }


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


    //method used to initialise the adpater of the recyclerView.
    private void updateUI () {
        AttendanceLab attendanceLab = AttendanceLab.get(getActivity());
        String erNo = SharedPreferences.getStoredErno(getActivity());
        List<AttendanceData> attendances = attendanceLab.getAttendances(erNo);
        if (mAdapter == null) {
            mAdapter = new AttendanceAdapter(attendances);
            mAttendanceRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setAttendances(attendances);
        }

    }


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
}
