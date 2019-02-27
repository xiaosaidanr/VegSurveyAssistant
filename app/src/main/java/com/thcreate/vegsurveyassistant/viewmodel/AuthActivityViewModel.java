package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.thcreate.vegsurveyassistant.BasicApp;
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
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

public class AuthActivityViewModel extends AndroidViewModel {

    private static final String TAG = "AuthActivityViewModel";

    private  AuthApi mAuthApi;
    private UserApi mUserApi;

    public User user;
    public MutableLiveData<Boolean> isGetUserSuccess;
    public MutableLiveData<GetVerificationCodeStatus> getVerificationCodeStatus;
    //为了防止用户在用某一个手机号获取验证码 然后注册的时候又不用这个手机号注册 则记录下成功获取验证码的手机号供注册使用
    private String verificationCodeRelatedPhoneNumber = null;
    private String verificationKey = null;

    public AuthActivityViewModel(@NonNull Application application) {
        super(application);
        initRequest();
        isGetUserSuccess = new MutableLiveData<>();
        isGetUserSuccess.setValue(false);
        getVerificationCodeStatus = new MutableLiveData<>();
        getVerificationCodeStatus.setValue(GetVerificationCodeStatus.NONE);
        user = new User();
    }

    private void initRequest(){
        mAuthApi = HttpServiceGenerator.getInstance().createService(AuthApi.class);
        mUserApi = HttpServiceGenerator.getInstance().createServiceAuth(UserApi.class);
    }

