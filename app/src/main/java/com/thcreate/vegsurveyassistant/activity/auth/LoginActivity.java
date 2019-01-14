package com.thcreate.vegsurveyassistant.activity.auth;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.button.MaterialButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.activity.BaseActivity;
import com.thcreate.vegsurveyassistant.activity.MainActivity;
import com.thcreate.vegsurveyassistant.http.api.AuthApi;
import com.thcreate.vegsurveyassistant.service.ActivityCollector;
import com.thcreate.vegsurveyassistant.service.SessionManager;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends BaseActivity {

    TextInputEditText phoneTextInputEditText;
    TextInputEditText verificationCodeInputEditText;
    MaterialButton loginButton;
    MaterialButton getVerificationCodeButton;
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
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().connectTimeout(4, TimeUnit.SECONDS);
        String baseUrl = "https://www.baidu.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .build();
        mRequest = retrofit.create(AuthApi.class);
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

            Call<ResponseBody> call = mRequest.getVerificationCode("phone", phoneNumber);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    new CountDownTimer(30000, 1000) {
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
                    }.start();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
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

}
