package com.thcreate.vegsurveyassistant.upload;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.http.api.SpeciesApi;
import com.thcreate.vegsurveyassistant.repository.SpeciesRepository;

import retrofit2.Retrofit;

public class SpeciesUploadService implements IUploadService {

    private BasicApp mApplication;
    private Retrofit mRetrofit;
    private SpeciesApi mRequest;
    private SpeciesRepository mSpeciesRepository;

    public SpeciesUploadService() {
        mApplication = BasicApp.getAppliction();
        mSpeciesRepository = mApplication.getSpeicesRepository();
    }

    @Override
    public void start() {

    }

    @Override
    public void cancel() {

    }
}
