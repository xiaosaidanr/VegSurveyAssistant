package com.thcreate.vegsurveyassistant.http.api;

import com.thcreate.vegsurveyassistant.db.entity.model.Picture;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PictureApi {

    @POST("data/pictures")
    Call<ResponseBody> addPicture(@Body Picture picture);

    @DELETE("data/pictures/{picture_id}")
    Call<ResponseBody> deletePicture(@Path("picture_id") String pictureId);

}
