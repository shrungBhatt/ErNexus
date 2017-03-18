package com.example.andorid.ersnexus.models;


public class AttendanceData {
    private String mSubjectCode;
    private String mFacultyCode;
    private String mDate;
    private String mTime;
    private String mEnrollmentNumber;

    public AttendanceData (String subjectCode, String facultyCode, String date,
                           String enrollmentNumber) {
        mSubjectCode = subjectCode;
        mFacultyCode = facultyCode;
        mDate = date;
        mEnrollmentNumber = enrollmentNumber;
    }


    public String getEnrollmentNumber () {
        return mEnrollmentNumber;
    }

    public void setEnrollmentNumber (String enrollmentNumber) {
        mEnrollmentNumber = enrollmentNumber;
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

}
