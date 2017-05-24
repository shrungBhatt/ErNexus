package com.example.andorid.ersnexus.webservices;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.andorid.ersnexus.userprofile.homeactivity.UserProfileHomeActivity;
import com.example.andorid.ersnexus.util.SharedPreferencesData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

//This class is used for performing tasks in the background.

public class BackgroundDbConnector extends AsyncTask<String, Void, String> {

    private Context mContext;
    public String mType;
    private HttpURLConnection mHttpURLConnection;


    //Constructor to initialise this class.
    public BackgroundDbConnector (Context context) {
        mContext = context;
    }


    //Method carried out to perform background tasks.
    @Override
    protected String doInBackground (String... params) {

        //The first parameter of the background task method.
        String type = params[0];
        mType = type;

        //It is an login call.
        switch (type) {
            case "login":
                try {
                    //Fetch the username and password from the background method call.
                    String username = params[1];
                    String password = params[2];

                    mHttpURLConnection = URLManager.
                            getConnection(URLManager.LOGIN_URL);

                    //Creating the outputStream
                    OutputStream outputStream = mHttpURLConnection.getOutputStream();
                    //Writing in the outputStream.
                    BufferedWriter bufferedWriter = new BufferedWriter(new
                            OutputStreamWriter(outputStream, "UTF-8"));

                    //This is for connecting the variables in the app and in the php file.
                    String postData = URLEncoder.encode("username", "UTF-8") + "=" +//$_POST["username"]
                            URLEncoder.encode(username, "UTF-8") + "&" +
                            URLEncoder.encode("password", "UTF-8") + "=" +//$_POST["password"]
                            URLEncoder.encode(password, "UTF-8");

                    //Feeding the data.
                    bufferedWriter.write(postData);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    //Creating an inputStream to fetch the results.
                    InputStream inputStream = mHttpURLConnection.getInputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                            inputStream, "iso-8859-1"));

                    //Getting the results
                    String result = "";
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    mHttpURLConnection.disconnect();
                    //Returning the results
                    return result;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "register": //It is an register POST call.
                try {
                    //Fetching the values to be registered.
                    String enrollmentnumber = params[1];
                    String username = params[2];
                    String fullname = params[3];
                    String password = params[4];
                    String emailid = params[5];
                    String dob = params[6];


                    mHttpURLConnection = URLManager.
                            getConnection(URLManager.REGISTER_STUDENT_URL);

                    OutputStream outputStream = mHttpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new
                            OutputStreamWriter(outputStream, "UTF-8"));

                    String postData = URLEncoder.encode("enrollmentnumber", "UTF-8") + "=" +//$_POST["enrollmentnumber"]
                            URLEncoder.encode(enrollmentnumber, "UTF-8") +
                            "&" +
                            URLEncoder.encode("username", "UTF-8") + "=" +//$_POST["username"]
                            URLEncoder.encode(username, "UTF-8") +
                            "&" +
                            URLEncoder.encode("fullname", "UTF-8") + "=" +//$_POST["fullname"]
                            URLEncoder.encode(fullname, "UTF-8") +
                            "&" +
                            URLEncoder.encode("password", "UTF-8") + "=" +//$_POST["password"]
                            URLEncoder.encode(password, "UTF-8") +
                            "&" +
                            URLEncoder.encode("emailid", "UTF-8") + "=" +//$_POST["emailid"]
                            URLEncoder.encode(emailid, "UTF-8") +
                            "&" +
                            URLEncoder.encode("dob", "UTF-8") + "=" +//$_POST["dob"]
                            URLEncoder.encode(dob, "UTF-8");

                    bufferedWriter.write(postData);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = mHttpURLConnection.getInputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                            inputStream, "iso-8859-1"));

                    String result = "";
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    mHttpURLConnection.disconnect();
                    return result;

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "attendance":
                try {
                    //Fetching the values to be registered.
                    String enrollmentnumber = params[1];
                    String subject_code = params[2];
                    String faculty_code = params[3];
                    String date = params[4];


                    mHttpURLConnection = URLManager.
                            getConnection(URLManager.REGISTER_ATTENDANCE_URL);

                    OutputStream outputStream = mHttpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new
                            OutputStreamWriter(outputStream, "UTF-8"));

                    String postData = URLEncoder.encode("enrollmentnumber", "UTF-8") + "=" +//$_POST["enrollmentnumber"]
                            URLEncoder.encode(enrollmentnumber, "UTF-8") +
                            "&" +
                            URLEncoder.encode("subject_code", "UTF-8") + "=" +//$_POST["sunject_code"]
                            URLEncoder.encode(subject_code, "UTF-8") +
                            "&" +
                            URLEncoder.encode("faculty_code", "UTF-8") + "=" +//$_POST["faculty_code"]
                            URLEncoder.encode(faculty_code, "UTF-8") +
                            "&" +
                            URLEncoder.encode("date", "UTF-8") + "=" +//$_POST["date"]
                            URLEncoder.encode(date, "UTF-8");

                    bufferedWriter.write(postData);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = mHttpURLConnection.getInputStream();

                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                            inputStream, "iso-8859-1"));

                    String result = "";
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    mHttpURLConnection.disconnect();
                    return result;

                } catch (IOException e) {
                    e.printStackTrace();
                }

        }
        return null;
    }


    @Override
    protected void onPostExecute (String result) {
        if (result == null) {
            return;
        }
        switch (mType) {
            case "login":
                if (result != null) {
                    SharedPreferencesData.setStoredLoginStatus(mContext, true);
                    SharedPreferencesData.setStoredErno(mContext,result);
                    mContext.startActivity(new Intent(mContext, UserProfileHomeActivity.class));
                } else {
                    Toast.makeText(mContext, "Wrong Username or Password!", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case "register":
                Toast.makeText(mContext, "Registered!", Toast.LENGTH_SHORT).show();
                break;
            case "attendance":
                Toast.makeText(mContext, "Submitted!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}

