package com.example.andorid.ersnexus.models;

//This is a model class which contains getters and setters used for attendances purpose.


import java.util.List;

public class AttendanceData {
    private String mSubjectCode;
    private String mFacultyCode;
    private String mDate;
    private String mTime;
    private String mEnrollmentNumber;
    private List<AttendanceData> mAttendanceDatas;


    public AttendanceData(){

    }
    //Constructor method used to set the values for the getters.
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

    public List<AttendanceData> getAttendanceDatas () {
        return mAttendanceDatas;
    }

    public void setAttendanceDatas (List<AttendanceData> attendanceDatas) {
        mAttendanceDatas = attendanceDatas;
    }
}
