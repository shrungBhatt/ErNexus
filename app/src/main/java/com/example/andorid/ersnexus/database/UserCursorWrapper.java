package com.example.andorid.ersnexus.database;

import android.database.Cursor;
import android.database.CursorWrapper;


public class UserCursorWrapper extends CursorWrapper {
    public UserCursorWrapper (Cursor cursor) {
        super(cursor);
    }

}
