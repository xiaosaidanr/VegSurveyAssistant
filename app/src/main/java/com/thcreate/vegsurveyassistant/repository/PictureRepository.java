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

    public LiveData<List<PictureEntity>> loadAllPictureEntityByTypeAndOwnerId(String type, String ownerId){
        return mDatabase.pictureDao().getPictureEntityListByTypeAndOwnerId(type, ownerId);
    }
    public void softDeletePictureEntityByTypeAndOwnerId(String type, String ownerId){
        long deleteAt = new Date().getTime();
        mAppExecutors.diskIO().execute(()->{
            mDatabase.pictureDao().softDeletePictureEntityByTypeAndOwnerId(type, ownerId, deleteAt);
        });
    }
    public void softDeletePictureEntityById(int id){
        long deleteAt = new Date().getTime();
        mAppExecutors.diskIO().execute(()->{
            mDatabase.pictureDao().softDeleteById(id, deleteAt);
        });
    }
    //TODO softDeleteByPictureId
    public void deletePictureEntityByPictureId(String pictureId){

    }

}
