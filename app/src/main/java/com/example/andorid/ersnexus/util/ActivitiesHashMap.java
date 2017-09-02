package com.example.andorid.ersnexus.util;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Shrung on 28-Aug-17.
 */


//This class is used to generate the hashMap used to fetch the corresponding code of activity to
//send request to the database.
public class ActivitiesHashMap {
    public static ConcurrentHashMap<String,Integer> mConcurrentHashMap;
    public static ConcurrentHashMap<String,String> mActivityLevelMap;

    public ActivitiesHashMap(){

    }

    public static ConcurrentHashMap<String,Integer> generateActivityHashMap(){

        mConcurrentHashMap = new ConcurrentHashMap<>();
        mConcurrentHashMap.put("Village Visit",1);
        mConcurrentHashMap.put("Learning Engineering",2);
        mConcurrentHashMap.put("History of Science and Technology",3);
        mConcurrentHashMap.put("Life Skills",4);
        mConcurrentHashMap.put("Group Discussion",5);
        mConcurrentHashMap.put("Technical Quiz",6);
        mConcurrentHashMap.put("Aptitude/Reasoning",7);
        mConcurrentHashMap.put("Tech-Fest",8);
        mConcurrentHashMap.put("Workshop",9);
        mConcurrentHashMap.put("Short term training program(STTP)",10);
        mConcurrentHashMap.put("Massive open online courses(MOOC)",11);
        mConcurrentHashMap.put("Student Skill development",12);
        mConcurrentHashMap.put("Paper Presentation",13);
        mConcurrentHashMap.put("Poster",14);
        mConcurrentHashMap.put("Training/Internship/Professional Certification",15);
        mConcurrentHashMap.put("Industrial/Exhibition Visit",16);
        mConcurrentHashMap.put("Consultancy Projects",17);
        mConcurrentHashMap.put("Sports",18);
        mConcurrentHashMap.put("Cultural",19);
        mConcurrentHashMap.put("Community Service and Allied Activities (Two Day)",20);
        mConcurrentHashMap.put("Community Service and Allied Activities (One week)",21);
        mConcurrentHashMap.put("Community Service and Allied Activities (One month)",22);
        mConcurrentHashMap.put("Community Service and Allied Activities (One semester)",23);
        mConcurrentHashMap.put("Community Service and Allied Activities (One academic year)",24);
        mConcurrentHashMap.put("NSS/NCC (One year Completion)",25);
        mConcurrentHashMap.put("High Customer Review for Creative Products",26);
        mConcurrentHashMap.put("Awards/Recognitions for Products",27);
        mConcurrentHashMap.put("Legally Registered a Start-up Company",28);
        mConcurrentHashMap.put("Filed a patent",29);
        mConcurrentHashMap.put("Published a patent",30);
        mConcurrentHashMap.put("Completed Prototype Development",31);
        mConcurrentHashMap.put("Generated Significant Revenue/Profits",32);
        mConcurrentHashMap.put("Patent Approved",33);
        mConcurrentHashMap.put("Attracted External Funding (Investor)",34);
        mConcurrentHashMap.put("Other Major Industry Specific Achievements",35);
        mConcurrentHashMap.put("User/Industry Implementing the Innovation developed by Student",36);
        mConcurrentHashMap.put("Significant Value Addition / Augmentation of Grassroots / Social Innovation",37);
        mConcurrentHashMap.put("Hobby Club Activities / ECell / Placement Cell / Media Club, etc.",1);
        mConcurrentHashMap.put("Festival (Technical / Business / Others) Events",2);
        mConcurrentHashMap.put("Professional Societies(ASME / ACM / IEEE / SAE, etc.)",3);
        mConcurrentHashMap.put("Special Initiatives",4);
        mConcurrentHashMap.put("GTU Innovation Club / S4 Extension Centre",5);
        mConcurrentHashMap.put("GIC Associates",6);



        return mConcurrentHashMap;
    }

    public static ConcurrentHashMap<String,String> getActivityLevelMap(){
        mActivityLevelMap = new ConcurrentHashMap<>();
        mActivityLevelMap.put("College","college");
        mActivityLevelMap.put("Zonal","zonal");
        mActivityLevelMap.put("State","state");
        mActivityLevelMap.put("National","national");
        mActivityLevelMap.put("International","international");

        return mActivityLevelMap;
    }

}
