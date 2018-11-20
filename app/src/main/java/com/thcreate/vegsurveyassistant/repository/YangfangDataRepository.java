package com.thcreate.vegsurveyassistant.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.User;

public class YangfangDataRepository {

    private static YangfangDataRepository sINSTANCE;

    private final AppDatabase mDatabase;
    private final Context mApplicationContext;
    private final AppExecutors mAppExecutors;

    private YangfangDataRepository(final Context context, final AppDatabase database, final AppExecutors appExecutors){
        mApplicationContext = context;
        mDatabase = database;
        mAppExecutors = appExecutors;
    }

    public static YangfangDataRepository getInstance(final Context context, final AppDatabase database, final AppExecutors appExecutors) {
        if (sINSTANCE == null) {
            synchronized (WuzhongDataRepository.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new YangfangDataRepository(context, database, appExecutors);
                }
            }
        }
        return sINSTANCE;
    }



    public LiveData<QiaomuYangfang> getQiaomuYangfangByYangfangCode(String yangfangCode){
        return mDatabase.qiaomuYangfangDao().getQiaomuYangfangByYangfangCode(yangfangCode);
    }
    public void insertQiaomuyf(QiaomuYangfang data){
        mAppExecutors.diskIO().execute(()->{
            User currentUser = mDatabase.userDao().getCurrentUserSync();
            if (currentUser != null){
                data.userId = currentUser.id;
                mDatabase.qiaomuYangfangDao().insert(data);
            }
        });
    }
    public void updateQiaomuyf(QiaomuYangfang data){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.qiaomuYangfangDao().update(data);
        });
    }
    public LiveData<GuanmuYangfang> getGuanmuYangfangByYangfangCode(String yangfangCode){
        return mDatabase.guanmuYangfangDao().getGuanmuYangfangByYangfangCode(yangfangCode);
    }
    public void insertGuanmuyf(GuanmuYangfang data){
        mAppExecutors.diskIO().execute(()->{
            User currentUser = mDatabase.userDao().getCurrentUserSync();
            if (currentUser != null){
                data.userId = currentUser.id;
                mDatabase.guanmuYangfangDao().insert(data);
            }
        });
    }
    public void updateGuanmuyf(GuanmuYangfang data){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.guanmuYangfangDao().update(data);
        });
    }
    public LiveData<CaobenYangfang> getCaobenYangfangByYangfangCode(String yangfangCode){
        return mDatabase.caobenYangfangDao().getCaobenYangfangByYangfangCode(yangfangCode);
    }
    public void insertCaobenyf(CaobenYangfang data){
        mAppExecutors.diskIO().execute(()->{
            User currentUser = mDatabase.userDao().getCurrentUserSync();
            if (currentUser != null){
                data.userId = currentUser.id;
                mDatabase.caobenYangfangDao().insert(data);
            }
        });
    }
    public void updateCaobenyf(CaobenYangfang data){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.caobenYangfangDao().update(data);
        });
    }
}
