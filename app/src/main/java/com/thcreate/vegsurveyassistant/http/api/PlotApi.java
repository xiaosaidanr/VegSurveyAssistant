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

    @POST("api/data/lands/{land_id}/plots")
    <T> Call<ResponseBody> addPlot(@Path("land_id") String landId, @Body T plot);

    @PUT("api/data/lands/{land_id}/plots/{plot_id}")
    <T> Call<ResponseBody> updatePlot(@Path("land_id") String landId, @Path("plot_id") String plotId, @Body T plot);

    @DELETE("api/data/lands/{land_id}/plots/{plot_id}")
    Call<ResponseBody> deletePlot(@Path("land_id") String landId, @Path("plot_id") String plotId);

}
