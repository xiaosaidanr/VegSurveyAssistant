package com.thcreate.vegsurveyassistant;

import android.app.Application;

import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.repository.YangdianDataRepository;

public class BasicApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public AppDatabase getDatabase(){
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public YangdianDataRepository getYangdianDataRepository(){
        return YangdianDataRepository.getInstance(this, getDatabase(), mAppExecutors);
    };

}
