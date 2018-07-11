package com.example.andorid.ersnexus.faculty_dashboard;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.andorid.ersnexus.R;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QrCodeGeneratorActivity extends AppCompatActivity {

    private EditText mFacultyCodeEdt;
    private EditText mSubjectCodeEdt;
    private ImageView mQrCodeHolder;
    private FloatingActionButton mFloatingActionButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code_generator);

        mFacultyCodeEdt = (EditText) findViewById(R.id.qr_generate_faculty_code);
        mSubjectCodeEdt = (EditText) findViewById(R.id.qr_generate_subject_code);
        mQrCodeHolder = (ImageView) findViewById(R.id.qr_code_holder);
        mFloatingActionButton = (FloatingActionButton) findViewById(R.id.generate_qr_code);


        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mFacultyCodeEdt.getText().toString().trim().length() > 0 &&
                        !mFacultyCodeEdt.getText().toString().equals(" ")) {

                    if (mSubjectCodeEdt.getText().toString().trim().length() > 0 &&
                            !mSubjectCodeEdt.getText().toString().equals(" ")) {

                        mQrCodeHolder.setVisibility(View.VISIBLE);
                        mQrCodeHolder.setImageBitmap(generateQrCode());

                    } else {
                        mSubjectCodeEdt.setError("Enter Subject code");
                    }

                } else {
                    mFacultyCodeEdt.setError("Enter Faculty code");
                }
            }
        });
    }

    private Bitmap generateQrCode() {

        QrModel qrModel = new QrModel();
        qrModel.setFacultyCode(mFacultyCodeEdt.getText().toString().trim());
        qrModel.setSubjectCode(mSubjectCodeEdt.getText().toString().trim());

        String qrText = new Gson().toJson(qrModel);


        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(qrText, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }


    private class QrModel{
        @SerializedName("subject")
        private String subjectCode;
        @SerializedName("faculty")
        private String facultyCode;

        public String getSubjectCode() {
            return subjectCode;
        }

        public void setSubjectCode(String subjectCode) {
            this.subjectCode = subjectCode;
        }

        public String getFacultyCode() {
            return facultyCode;
        }

        public void setFacultyCode(String facultyCode) {
            this.facultyCode = facultyCode;
        }
    }


}
