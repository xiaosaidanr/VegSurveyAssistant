package com.thcreate.vegsurveyassistant.http.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserApi {
    //TODO user http api
    @GET("api/user")
    Call<ResponseBody> getUser(@Header("Authorization") String accessToken);
}
