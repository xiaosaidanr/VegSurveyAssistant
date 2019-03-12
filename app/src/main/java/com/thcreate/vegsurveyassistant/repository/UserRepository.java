package com.thcreate.vegsurveyassistant.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.entity.UserEntity;

public class UserRepository {

    private static UserRepository sINSTANCE;

    private final AppDatabase mDatabase;
    private final Context mApplicationContext;
    private final AppExecutors mAppExecutors;

    private UserRepository(final Context context, final AppDatabase database, final AppExecutors appExecutors){
        mApplicationContext = context;
        mDatabase = database;
        mAppExecutors = appExecutors;
    }
    public static UserRepository getInstance(final Context context, final AppDatabase database, final AppExecutors appExecutors) {
        if (sINSTANCE == null) {
            synchronized (UserRepository.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new UserRepository(context, database, appExecutors);
                }
            }
        }
        return sINSTANCE;
    }

    public void insert(int id, String name){
        UserEntity newUser = new UserEntity(id, name);
        mAppExecutors.diskIO().execute(()->{
            UserEntity oldUser = mDatabase.userDao().getUserById(newUser.id);
            if (oldUser == null){
                mDatabase.userDao().insert(newUser);
            }
            else {
                mDatabase.userDao().update(newUser);
            }
        });
    }

    public LiveData<UserEntity> getUserByIdAsync(int id){
        return mDatabase.userDao().getUserByIdAsync(id);
    }

}
