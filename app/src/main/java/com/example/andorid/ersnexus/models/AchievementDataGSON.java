package com.example.andorid.ersnexus.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AchievementDataGSON {


    @SerializedName("list")
    @Expose
    private ArrayList<List> list = null;

    public ArrayList<List> getList() {
        return list;
    }

    public void setList(ArrayList<List> list) {
        this.list = list;
    }

    public class List {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("enrollmentnumber")
        @Expose
        private String enrollmentnumber;
        @SerializedName("activity_name")
        @Expose
        private String activityName;
        @SerializedName("activity_image")
        @Expose
        private String activityImage;
        @SerializedName("sub_activity")
        @Expose
        private String subActivity;
        @SerializedName("activity_level")
        @Expose
        private String activityLevel;
        @SerializedName("activity_date")
        @Expose
        private String activityDate;
        @SerializedName("activity_points")
        @Expose
        private String activityPoints;
        @SerializedName("activity_description")
        @Expose
        private String activityDescription;
        @SerializedName("status")
        @Expose
        private String status;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEnrollmentnumber() {
            return enrollmentnumber;
        }

        public void setEnrollmentnumber(String enrollmentnumber) {
            this.enrollmentnumber = enrollmentnumber;
        }

        public String getActivityName() {
            return activityName;
        }

        public void setActivityName(String activityName) {
            this.activityName = activityName;
        }

        public String getActivityImage() {
            return activityImage;
        }

        public void setActivityImage(String activityImage) {
            this.activityImage = activityImage;
        }

        public String getSubActivity() {
            return subActivity;
        }

        public void setSubActivity(String subActivity) {
            this.subActivity = subActivity;
        }

        public String getActivityLevel() {
            return activityLevel;
        }

        public void setActivityLevel(String activityLevel) {
            this.activityLevel = activityLevel;
        }

        public String getActivityDate() {
            return activityDate;
        }

        public void setActivityDate(String activityDate) {
            this.activityDate = activityDate;
        }

        public String getActivityPoints() {
            return activityPoints;
        }

        public void setActivityPoints(String activityPoints) {
            this.activityPoints = activityPoints;
        }

        public String getActivityDescription() {
            return activityDescription;
        }

        public void setActivityDescription(String activityDescription) {
            this.activityDescription = activityDescription;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }


}
