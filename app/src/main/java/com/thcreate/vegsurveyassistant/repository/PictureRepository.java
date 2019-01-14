package com.thcreate.vegsurveyassistant.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.entity.PictureEntity;
import com.thcreate.vegsurveyassistant.db.entity.PlotPictureEntity;
import com.thcreate.vegsurveyassistant.service.SessionManager;

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

    public void insertPlotPictureEntity(PlotPictureEntity entity){
        Date dateNow = new Date();
        entity.createAt = dateNow;
        entity.updateAt = dateNow;
        mAppExecutors.diskIO().execute(()->{
            mDatabase.pictureDao().insertPlotPicture(entity);
        });
    }
    public void softDeletePlotPictureEntity(PlotPictureEntity entity){
        Date dateNow = new Date();
        entity.updateAt = dateNow;
        entity.deleteAt = dateNow;
        mAppExecutors.diskIO().execute(()->{
            mDatabase.pictureDao().updatePlotPicture(entity);
        });
    }
    public void softDeletePlotPictureEntityById(int id){
        long deleteAt = new Date().getTime();
        mAppExecutors.diskIO().execute(()->{
            mDatabase.pictureDao().softDeletePlotPictureById(id, deleteAt);
        });
    }
    public void deletePlotPictureEntity(PlotPictureEntity entity){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.pictureDao().deletePlotPicture(entity);
        });
    }
    public LiveData<List<PlotPictureEntity>> loadAllPlotPictureEntityByOwnerId(String ownerId){
        return mDatabase.pictureDao().getPlotPictureEntityListByOwnerId(ownerId);
    }

    public List<PlotPictureEntity> getPlotPictureEntityListNeedDelete(){
        String ownerIdLimit = String.valueOf(SessionManager.getLoggedInUserId()) + "-%";
        return mDatabase.pictureDao().getPlotPictureEntityListNeedDelete(ownerIdLimit);
    }
    public void updatePlotPictureEntityUploadAtByPictureId(String pictureId, long uploadAt){
        mDatabase.pictureDao().updatePlotPictureEntityUploadAtByPictureId(pictureId, uploadAt);
    }
    public List<PlotPictureEntity> getPlotPictureEntityListNeedAddRemote(){
        String ownerIdLimit = String.valueOf(SessionManager.getLoggedInUserId()) + "-%";
        return mDatabase.pictureDao().getPlotPictureEntityListNeedAddRemote(ownerIdLimit);
    }

}
