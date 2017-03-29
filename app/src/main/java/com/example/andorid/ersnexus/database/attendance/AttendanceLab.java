package com.example.andorid.ersnexus.database.attendance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.andorid.ersnexus.database.attendance.AttendanceDbSchema.AttendanceTable;
import com.example.andorid.ersnexus.models.AttendanceData;

import java.util.ArrayList;
import java.util.List;

//This is a singleton class and used to fetch and store the values in attendance database.
//And also used to query out the attendances from the attendance database.


public class AttendanceLab {
    private static AttendanceLab sAttendanceLab;

    private SQLiteDatabase mDatabase;
    private Context mContext;


    public static AttendanceLab get (Context context) {
        if (sAttendanceLab == null) {
            sAttendanceLab = new AttendanceLab(context);
        }
        return sAttendanceLab;
    }

    private AttendanceLab (Context context) {

        mContext = context.getApplicationContext();
        mDatabase = new AttendanceBaseHelper(mContext).getWritableDatabase();

    }

    //Method used to fetch the values and insert it in the database
    public void addAttendance (AttendanceData attendanceData) {
        ContentValues values = getContentValues(attendanceData);
        mDatabase.insert(AttendanceTable.NAME, null, values);
    }

    //method used to store the values in the attendance database.
    private static ContentValues getContentValues (AttendanceData attendanceData) {
        ContentValues values = new ContentValues();

        values.put(AttendanceTable.Cols.ENROLLMENT_NUMBER, attendanceData.getEnrollmentNumber());
        values.put(AttendanceTable.Cols.SUBJECT_CODE, attendanceData.getSubjectCode());
        values.put(AttendanceTable.Cols.FACULTY_CODE, attendanceData.getSubjectCode());
        values.put(AttendanceTable.Cols.DATE, attendanceData.getDate());

        return values;
    }

    //This method is used to create an arrayList which is to be given to the recyclerView in
    //attendance tab.
    //It also queries the attendances from the attendance database.
    public List<AttendanceData> getAttendances (String erNo) {

        List<AttendanceData> attendanceDatas = new ArrayList<>();

        AttendanceCursorWrapper cursor =
                queryAttendance(AttendanceTable.Cols.ENROLLMENT_NUMBER + " = ?",new String[]{erNo});

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                attendanceDatas.add(cursor.getAttendance());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return attendanceDatas;
    }

    public List<AttendanceData> getAttendances (String erNo,String subjectCode) {

        List<AttendanceData> attendanceDatas = new ArrayList<>();

        AttendanceCursorWrapper cursor =
                queryAttendance(AttendanceTable.Cols.ENROLLMENT_NUMBER + " = ?" + " AND " +
                        AttendanceTable.Cols.SUBJECT_CODE + " = ?",new String[]{erNo,subjectCode});

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                attendanceDatas.add(cursor.getAttendance());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return attendanceDatas;
    }

    public List<AttendanceData> getAttendances (String erNo,String subjectCode,String facultyCode) {

        List<AttendanceData> attendanceDatas = new ArrayList<>();

        AttendanceCursorWrapper cursor =
                queryAttendance(AttendanceTable.Cols.ENROLLMENT_NUMBER + " = ?" + " AND " +
                        AttendanceTable.Cols.FACULTY_CODE + " = ?",new String[]{erNo,facultyCode});

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                attendanceDatas.add(cursor.getAttendance());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return attendanceDatas;
    }

    public List<AttendanceData> getAttendances (String erNo,String subjectCode,String facultyCode,
                                                String date) {

        List<AttendanceData> attendanceDatas = new ArrayList<>();

        AttendanceCursorWrapper cursor =
                queryAttendance(AttendanceTable.Cols.ENROLLMENT_NUMBER + " = ?" + " AND " +
                        AttendanceTable.Cols.DATE + " = ?",new String[]{erNo,date});

        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                attendanceDatas.add(cursor.getAttendance());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return attendanceDatas;
    }


    //Method used to make queries.
    private AttendanceCursorWrapper queryAttendance (String selection, String[] selectionArgs) {
        Cursor cursor = mDatabase.query(
                AttendanceTable.NAME,
                null,//Columns - null select columns
                selection,
                selectionArgs,
                null,//groupBy
                null,//having
                null //orderBy
        );
        return new AttendanceCursorWrapper(cursor);
    }


}
