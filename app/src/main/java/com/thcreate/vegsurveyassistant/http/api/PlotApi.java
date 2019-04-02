package com.thcreate.vegsurveyassistant.http.api;

import com.thcreate.vegsurveyassistant.db.entity.model.ArborSampleplot;
import com.thcreate.vegsurveyassistant.db.entity.model.BaseSampleplot;
import com.thcreate.vegsurveyassistant.db.entity.model.HerbSampleplot;
import com.thcreate.vegsurveyassistant.db.entity.model.ShrubSampleplot;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PlotApi {

//    @POST("api/data/lands/{land_id}/plots")
//    Call<ResponseBody> addArborPlot(@Path("land_id") String landId, @Body ArborSampleplot plot);
//
//    @POST("api/data/lands/{land_id}/plots")
//    Call<ResponseBody> addHerbPlot(@Path("land_id") String landId, @Body HerbSampleplot plot);
//
//    @POST("api/data/lands/{land_id}/plots")
//    Call<ResponseBody> addShrubPlot(@Path("land_id") String landId, @Body ShrubSampleplot plot);
    @POST("api/data/lands/{land_id}/plots")
    Call<ResponseBody> addPlot(@Path("land_id") String landId, @Body BaseSampleplot plot);

//    @PUT("api/data/lands/{land_id}/plots/{plot_id}")
//    Call<ResponseBody> updateArborPlot(@Path("land_id") String landId, @Path("plot_id") String plotId, @Body ArborSampleplot plot);
//
//    @PUT("api/data/lands/{land_id}/plots/{plot_id}")
//    Call<ResponseBody> updateHerbPlot(@Path("land_id") String landId, @Path("plot_id") String plotId, @Body HerbSampleplot plot);
//
//    @PUT("api/data/lands/{land_id}/plots/{plot_id}")
//    Call<ResponseBody> updateShrubPlot(@Path("land_id") String landId, @Path("plot_id") String plotId, @Body ShrubSampleplot plot);
    @PUT("api/data/lands/{land_id}/plots/{plot_id}")
    Call<ResponseBody> updatePlot(@Path("land_id") String landId, @Path("plot_id") String plotId, @Body BaseSampleplot plot);

    @DELETE("api/data/lands/{land_id}/plots/{plot_id}")
    Call<ResponseBody> deletePlot(@Path("land_id") String landId, @Path("plot_id") String plotId);

}
