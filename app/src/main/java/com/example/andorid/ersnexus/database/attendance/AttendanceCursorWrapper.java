package com.example.andorid.ersnexus.database.attendance;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.andorid.ersnexus.models.AttendanceData;

import static com.example.andorid.ersnexus.database.attendance.AttendanceDbSchema.*;

//This class is a cursor which is used to fetch the attendance from the attendance database.

public class AttendanceCursorWrapper extends CursorWrapper {

    //Constructor used to call this class in another class as an instance of cursor.
    public AttendanceCursorWrapper (Cursor cursor) {
        super(cursor);
    }


    //This method is used to get the ColumnIndex of various entities from the attendance database.
    public AttendanceData getAttendance () {
        String enrollmentNumber = getString(getColumnIndex(AttendanceTable.Cols.ENROLLMENT_NUMBER));
        String subjectCode = getString(getColumnIndex(AttendanceTable.Cols.SUBJECT_CODE));
        String facultyCode = getString(getColumnIndex(AttendanceTable.Cols.FACULTY_CODE));
        String date = getString(getColumnIndex(AttendanceTable.Cols.DATE));

        return new AttendanceData(subjectCode, facultyCode, date,
                enrollmentNumber);

    }
}
