package com.thcreate.vegsurveyassistant.http.api;

import com.thcreate.vegsurveyassistant.db.entity.model.Samplepoint;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PointApi {

    @GET("data/points/{point_id}")
    Call<Samplepoint> getPoint(@Path("point_id") String pointId);

    @GET("data/points")
    Call<List<Samplepoint>> getPoints();

    @POST("data/points")
    Call<ResponseBody> addPoint(@Body Samplepoint point);

    @PUT("data/points/{point_id}")
    Call<ResponseBody> updatePoint(@Path("point_id") String pointId, @Body Samplepoint point);

    @DELETE("data/points/{point_id}")
    Call<ResponseBody> deletePoint(@Path("point_id") String pointId);

}
