package com.thcreate.vegsurveyassistant.http.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthApi {

    @FormUrlEncoded
    @POST("/verificationCodes")
    Call<ResponseBody> getVerificationCode(@Field("type") String type, @Field("phone") String phone);

    @FormUrlEncoded
    @POST("/login")
    Call<ResponseBody> login(@Field("phone") String phone, @Field("verificationCode") String verificationCode);

}
