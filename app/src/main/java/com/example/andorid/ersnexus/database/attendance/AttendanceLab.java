package com.example.andorid.ersnexus.database.attendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.andorid.ersnexus.database.attendance.AttendanceDbSchema.AttendanceTable;
import com.example.andorid.ersnexus.models.Attendance;


public class AttendanceLab {
    private static AttendanceLab sAttendanceLab;

    private SQLiteDatabase mDatabase;
    private Context mContext;


    public static AttendanceLab get(Context context){
        if (sAttendanceLab == null) {
            sAttendanceLab = new AttendanceLab(context);
        }
        return sAttendanceLab;
    }

    private AttendanceLab (Context context) {

        mContext = context.getApplicationContext();
        mDatabase = new AttendanceBaseHelper(mContext).getWritableDatabase();

    }

    public void addAttendance(Attendance attendance){
        ContentValues values = getContentValues(attendance);
        mDatabase.insert(AttendanceTable.NAME, null, values);
    }

    private static ContentValues getContentValues(Attendance attendance){
        ContentValues values = new ContentValues();

        values.put(AttendanceTable.Cols.ENROLLMENT_NUMBER,attendance.getEnrollmentNumber());
        values.put(AttendanceTable.Cols.SUBJECT_CODE,attendance.getSubjectCode());
        values.put(AttendanceTable.Cols.FACULTY_CODE,attendance.getSubjectCode());
        values.put(AttendanceTable.Cols.DATE,attendance.getDate().toString());
        values.put(AttendanceTable.Cols.TIME,attendance.getTime().toString());

        return values;
    }




}
