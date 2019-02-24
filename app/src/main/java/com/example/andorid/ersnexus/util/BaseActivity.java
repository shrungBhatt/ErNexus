package com.example.andorid.ersnexus.util;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;

import com.example.andorid.ersnexus.R;

public abstract class BaseActivity extends AppCompatActivity {


    private Dialog mProgressDialog;


    /**
     * This method is used to hide or show particular views when keyboard is opened or closed
     *
     * @param views varags of view.
     */
    protected void initSoftKeyboardListener(View... views) {
        final int MIN_KEYBOARD_HEIGHT_PX = 150;
        final View decorView = getWindow().getDecorView();
        decorView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private final Rect windowVisibleDisplayFrame = new Rect();
            private int lastVisibleDecorViewHeight;

            @Override
            public void onGlobalLayout() {
                decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame);
                final int visibleDecorViewHeight = windowVisibleDisplayFrame.height();

                if (lastVisibleDecorViewHeight != 0) {
                    if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {
                        for (View view : views) {
                            view.setVisibility(View.GONE);
                        }
                    } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                        for (View view : views) {
                            view.setVisibility(View.VISIBLE);
                        }

                    }
                }
                lastVisibleDecorViewHeight = visibleDecorViewHeight;
            }
        });
    }


    public void showProgressBar(String TAG) {
        mProgressDialog = new Dialog(this);
        mProgressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mProgressDialog.setContentView(R.layout.circle_progress);
        mProgressDialog.setCancelable(false);

        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        mProgressDialog.getWindow().setGravity(Gravity.CENTER);
        try {
            mProgressDialog.show();
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
    }

    public void startActivityMethod(Class<?> cls) {
        startActivity(new Intent(this, cls));
    }

    public void hideProgressBar() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }
        } catch (Exception e) {
            Log.e("BaseClassForInterface", "Error in hideProgressBar");
        }
    }


}
