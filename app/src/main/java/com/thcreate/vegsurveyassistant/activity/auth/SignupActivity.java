package com.thcreate.vegsurveyassistant.activity.auth;

import android.os.CountDownTimer;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.http.api.AuthApi;
import com.thcreate.vegsurveyassistant.http.api.UserApi;
import com.thcreate.vegsurveyassistant.http.model.GetVerificationCodeResponse;
import com.thcreate.vegsurveyassistant.http.model.SignupRequestBody;
import com.thcreate.vegsurveyassistant.http.model.SignupResponse;
import com.thcreate.vegsurveyassistant.http.service.HttpServiceGenerator;
import com.thcreate.vegsurveyassistant.service.SessionManager;
import com.thcreate.vegsurveyassistant.util.HTTP;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    TextInputEditText phoneTextInputEditText;
    TextInputEditText verificationCodeTextInputEditText;
    TextInputEditText usernameTextInputEditText;
    TextInputEditText passwordTextInputEditText;
    Button registerButton;
    Button verificationCodeButton;
    Button setTokenButton;
    Button getUserButton;
    CountDownTimer mCountDownTimer = null;
    String verificationKey = null;

    String accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImp0aSI6ImM0NTBlYjM1Nzc4YzYwOTJkMjc1NWJkNjgxN2EyOGU0NGYxYmI4MGIzY2IyMTMzMTFjYjc0MzZhMmFmNmM3MTZlOTk2MWU2YjUwNDE2MTRkIn0.eyJhdWQiOiIxIiwianRpIjoiYzQ1MGViMzU3NzhjNjA5MmQyNzU1YmQ2ODE3YTI4ZTQ0ZjFiYjgwYjNjYjIxMzMxMWNiNzQzNmEyYWY2YzcxNmU5OTYxZTZiNTA0MTYxNGQiLCJpYXQiOjE1NTA2NTQ0OTksIm5iZiI6MTU1MDY1NDQ5OSwiZXhwIjoxNTUxOTUwNDk4LCJzdWIiOiIzIiwic2NvcGVzIjpbXX0.C7vcm7R_yP_ZvPum64-fYzPdN579uBjvCKp_3zGHR17EX6BaRCMW2wLwM0cUBvnTeiPWRB6D27xW7fHcy5dhBjfHlkJeKlFjs6dpXjYx0mQvJCvQTNZZT8zn8jadk8CQ6eNlE3ucCYU_B6bcg-l1vV2YTXeCO6jT5ZfcRedY6cjUQ-GKrdOyhqMGl81eFLexpI4PTX_thAXjIyyW5C2vJyFdDyNh5tnVtWuzi1vrH3AfKdBve7NQZd_qB4eifo5VvsujAfylPfzVqIC2TBpJ-UFstt7bB0lzD-4nl4OTL9FvLtSuqxya4A87YZqpksBZLgAlUaBkeINxGjSLCpg5Ho9E-NpzGIuO-ivZsvI1qL9ZFX2DXbOxn2ZyVfZ77vOIMcdGI6xGnn1raDHbmicke8_z6_MmasRYIbGgjX7z7KgDZmgPqMkFo3aw0KHIpnfY7oalCdO619ChKoc-txLDoJU4RmOLQKtU8kChP9Zs0s-zrZ-K1fkUn6WUr2DpAvPWmjOh-c0PmvPfRPzVBNlHOIVReQnefWae78w7M7j863Gg2rH_tnIa9Lsy5M8PU24-E-pT5xSKlCwOrK5KtyFRICSk_wYZHLitAwMwiLL45pFiPolHEgGP_L5D90wv1zaZN4w0126qywtGPtQAZ69o4pfvGhBoBuRSJgKIUz7CiLM";
    String refreshToken = "def50200ec2d1957bfe9160a84e6f112a9f5fc2e11ae3e5b8a12bacfb93219e63944b7c95be18b5e13c378a82a2cf69744ab0f138fd41a5766979ea4dbbf0763e8e0dd71f037acded0c0eb8e9806ecb4d7fe4f9b9fbcc83b2ffc3dbfb661e0689ff352cf43c1066b49bf974b79cd5cc1493f33ef7e2a4c04fd77a53c05935606562efae933d6930bf097e3809f81ec879c1cbc94870b9581dfb3f63be7734c9b76018fa8e11ec0dec0fa502a250010a7bfd209fb18b83704ef6a2089fa6a218bf0c6b5604a6a537b9388148daee7d169da41347f5523ee728386087fa09a3ce7e4f56128c93faa06faf4b0af3e525b377211300d5f0841e6fc0ce9056590340eb440db9f81d0b821f867d09e14c1125f7e333267e531f4fd3bf84dff7d9a449c1d50e54845d81856066f013c696bac61e8905012070b665eca5d99c857f0ed9ea4d897e6c0ded116af19cce0bc03308e10bf09cbc4afd983c7fe562d9f6b8470c4";
    String tokenType = "Bearer";
    int expiresIn = 1295999;

    AuthApi mRequest;
    UserApi mUserApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initBinding();
        initRequest();
        initListener();
    }

    private void initBinding(){
        phoneTextInputEditText = findViewById(R.id.phoneTextInputEditText);
        verificationCodeTextInputEditText = findViewById(R.id.verificationCodeTextInputEditText);
        usernameTextInputEditText = findViewById(R.id.usernameTextInputEditText);
        passwordTextInputEditText = findViewById(R.id.passwordTextInputEditText);
        registerButton = findViewById(R.id.registerButton);
        verificationCodeButton = findViewById(R.id.verificationCodeButton);
        setTokenButton = findViewById(R.id.setTokenButton);
        getUserButton = findViewById(R.id.getUserButton);
    }

    private void initRequest(){
        mRequest = HttpServiceGenerator.createService(AuthApi.class);
        mUserApi = HttpServiceGenerator.createService(UserApi.class);
    }

    private void initListener(){
        phoneTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (TextUtils.isDigitsOnly(s) && s.length() == 11){
                    verificationCodeButton.setEnabled(true);
                }
                else{
                    verificationCodeButton.setEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        verificationCodeButton.setOnClickListener((view)->{
            String phoneNumber = String.valueOf(phoneTextInputEditText.getText());

            String buttonText = String.valueOf(verificationCodeButton.getText());

            phoneTextInputEditText.setEnabled(false);

            verificationCodeButton.setEnabled(false);
            verificationCodeButton.setText(R.string.sending);

            getVerificationCode(buttonText, HTTP.PHONE, phoneNumber);

        });

        registerButton.setOnClickListener((view)->{
            CharSequence phoneNumber = phoneTextInputEditText.getText();
            if (TextUtils.isEmpty(phoneNumber)){
                Toast.makeText(getApplication(), getResources().getText(R.string.please_input_phone_number), Toast.LENGTH_SHORT).show();
                return;
            }
            if (!TextUtils.isDigitsOnly(phoneNumber) || phoneNumber.length() != 11){
                Toast.makeText(getApplication(), getResources().getText(R.string.please_input_valid_phone_number), Toast.LENGTH_SHORT).show();
                return;
            }
            CharSequence verificationCode = verificationCodeTextInputEditText.getText();
            if (TextUtils.isEmpty(verificationCode)){
                Toast.makeText(getApplication(), getResources().getText(R.string.please_input_password), Toast.LENGTH_SHORT).show();
                return;
            }
            if (!TextUtils.isDigitsOnly(verificationCode)){
                Toast.makeText(getApplication(), getResources().getText(R.string.please_input_valid_verification_code), Toast.LENGTH_SHORT).show();
            }
            CharSequence username = usernameTextInputEditText.getText();
            if (TextUtils.isEmpty(username)){
                Toast.makeText(getApplication(), getResources().getText(R.string.please_input_username), Toast.LENGTH_SHORT).show();
                return;
            }
            CharSequence password = passwordTextInputEditText.getText();
            if (TextUtils.isEmpty(username)){
                Toast.makeText(getApplication(), getResources().getText(R.string.please_input_password), Toast.LENGTH_SHORT).show();
                return;
            }
            signup();
//            login();
//            gotoMainActivity();
        });

        setTokenButton.setOnClickListener((view)->{
            SessionManager.setToken(accessToken, refreshToken, expiresIn, tokenType);
            Toast.makeText(getApplication(), "写入token成功", Toast.LENGTH_SHORT).show();
        });

        getUserButton.setOnClickListener((view)->{
            String token = "Bearer " + accessToken;
            Call<ResponseBody> call = mUserApi.getUser(token);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        });
    }

    private void getVerificationCode(String buttonOriginalName, String type, String phoneNumber){
        Call<GetVerificationCodeResponse> call = mRequest.getVerificationCode(type, phoneNumber);
        call.enqueue(new Callback<GetVerificationCodeResponse>() {
            @Override
            public void onResponse(Call<GetVerificationCodeResponse> call, Response<GetVerificationCodeResponse> response) {
                if (response.isSuccessful()){
                    verificationKey = response.body().verificationKey;
                    Log.d("testtesttest", verificationKey);
                    if (mCountDownTimer != null){
                        mCountDownTimer.cancel();
                        mCountDownTimer = null;
                    }
                    mCountDownTimer = new CountDownTimer(HTTP.VERIFICATION_CODE_GET_TIME_LIMIT, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            verificationCodeButton.setText(String.format("已发送%ss", String.valueOf(millisUntilFinished/1000)));
                        }

                        @Override
                        public void onFinish() {
                            phoneTextInputEditText.setEnabled(true);

                            verificationCodeButton.setEnabled(true);
                            verificationCodeButton.setText(R.string.reget_verification_code);
                        }
                    };
                    mCountDownTimer.start();
                }
                else {
                    onFailure(call, new Throwable());
                }
            }

            @Override
            public void onFailure(Call<GetVerificationCodeResponse> call, Throwable t) {
                phoneTextInputEditText.setEnabled(true);

                verificationCodeButton.setEnabled(true);
                verificationCodeButton.setText(buttonOriginalName);

                Toast.makeText(getApplication(), getResources().getText(R.string.net_unreachable), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void signup(){
        if (verificationKey == null){
            Toast.makeText(getApplication(), getResources().getText(R.string.verification_code_error), Toast.LENGTH_SHORT).show();
            return;
        }
        String verificationCode = String.valueOf(verificationCodeTextInputEditText.getText());
        String name = String.valueOf(usernameTextInputEditText.getText());
        String password = String.valueOf(passwordTextInputEditText.getText());
        String phone = String.valueOf(phoneTextInputEditText.getText());
        String type = HTTP.PHONE;

        Call<SignupResponse> call = mRequest.signup(new SignupRequestBody(verificationKey, verificationCode, name, password, phone, type).generateRequestBody());
        call.enqueue(new Callback<SignupResponse>() {
            @Override
            public void onResponse(Call<SignupResponse> call, Response<SignupResponse> response) {
                if (response.isSuccessful()){
                    Log.d("testtesttest", response.body().accessToken);
                    Log.d("testtesttest", response.body().refreshToken);
                    Log.d("testtesttest", response.body().expiresIn);
                }
                else {
                    onFailure(call, new Throwable());
                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                Toast.makeText(getApplication(), getResources().getText(R.string.net_unreachable), Toast.LENGTH_SHORT).show();
            }
        });
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
