package com.example.andorid.ersnexus.database.attendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.andorid.ersnexus.database.attendance.AttendanceDbSchema.AttendanceTable;
import com.example.andorid.ersnexus.models.AttendanceData;


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

    public void addAttendance(AttendanceData attendanceData){
        ContentValues values = getContentValues(attendanceData);
        mDatabase.insert(AttendanceTable.NAME, null, values);
    }

    private static ContentValues getContentValues(AttendanceData attendanceData){
        ContentValues values = new ContentValues();

        values.put(AttendanceTable.Cols.ENROLLMENT_NUMBER, attendanceData.getEnrollmentNumber());
        values.put(AttendanceTable.Cols.SUBJECT_CODE, attendanceData.getSubjectCode());
        values.put(AttendanceTable.Cols.FACULTY_CODE, attendanceData.getSubjectCode());
        values.put(AttendanceTable.Cols.DATE, attendanceData.getDate());

        return values;
    }




}
