package com.example.andorid.ersnexus.database.userdata;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.andorid.ersnexus.database.userdata.UserDbSchema.UserTable;

//Class that creates the database of user and contains queries to fetch data from the database.

public class UserBaseHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "userBase.db";


    //variable to call the write and readable methods of SQLiteDatabase.
    private SQLiteDatabase mDatabase;
    private Context context;

    //Constructor used to get the instance of this class in other classes to get the use of
    //readable and writable tasks carried out.
    public UserBaseHelper (Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }


    //Method used to create the structure of the database.
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


    //Method used to fetch the user password from the user database.
    public String fetchUserPass (String userName) {

        mDatabase = this.getReadableDatabase();


        Cursor c = queryUsers(new String[]{UserTable.Cols.USER_NAME, UserTable.Cols.PASSWORD},
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

    //Method used to fetch the user enrollment number from the user database.
    public String fetchErNo (String userName) {

        mDatabase = this.getReadableDatabase();


        Cursor c = queryUsers(new String[]{UserTable.Cols.USER_NAME, UserTable.Cols.ENROLLMENT_NUMBER},
                UserTable.Cols.USER_NAME + " = ?", new String[]{userName});


        String uName,erNo;
        erNo = "not found";
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                uName = c.getString(c.getColumnIndex(UserTable.Cols.USER_NAME));
                if(uName.equals(userName)){
                    erNo = c.getString(c.getColumnIndex(UserTable.Cols.ENROLLMENT_NUMBER));
                }
                c.moveToNext();
            }
        }finally{
            c.close();
        }
        return erNo;
    }

    //Method used to fetch the user full name from the user database.
    public String fetchFullName (String userName) {

        mDatabase = this.getReadableDatabase();


        Cursor c = queryUsers(new String[]{UserTable.Cols.USER_NAME, UserTable.Cols.FULL_NAME},
                UserTable.Cols.USER_NAME + " = ?", new String[]{userName});


        String uName,fullName;
        fullName = "not found";
        try {
            c.moveToFirst();
            while (!c.isAfterLast()) {
                uName = c.getString(c.getColumnIndex(UserTable.Cols.USER_NAME));
                if(uName.equals(userName)){
                    fullName = c.getString(c.getColumnIndex(UserTable.Cols.FULL_NAME));
                }
                c.moveToNext();
            }
        }finally{
            c.close();
        }
        return fullName;
    }


    //This method is called if there is any change in the database and there
    // is a pre existing database
    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + UserTable.NAME;
        db.execSQL(query);

        this.onCreate(db);

    }

    //Method used to create a query to ask the database.
    private Cursor queryUsers (String[] projection, String whereClause, String[] whereArgs) {
        return mDatabase.query(
                UserTable.NAME,
                projection,//Columns - null select columns
                whereClause,
                whereArgs,
                null,//groupBy
                null,//having
                null //orderBy
        );
    }


}
