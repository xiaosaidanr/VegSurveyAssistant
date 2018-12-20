package com.thcreate.vegsurveyassistant.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.entity.PictureEntity;

import java.util.Date;
import java.util.List;

public class PictureRepository {

    private static PictureRepository sINSTANCE;

    private final AppDatabase mDatabase;
    private final Context mApplicationContext;
    private final AppExecutors mAppExecutors;

    private PictureRepository(final Context context, final AppDatabase database, final AppExecutors appExecutors){
        mApplicationContext = context;
        mDatabase = database;
        mAppExecutors = appExecutors;
    }
    public static PictureRepository getInstance(final Context context, final AppDatabase database, final AppExecutors appExecutors) {
        if (sINSTANCE == null) {
            synchronized (PictureRepository.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new PictureRepository(context, database, appExecutors);
                }
            }
        }
        return sINSTANCE;
    }

    public void insertPictureEntity(PictureEntity entity){
        Date dateNow = new Date();
        entity.createAt = dateNow;
        entity.updateAt = dateNow;
        mAppExecutors.diskIO().execute(()->{
            mDatabase.pictureDao().insert(entity);
        });
    }
    public void updatePictureEntity(PictureEntity entity){
        Date dateNow = new Date();
        entity.updateAt = dateNow;
        if (entity.createAt == null){
            entity.createAt = dateNow;
        }
        mAppExecutors.diskIO().execute(()->{
            mDatabase.pictureDao().update(entity);
        });
    }
    public void deletePictureEntity(PictureEntity entity){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.pictureDao().delete(entity);
        });
    }
    public LiveData<List<PictureEntity>> loadAllPictureEntityByTypeAndOwnerId(String type, String ownerId){
        return mDatabase.pictureDao().getPictureEntityListByTypeAndOwnerId(type, ownerId);
    }
    public void softDeletePictureEntityById(int id){
        long deleteAt = new Date().getTime();
        mAppExecutors.diskIO().execute(()->{
            mDatabase.pictureDao().softDeleteById(id, deleteAt);
        });
    }

}
