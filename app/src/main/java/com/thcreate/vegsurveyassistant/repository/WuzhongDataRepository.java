package com.thcreate.vegsurveyassistant.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.entity.CaobenWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.User;

public class WuzhongDataRepository {

    private static WuzhongDataRepository sINSTANCE;

    private final AppDatabase mDatabase;
    private final Context mApplicationContext;
    private final AppExecutors mAppExecutors;

    private WuzhongDataRepository(final Context context, final AppDatabase database, final AppExecutors appExecutors){
        mApplicationContext = context;
        mDatabase = database;
        mAppExecutors = appExecutors;
    }
    public static WuzhongDataRepository getInstance(final Context context, final AppDatabase database, final AppExecutors appExecutors) {
        if (sINSTANCE == null) {
            synchronized (WuzhongDataRepository.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new WuzhongDataRepository(context, database, appExecutors);
                }
            }
        }
        return sINSTANCE;
    }



    public LiveData<QiaomuWuzhong> getQiaomuWuzhongByWuzhongCode(String wuzhongCode){
        return mDatabase.qiaomuWuzhongDao().getQiaomuWuzhongByWuzhongCode(wuzhongCode);
    }
    public void insertQiaomuwz(QiaomuWuzhong data){
        mAppExecutors.diskIO().execute(()->{
            User currentUser = mDatabase.userDao().getCurrentUserSync();
            if (currentUser != null){
                data.userId = currentUser.id;
                mDatabase.qiaomuWuzhongDao().insert(data);
            }
        });
    }
    public void updateQiaomuwz(QiaomuWuzhong data){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.qiaomuWuzhongDao().update(data);
        });
    }



    public LiveData<GuanmuWuzhong> getGuanmuWuzhongByWuzhongCode(String wuzhongCode){
        return mDatabase.guanmuWuzhongDao().getGuanmuWuzhongByWuzhongCode(wuzhongCode);
    }
    public void insertGuanmuwz(GuanmuWuzhong data){
        mAppExecutors.diskIO().execute(()->{
            User currentUser = mDatabase.userDao().getCurrentUserSync();
            if (currentUser != null){
                data.userId = currentUser.id;
                mDatabase.guanmuWuzhongDao().insert(data);
            }
        });
    }
    public void updateGuanmuwz(GuanmuWuzhong data){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.guanmuWuzhongDao().update(data);
        });
    }



    public LiveData<CaobenWuzhong> getCaobenWuzhongByWuzhongCode(String wuzhongCode){
        return mDatabase.caobenWuzhongDao().getCaobenWuzhongByWuzhongCode(wuzhongCode);
    }
    public void insertCaobenwz(CaobenWuzhong data){
        mAppExecutors.diskIO().execute(()->{
            User currentUser = mDatabase.userDao().getCurrentUserSync();
            if (currentUser != null){
                data.userId = currentUser.id;
                mDatabase.caobenWuzhongDao().insert(data);
            }
        });
    }
    public void updateCaobenwz(CaobenWuzhong data){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.caobenWuzhongDao().update(data);
        });
    }
}