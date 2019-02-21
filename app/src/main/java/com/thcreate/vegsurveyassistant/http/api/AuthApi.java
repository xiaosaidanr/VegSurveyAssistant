package com.thcreate.vegsurveyassistant.http.api;

import com.thcreate.vegsurveyassistant.http.model.GetVerificationCodeResponse;
import com.thcreate.vegsurveyassistant.http.model.SignupRequestBody;
import com.thcreate.vegsurveyassistant.http.model.SignupResponse;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;

public interface AuthApi {

    @FormUrlEncoded
    @POST("/api/verificationCodes")
    Call<GetVerificationCodeResponse> getVerificationCode(@Field("type") String type, @Field("phone") String phone);

    @Multipart
    @POST("/api/users")
    Call<SignupResponse> signup(@PartMap Map<String, RequestBody> requestBodyMap);

}
