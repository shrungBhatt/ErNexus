package com.example.andorid.ersnexus.userdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.andorid.ersnexus.database.UserBaseHelper;

import com.example.andorid.ersnexus.database.UserDbSchema.UserTable;


public class UserLab {
    private static UserLab sUserLab;
    private SQLiteDatabase mDatabase;
    private UserBaseHelper mHelper;
    private Context mContext;


    public static UserLab get (Context context) {
        if (sUserLab == null) {
            sUserLab = new UserLab(context);
        }
        return sUserLab;
    }

    private UserLab (Context context) {

        mContext = context.getApplicationContext();
        mHelper = new UserBaseHelper(mContext);
        mDatabase = new UserBaseHelper(mContext).getWritableDatabase();

    }

    public void addUser (UserData userData) {

        ContentValues values = getContentValues(userData);
        mDatabase.insert(UserTable.NAME, null, values);



    }

    public static ContentValues getContentValues (UserData userData) {
        ContentValues values = new ContentValues();

        values.put(UserTable.Cols.UUID, userData.getId().toString());
        values.put(UserTable.Cols.ENROLLMENT_NUMBER, userData.getEnrollmentNumber());
        values.put(UserTable.Cols.USER_NAME, userData.getUserName());
        values.put(UserTable.Cols.FULL_NAME, userData.getFullName());
        values.put(UserTable.Cols.PASSWORD, userData.getPassword());
        values.put(UserTable.Cols.EMAIL, userData.getEmail());
        values.put(UserTable.Cols.DATE_OF_BIRTH, userData.getDob().toString());

        return values;
    }



}
