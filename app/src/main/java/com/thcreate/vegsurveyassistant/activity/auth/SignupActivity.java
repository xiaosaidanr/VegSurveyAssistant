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
import com.thcreate.vegsurveyassistant.http.model.GetUserResponse;
import com.thcreate.vegsurveyassistant.http.model.GetVerificationCodeResponse;
import com.thcreate.vegsurveyassistant.http.model.SignupRequestBody;
import com.thcreate.vegsurveyassistant.http.model.Token;
import com.thcreate.vegsurveyassistant.http.service.HttpServiceGenerator;
import com.thcreate.vegsurveyassistant.service.SessionManager;
import com.thcreate.vegsurveyassistant.util.HTTP;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    TextInputEditText phoneTextInputEditText;
    TextInputEditText verificationCodeTextInputEditText;
    TextInputEditText usernameTextInputEditText;
    TextInputEditText passwordTextInputEditText;
    Button registerButton;
    Button verificationCodeButton;
    Button getUserButton;
    Button loginButton;
    Button refreshTokenButton;
    CountDownTimer mCountDownTimer = null;
    String verificationKey = null;

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
        getUserButton = findViewById(R.id.getUserButton);
        loginButton = findViewById(R.id.loginButton);
        refreshTokenButton = findViewById(R.id.refreshTokenButton);
    }

    private void initRequest(){
        mRequest = HttpServiceGenerator.getInstance().createService(AuthApi.class);
        mUserApi = HttpServiceGenerator.getInstance().createServiceAuth(UserApi.class);
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
        });

        getUserButton.setOnClickListener((view)->{
//            String accessToken = "Bearer " + SessionManager.getToken().accessToken;
//            Observable<GetUserResponse> observable = mUserApi.getUser(accessToken);
            Observable<GetUserResponse> observable = mUserApi.getUser();
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<GetUserResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }
                        @Override
                        public void onNext(GetUserResponse responseBody) {
                            Log.d("testtesttest", String.valueOf(responseBody.id));
                            Log.d("testtesttest", String.valueOf(responseBody.name));
                            Log.d("testtesttest", String.valueOf(responseBody.email));
                            Log.d("testtesttest", String.valueOf(responseBody.avatar));
                            Log.d("testtesttest", String.valueOf(responseBody.boundPhone));
                            Log.d("testtesttest", String.valueOf(responseBody.boundWechat));
                            Log.d("testtesttest", String.valueOf(responseBody.createdAt));
                            Log.d("testtesttest", String.valueOf(responseBody.updatedAt));
                            Toast.makeText(getApplication(), "获取用户信息成功", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, e.getMessage());
                        }
                        @Override
                        public void onComplete() {
                        }
                    });
        });

        loginButton.setOnClickListener((view)->{
            Observable<Token> observable = mRequest.login("13521936487", "123456", HTTP.client_id, HTTP.client_secret, HTTP.PASSWORD);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Token>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }
                        @Override
                        public void onNext(Token responseBody) {
                            Log.d("testtesttest", String.valueOf(responseBody.tokenType));
                            Log.d("testtesttest", String.valueOf(responseBody.expiresIn));
                            Log.d("testtesttest", String.valueOf(responseBody.accessToken));
                            Log.d("testtesttest", String.valueOf(responseBody.refreshToken));

                            Toast.makeText(getApplication(), "登录成功", Toast.LENGTH_SHORT).show();

                            SessionManager.setToken(responseBody);
                        }
                        @Override
                        public void onError(Throwable e) {
                        }
                        @Override
                        public void onComplete() {
                        }
                    });
        });

        refreshTokenButton.setOnClickListener((view)->{
//            Token token = SessionManager.getToken();
//            String accessToken = "Bearer " + token.accessToken;
//            String refreshToken = token.refreshToken;
//            Observable<Token> observable = mRequest.refreshToken(accessToken, HTTP.REFRESH_TOKEN, HTTP.client_id, HTTP.client_secret, refreshToken);
//            observable.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<Token>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//                        }
//                        @Override
//                        public void onNext(Token responseBody) {
//                            Log.d("testtesttest", String.valueOf(responseBody.tokenType));
//                            Log.d("testtesttest", String.valueOf(responseBody.expiresIn));
//                            Log.d("testtesttest", String.valueOf(responseBody.accessToken));
//                            Log.d("testtesttest", String.valueOf(responseBody.refreshToken));
//
//                            Toast.makeText(getApplication(), "token刷新成功", Toast.LENGTH_SHORT).show();
//
//                            SessionManager.setToken(responseBody);
//                        }
//                        @Override
//                        public void onError(Throwable e) {
//                        }
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
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

        Call<Token> call = mRequest.signup(new SignupRequestBody(verificationKey, verificationCode, name, password, phone, type).generateRequestBody());
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getApplication(), "注册成功", Toast.LENGTH_SHORT).show();
                    SessionManager.setToken(response.body());
                }
                else {
                    onFailure(call, new Throwable());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
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
