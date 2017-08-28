package com.example.andorid.ersnexus.userprofile.tabs.achievements;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.util.ActivitiesHashMap;
import com.example.andorid.ersnexus.util.DatePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


public class AddAchievementFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final int REQUEST_DATE = 0;
    private static final String DIALOG_DATE = "dialog_date";

    private Spinner mActivityTypeSpinner;
    private Spinner mActivitySubTpyeSpinner;
    private Spinner mActivityLevelSpinner;
    private EditText mActivityDescriptionEditText;
    private Button mActivityDateButton;
    private Button mActivitySubmitButton;
    private int mActivitySelectedPosition;
    private Date mDate;
    private ConcurrentHashMap<String,Integer> mConcurrentHashMap;
    private String mSubActivityString;
    private String mActivityLevelString;

    DateFormat formatDate = DateFormat.getDateInstance();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mDate = new Date();

        //Method used to generate the key pair of activities and what code does it represent
        //in the online database.
        ActivitiesHashMap.generateActivityHashMap();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_achievement, container, false);


        //Main activity category spinner.
        mActivityTypeSpinner = (Spinner) v.findViewById(R.id.type_of_activity_spinner);
        //Setting the adapter.
        setAdapter(R.array.type_of_activity, mActivityTypeSpinner);

        //Activity Description editText.
        mActivityDescriptionEditText = (EditText) v.findViewById(R.id.achievement_description);

        //Spinner for selecting the sub activity.
        mActivitySubTpyeSpinner = (Spinner) v.findViewById(R.id.sub_activity_spinner);

        //Level of the activity (College,zonal, etc.)
        mActivityLevelSpinner = (Spinner)v.findViewById(R.id.competition_level_spinner);

        //To select the data of the activity performed.
        mActivityDateButton = (Button)v.findViewById(R.id.activity_date);
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



        return v;
    }



    //Method called when particular spinner is selected by the user.
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId()) {

            //If main activity spinner is selected.
            case R.id.type_of_activity_spinner:
                mActivitySelectedPosition = i;
                setSubActivityAdapter(mActivitySelectedPosition);
                if(mActivitySelectedPosition == 5){
                    setAdapter(R.array.participation_level,mActivityLevelSpinner);
                }else {
                    setAdapter(R.array.competition_level, mActivityLevelSpinner);
                }
                break;

            //If sub activity spinner is selected.
            case R.id.sub_activity_spinner:
                mSubActivityString = mActivitySubTpyeSpinner.getSelectedItem().toString();
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
        setAdapter(arrayId,mActivitySubTpyeSpinner);

    }


    //On activity result used fot the date dialog fragment.
    @Override
    public void onActivityResult (int requestCode, int resultCode, Intent data) {
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
    private void updateDate () {
        mActivityDateButton.setText(formatDate.format(getDate()));

    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

}
