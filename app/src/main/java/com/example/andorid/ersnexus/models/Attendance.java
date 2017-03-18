package com.example.andorid.ersnexus.models;


import java.util.Date;



public class Attendance {
    private int mSubjectCode;
    private int mFacultyCode;
    private Date mDate;
    private Date mTime;
    private String mEnrollmentNumber;

    public Attendance(int subjectCode,int facultyCode,Date date,Date time,String enrollmentNumber){
        mSubjectCode = subjectCode;
        mFacultyCode = facultyCode;
        mDate = date;
        mTime = time;
        mEnrollmentNumber = enrollmentNumber;
    }


    public String getEnrollmentNumber () {
        return mEnrollmentNumber;
    }

    public void setEnrollmentNumber (String enrollmentNumber) {
        mEnrollmentNumber = enrollmentNumber;
    }

    public int getSubjectCode () {
        return mSubjectCode;
    }

    public void setSubjectCode (int subjectCode) {
        mSubjectCode = subjectCode;
    }

    public int getFacultyCode () {
        return mFacultyCode;
    }

    public void setFacultyCode (int facultyCode) {
        mFacultyCode = facultyCode;
    }

    public Date getDate () {
        return mDate;
    }

    public void setDate (Date date) {
        mDate = date;
    }

    public Date getTime () {
        return mTime;
    }

    public void setTime (Date time) {
        mTime = time;
    }
}
