package com.thcreate.vegsurveyassistant.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.content.Context;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.entity.SamplepointEntity;
import com.thcreate.vegsurveyassistant.db.entity.User;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.PointMainInfo;

import java.util.Date;
import java.util.List;

public class SamplepointRepository {

    private static SamplepointRepository sINSTANCE;

    private final AppDatabase mDatabase;
    private final Context mApplicationContext;
    private final AppExecutors mAppExecutors;

    private LiveData<User> mCurrentUser;

    private LiveData<Integer> mCurrentUserId;

    private SamplepointRepository(final Context context, final AppDatabase database, final AppExecutors appExecutors){
        mApplicationContext = context;
        mDatabase = database;
        mAppExecutors = appExecutors;
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
    public static SamplepointRepository getInstance(final Context context, final AppDatabase database, final AppExecutors appExecutors) {
        if (sINSTANCE == null) {
            synchronized (SamplepointRepository.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new SamplepointRepository(context, database, appExecutors);
                }
            }
        }
        return sINSTANCE;
    }

    public LiveData<List<PointMainInfo>> loadAllSamplepoint() {
        return Transformations.switchMap(mCurrentUserId, id -> {
            if (id != null){
                return mDatabase.samplepointDao().getPointMainInfoListByUserId(id);
            }
            else{
                return null;
            }
        });
    }
    public LiveData<SamplepointEntity> loadSamplepointByPointId(String pointId){
        return mDatabase.samplepointDao().getSamplepointEntityByPointId(pointId);
    }
    public void insertSamplepoint(SamplepointEntity data){
        Date dateNow = new Date();
        data.createAt = dateNow;
        data.updateAt = dateNow;
        mAppExecutors.diskIO().execute(()->{
            mDatabase.samplepointDao().insert(data);
        });
    }
    public void updateSamplepoint(SamplepointEntity data){
        Date dateNow = new Date();
        data.updateAt = dateNow;
        if (data.createAt == null){
            data.createAt = dateNow;
        }
        mAppExecutors.diskIO().execute(()->{
            mDatabase.samplepointDao().update(data);
        });
    }
    public void softDeleteSamplepointById(int id){
        long deleteAt = new Date().getTime();
        mAppExecutors.diskIO().execute(()->{
            mDatabase.samplepointDao().softDeleteById(id, deleteAt);
        });
    }
    public void deleteSamplepointById(int id){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.samplepointDao().deleteById(id);
        });
    }

}
