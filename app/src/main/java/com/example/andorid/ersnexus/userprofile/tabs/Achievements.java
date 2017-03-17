package com.example.andorid.ersnexus.userprofile.tabs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andorid.ersnexus.R;

/**
 * Created by Bhatt on 17-03-2017.
 */

public class Achievements extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.achievements,container,false);
        return v;
    }
}
