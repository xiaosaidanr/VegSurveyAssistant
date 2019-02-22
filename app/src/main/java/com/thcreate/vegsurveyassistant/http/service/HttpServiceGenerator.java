package com.thcreate.vegsurveyassistant.http.service;

import com.thcreate.vegsurveyassistant.BuildConfig;
import com.thcreate.vegsurveyassistant.http.interceptor.TokenInterceptor;
import com.thcreate.vegsurveyassistant.util.HTTP;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpServiceGenerator {

    private static final String BASE_URL = HTTP.SERVICE_URL;

    private static volatile HttpServiceGenerator sINSTANCE;

    public static HttpServiceGenerator getInstance(){
        if (sINSTANCE == null){
            synchronized (HttpServiceGenerator.class){
                if (sINSTANCE == null){
                    sINSTANCE = new HttpServiceGenerator();
                }
            }
        }
        return sINSTANCE;
    }

    private HttpServiceGenerator() {

        logging = new HttpLoggingInterceptor();
        if (BuildConfig.DEBUG){
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        }
        else {
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
        }

        baseHttpClient = new OkHttpClient.Builder()
                .callTimeout(HTTP.REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(logging)
                .build();
        authHttpClient = baseHttpClient.newBuilder()
                .addInterceptor(new TokenInterceptor())
                .build();

        baseRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(baseHttpClient)
                .build();
        authRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(authHttpClient)
                .build();
    }

    private HttpLoggingInterceptor logging;

    private OkHttpClient baseHttpClient;
    private  OkHttpClient authHttpClient;

    private Retrofit baseRetrofit;
    private Retrofit authRetrofit;


    public <S> S createService(Class<S> serviceClass){
        return baseRetrofit.create(serviceClass);
    }

    public <S> S createServiceAuth(Class<S> serviceClass){
        return authRetrofit.create(serviceClass);
    }

}
