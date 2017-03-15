package com.example.andorid.ersnexus.util;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;



public class Message extends AppCompatActivity {

    public static void message(String string, Context context){
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }
}
