package com.thcreate.vegsurveyassistant.http.service;

import com.thcreate.vegsurveyassistant.BuildConfig;
import com.thcreate.vegsurveyassistant.util.HTTP;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpServiceGenerator {

    private static final String BASE_URL = HTTP.SERVICE_URL;

    private static Retrofit.Builder builder =
            new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = builder.build();

    private static HttpLoggingInterceptor logging =
            new HttpLoggingInterceptor();

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder()
                    .callTimeout(HTTP.REQUEST_TIMEOUT, TimeUnit.SECONDS);


    public static <S> S createService(Class<S> serviceClass){
        if (!httpClient.interceptors().contains(logging)){
            if (BuildConfig.DEBUG){
                logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            }
            else {
                logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            }
            httpClient.addInterceptor(logging);
            builder.client(httpClient.build());
            retrofit = builder.build();
        }
        return retrofit.create(serviceClass);
    }

}
