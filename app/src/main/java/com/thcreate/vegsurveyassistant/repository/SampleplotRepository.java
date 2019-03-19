package com.thcreate.vegsurveyassistant.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.entity.PlotPlotEntity;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.PlotMainInfo;
import com.thcreate.vegsurveyassistant.service.SessionManager;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;
import java.util.List;

public class SampleplotRepository {

    private static SampleplotRepository sINSTANCE;

    private final AppDatabase mDatabase;
    private final Context mApplicationContext;
    private final AppExecutors mAppExecutors;

    private SampleplotRepository(final Context context, final AppDatabase database, final AppExecutors appExecutors){
        mApplicationContext = context;
        mDatabase = database;
        mAppExecutors = appExecutors;
    }

    public static SampleplotRepository getInstance(final Context context, final AppDatabase database, final AppExecutors appExecutors) {
        if (sINSTANCE == null) {
            synchronized (SampleplotRepository.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new SampleplotRepository(context, database, appExecutors);
                }
            }
        }
        return sINSTANCE;
    }

    public String getLandIdByPlotId(String plotId){
        return mDatabase.sampleplotDao().getLandIdByPlotId(plotId);
    }

    public LiveData<SampleplotEntity> getSampleplotEntityByPlotId(String plotId){
        return mDatabase.sampleplotDao().getSampleplotEntityByPlotId(plotId);
    }

    public List<PlotMainInfo> getAllArborSampleplotEntityByLandIdSync(String landId){
        return mDatabase.sampleplotDao().getPlotMainInfoListByLandIdAndTypeSync(landId, Macro.ARBOR);
    }
    public LiveData<List<PlotMainInfo>> getAllArborSampleplotEntityByLandId(String landId){
        return mDatabase.sampleplotDao().getPlotMainInfoListByLandIdAndType(landId, Macro.ARBOR);
    }

    public List<PlotMainInfo> getAllShrubSampleplotEntityByLandIdSync(String landId){
        return mDatabase.sampleplotDao().getPlotMainInfoListByLandIdAndTypeSync(landId, Macro.SHRUB);
    }
    public LiveData<List<PlotMainInfo>> getAllShrubSampleplotEntityByLandId(String landId){
        return mDatabase.sampleplotDao().getPlotMainInfoListByLandIdAndType(landId, Macro.SHRUB);
    }

//    public List<PlotMainInfo> getAllHerbSampleplotEntityByLandIdSync(String landId){
//        return mDatabase.sampleplotDao().getPlotMainInfoListByLandIdAndTypeSync(landId, Macro.HERB);
//    }
    public LiveData<List<PlotMainInfo>> getAllHerbSampleplotEntityByLandId(String landId){
        return mDatabase.sampleplotDao().getPlotMainInfoListByLandIdAndType(landId, Macro.HERB);
    }

    public void insertSampleplotEntity(SampleplotEntity data){
        Date dateNow = new Date();
        data.createAt = dateNow;
        data.updateAt = dateNow;
        mAppExecutors.diskIO().execute(()->{
            mDatabase.sampleplotDao().insert(data);
        });
    }

    public void updateSampleplotEntity(SampleplotEntity data){
        Date dateNow = new Date();
        data.updateAt = dateNow;
        if (data.createAt == null){
            data.createAt = dateNow;
        }
        updateSampleplotEntityManual(data);
    }
    public void updateSampleplotEntityManual(SampleplotEntity data){
        mAppExecutors.diskIO().execute(()->{
            if (data.id == 0){
                SampleplotEntity tmp = mDatabase.sampleplotDao().getSampleplotEntityByPlotIdSync(data.plotId);
                if (tmp != null){
                    data.id = tmp.id;
                    mDatabase.sampleplotDao().update(data);
                }
            }
            else {
                mDatabase.sampleplotDao().update(data);
            }
        });
    }
    public void updateSampleplotEntityUploadAtByPlotId(String plotId, long uploadAt){
        mDatabase.sampleplotDao().updateSampleplotEntityUploadAtByPlotId(plotId, uploadAt);
    }

    private void deleteRelatedSampleplotEntityByParentId(String parentId){
        List<PlotPlotEntity> plotPlotEntityList = mDatabase.plotPlotDao().getPlotPlotEntityListByParentIdSync(parentId);
        if (plotPlotEntityList == null){
            return;
        }
        for (PlotPlotEntity entity:
                plotPlotEntityList) {
            mDatabase.plotPlotDao().delete(entity);
            mDatabase.sampleplotDao().deleteByPlotId(entity.childId);
            deleteRelatedSampleplotEntityByParentId(entity.childId);
        }
    }

