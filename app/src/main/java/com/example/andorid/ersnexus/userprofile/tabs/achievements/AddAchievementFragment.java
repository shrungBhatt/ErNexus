package com.example.andorid.ersnexus.userprofile.tabs.achievements;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.util.ActivitiesHashMap;
import com.example.andorid.ersnexus.util.DatePickerFragment;
import com.example.andorid.ersnexus.util.SharedPreferencesData;
import com.example.andorid.ersnexus.webservices.URLManager;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class AddAchievementFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "AddAchievementFragment";
    private static final int REQUEST_DATE = 0;
    private static final String DIALOG_DATE = "dialog_date";

    private Spinner mActivityTypeSpinner;
    private Spinner mActivitySubTypeSpinner;
    private Spinner mActivityLevelSpinner;
    private EditText mActivityDescriptionEditText;
    private Button mActivityDateButton;
    private Button mActivitySubmitButton;
    private int mActivitySelectedPosition;
    private Date mDate;
    private String mActivityString;
    private String mSubActivityString;
    private String mActivityLevelString;
    private int mPoints;
    private int mTotalPoints;
    private CheckBox mWinnerCheckbox;
    private Button mCalculatePointsButton;
    private TextView mTotalPointsTextView;

    //variable used to change the dateFormat.
    DateFormat formatDate = DateFormat.getDateInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDate = new Date();

        //Method used to generate the key pair of activities and what code does it represent
        //in the online database.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_achievement, container, false);

        mWinnerCheckbox = (CheckBox) v.findViewById(R.id.are_you_a_winner_checkbox);

        //Main activity category spinner.
        mActivityTypeSpinner = (Spinner) v.findViewById(R.id.type_of_activity_spinner);
        //Setting the adapter.
        setAdapter(R.array.type_of_activity, mActivityTypeSpinner);

        //Activity Description editText.
        mActivityDescriptionEditText = (EditText) v.findViewById(R.id.achievement_description);

        //Spinner for selecting the sub activity.
        mActivitySubTypeSpinner = (Spinner) v.findViewById(R.id.sub_activity_spinner);

        //Level of the activity (College,zonal, etc.)
        mActivityLevelSpinner = (Spinner) v.findViewById(R.id.competition_level_spinner);

        //To select the data of the activity performed.
        mActivityDateButton = (Button) v.findViewById(R.id.activity_date);
        updateDate();
        mActivityDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(getDate());
                dialog.setTargetFragment(AddAchievementFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        //button to submit the data of the activity into the database.
        mActivitySubmitButton = (Button) v.findViewById(R.id.register_achievement_button);
        mActivitySubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSubmitRequest();
            }
        });

        mCalculatePointsButton = (Button)v.findViewById(R.id.calculate_points);
        mCalculatePointsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchActivityPointsRequest();
            }
        });

        mTotalPointsTextView = (TextView)v.findViewById(R.id.total_points_textView);




        return v;
    }


    //Method called when particular spinner is selected by the user.
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId()) {

            //If main activity spinner is selected.
            case R.id.type_of_activity_spinner:

                //get the position of the activity selected in the type of activity spinner.
                mActivitySelectedPosition = i;

                //condition check to see whether appropriate main activity is selected
                //so that we can disable the checkbox corresponding to the activity.
                activityWinnerConditionCheck();

                //store the string of the selected item of type of activity spinner.
                mActivityString = mActivityTypeSpinner.getSelectedItem().toString();

                //set the subActivity spinner according to the item selected in type of activity
                //spinner.
                setSubActivityAdapter(mActivitySelectedPosition);

                //condition used to switch the mActivityLevelSpinner's array adapter.
                if (mActivitySelectedPosition == 5) {
                    setAdapter(R.array.participation_level, mActivityLevelSpinner);
                } else {
                    setAdapter(R.array.competition_level, mActivityLevelSpinner);
                }
                break;

            //If sub activity spinner is selected.
            case R.id.sub_activity_spinner:
                mSubActivityString = mActivitySubTypeSpinner.getSelectedItem().toString();
                break;

            //If the competition level spinner is selected.
            case R.id.competition_level_spinner:
                mActivityLevelString = mActivityLevelSpinner.getSelectedItem().toString();
                break;


        }
    }


    //Method called when none of the spinner is selected.
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //method used to fetch the activity points from the database.
    private void fetchActivityPointsRequest() {
        ConcurrentHashMap<String,Integer> mSubActivityHashMap = ActivitiesHashMap.
                generateActivityHashMap();
        final String activityId = mSubActivityHashMap.get(mSubActivityString).toString();


        //Hashmap used to get the sub activity name and activity level.
        ConcurrentHashMap<String,String> mActivityLevelMap = ActivitiesHashMap.
                getActivityLevelMap();
        final String activityLevel = mActivityLevelMap.get(mActivityLevelString);


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URLManager.FETCH_ACTIVITY_POINTS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        mPoints = Integer.parseInt(response);
                        if(mWinnerCheckbox.isChecked()){
                            mTotalPoints = mPoints + 3;
                        }else{
                            mTotalPoints = mPoints;
                        }
                        mTotalPointsTextView.setText(Integer.toString(mTotalPoints));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Fetch Activity Points Error: " + error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("activity_id", activityId);
                params.put("activity_level", activityLevel);
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    //method used to submit the activity details in the database.
    private void sendSubmitRequest() {

        //data members used to send the values of various fields of activity in POST request.
        final String erno = SharedPreferencesData.getStoredErno(getActivity());
        final String date = mActivityDateButton.getText().toString();
        final String description = mActivityDescriptionEditText.getText().toString();
        final String totalPoints = Integer.toString(mTotalPoints);


        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URLManager.SUBMIT_ACTIVITY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"Submit Error:"+error.toString());

            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("enrollmentnumber",erno);
                params.put("activity_name",mActivityString);
                params.put("sub_activity",mSubActivityString);
                params.put("activity_description",description);
                params.put("activity_date",date);
                params.put("activity_level",mActivityLevelString);
                params.put("activity_points",totalPoints);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);

    }


    //Method used to fill the spinner with the String arrays declared in the String.xml file.
    //And set the adapter of the spinner.
    private void setAdapter(int id, Spinner spinner) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                id, R.layout.simple_spinner_layout);
        adapter.setDropDownViewResource(R.layout.spinner_layout);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    //Method used to set the sub activity adapter.
    private void setSubActivityAdapter(int position) {
        int arrayId = 0;
        //Selecting the string array based on what user selected in main activity spinner.
        switch (position) {
            case 0:
                arrayId = R.array.bridge_course_sub_activity;
                break;
            case 1:
                arrayId = R.array.technical_research_skills_sub_activities;
                break;
            case 2:
                arrayId = R.array.sports_cultural_sub_activities;
                break;
            case 3:
                arrayId = R.array.community_outreach_social_initiatives_sub_activities;
                break;
            case 4:
                arrayId = R.array.innovation_ipr_entrepreneurship_sub_activities;
                break;
            case 5:
                arrayId = R.array.leadership_management_sub_activities;
                break;
        }
        setAdapter(arrayId, mActivitySubTypeSpinner);

    }


    //On activity result used fot the date dialog fragment.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }

        if (requestCode == REQUEST_DATE) {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            setDate(date);
            updateDate();
        }
    }

    //Method used to set the simple style of selected data.
    private void updateDate() {
        mActivityDateButton.setText(formatDate.format(getDate()));

    }

    //Method used to disable the checkBox if activities other than A2 and A3 are selected.
    private void activityWinnerConditionCheck(){
        if(mActivitySelectedPosition == 0 || mActivitySelectedPosition == 3 ||
                mActivitySelectedPosition == 4 || mActivitySelectedPosition == 5){
            mWinnerCheckbox.setEnabled(false);

        }else{
            mWinnerCheckbox.setEnabled(true);
        }

    }


    private Date getDate() {
        return mDate;
    }

    private void setDate(Date date) {
        mDate = date;
    }

}
