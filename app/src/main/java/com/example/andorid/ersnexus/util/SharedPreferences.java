package com.example.andorid.ersnexus.util;

import android.content.Context;
import android.preference.PreferenceManager;



public class SharedPreferences {
    private static final String PREF_ERNO = "enrollmentNumber";
    private static final String PREF_LOGIN_RESULT = "login result";

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

    public static String getStoredResultOfLogin(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_LOGIN_RESULT,null);
    }

    public static void setStoredResultOfLogin(Context context,String status){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_LOGIN_RESULT,status)
                .apply();
    }

}
