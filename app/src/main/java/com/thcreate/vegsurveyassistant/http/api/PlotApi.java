package com.thcreate.vegsurveyassistant.http.api;

import com.thcreate.vegsurveyassistant.db.entity.model.BaseSampleplot;
import com.thcreate.vegsurveyassistant.db.entity.model.HerbSampleplot;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PlotApi {

    @POST("api/data/plots")
    <T> Call<ResponseBody> addPlot(@Body T plot);

    @PUT("api/data/plots/{plot_id}")
    <T> Call<ResponseBody> updatePlot(@Path("plot_id") String plotId, @Body T plot);

    @DELETE("api/data/plots/{plot_id}")
    Call<ResponseBody> deletePlot(@Path("plot_id") String plotId);

}
