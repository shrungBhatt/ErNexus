package com.example.andorid.ersnexus.database.attendance;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.andorid.ersnexus.database.attendance.AttendanceDbSchema.AttendanceTable;
//Just testing
//This class is used to create the database table and upgrade it if there is some alteration.

public class AttendanceBaseHelper extends SQLiteOpenHelper{

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "attendance.db";

    //variable to call the write and readable methods of SQLiteDatabase.
    private Context context;

    //Constructor used to get the instance of this class in other classes to get the use of
    //readable and writable tasks carried out.
    public AttendanceBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }


    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL("create table " + AttendanceTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                AttendanceTable.Cols.ENROLLMENT_NUMBER + ", " +
                AttendanceTable.Cols.SUBJECT_CODE + ", " +
                AttendanceTable.Cols.FACULTY_CODE + ", " +
                AttendanceTable.Cols.DATE + ", " +
                AttendanceTable.Cols.TIME +
                ")"
        );

    }

    //upgrade method is used when there is some alteration in the pre-exisiting database.
    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
