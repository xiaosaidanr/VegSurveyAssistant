package com.thcreate.vegsurveyassistant.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.entity.User;
import com.thcreate.vegsurveyassistant.db.entity.Yangdi;

public class YangdiDataRepository {

    private static YangdiDataRepository sINSTANCE;

    private final AppDatabase mDatabase;
    private final Context mApplicationContext;
    private final AppExecutors mAppExecutors;

    private YangdiDataRepository(final Context context, final AppDatabase database, final AppExecutors appExecutors){
        mApplicationContext = context;
        mDatabase = database;
        mAppExecutors = appExecutors;
    }
    public static YangdiDataRepository getInstance(final Context context, final AppDatabase database, final AppExecutors appExecutors) {
        if (sINSTANCE == null) {
            synchronized (WuzhongDataRepository.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new YangdiDataRepository(context, database, appExecutors);
                }
            }
        }
        return sINSTANCE;
    }



    public LiveData<Yangdi> getYangdiByYangdiCode(String yangdiCode){
        return mDatabase.yangdiDao().getYangdiByYangdiCode(yangdiCode);
    }
    public void insertYangdi(Yangdi data){
        mAppExecutors.diskIO().execute(()->{
            User currentUser = mDatabase.userDao().getCurrentUserSync();
            if (currentUser != null){
                data.userId = currentUser.id;
                mDatabase.yangdiDao().insert(data);
            }
        });
    }
    public void updateYangdi(Yangdi data){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.yangdiDao().update(data);
        });
    }
}
