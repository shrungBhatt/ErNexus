package com.example.andorid.ersnexus.userdata;

import java.util.Date;
import java.util.UUID;



public class UserData {
    private UUID mId;
    private String mUserName;
    private String mFullName;
    private String mPassword;
    private String mCorrectPassword;
    private Date mDob;
    private String mEmail;
    private String mErNo1;
    private String mErNo2;
    private String mErNo3;
    private String mErNo4;
    private String mErNo5;
    private String mErNo6;
    private String mErNo7;
    private String mErNo8;
    private String mErNo9;
    private String mErNo10;
    private String mErNo11;
    private String mErNo12;
    private String mEnrollmentNumber;



    public UserData () {

        mId = UUID.randomUUID();
        mDob = new Date();

    }

    public UserData(String userName,String password){
        mUserName = userName;
        mPassword = password;

    }

    public UUID getId () {
        return mId;
    }

    public String getEnrollmentNumber () {
        return mErNo1 + mErNo2 + mErNo3 + mErNo4 + mErNo5 + mErNo6 + mErNo7 + mErNo8 + mErNo9 +
                mErNo10 + mErNo11 + mErNo12;
    }

    public void setEnrollmentNumber (String enrollmentNumber) {
        mEnrollmentNumber = enrollmentNumber;
    }

    public String getUserName () {
        return mUserName;
    }

    public void setUserName (String userName) {
        mUserName = userName;
    }

    public String getFullName () {
        return mFullName;
    }

    public void setFullName (String fullName) {
        mFullName = fullName;
    }

    public String getPassword () {
        return mPassword;
    }

    public void setPassword (String password) {
        mPassword = password;
    }

    public String getCorrectPassword () {
        return mCorrectPassword;
    }

    public void setCorrectPassword (String correctPassword) {
        mCorrectPassword = correctPassword;
    }

    public Date getDob () {
        return mDob;
    }

    public void setDob (Date dob) {
        mDob = dob;
    }

    public String getEmail () {
        return mEmail;
    }

    public void setEmail (String email) {
        mEmail = email;
    }

    public String getErNo1 () {
        return mErNo1;
    }

    public void setErNo1 (String erNo1) {
        mErNo1 = erNo1;
    }

    public String getErNo2 () {
        return mErNo2;
    }

    public void setErNo2 (String erNo2) {
        mErNo2 = erNo2;
    }

    public String getErNo3 () {
        return mErNo3;
    }

    public void setErNo3 (String erNo3) {
        mErNo3 = erNo3;
    }

    public String getErNo4 () {
        return mErNo4;
    }

    public void setErNo4 (String erNo4) {
        mErNo4 = erNo4;
    }

    public String getErNo5 () {
        return mErNo5;
    }

    public void setErNo5 (String erNo5) {
        mErNo5 = erNo5;
    }

    public String getErNo6 () {
        return mErNo6;
    }

    public void setErNo6 (String erNo6) {
        mErNo6 = erNo6;
    }

    public String getErNo7 () {
        return mErNo7;
    }

    public void setErNo7 (String erNo7) {
        mErNo7 = erNo7;
    }

    public String getErNo8 () {
        return mErNo8;
    }

    public void setErNo8 (String erNo8) {
        mErNo8 = erNo8;
    }

    public String getErNo9 () {
        return mErNo9;
    }

    public void setErNo9 (String erNo9) {
        mErNo9 = erNo9;
    }

    public String getErNo10 () {
        return mErNo10;
    }

    public void setErNo10 (String erNo10) {
        mErNo10 = erNo10;
    }

    public String getErNo11 () {
        return mErNo11;
    }

    public void setErNo11 (String erNo11) {
        mErNo11 = erNo11;
    }

    public String getErNo12 () {
        return mErNo12;
    }

    public void setErNo12 (String erNo12) {
        mErNo12 = erNo12;
    }


}
