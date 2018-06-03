package com.example.andorid.ersnexus.models;


//Model class of AchievementData.
public class AchievementData {

    private int mId;
    private String mDescription;
    private String mActivity;
    private String mSubActivity;
    private int mPoints;
    private String mDate;
    private String mActivityLevel;
    private String mStatus;
    private String mImage;
    private String mImageDesc;


    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getImageDesc() {
        return mImageDesc;
    }

    public void setImageDesc(String imageDesc) {
        mImageDesc = imageDesc;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getActivity() {
        return mActivity;
    }

    public void setActivity(String activity) {
        mActivity = activity;
    }

    public String getSubActivity() {
        return mSubActivity;
    }

    public void setSubActivity(String subActivity) {
        mSubActivity = subActivity;
    }

    public int getPoints() {
        return mPoints;
    }

    public void setPoints(int points) {
        mPoints = points;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getActivityLevel() {
        return mActivityLevel;
    }

    public void setActivityLevel(String activityLevel) {
        mActivityLevel = activityLevel;
    }
}