    public void login(){
        String phoneNumber = user.phoneNumber.get();
        String password = user.password.get();
        if (checkPhoneNumber(phoneNumber) && checkPassword(password)){
            Observable<Token> observable = mAuthApi.login(phoneNumber, password, HTTP.client_id, HTTP.client_secret, HTTP.PASSWORD);
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Token>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }
                        @Override
                        public void onNext(Token responseBody) {
                            SessionManager.setToken(responseBody);
                            getUserInfo();
                        }
                        @Override
                        public void onError(Throwable e) {
                            try {
                                Response response = ((HttpException) e).response();
                                handleLoginErrorResponse(response);
                            }
                            catch (Exception ex){
                                Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.net_unreachable), Toast.LENGTH_SHORT).show();
                                ex.printStackTrace();
                            }
                        }
                        @Override
                        public void onComplete() {
                        }
                    });
        }
    }

    public void signup(){
        String phoneNumber = user.phoneNumber.get();
        String verificationCode = user.verificationCode.get();
        String username = user.username.get();
        String password = user.password.get();
        if (checkPhoneNumber(phoneNumber) && checkVerificationCode(verificationCode) && checkVerificationKey() && checkUsername(username) && checkPassword(password)){
            Observable<Token> observable = mAuthApi.signup(new SignupRequestBody(verificationKey, verificationCode, username, password, verificationCodeRelatedPhoneNumber, HTTP.PHONE).generateRequestBody());
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Token>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                        }
                        @Override
                        public void onNext(Token responseBody) {
                            SessionManager.setToken(responseBody);
                            getUserInfo();
                        }
                        @Override
                        public void onError(Throwable e) {
                            try {
                                Response response = ((HttpException) e).response();
                                handleSignupErrorResponse(response);
                            }
                            catch (Exception ex){
                                Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.net_unreachable), Toast.LENGTH_SHORT).show();
                                ex.printStackTrace();
                            }
                        }
                        @Override
                        public void onComplete() {
                        }
                    });
            }
    }

    public void getVerificationCode(){
        getVerificationCodeStatus.setValue(GetVerificationCodeStatus.SENDING);
        String phoneNumber = user.phoneNumber.get();
        Observable<GetVerificationCodeResponse> observable = mAuthApi.getVerificationCode(HTTP.PHONE, phoneNumber);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetVerificationCodeResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(GetVerificationCodeResponse responseBody) {
                        Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.get_verification_code_success), Toast.LENGTH_SHORT).show();
                        verificationKey = responseBody.verificationKey;
                        verificationCodeRelatedPhoneNumber = phoneNumber;
                        getVerificationCodeStatus.setValue(GetVerificationCodeStatus.SUCCESS);
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            getVerificationCodeStatus.setValue(GetVerificationCodeStatus.FAIL);
                            Response response = ((HttpException) e).response();
                            handleSignupErrorResponse(response);
                        }
                        catch (Exception ex){
                            Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.net_unreachable), Toast.LENGTH_SHORT).show();
                            ex.printStackTrace();
                        }
                    }
                    @Override
                    public void onComplete() {
                        getVerificationCodeStatus.setValue(GetVerificationCodeStatus.NONE);
                    }
                });
    }

    private boolean checkPhoneNumber(String phoneNumber){
        if (TextUtils.isEmpty(phoneNumber)){
            Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.please_input_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!TextUtils.isDigitsOnly(phoneNumber) || phoneNumber.length() != 11){
            Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.please_input_valid_phone_number), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkVerificationCode(String verificationCode){
        if (TextUtils.isEmpty(verificationCode) || !TextUtils.isDigitsOnly(verificationCode)){
            Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.please_input_valid_verification_code), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkVerificationKey(){
        if (verificationKey == null){
            Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.verification_code_error), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkUsername(String username){
        if (TextUtils.isEmpty(username)){
            Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.please_input_username), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean checkPassword(String password){
        if (TextUtils.isEmpty(password)){
            Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.please_input_password), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password.length() < 6){
            Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.password_no_less_than_six_letter), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void getUserInfo(){
        Observable<GetUserResponse> observable = mUserApi.getUser();
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GetUserResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(GetUserResponse responseBody) {
                        afterGetUserInfo(responseBody.id, responseBody.name);
                        Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.login_success), Toast.LENGTH_SHORT).show();
                        isGetUserSuccess.setValue(true);
                    }
                    @Override
                    public void onError(Throwable e) {
                        try {
                            ResponseBody responseBody = ((HttpException) e).response().errorBody();
                            //TODO 处理获取用户信息失败
                            Toast.makeText(getApplication(), responseBody.string(), Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception ex){
                            Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.net_unreachable), Toast.LENGTH_SHORT).show();
                            ex.printStackTrace();
                        }
                    }
                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void afterGetUserInfo(int userId, String username){
        SessionManager.login(userId);
        ((BasicApp)getApplication()).getUserRepository().insert(userId, username);
    }

    private void handleLoginErrorResponse(Response response){
        switch (response.code()){
            case 401:
                Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.phone_password_dismatch), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    private void handleSignupErrorResponse(Response response){
        switch (response.code()){
            case 401:
                Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.verification_code_error), Toast.LENGTH_SHORT).show();
                break;
            case 422:
                Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.verification_code_expired), Toast.LENGTH_SHORT).show();
                break;
            case 500:
                Toast.makeText(getApplication(), getApplication().getResources().getText(R.string.phone_been_taken), Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
//        try {
//            String responseString = responseBody.;
//            Gson gson = new GsonBuilder()
//                    .excludeFieldsWithoutExposeAnnotation()
//                    .serializeNulls()
//                    .create();
//            ErrorResponse errorResponse = gson.fromJson(responseString, ErrorResponse.class);
//            if (errorResponse.statusCode != 200){
//                switch (errorResponse.statusCode){
//                    case 401:
//                        Toast.makeText(getApplication(), getResources().getText(R.string.verification_code_error), Toast.LENGTH_SHORT).show();
//                        break;
//                    case 422:
//                        Toast.makeText(getApplication(), getResources().getText(R.string.verification_code_expired), Toast.LENGTH_SHORT).show();
//                        break;
//                    case 500:
//                        Toast.makeText(getApplication(), getResources().getText(R.string.phone_been_taken), Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
    }

    public static class User {
        public final ObservableField<String> phoneNumber = new ObservableField<>();
        public final ObservableField<String> verificationCode = new ObservableField<>();
        public final ObservableField<String> username = new ObservableField<>();
        public final ObservableField<String> password = new ObservableField<>();
    }

    public enum GetVerificationCodeStatus {
        NONE,
        SENDING,
        SUCCESS,
        FAIL
    }

}
