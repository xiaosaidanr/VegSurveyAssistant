package com.thcreate.vegsurveyassistant;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.thcreate.vegsurveyassistant.activity.auth.LoginActivity;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.repository.PictureRepository;
import com.thcreate.vegsurveyassistant.repository.SpeciesRepository;
import com.thcreate.vegsurveyassistant.repository.SamplelandRepository;
import com.thcreate.vegsurveyassistant.repository.SamplepointRepository;
import com.thcreate.vegsurveyassistant.repository.SampleplotRepository;
import com.thcreate.vegsurveyassistant.repository.UserRepository;
import com.thcreate.vegsurveyassistant.service.ActivityCollector;
import com.thcreate.vegsurveyassistant.service.SessionManager;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.worker.UploadWorker;

import java.util.concurrent.TimeUnit;

import androidx.work.Configuration;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

public class BasicApp extends Application {

    private AppExecutors mAppExecutors;

    private static BasicApp instance;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();

        /*
         * 初始化后台数据上传程序
         */
        initWork();

        /*
         * 初始化定位sdk，建议在Application中创建
         */
        SDKInitializer.initialize(getApplicationContext());
        SDKInitializer.setCoordType(CoordType.BD09LL);

        instance = this;

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
    public PictureRepository getPictureRepository(){
        return PictureRepository.getInstance(this, getDatabase(), mAppExecutors);
    }

    public UserRepository getUserRepository(){
        return UserRepository.getInstance(this, getDatabase(), mAppExecutors);
    }

    public static BasicApp getAppliction(){
        return instance;
    }


    private void initWork(){
        WorkManager.initialize(this, new Configuration.Builder().build());
        //后台work运行的约束 只有在有网的情况下才运行
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        //设置周期运行的work 5min
        PeriodicWorkRequest uploadWorkRequest = new PeriodicWorkRequest.Builder(UploadWorker.class, 5, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .addTag(Macro.UPLOAD)
                .build();
        //添加唯一name的work 有重名的添加 则替换
        WorkManager.getInstance().enqueueUniquePeriodicWork(
                Macro.DATA_UPLOAD_UNIQUE_NAME,
                ExistingPeriodicWorkPolicy.REPLACE,
                uploadWorkRequest
        );
    }


    public void Logout(){
        SessionManager.logout();
        ActivityCollector.finishAll();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
