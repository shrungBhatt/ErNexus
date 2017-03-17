package com.example.andorid.ersnexus.userscanattendance;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.andorid.ersnexus.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;



public class UserScanAttendanceFragment extends Fragment {

    private Button buttonScan;
    private TextView textViewName, textViewAddress;
    //qr code scanner object
    private IntentIntegrator qrScan;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.fragment_user_scan_attendance,container,false);



        buttonScan = (Button)v. findViewById(R.id.buttonScan);
        textViewName = (TextView)v. findViewById(R.id.textViewName);
        textViewAddress = (TextView)v. findViewById(R.id.textViewAddress);

        qrScan = new IntentIntegrator(getActivity());

        buttonScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {

                qrScan.setBeepEnabled(false);
                qrScan.initiateScan();
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(getActivity(), "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    textViewName.setText(obj.getString("name"));
                    textViewAddress.setText(obj.getString("address"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(getActivity(), result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}

