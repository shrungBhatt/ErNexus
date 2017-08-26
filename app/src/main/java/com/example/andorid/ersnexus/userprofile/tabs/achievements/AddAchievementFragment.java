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
import com.example.andorid.ersnexus.util.DatePickerFragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


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

    DateFormat formatDate = DateFormat.getDateInstance();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        mDate = new Date();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_achievement, container, false);


        mActivityTypeSpinner = (Spinner) v.findViewById(R.id.type_of_activity_spinner);
        setAdapter(R.array.type_of_activity, mActivityTypeSpinner);

        mActivityDescriptionEditText = (EditText) v.findViewById(R.id.achievement_description);

        mActivitySubTpyeSpinner = (Spinner) v.findViewById(R.id.sub_activity_spinner);

        mActivityLevelSpinner = (Spinner)v.findViewById(R.id.competition_level_spinner);

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



    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        switch (adapterView.getId()) {
            case R.id.type_of_activity_spinner:
//                String text = mActivityTypeSpinner.getSelectedItem().toString();
//                mActivityDescriptionEditText.setText(text);
                mActivitySelectedPosition = i;
                setSubActivityAdapter(mActivitySelectedPosition);
                if(mActivitySelectedPosition == 5){
                    setAdapter(R.array.participation_level,mActivityLevelSpinner);
                }else {
                    setAdapter(R.array.competition_level, mActivityLevelSpinner);
                }
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

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
