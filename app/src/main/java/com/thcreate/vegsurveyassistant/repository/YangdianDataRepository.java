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

    private LiveData<Integer> mCurrentUserId;

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
        mCurrentUserId = Transformations.map(mCurrentUser, user->{
            if (user != null){
                return user.id;
            }
            else {
                return null;
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



//    public LiveData<User> getCurrentUser(){
//        return mCurrentUser;
//    }
    public LiveData<List<Yangdian>> loadAllYangdian() {
        return Transformations.switchMap(mCurrentUserId, id -> {
            if (id != null){
                return mDatabase.yangdianDao().getAllYangdianByUserId(id);
            }
            else{
                return null;
            }
        });
    }
    public LiveData<Yangdian> getYangdianByYangdianCode(String yangdianCode){
        return mDatabase.yangdianDao().getYangdianByYangdianCode(yangdianCode);
    }
    public void insertYangdian(Yangdian data){
        mAppExecutors.diskIO().execute(()->{
            User currentUser = mDatabase.userDao().getCurrentUserSync();
            if (currentUser != null){
                data.userId = currentUser.id;
                mDatabase.yangdianDao().insert(data);
            }
        });
    }
    public void updateYangdian(Yangdian data){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.yangdianDao().update(data);
        });
    }
    public void deleteYangdianById(int id){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.yangdianDao().deleteById(id);
        });
    }

}
