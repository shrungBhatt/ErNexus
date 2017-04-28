package com.example.andorid.ersnexus.models;

/**
 * Created by Bhatt on 28-04-2017.
 */

public class AssignmentData {
    private String mAssignmentName;
    private String mSubjectCode;
    private String mFacultyCode;
    private String mDate;
    private String mClass;

    public String getAssignmentName () {
        return mAssignmentName;
    }

    public void setAssignmentName (String assignmentName) {
        mAssignmentName = assignmentName;
    }

    public String getSubjectCode () {
        return mSubjectCode;
    }

    public void setSubjectCode (String subjectCode) {
        mSubjectCode = subjectCode;
    }

    public String getFacultyCode () {
        return mFacultyCode;
    }

    public void setFacultyCode (String facultyCode) {
        mFacultyCode = facultyCode;
    }

    public String getDate () {
        return mDate;
    }

    public void setDate (String date) {
        mDate = date;
    }


    public String getAssignmentClass () {
        return mClass;
    }

    public void setAssignmentClass (String aClass) {
        mClass = aClass;
    }
}
