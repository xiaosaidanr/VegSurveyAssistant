package com.thcreate.vegsurveyassistant.activity;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mIsHandleBackPressed = false;
    }


    private boolean mIsHandleBackPressed;
    public void setIsHandleBackPressed(boolean value){
        mIsHandleBackPressed = value;
    }

    private String mAlertDialogMessage = "放弃本次编辑?";
    private String mPositiveButtonText = "确定";
    private String mNegativeButtonText = "取消";
    public void setmAlertDialog(@Nullable String alertDialogMessage,
                                       @Nullable String positiveButtonText,
                                       @Nullable String negativeButtonText){
        if (alertDialogMessage != null) mAlertDialogMessage = alertDialogMessage;
        if (positiveButtonText != null) mPositiveButtonText = positiveButtonText;
        if (negativeButtonText != null) mNegativeButtonText = negativeButtonText;
    }



    @Override
    public void onBackPressed() {
        if (mIsHandleBackPressed){
            new AlertDialog.Builder(this)
                    .setMessage(mAlertDialogMessage)
                    .setPositiveButton(mPositiveButtonText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onPositiveButtonPressed();
                        }
                    })
                    .setNegativeButton(mNegativeButtonText, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onNegativeButtonPressed();
                        }
                    })
                    .show();
        }
        else {
            super.onBackPressed();
        }
    }

    public void onPositiveButtonPressed(){
        super.onBackPressed();
    }

    public void onNegativeButtonPressed(){
        return;
    }

}
