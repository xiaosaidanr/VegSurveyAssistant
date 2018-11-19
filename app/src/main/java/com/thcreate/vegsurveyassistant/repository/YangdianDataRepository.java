package com.thcreate.vegsurveyassistant.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.dao.UserDao;
import com.thcreate.vegsurveyassistant.db.entity.User;
import com.thcreate.vegsurveyassistant.db.entity.Yangdian;

import java.util.List;

public class YangdianDataRepository {

    private static YangdianDataRepository sINSTANCE;

    private final AppDatabase mDatabase;

    private final Context mApplicationContext;

    private final AppExecutors mAppExecutors;

    private LiveData<User> mCurrentUser;

    private MediatorLiveData<Integer> mCurrentUserId;

    private YangdianDataRepository(final Context context, final AppDatabase database, final AppExecutors appExecutors){
        mApplicationContext = context;
        mDatabase = database;
        mAppExecutors = appExecutors;

        appExecutors.diskIO().execute(()->{
            User user = mDatabase.userDao().getCurrentUserSync();
            if (user == null){
                User newUser = new User("13521936487", 1);
                mDatabase.userDao().insert(newUser);
            }
        });

        mCurrentUser = mDatabase.userDao().getCurrentUserAsync();
        mCurrentUserId = new MediatorLiveData<>();
        mCurrentUserId.addSource(mCurrentUser,
            userEntity -> {
                if (mDatabase.getDatabaseCreated().getValue() != null) {
                    mCurrentUserId.postValue(userEntity.id);
                }
            });
    }

    public static YangdianDataRepository getInstance(final Context context, final AppDatabase database, final AppExecutors appExecutors) {
        if (sINSTANCE == null) {
            synchronized (YangdianDataRepository.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new YangdianDataRepository(context, database, appExecutors);
                }
            }
        }
        return sINSTANCE;
    }


    public LiveData<User> getCurrentUser(){
        return mCurrentUser;
    }

//    public LiveData<List<Yangdian>> loadAllYangdian() {
//        return mDatabase.yangdianDao().getAllYangdianByUserId(mCurrentUserId.getValue());
//    }
//
//    public LiveData<Yangdian> getYangdianByYangdianCode(String yangdianCode){
//        return mDatabase.yangdianDao().getYangdianByYangdianCode(yangdianCode);
//    }
//
//    public void insertYangdian(Yangdian data){
//
//    }
//
//    public void updateYangdian(Yangdian data){
//
//    }

}
