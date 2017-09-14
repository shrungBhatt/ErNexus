package com.example.andorid.ersnexus.webservices;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class URLManager {
    public static final String URL = "http://ersnexus.esy.es/";

    //These are the URLs used in the BackgroundDBConnector class.
    public static final String LOGIN_URL = URL + "login.php";
    public static final String REGISTER_STUDENT_URL = URL + "register.php";
//    public static final String FETCH_ERNO_URL = URL + "enrollmentnumber.php";
    public static final String REGISTER_ATTENDANCE_URL = URL + "attendance.php";

    //These are the URLs used in the Attendance Tabs bacground task.
    public static final String SORT_ERNO_URL = URL + "sort_attendance_erno.php";
    public static final String SORT_SUBJECT_CODE_URL = URL + "sort_attendance_subject_code.php";
    public static final String SORT_FACULTY_CODE_URL = URL + "sort_attendance_faculty_code.php";
    public static final String SORT_DATE_URL = URL + "sort_attendance_date.php";

    //These are the URLs used in the Assignment Tab background task.
    public static final String FETCH_ASSIGNMENTS_URL = URL + "fetch_assignments.php";

    //These are the URLs used in the NewsFeed Tab background task.
    public static final String FETCH_NEWS_URL = URL + "fetch_news.php";

    //These are the URLs to submit and fetch the activity points from the database.
    public static final String FETCH_ACTIVITY_POINTS = URL + "fetch_activity_points.php";
    public static final String SUBMIT_ACTIVITY = URL + "submit_activity.php";
    public static final String FETCH_ACTIVITY_A6_POINTS = URL +"fetch_activity_points_a6.php";

    //This url is used to fetch the JSON of achievements_data from the database.
    public static final String FETCH_ACHIEVEMENT_DATA = URL + "fetch_achievement_data.php";

    /*public static HttpURLConnection getConnection (String url) throws IOException {
        URL mUrl = new URL(url);
        HttpURLConnection httpURLConnection = (HttpURLConnection) mUrl.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);

        return httpURLConnection;
    }*/
}
