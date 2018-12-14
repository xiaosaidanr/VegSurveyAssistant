package com.thcreate.vegsurveyassistant;

import android.app.Application;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.repository.SpeciesRepository;
import com.thcreate.vegsurveyassistant.repository.SamplelandRepository;
import com.thcreate.vegsurveyassistant.repository.SamplepointRepository;
import com.thcreate.vegsurveyassistant.repository.SampleplotRepository;

public class BasicApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();

        /*
         * 初始化定位sdk，建议在Application中创建
         */
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.BD09LL);
    }

    public AppDatabase getDatabase(){
        return AppDatabase.getInstance(this, mAppExecutors);
    }

    public SamplepointRepository getSamplepointRepository(){
        return SamplepointRepository.getInstance(this, getDatabase(), mAppExecutors);
    }
    public SpeciesRepository getSpeicesRepository(){
        return SpeciesRepository.getInstance(this, getDatabase(), mAppExecutors);
    }
    public SampleplotRepository getSampleplotRepository(){
        return SampleplotRepository.getInstance(this, getDatabase(), mAppExecutors);
    }
    public SamplelandRepository getSamplelandRepository(){
        return SamplelandRepository.getInstance(this, getDatabase(), mAppExecutors);
    }

}
