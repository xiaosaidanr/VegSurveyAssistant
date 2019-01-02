package com.thcreate.vegsurveyassistant.http.api;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface SpeciesApi {

    @POST("data/species")
    <T> Call<ResponseBody> addSpecies(@Body T species);

    @PUT("data/species/{species_id}")
    <T> Call<ResponseBody> updateSpecies(@Path("species_id") String speciesId, @Body T species);

    @DELETE("data/species/{species_id}")
    Call<ResponseBody> deleteSpecies(@Path("species_id") String speciesId);

}
