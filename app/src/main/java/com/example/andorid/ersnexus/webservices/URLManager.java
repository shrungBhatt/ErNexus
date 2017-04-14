package com.example.andorid.ersnexus.webservices;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class URLManager {
    public static final String URL = "http://192.168.2.3/ersnexus/";

    //These are the URLs used in the BackgroundDBConnector class.
    public static final String LOGIN_URL = URL + "login.php";
    public static final String REGISTER_STUDENT_URL = URL + "register.php";
    public static final String FETCH_ERNO_URL = URL + "enrollmentnumber.php";
    public static final String REGISTER_ATTENDANCE_URL = URL + "attendance.php";

    //These are the URLs used in the Attendance Tabs bacground task.
    public static final String SORT_ERNO_URL = URL + "sort_attendance_erno.php";
    public static final String SORT_SUBJECT_CODE_URL = URL + "sort_attendance_subject_code.php";
    public static final String SORT_FACULTY_CODE_URL = URL + "sort_attendance_faculty_code.php";
    public static final String SORT_DATE_URL = URL + "sort_attendance_date.php";

    public static HttpURLConnection getConnection(String url) throws IOException {
        URL mUrl = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) mUrl.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);

        return httpURLConnection;
    }
}