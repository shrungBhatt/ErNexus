package com.example.andorid.ersnexus.models;


//Model class of ActivityData.
public class ActivityData {

    private int mId;
    private String mErno;
    private String mActivityString;
    private String mSubActivityString;
    private String mActivityLevelString;
    private int mTotalPoints;
    private String mActivityDescription;
    private String mActivityDate;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getErno() {
        return mErno;
    }

    public void setErno(String erno) {
        mErno = erno;
    }

    public String getActivityString() {
        return mActivityString;
    }

    public void setActivityString(String activityString) {
        mActivityString = activityString;
    }

    public String getSubActivityString() {
        return mSubActivityString;
    }

    public void setSubActivityString(String subActivityString) {
        mSubActivityString = subActivityString;
    }

    public String getActivityLevelString() {
        return mActivityLevelString;
    }

    public void setActivityLevelString(String activityLevelString) {
        mActivityLevelString = activityLevelString;
    }

    public int getTotalPoints() {
        return mTotalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        mTotalPoints = totalPoints;
    }

    public String getActivityDescription() {
        return mActivityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        mActivityDescription = activityDescription;
    }

    public String getActivityDate() {
        return mActivityDate;
    }

    public void setActivityDate(String activityDate) {
        mActivityDate = activityDate;
    }
}
