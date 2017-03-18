package com.example.andorid.ersnexus.util;

import android.content.Context;
import android.preference.PreferenceManager;



public class SharedPreferences {
    private static final String PREF_ERNO = "enrollmentNumber";

    public static String getStoredErno(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_ERNO,null);
    }

    public static void setStoredErno(Context context,String erno){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_ERNO,erno)
                .apply();
    }

}
