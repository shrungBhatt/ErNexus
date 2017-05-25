package com.example.andorid.ersnexus.models;


import java.util.List;

public class NewsData {

    private String mNewsTopic;
    private String mNewsDate;
    private String mNewsDetails;
    private int mId;
    public static List<NewsData> mNewsData;


    public int getId () {
        return mId;
    }

    public void setId (int id) {
        mId = id;
    }

    public String getNewsTopic () {
        return mNewsTopic;
    }

    public void setNewsTopic (String newsTopic) {
        mNewsTopic = newsTopic;
    }

    public String getNewsDate () {
        return mNewsDate;
    }

    public void setNewsDate (String newsDate) {
        mNewsDate = newsDate;
    }

    public String getNewsDetails () {
        return mNewsDetails;
    }

    public void setNewsDetails (String newsDetails) {
        mNewsDetails = newsDetails;
    }

    public static List<NewsData> getmNewsData () {
        return mNewsData;
    }

    public static void setmNewsData (List<NewsData> mNewsData) {
        NewsData.mNewsData = mNewsData;
    }
}
