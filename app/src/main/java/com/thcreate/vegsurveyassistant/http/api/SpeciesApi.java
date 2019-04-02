package com.thcreate.vegsurveyassistant.http.api;

import com.thcreate.vegsurveyassistant.db.entity.model.ArborSpecies;
import com.thcreate.vegsurveyassistant.db.entity.model.BaseSpecies;
import com.thcreate.vegsurveyassistant.db.entity.model.HerbSpecies;
import com.thcreate.vegsurveyassistant.db.entity.model.ShrubSpecies;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SpeciesApi {

//    @POST("api/data/lands/{land_id}/plots/{plot_id}/species")
//    Call<ResponseBody> addArborSpecies(@Path("land_id") String landId, @Path("plot_id") String plotId, @Body ArborSpecies species);
//
//    @POST("api/data/lands/{land_id}/plots/{plot_id}/species")
//    Call<ResponseBody> addHerbSpecies(@Path("land_id") String landId, @Path("plot_id") String plotId, @Body HerbSpecies species);
//
//    @POST("api/data/lands/{land_id}/plots/{plot_id}/species")
//    Call<ResponseBody> addShrubSpecies(@Path("land_id") String landId, @Path("plot_id") String plotId, @Body ShrubSpecies species);
    @POST("api/data/lands/{land_id}/plots/{plot_id}/species")
    Call<ResponseBody> addSpecies(@Path("land_id") String landId, @Path("plot_id") String plotId, @Body BaseSpecies species);

//    @PUT("api/data/lands/{land_id}/plots/{plot_id}/species/{species_id}")
//    Call<ResponseBody> updateArborSpecies(@Path("land_id") String landId, @Path("plot_id") String plotId, @Path("species_id") String speciesId, @Body ArborSpecies species);
//
//    @PUT("api/data/lands/{land_id}/plots/{plot_id}/species/{species_id}")
//    Call<ResponseBody> updateHerbSpecies(@Path("land_id") String landId, @Path("plot_id") String plotId, @Path("species_id") String speciesId, @Body HerbSpecies species);
//
//    @PUT("api/data/lands/{land_id}/plots/{plot_id}/species/{species_id}")
//    Call<ResponseBody> updateShrubSpecies(@Path("land_id") String landId, @Path("plot_id") String plotId, @Path("species_id") String speciesId, @Body ShrubSpecies species);
    @PUT("api/data/lands/{land_id}/plots/{plot_id}/species/{species_id}")
    Call<ResponseBody> updateSpecies(@Path("land_id") String landId, @Path("plot_id") String plotId, @Path("species_id") String speciesId, @Body BaseSpecies species);

    @DELETE("api/data/lands/{land_id}/plots/{plot_id}/species/{species_id}")
    Call<ResponseBody> deleteSpecies(@Path("land_id") String landId, @Path("plot_id") String plotId, @Path("species_id") String speciesId);

}
