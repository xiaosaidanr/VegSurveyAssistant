package com.thcreate.vegsurveyassistant.http.api;

import com.thcreate.vegsurveyassistant.db.entity.model.Sampleland;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LandApi {

    @GET("api/data/lands/{land_id}")
    Call<Sampleland> getLand(@Path("land_id") String landId);

    @GET("api/data/lands")
    Call<List<Sampleland>> getLands();

    @POST("api/data/lands")
    Call<ResponseBody> addLand(@Body Sampleland land);

    @PUT("api/data/lands/{land_id}")
    Call<ResponseBody> updateLand(@Path("land_id") String landId, @Body Sampleland land);

    @DELETE("api/data/lands/{land_id}")
    Call<ResponseBody> deleteLand(@Path("land_id") String landId);

}
