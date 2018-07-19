package com.example.andorid.ersnexus.webservices;



public class URLManager {
    private static final String URL = "http://ersnexus.esy.es/";

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

    //This url is used to fatch all the assignments for faculty dashboard list
    public static final String FETCH_ALL_ACHIEVEMENT_DATA = URL + "fetch_all_achievement_data.php";

    //This url is used to register the user.
    public static final String REGISTER_FACULTY = URL + "register_faculty.php";

    //This url is used for login of faculty
    public static final String LOGIN_FACULTY = URL + "login_faculty.php";

    //This url is used to update the faculty status..
    public static final String UPDATE_ACHIEVEMENT_STATUS = URL + "update_achievement_status.php";
}
