package com.example.andorid.ersnexus.util;

import android.content.*;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.andorid.ersnexus.userprofile.homeactivity.UserProfileHomeActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

//This class is used for performing tasks in the background.

public class BackgroundDbConnector extends AsyncTask<String, Void, String> {

    private Context mContext;
    public  String mType;


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

        //Url for login page php file.
        String loginUrl = "http://192.168.2.3/ersnexus/login.php";

        //Url for register page php file.
        String registerUrl = "http://192.168.2.3/ersnexus/register.php";

        //It is an login call.
        if (type.equals("login")) {
            try {
                //Fetch the username and password from the background method call.
                String username = params[1];
                String password = params[2];

                //Creating a URL.
                URL url = new URL(loginUrl);
                //Connecting to the URL.
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                //Setting request method POST.
                httpURLConnection.setRequestMethod("POST");
                //This connection include Input and output interaction.
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                //Creating the outputStream
                OutputStream outputStream = httpURLConnection.getOutputStream();
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
                //Returning the results
                return result;

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (type.equals("register")) {//It is an register POST call.
            try {
                //Fetching the values to be registered.
                String enrollmentnumber = params[1];
                String username = params[2];
                String fullname = params[3];
                String password = params[4];
                String emailid = params[5];
                String dob = params[6];


                URL url = new URL(registerUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);

                OutputStream outputStream = httpURLConnection.getOutputStream();
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

                InputStream inputStream = httpURLConnection.getInputStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                        inputStream, "iso-8859-1"));

                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null) {
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute (String result) {
        if(mType.equals("login")) {
            if(result.equals("success")){
                String username = SharedPreferences.getStoredUsername(mContext);
                mContext.startActivity(new Intent(mContext, UserProfileHomeActivity.class));
                Toast.makeText(mContext,"Welcome "+username,Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(mContext, "Wrong Username or Password!", Toast.LENGTH_SHORT).show();
            }
        }else if(mType.equals("register")){
            Toast.makeText(mContext, "Registered!", Toast.LENGTH_SHORT).show();
        }
    }

}

