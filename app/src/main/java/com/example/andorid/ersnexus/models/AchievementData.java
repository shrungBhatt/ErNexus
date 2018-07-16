package com.example.andorid.ersnexus.models;


import com.google.gson.annotations.SerializedName;

//Model class of AchievementData.
public class AchievementData {

    @SerializedName("id")
    private int mId;

    @SerializedName("activity_description")
    private String mDescription;

    @SerializedName("activity_name")
    private String mActivity;

    @SerializedName("sub_activity")
    private String mSubActivity;

    @SerializedName("activity_points")
    private int mPoints;

    @SerializedName("activity_date")
    private String mDate;

    @SerializedName("activity_level")
    private String mActivityLevel;

    @SerializedName("status")
    private String mStatus;

    @SerializedName("activity_image")
    private String mImage;
    private String mImageDesc;

    @SerializedName("enrollmentnumber")
    private String mErno;


    public String getErno() {
        return mErno;
    }

    public void setErno(String erno) {
        mErno = erno;
    }

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
