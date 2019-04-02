package com.thcreate.vegsurveyassistant.activity.auth;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.CountDownTimer;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.activity.BaseActivity;
import com.thcreate.vegsurveyassistant.activity.MainActivity;
import com.thcreate.vegsurveyassistant.databinding.ActivitySignupBinding;
import com.thcreate.vegsurveyassistant.util.HTTP;
import com.thcreate.vegsurveyassistant.viewmodel.AuthActivityViewModel;

public class SignupActivity extends BaseActivity {

    private static final String TAG = "SignupActivity";

    private ActivitySignupBinding mBinding;
    private AuthActivityViewModel mViewModel;

    TextInputEditText phoneTextInputEditText;
    TextInputEditText verificationCodeTextInputEditText;
    MaterialButton getVerificationCodeButton;

    CountDownTimer mCountDownTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(AuthActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_signup);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);

        phoneTextInputEditText = mBinding.phoneTextInputEditText;
        verificationCodeTextInputEditText = mBinding.verificationCodeTextInputEditText;
        getVerificationCodeButton = mBinding.getVerificationCodeButton;

        initBinding();
        initListener();
    }

    private void initBinding(){
        mViewModel.isGetUserSuccess.observe(this, (isGetUserSuccess)->{
            if (isGetUserSuccess != null){
                if (isGetUserSuccess){
                    Intent intent = new Intent(this, MainActivity.class);
                    intent.putExtra("activity", this.getClass().getSimpleName());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
            }
        });

        mViewModel.getVerificationCodeStatus.observe(this, (getVerificationCodeStatus)->{
            if (getVerificationCodeStatus != null){
                switch (getVerificationCodeStatus){
                    case NONE:
                        break;
                    case SENDING:
                        phoneTextInputEditText.setEnabled(false);
                        getVerificationCodeButton.setEnabled(false);
                        getVerificationCodeButton.setText(R.string.sending);
                        break;
                    case SUCCESS:
                        if (mCountDownTimer != null){
                            mCountDownTimer.cancel();
                            mCountDownTimer = null;
                        }
                        mCountDownTimer = new CountDownTimer(HTTP.VERIFICATION_CODE_GET_TIME_LIMIT, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                getVerificationCodeButton.setText(String.format("已发送%ss", String.valueOf(millisUntilFinished/1000)));
                            }
                            @Override
                            public void onFinish() {
                                phoneTextInputEditText.setEnabled(true);
                                getVerificationCodeButton.setEnabled(true);
                                getVerificationCodeButton.setText(R.string.get_verification_code);
                            }
                        };
                        mCountDownTimer.start();
                        break;
                    case FAIL:
                        phoneTextInputEditText.setEnabled(true);
                        getVerificationCodeButton.setEnabled(true);
                        getVerificationCodeButton.setText(R.string.get_verification_code);
                    default:
                        break;
                }
            }
        });
    }

    private void initListener(){
        phoneTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isDigitsOnly(s) && s.length() == 11){
                    getVerificationCodeButton.setEnabled(true);
                }
                else{
                    getVerificationCodeButton.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void getVerificationCode(View v){
        mViewModel.getVerificationCode();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountDownTimer != null){
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }
}
