package com.thcreate.vegsurveyassistant.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.entity.SamplelandEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.LandMainInfo;
import com.thcreate.vegsurveyassistant.db.entity.model.Sampleland;
import com.thcreate.vegsurveyassistant.service.SessionManager;

import java.util.Date;
import java.util.List;

public class SamplelandRepository {

    private static SamplelandRepository sINSTANCE;

    private final AppDatabase mDatabase;
    private final Context mApplicationContext;
    private final AppExecutors mAppExecutors;

//    private LiveData<UserEntity> mCurrentUser;
//
//    private LiveData<Integer> mCurrentUserId;

    private SamplelandRepository(final Context context, final AppDatabase database, final AppExecutors appExecutors){
        mApplicationContext = context;
        mDatabase = database;
        mAppExecutors = appExecutors;

//        mCurrentUser = mDatabase.userDao().getCurrentUserAsync();
//        mCurrentUserId = Transformations.map(mCurrentUser, user->{
//            if (user != null){
//                return user.id;
//            }
//            else {
//                return null;
//            }
//        });
    }
    public static SamplelandRepository getInstance(final Context context, final AppDatabase database, final AppExecutors appExecutors) {
        if (sINSTANCE == null) {
            synchronized (SamplelandRepository.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new SamplelandRepository(context, database, appExecutors);
                }
            }
        }
        return sINSTANCE;
    }


    /**
     * 根据type参数返回当前用户下所有的相应类型的样地数据
     *
     * @param type "tree"代表森林 "bush"代表灌丛 "grass"代表草地
     * @return List<SamplelandEntity> wrap by LiveData
     */
    public LiveData<List<LandMainInfo>> loadAllSamplelandEntityByType(String type) {
//        return Transformations.switchMap(mCurrentUserId, id -> {
//            if (id != null){
//                return mDatabase.samplelandDao().getLandMainInfoListByUserIdAndType(id, type);
//            }
//            else{
//                return null;
//            }
//        });
        return mDatabase.samplelandDao().getLandMainInfoListByUserIdAndType(SessionManager.getLoggedInUserId(), type);
    }
    public LiveData<SamplelandEntity> getSamplelandEntityByLandId(String landId){
        return mDatabase.samplelandDao().getSamplelandEntityByLandId(landId);
    }

    public SamplelandEntity getSampleLandEntityByLandIdSync(String landId){
        return mDatabase.samplelandDao().getSamplelandEntityByLandIdSync(landId);
    }

    public LiveData<List<LandMainInfo>> loadAllSamplelandEntity(){
//        return Transformations.switchMap(mCurrentUserId, id -> {
//            if (id != null){
//                return mDatabase.samplelandDao().getLandMainInfoListByUserId(id);
//            }
//            else{
//                return null;
//            }
//        });
        return mDatabase.samplelandDao().getLandMainInfoListByUserId(SessionManager.getLoggedInUserId());
    }
    public void insertSamplelandEntity(SamplelandEntity data){
        Date dateNow = new Date();
        data.createAt = dateNow;
        data.updateAt = dateNow;
        mAppExecutors.diskIO().execute(()->{
            mDatabase.samplelandDao().insert(data);
        });
    }
    public void insertSamplelandEntityManualSync(SamplelandEntity data){
        mDatabase.samplelandDao().insert(data);
    }
    public void updateSamplelandEntityManualSync(SamplelandEntity data){
        mDatabase.samplelandDao().update(data);
    }
    public void updateSamplelandEntity(SamplelandEntity data){
        Date dateNow = new Date();
        data.updateAt = dateNow;
        if (data.createAt == null){
            data.createAt = dateNow;
        }
        mAppExecutors.diskIO().execute(()-> {
            if (data.id == 0){
                SamplelandEntity tmp = mDatabase.samplelandDao().getSamplelandEntityByLandIdSync(data.landId);
                if (tmp != null){
                    data.id = tmp.id;
                    mDatabase.samplelandDao().update(data);
                }
            }
            else {
                mDatabase.samplelandDao().update(data);
            }
        });
    }
    public void updateSamplelandEntityUploadAtByLandId(String landId, long uploadAt){
        mDatabase.samplelandDao().updateSamplelandEntityUploadAtByLandId(landId, uploadAt);
    }
    public void deleteSamplelandEntity(SamplelandEntity data){
        mAppExecutors.diskIO().execute(()-> {
            if (data.id == 0){
                SamplelandEntity tmp = mDatabase.samplelandDao().getSamplelandEntityByLandIdSync(data.landId);
                if (tmp != null){
                    data.id = tmp.id;
                    mDatabase.samplelandDao().delete(data);
                }

            }
            else {
                mDatabase.samplelandDao().delete(data);
            }
        });
    }
    public void deleteSamplelandEntityById(int id){
        mAppExecutors.diskIO().execute(()-> mDatabase.samplelandDao().deleteById(id));
    }
    public void softDeleteSamplelandEntityById(int id){
        long deleteAt = new Date().getTime();
        mAppExecutors.diskIO().execute(()-> mDatabase.samplelandDao().softDeleteById(id, deleteAt));
    }

    public List<SamplelandEntity> getSamplelandEntityListNeedDeleteRemote(){
        return mDatabase.samplelandDao().getSamplelandEntityListNeedDeleteRemote(SessionManager.getLoggedInUserId());
    }
    public void deleteSamplelandEntitiesNeedDeleteLocal(){
        mDatabase.samplelandDao().deleteSamplelandEntitiesNeedDeleteLocal(SessionManager.getLoggedInUserId());
    }
    public List<SamplelandEntity> getSamplelandEntityListNeedAddRemote(){
        return mDatabase.samplelandDao().getSamplelandEntityListNeedAddRemote(SessionManager.getLoggedInUserId());
    }
    public List<SamplelandEntity> getSamplelandEntityListNeedUpdateRemote(){
        return mDatabase.samplelandDao().getSamplelandEntityListNeedUpdateRemote(SessionManager.getLoggedInUserId());
    }
}
