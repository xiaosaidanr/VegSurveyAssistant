package com.thcreate.vegsurveyassistant.activity.auth;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Toast;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.activity.BaseActivity;
import com.thcreate.vegsurveyassistant.activity.MainActivity;
import com.thcreate.vegsurveyassistant.http.api.AuthApi;
import com.thcreate.vegsurveyassistant.http.model.GetVerificationCodeResponse;
import com.thcreate.vegsurveyassistant.http.service.HttpServiceGenerator;
import com.thcreate.vegsurveyassistant.service.ActivityCollector;
import com.thcreate.vegsurveyassistant.service.SessionManager;
import com.thcreate.vegsurveyassistant.util.HTTP;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    TextInputEditText phoneTextInputEditText;
    TextInputEditText verificationCodeInputEditText;
    MaterialButton loginButton;
    MaterialButton getVerificationCodeButton;
    CountDownTimer mCountDownTimer = null;
    AuthApi mRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initBinding();
        initRequest();
        initListener();
    }

    private void initBinding(){
        phoneTextInputEditText = findViewById(R.id.phoneTextInputEditText);
        verificationCodeInputEditText = findViewById(R.id.verificationCodeTextInputEditText);
        loginButton = findViewById(R.id.buttonLogin);
        getVerificationCodeButton = findViewById(R.id.buttonGetVerificationCode);
    }

    private void initRequest(){
        mRequest = HttpServiceGenerator.createService(AuthApi.class);
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
        getVerificationCodeButton.setOnClickListener((view)->{
            String phoneNumber = String.valueOf(phoneTextInputEditText.getText());

            String buttonText = String.valueOf(getVerificationCodeButton.getText());

            phoneTextInputEditText.setEnabled(false);

            getVerificationCodeButton.setEnabled(false);
            getVerificationCodeButton.setText(R.string.sending);

            Call<GetVerificationCodeResponse> call = mRequest.getVerificationCode(HTTP.PHONE, phoneNumber);
            call.enqueue(new Callback<GetVerificationCodeResponse>() {
                @Override
                public void onResponse(Call<GetVerificationCodeResponse> call, Response<GetVerificationCodeResponse> response) {
                    if (response.body() == null){
                        onFailure(call, new Throwable());
                        return;
                    }
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
                            getVerificationCodeButton.setText(R.string.reget_verification_code);
                        }
                    };
                    mCountDownTimer.start();
                }

                @Override
                public void onFailure(Call<GetVerificationCodeResponse> call, Throwable t) {
                    phoneTextInputEditText.setEnabled(true);

                    getVerificationCodeButton.setEnabled(true);
                    getVerificationCodeButton.setText(buttonText);

                    Toast.makeText(getApplication(), getResources().getText(R.string.net_unreachable), Toast.LENGTH_SHORT).show();
                }
            });
        });
        loginButton.setOnClickListener((view)->{
            CharSequence phoneNumber = phoneTextInputEditText.getText();
            if (TextUtils.isEmpty(phoneNumber)){
                Toast.makeText(getApplication(), getResources().getText(R.string.please_input_phone_number), Toast.LENGTH_SHORT).show();
                return;
            }
            if (!TextUtils.isDigitsOnly(phoneNumber) || phoneNumber.length() != 11){
                Toast.makeText(getApplication(), getResources().getText(R.string.please_input_valid_phone_number), Toast.LENGTH_SHORT).show();
                return;
            }
            CharSequence verificationCode = verificationCodeInputEditText.getText();
            if (TextUtils.isEmpty(verificationCode)){
                Toast.makeText(getApplication(), getResources().getText(R.string.please_input_verification_code), Toast.LENGTH_SHORT).show();
                return;
            }
            if (!TextUtils.isDigitsOnly(verificationCode)){
                Toast.makeText(getApplication(), getResources().getText(R.string.please_input_valid_verification_code), Toast.LENGTH_SHORT).show();
            }
            login();
            gotoMainActivity();
        });
    }

    private void login(){
        //TODO userid1
        SessionManager.login(1);
    }

    private void gotoMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
        if (mCountDownTimer != null){
            mCountDownTimer.cancel();
            mCountDownTimer = null;
        }
    }
}
