package com.example.andorid.ersnexus.models;


import java.util.List;

public class AssignmentData {
    private String mAssignmentName;
    private String mSubjectCode;
    private String mFacultyCode;
    private String mDate;
    private String mClass;
    private int mId;
    private static List<AssignmentData> mAssignments;

    public int getId () {
        return mId;
    }

    public void setId (int id) {
        mId = id;
    }

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

    public static List<AssignmentData> getAssignments () {
        return mAssignments;
    }

    public static void setAssignments (List<AssignmentData> assignments) {
        mAssignments = assignments;
    }
}
