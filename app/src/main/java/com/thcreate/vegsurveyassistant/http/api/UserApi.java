package com.thcreate.vegsurveyassistant.http.api;

import com.thcreate.vegsurveyassistant.http.model.GetUserResponse;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserApi {
    //TODO user http api
    @GET("api/user")
    Observable<GetUserResponse> getUser();
//    Observable<GetUserResponse> getUser(@Header("Authorization") String accessToken);
}
