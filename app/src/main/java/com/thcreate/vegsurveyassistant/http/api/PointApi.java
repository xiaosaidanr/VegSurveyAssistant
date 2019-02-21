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

    @GET("api/data/points/{point_id}")
    Call<Samplepoint> getPoint(@Path("point_id") String pointId);

    @GET("api/data/points")
    Call<List<Samplepoint>> getPoints();

    @POST("api/data/points")
    Call<ResponseBody> addPoint(@Body Samplepoint point);

    @PUT("api/data/points/{point_id}")
    Call<ResponseBody> updatePoint(@Path("point_id") String pointId, @Body Samplepoint point);

    @DELETE("api/data/points/{point_id}")
    Call<ResponseBody> deletePoint(@Path("point_id") String pointId);

}
