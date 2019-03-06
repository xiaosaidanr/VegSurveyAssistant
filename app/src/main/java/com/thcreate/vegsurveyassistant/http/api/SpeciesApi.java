package com.thcreate.vegsurveyassistant.http.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SpeciesApi {

    @POST("api/data/lands/{land_id}/plots/{plot_id}/species")
    <T> Call<ResponseBody> addSpecies(@Path("land_id") String landId, @Path("plot_id") String plotId, @Body T species);

    @PUT("api/data/lands/{land_id}/plots/{plot_id}/species/{species_id}")
    <T> Call<ResponseBody> updateSpecies(@Path("land_id") String landId, @Path("plot_id") String plotId, @Path("species_id") String speciesId, @Body T species);

    @DELETE("api/data/lands/{land_id}/plots/{plot_id}/species/{species_id}")
    Call<ResponseBody> deleteSpecies(@Path("land_id") String landId, @Path("plot_id") String plotId, @Path("species_id") String speciesId);

}
