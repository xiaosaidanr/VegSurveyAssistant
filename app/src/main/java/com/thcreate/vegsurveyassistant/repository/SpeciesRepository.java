package com.thcreate.vegsurveyassistant.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.SpeciesMainInfo;
import com.thcreate.vegsurveyassistant.service.SessionManager;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;
import java.util.List;

public class SpeciesRepository {

    private static SpeciesRepository sINSTANCE;

    private final AppDatabase mDatabase;
    private final Context mApplicationContext;
    private final AppExecutors mAppExecutors;

    private SpeciesRepository(final Context context, final AppDatabase database, final AppExecutors appExecutors){
        mApplicationContext = context;
        mDatabase = database;
        mAppExecutors = appExecutors;
    }
    public static SpeciesRepository getInstance(final Context context, final AppDatabase database, final AppExecutors appExecutors) {
        if (sINSTANCE == null) {
            synchronized (SpeciesRepository.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new SpeciesRepository(context, database, appExecutors);
                }
            }
        }
        return sINSTANCE;
    }



    public LiveData<SpeciesEntity> getSpeciesEntityBySpeciesId(String speciesId){
        return mDatabase.speciesDao().getSpeciesEntityBySpeciesId(speciesId);
    }

    public LiveData<List<SpeciesMainInfo>> getAllArborSpeciesEntityByPlotId(String plotId){
        return mDatabase.speciesDao().getSpeciesMainInfoListByPlotIdAndType(plotId, Macro.ARBOR);
    }

    public LiveData<List<SpeciesMainInfo>> getAllShrubSpeciesEntityByPlotId(String plotId){
        return mDatabase.speciesDao().getSpeciesMainInfoListByPlotIdAndType(plotId, Macro.SHRUB);
    }

    public LiveData<List<SpeciesMainInfo>> getAllHerbSpeciesEntityByPlotId(String plotId){
        return mDatabase.speciesDao().getSpeciesMainInfoListByPlotIdAndType(plotId, Macro.HERB);
    }

    public void insertSpeciesEntity(SpeciesEntity data){
        Date dateNow = new Date();
        data.createAt = dateNow;
        data.updateAt = dateNow;
        mAppExecutors.diskIO().execute(()->{
            mDatabase.speciesDao().insert(data);
        });
    }
    public void updateSpeciesEntityManual(SpeciesEntity data){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.speciesDao().update(data);
        });
    }
    public void updateSpeciesEntity(SpeciesEntity data){
        Date dateNow = new Date();
        data.updateAt = dateNow;
        if (data.createAt == null){
            data.createAt = dateNow;
        }
        updateSpeciesEntityManual(data);
    }
    public void updateSpeciesEntityUploadAtBySpeciesId(String speciesId, long uploadAt){
        mDatabase.speciesDao().updateSpeciesEntityUploadAtBySpeciesId(speciesId, uploadAt);
    }
    public void softDeleteSpeciesEntityById(int id){
        long deleteAt = new Date().getTime();
        mAppExecutors.diskIO().execute(()->{
            mDatabase.speciesDao().softDeleteById(id, deleteAt);
        });
    }
    public void deleteSpeciesEntityById(int id){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.speciesDao().deleteById(id);
        });
    }

    public List<SpeciesEntity> getSpeciesEntityListNeedDeleteRemote(){
        String speciesIdLimit = String.valueOf(SessionManager.getLoggedInUserId()) + "-%";
        return mDatabase.speciesDao().getSpeciesEntityListNeedDeleteRemote(speciesIdLimit);
    }
    public void deleteSpeciesEntitiesNeedDeleteLocal(){
        String speciesIdLimit = String.valueOf(SessionManager.getLoggedInUserId()) + "-%";
        mDatabase.speciesDao().deleteSpeciesEntitiesNeedDeleteLocal(speciesIdLimit);
    }
    public List<SpeciesEntity> getSpeciesEntityListNeedAddRemote(){
        String speciesIdLimit = String.valueOf(SessionManager.getLoggedInUserId()) + "-%";
        return mDatabase.speciesDao().getSpeciesEntityListNeedAddRemote(speciesIdLimit);
    }
    public List<SpeciesEntity> getSpeciesEntityListNeedUpdateRemote(){
        String speciesIdLimit = String.valueOf(SessionManager.getLoggedInUserId()) + "-%";
        return mDatabase.speciesDao().getSpeciesEntityListNeedUpdateRemote(speciesIdLimit);
    }
    public List<SpeciesEntity> getNotDeletedSpeciesEntityListByPlotId(String plotId){
        return mDatabase.speciesDao().getNotDeletedSpeciesEntityListByPlotId(plotId);
    }

}
