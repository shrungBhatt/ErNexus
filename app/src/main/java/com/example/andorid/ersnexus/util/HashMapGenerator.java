package com.example.andorid.ersnexus.util;

import android.util.SparseArray;


/**
 * Created by jigsaw on 17/3/18.
 */

public class HashMapGenerator {

    private static SparseArray<String> sHashMap;

    static{
        sHashMap = new SparseArray<>();
        fillHashMap();
    }

    private static void fillHashMap(){

        sHashMap.put(0,"Pending");
        sHashMap.put(2,"Rejected");
        sHashMap.put(1,"Confirmed");

    }

    public static String getStatusFromHashMap(int statusId){
        return sHashMap.get(statusId);
    }

}
