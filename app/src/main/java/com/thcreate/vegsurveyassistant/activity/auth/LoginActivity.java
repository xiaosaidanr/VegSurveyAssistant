package com.thcreate.vegsurveyassistant.activity.auth;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.activity.BaseActivity;
import com.thcreate.vegsurveyassistant.activity.MainActivity;
import com.thcreate.vegsurveyassistant.databinding.ActivityLoginBinding;
import com.thcreate.vegsurveyassistant.service.ActivityCollector;
import com.thcreate.vegsurveyassistant.viewmodel.AuthActivityViewModel;

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";

    private ActivityLoginBinding mBinding;
    private AuthActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AuthActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
        initBinding();
    }

    private void initBinding(){
        mViewModel.isGetUserSuccess.observe(this, (isGetUserSuccess)->{
            if (isGetUserSuccess != null){
                if (isGetUserSuccess){
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    public void forgetPassword(View v) {
        Log.v(TAG, "忘记密码被点击");
    }

    public void register(View v) {
        Intent intent = new Intent(this, SignupActivity.class);
        startActivity(intent);
    }

    private long mFirstBackButtonPressTime = 0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if (System.currentTimeMillis()-mFirstBackButtonPressTime>2000){
                Toast.makeText(this,R.string.press_again_to_exit,Toast.LENGTH_SHORT).show();
                mFirstBackButtonPressTime = System.currentTimeMillis();
            }
            else {
                ActivityCollector.finishAll();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
