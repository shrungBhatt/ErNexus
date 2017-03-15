package com.example.andorid.ersnexus.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.andorid.ersnexus.database.UserDbSchema.UserTable;


public class UserBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "userBase.db";


    private SQLiteDatabase mDatabase;
    private Context context;

    public UserBaseHelper (Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }


    @Override
    public void onCreate (SQLiteDatabase db) {
        db.execSQL("create table " + UserTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                UserTable.Cols.UUID + ", " +
                UserTable.Cols.ENROLLMENT_NUMBER + ", " +
                UserTable.Cols.USER_NAME + ", " +
                UserTable.Cols.FULL_NAME + ", " +
                UserTable.Cols.PASSWORD + ", " +
                UserTable.Cols.EMAIL + "," +
                UserTable.Cols.DATE_OF_BIRTH +
                ")"
        );

    }


    public String fetchUser (String userName) {

        mDatabase = this.getReadableDatabase();


        Cursor c = queryCrimes(new String[]{UserTable.Cols.USER_NAME, UserTable.Cols.PASSWORD},
                UserTable.Cols.USER_NAME + " = ?", new String[]{userName});


        String uName,pass;
        pass = "not found";
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                uName = c.getString(c.getColumnIndex(UserTable.Cols.USER_NAME));
                if(uName.equals(userName)){
                    pass = c.getString(c.getColumnIndex(UserTable.Cols.PASSWORD));
                }
                c.moveToNext();
            }
        }finally{
            c.close();
        }
        return pass;
    }

    public String fetchErNo (String userName) {

        mDatabase = this.getReadableDatabase();


        Cursor c = queryCrimes(new String[]{UserTable.Cols.USER_NAME, UserTable.Cols.ENROLLMENT_NUMBER},
                UserTable.Cols.USER_NAME + " = ?", new String[]{userName});


        String uname,erNo;
        erNo = "not found";
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                uname = c.getString(c.getColumnIndex(UserTable.Cols.USER_NAME));
                if(uname.equals(userName)){
                    erNo = c.getString(c.getColumnIndex(UserTable.Cols.ENROLLMENT_NUMBER));
                }
                c.moveToNext();
            }
        }finally{
            c.close();
        }
        return erNo;
    }
    public String fetchFullName (String userName) {

        mDatabase = this.getReadableDatabase();


        Cursor c = queryCrimes(new String[]{UserTable.Cols.USER_NAME, UserTable.Cols.FULL_NAME},
                UserTable.Cols.USER_NAME + " = ?", new String[]{userName});


        String uname,fullName;
        fullName = "not found";
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                uname = c.getString(c.getColumnIndex(UserTable.Cols.USER_NAME));
                if(uname.equals(userName)){
                    fullName = c.getString(c.getColumnIndex(UserTable.Cols.FULL_NAME));
                }
                c.moveToNext();
            }
        }finally{
            c.close();
        }
        return fullName;
    }


    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + UserTable.NAME;
        db.execSQL(query);

        this.onCreate(db);

    }

    private Cursor queryCrimes (String[] projection, String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                UserTable.NAME,
                projection,//Columns - null select columns
                whereClause,
                whereArgs,
                null,//groupBy
                null,//having
                null //orderBy
        );
        return cursor;
    }


}
