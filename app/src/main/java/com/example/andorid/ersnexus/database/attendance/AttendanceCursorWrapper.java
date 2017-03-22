package com.example.andorid.ersnexus.database.attendance;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.andorid.ersnexus.models.AttendanceData;

import static com.example.andorid.ersnexus.database.attendance.AttendanceDbSchema.*;



public class AttendanceCursorWrapper extends CursorWrapper {

    public AttendanceCursorWrapper (Cursor cursor) {
        super(cursor);
    }


    public AttendanceData getAttendance () {
        String enrollmentNumber = getString(getColumnIndex(AttendanceTable.Cols.ENROLLMENT_NUMBER));
        String subjectCode = getString(getColumnIndex(AttendanceTable.Cols.SUBJECT_CODE));
        String facultyCode = getString(getColumnIndex(AttendanceTable.Cols.FACULTY_CODE));
        String date = getString(getColumnIndex(AttendanceTable.Cols.DATE));

        return new AttendanceData(subjectCode, facultyCode, date,
                enrollmentNumber);

    }
}
