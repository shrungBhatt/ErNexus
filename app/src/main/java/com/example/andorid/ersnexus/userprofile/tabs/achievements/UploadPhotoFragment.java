package com.example.andorid.ersnexus.userprofile.tabs.achievements;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;

import android.support.design.widget.FloatingActionButton;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.andorid.ersnexus.R;
import com.example.andorid.ersnexus.util.Const;
import com.example.andorid.ersnexus.util.MyFileContentProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class UploadPhotoFragment extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    public static final String EXTRA_IMAGE = "extra_image";
    public static final String EXTRA_IMAGE_DESC = "extra_image_desc";

    private ArrayList<String> permissionList;
    private ImageView mUploadDocumentImage;
    private ImageView mDocumentsImageView;
    private TextView mUploadDocumentTv;
    private Button mOkButton;
    private Bitmap mImage;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_upload_photo);

        mUploadDocumentImage = (ImageView) findViewById(R.id.upload_document_image);
        mDocumentsImageView = (ImageView) findViewById(R.id.documents_image_view);
        mUploadDocumentTv = (TextView) findViewById(R.id.upload_document_tv);
        mOkButton = (Button) findViewById(R.id.ok_button);


        mUploadDocumentImage.setOnClickListener(view -> requestPersmission());

        mOkButton.setOnClickListener(view -> {
            AddAchievementFragment.setmImage(getImage());
            AddAchievementFragment.setmImageDesc(UUID.randomUUID() + "img");
            setResult(RESULT_OK);
            finish();
        });

    }

    public void requestPersmission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            OpenMediaPickerDialog();
        } else if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                || ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA)) {
            new AlertDialog.Builder(this)
                    .setMessage("ErNexus needs storage permission in order to take photo")
                    .setCancelable(false)
                    .setPositiveButton("Give Permission", (dialogInterface, i) -> requestReadStoragePermission())
                    .create().show();
        } else {
            requestReadStoragePermission();
        }


    }


    public void OpenMediaPickerDialog() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Upload attachment");

            String[] values = {"Take photo"};

            final ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
            builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (i == 0) {
                        OpenCamera(Const.OpenCamera);
                    }
                }
            });
            builder.create().show();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }


    /**
     * Open gallery to select photo
     *
     * @param requestCode int value
     */
    public void OpenCamera(int requestCode) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        Intent intent = new Intent();
        Uri capturedPhotoURI = MyFileContentProvider.CONTENT_URI;
        intent.putExtra(MediaStore.EXTRA_OUTPUT, capturedPhotoURI);
        startActivityForResult(intent, requestCode);
    }

    private void requestReadStoragePermission() {
        permissionList = new ArrayList<>();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(android.Manifest.permission.CAMERA);
        }

        if (permissionList != null && permissionList.size() > 0) {
            String[] permissiondata = permissionList.toArray(new String[0]);
            ActivityCompat.requestPermissions(this, permissiondata, Const.REQUEST_STORAGE_PERMISSION);
        } else {
            OpenMediaPickerDialog();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == Const.OpenCamera) {
                File out;
                out = new File(getFilesDir(), Const.IMAGE_CAPTURED);
//                Uri filePath = data.getData();
                mUploadDocumentImage.setVisibility(View.GONE);
                mUploadDocumentTv.setVisibility(View.GONE);
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.fromFile(out));
                    mDocumentsImageView.setImageBitmap(bitmap);
                    setImage(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap image) {
        mImage = image;
    }
}
