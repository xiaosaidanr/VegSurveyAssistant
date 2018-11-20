package com.thcreate.vegsurveyassistant;

import android.app.Application;

import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.repository.WuzhongDataRepository;
import com.thcreate.vegsurveyassistant.repository.YangdiDataRepository;
import com.thcreate.vegsurveyassistant.repository.YangdianDataRepository;
import com.thcreate.vegsurveyassistant.repository.YangfangDataRepository;

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
    }
    public WuzhongDataRepository getWuzhongDataRepository(){
        return WuzhongDataRepository.getInstance(this, getDatabase(), mAppExecutors);
    }
    public YangfangDataRepository getYangfangDataRepository(){
        return YangfangDataRepository.getInstance(this, getDatabase(), mAppExecutors);
    }
    public YangdiDataRepository getYangdiDataRepository(){
        return YangdiDataRepository.getInstance(this, getDatabase(), mAppExecutors);
    }

}
