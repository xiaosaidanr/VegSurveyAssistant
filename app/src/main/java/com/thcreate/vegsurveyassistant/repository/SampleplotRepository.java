package com.thcreate.vegsurveyassistant.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.PlotMainInfo;
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



    public LiveData<SampleplotEntity> getSampleplotEntityByPlotId(String plotId){
        return mDatabase.sampleplotDao().getSampleplotEntityByPlotId(plotId);
    }

    public LiveData<List<PlotMainInfo>> getAllArborSampleplotEntityByLandId(String landId){
        return mDatabase.sampleplotDao().getPlotMainInfoListByLandIdAndType(landId, Macro.ARBOR);
    }

    public LiveData<List<PlotMainInfo>> getAllShrubSampleplotEntityByLandId(String landId){
        return mDatabase.sampleplotDao().getPlotMainInfoListByLandIdAndType(landId, Macro.SHRUB);
    }

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

    public void deleteSampleplotEntityById(int id){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.sampleplotDao().deleteById(id);
        });
    }

    public void softDeleteSampleplotEntityById(int id){
        long deleteAt = new Date().getTime();
        mAppExecutors.diskIO().execute(()->{
            mDatabase.sampleplotDao().softDeleteById(id, deleteAt);
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
        });
    }

//    public void deleteArborSampleplotEntityByIdRelated(int id){
//        mAppExecutors.diskIO().execute(()->{
//            QiaomuYangfang tmp = mDatabase.qiaomuYangfangDao().getYangfangById(id);
//            String qiaomuyangfangCode = tmp.yangfangCode;
//            mDatabase.qiaomuYangfangDao().deleteById(id);
//            mDatabase.guanmuYangfangDao().deleteByQiaomuyangfangCode(qiaomuyangfangCode);
//            mDatabase.caobenYangfangDao().deleteByQiaomuyangfangCode(qiaomuyangfangCode);
//        });
//    }
//
//    public void deleteShrubSampleplotEntityByIdRelated(int id){
//        mAppExecutors.diskIO().execute(()->{
//            GuanmuYangfang tmp = mDatabase.guanmuYangfangDao().getYangfangById(id);
//            String guanmuyangfangCode = tmp.yangfangCode;
//            mDatabase.guanmuYangfangDao().deleteById(id);
//            mDatabase.caobenYangfangDao().deleteByGuanmuyangfangCode(guanmuyangfangCode);
//        });
//    }
//
//    public void deleteHerbSampleplotEntityByIdRelated(int id){
//        mAppExecutors.diskIO().execute(()->mDatabase.caobenYangfangDao().deleteById(id));
//    }

}
