package com.example.andorid.ersnexus.util;

import android.content.Context;
import android.preference.PreferenceManager;



public class SharedPreferencesData {
    private static final String PREF_ERNO = "enrollmentNumber";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_LOGIN_STATE = "login state";
    private static final String PREF_CURRENT_TIME_STAMP = "current timeStamp";

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

    public static String getStoredUsername (Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_USERNAME,null);
    }

    public static void setStoredUsername (Context context, String status){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_USERNAME,status)
                .apply();
    }

    public static Boolean getStoredLoginStatus (Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_LOGIN_STATE,false);
    }

    public static void setStoredLoginStatus (Context context, Boolean status){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_LOGIN_STATE,status)
                .apply();
    }

    public static Long getCurrentTimeStamp(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getLong(PREF_CURRENT_TIME_STAMP,0);
    }

    public static void setCurrntTimeStamp (Context context, Long currentTs){
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putLong(PREF_CURRENT_TIME_STAMP,currentTs)
                .apply();
    }

}
