package com.thcreate.vegsurveyassistant.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.User;
import com.thcreate.vegsurveyassistant.util.Macro;

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

    public LiveData<List<SpeciesEntity>> getAllArborSpeciesEntityByPlotId(String plotId){
        return mDatabase.speciesDao().getSpeciesEntityListByPlotIdAndType(plotId, Macro.ARBOR);
    }

    public LiveData<List<SpeciesEntity>> getAllShrubSpeciesEntityByPlotId(String plotId){
        return mDatabase.speciesDao().getSpeciesEntityListByPlotIdAndType(plotId, Macro.SHRUB);
    }

    public LiveData<List<SpeciesEntity>> getAllHerbSpeciesEntityByPlotId(String plotId){
        return mDatabase.speciesDao().getSpeciesEntityListByPlotIdAndType(plotId, Macro.HERB);
    }

    public void insertSpeciesEntity(SpeciesEntity data){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.speciesDao().insert(data);
        });
    }

    public void updateSpeciesEntity(SpeciesEntity data){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.speciesDao().update(data);
        });
    }

    public void deleteSpeciesEntityById(int id){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.speciesDao().deleteById(id);
        });
    }

}
