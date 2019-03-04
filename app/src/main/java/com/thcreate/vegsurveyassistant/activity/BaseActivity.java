package com.thcreate.vegsurveyassistant.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.thcreate.vegsurveyassistant.activity.auth.LoginActivity;
import com.thcreate.vegsurveyassistant.activity.auth.SignupActivity;
import com.thcreate.vegsurveyassistant.service.ActivityCollector;
import com.thcreate.vegsurveyassistant.service.SessionManager;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mIsHandleBackPressed = false;
        checkIsLoggedIn();
    }

    private void checkIsLoggedIn(){
        if (!this.getClass().getSimpleName().equals(LoginActivity.class.getSimpleName()) && !this.getClass().getSimpleName().equals(SignupActivity.class.getSimpleName())){
            if (!SessionManager.isLoggedIn()){
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
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

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText（特殊情况就是轨迹求或者实体案件会移动焦点）
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时没必要隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            if (event.getX() > left && event.getX() < right && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditView上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 多种隐藏软件盘方法的其中一种
     *
     * @param token
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
