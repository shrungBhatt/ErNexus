package com.example.andorid.ersnexus.util;

import android.content.Context;
import android.os.AsyncTask;

import com.example.andorid.ersnexus.models.AttendanceData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class BackgroundAttendanceFetcher extends AsyncTask<Void,Void,List<AttendanceData>> {
    private Context mContext;
    private List<AttendanceData> mAttendanceDatas;

    public  BackgroundAttendanceFetcher(Context context){
        mContext = context;
    }

    @Override
    protected List<AttendanceData> doInBackground (Void... params) {
        try {
            //Fetch the username and password from the background method call.

            List<AttendanceData> attendanceDatas = new ArrayList<>();

            //Creating a URL.
            URL url = new URL("http://192.168.2.3/ersnexus/fetchattendance.php");
            //Connecting to the URL.
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //Setting request method POST.
            httpURLConnection.setRequestMethod("POST");
            //This connection include Input and output interaction.
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);


            //Creating an inputStream to fetch the results.
            InputStream inputStream = httpURLConnection.getInputStream();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    inputStream, "iso-8859-1"));

            //Getting the results
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            try {
                JSONArray jsonArray = new JSONArray(result);

                for(int i = 0; i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    AttendanceData attendanceData = new AttendanceData();
                    attendanceData.
                            setEnrollmentNumber(jsonObject.getString("enrollmentnumber"));
                    attendanceData.setSubjectCode(jsonObject.getString("subjectcode"));
                    attendanceData.setFacultyCode(jsonObject.getString("facultycode"));
                    attendanceData.setDate(jsonObject.getString("date"));


                    attendanceDatas.add(attendanceData);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //mAttendanceDatas = attendanceDatas;
            //Returning the results
            return attendanceDatas;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onPostExecute(List<AttendanceData> items){
        AttendanceData attendanceData = new AttendanceData();
        attendanceData.setAttendanceDatas(items);
    }

}