//    public void deleteSampleplotEntityById(int id){
//        mAppExecutors.diskIO().execute(()->{
//            mDatabase.sampleplotDao().deleteById(id);
//        });
//    }

    public void softDeleteSampleplotEntityById(int id){
        long deleteAt = new Date().getTime();
        mAppExecutors.diskIO().execute(()->{
            mDatabase.sampleplotDao().softDeleteById(id, deleteAt);
            SampleplotEntity sampleplotEntity = mDatabase.sampleplotDao().getSampleplotEntityByIdSync(id);
            deleteRelatedSampleplotEntityByParentId(sampleplotEntity.plotId);
            mDatabase.plotPlotDao().deletePlotPlotEntityByChildId(sampleplotEntity.plotId);
        });
    }

    public void deleteSampleplotEntity(SampleplotEntity data){
        mAppExecutors.diskIO().execute(()->{
            if (data.id == 0){
                SampleplotEntity tmp = mDatabase.sampleplotDao().getSampleplotEntityByPlotIdSync(data.plotId);
                if (tmp != null){
                    data.id = tmp.id;
                    mDatabase.sampleplotDao().delete(data);
                }
            }
            else {
                mDatabase.sampleplotDao().delete(data);
            }
            deleteRelatedSampleplotEntityByParentId(data.plotId);
            mDatabase.plotPlotDao().deletePlotPlotEntityByChildId(data.plotId);
        });
    }



    public void insertPlotPlotEntity(PlotPlotEntity data){
        Date dateNow = new Date();
        data.createAt = dateNow;
        data.updateAt = dateNow;
        mAppExecutors.diskIO().execute(()->{
            mDatabase.plotPlotDao().insert(data);
        });
    }

    public void updatePlotPlotEntity(PlotPlotEntity data){
        Date dateNow = new Date();
        mAppExecutors.diskIO().execute(()->{
            PlotPlotEntity tmp = mDatabase.plotPlotDao().getPlotPlotEntityByChildIdSync(data.childId);
            if (tmp != null){
                tmp.parentId = data.parentId;
                tmp.parentType = data.parentType;
                tmp.updateAt = dateNow;
                mDatabase.plotPlotDao().update(tmp);
            }
        });
    }

    public PlotPlotEntity getPlotPlotEntityByChildIdSync(String childId){
        return mDatabase.plotPlotDao().getPlotPlotEntityByChildIdSync(childId);
    }

    public void getParentPlotPlotEntityByChildIdRecursively(List<String> parentList, String childId){
        PlotPlotEntity entity = getPlotPlotEntityByChildIdSync(childId);
        if (entity != null){
            if (entity.parentId != null){
                parentList.add(entity.parentId);
                getParentPlotPlotEntityByChildIdRecursively(parentList, entity.parentId);
            }
        }
    }

//    public LiveData<PlotPlotEntity> getPlotPlotEntityByChildId(String childId){
//        return mDatabase.plotPlotDao().getPlotPlotEntityByChildId(childId);
//    }
//
//    public LiveData<List<PlotPlotEntity>> getPlotPlotEntityListByParentId(String parentId){
//        return mDatabase.plotPlotDao().getPlotPlotEntityListByParentId(parentId);
//    }

    public List<PlotPlotEntity> getPlotPlotEntityListByLandIdSync(String landId){
        return mDatabase.plotPlotDao().getPlotPlotEntityListByLandIdSync(landId);
    }

    public List<SampleplotEntity> getSampleplotEntityListNeedDeleteRemote(){
        String plotIdLimit = String.valueOf(SessionManager.getLoggedInUserId()) + "-%";
        return mDatabase.sampleplotDao().getSampleplotEntityListNeedDeleteRemote(plotIdLimit);
    }
    public void deleteSampleplotEntitiesNeedDeleteLocal(){
        String plotIdLimit = String.valueOf(SessionManager.getLoggedInUserId()) + "-%";
        List<SampleplotEntity> speciesEntityList = mDatabase.sampleplotDao().getSampleplotEntitiesNeedDeleteLocal(plotIdLimit);
        if (speciesEntityList != null &&  speciesEntityList.size() > 0){
            for (SampleplotEntity entity :
                    speciesEntityList) {
                deleteSampleplotEntity(entity);
            }
        }
    }
    public List<SampleplotEntity> getSampleplotEntityNeedAddRemote(){
        String plotIdLimit = String.valueOf(SessionManager.getLoggedInUserId()) + "-%";
        return mDatabase.sampleplotDao().getSampleplotEntityListNeedAddRemote(plotIdLimit);
    }
    public List<SampleplotEntity> getSampleplotEntityNeedUpdateRemote(){
        String plotIdLimit = String.valueOf(SessionManager.getLoggedInUserId()) + "-%";
        return mDatabase.sampleplotDao().getSampleplotEntityListNeedUpdateRemote(plotIdLimit);
    }
    public List<SampleplotEntity> getNotDeletedSampleplotEntityListByLandId(String landId){
        return mDatabase.sampleplotDao().getNotDeletedSampleplotEntityListByLandId(landId);
    }

}
