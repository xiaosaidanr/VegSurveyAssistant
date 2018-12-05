package com.thcreate.vegsurveyassistant.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.entity.BaseYangfang;
import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.User;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.YangfangCode;

import java.util.List;

public class YangfangDataRepository {

    private static YangfangDataRepository sINSTANCE;

    private final AppDatabase mDatabase;
    private final Context mApplicationContext;
    private final AppExecutors mAppExecutors;

    private YangfangDataRepository(final Context context, final AppDatabase database, final AppExecutors appExecutors){
        mApplicationContext = context;
        mDatabase = database;
        mAppExecutors = appExecutors;
    }

    public static YangfangDataRepository getInstance(final Context context, final AppDatabase database, final AppExecutors appExecutors) {
        if (sINSTANCE == null) {
            synchronized (WuzhongDataRepository.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new YangfangDataRepository(context, database, appExecutors);
                }
            }
        }
        return sINSTANCE;
    }



    public LiveData<QiaomuYangfang> getQiaomuYangfangByYangfangCode(String yangfangCode){
        return mDatabase.qiaomuYangfangDao().getQiaomuYangfangByYangfangCode(yangfangCode);
    }
    public LiveData<List<YangfangCode>> getAllQiaomuYangfangCodeByYangdiCode(String yangdiCode){
        return mDatabase.qiaomuYangfangDao().getAllQiaomuYangfangCodeByYangdiCode(yangdiCode);
    }
    public LiveData<List<QiaomuYangfang>> getAllQiaomuYangfangByYangdiCode(String yangdiCode){
        return mDatabase.qiaomuYangfangDao().getAllQiaomuYangfangByYangdiCode(yangdiCode);
    }
    public void insertQiaomuyf(QiaomuYangfang data){
        mAppExecutors.diskIO().execute(()->{
            User currentUser = mDatabase.userDao().getCurrentUserSync();
            if (currentUser != null){
                data.userId = currentUser.id;
                mDatabase.qiaomuYangfangDao().insert(data);
            }
        });
    }
    public void updateQiaomuyf(QiaomuYangfang data){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.qiaomuYangfangDao().update(data);
        });
    }
    public void deleteQiaomuyf(QiaomuYangfang data){
        mAppExecutors.diskIO().execute(()->mDatabase.qiaomuYangfangDao().delete(data));
    }
    public void deleteQiaomuyfByIdRelated(int id){
        mAppExecutors.diskIO().execute(()->{
            QiaomuYangfang tmp = mDatabase.qiaomuYangfangDao().getYangfangById(id);
            String qiaomuyangfangCode = tmp.yangfangCode;
            mDatabase.qiaomuYangfangDao().deleteById(id);
            mDatabase.guanmuYangfangDao().deleteByQiaomuyangfangCode(qiaomuyangfangCode);
            mDatabase.caobenYangfangDao().deleteByQiaomuyangfangCode(qiaomuyangfangCode);
        });
    }



    public LiveData<GuanmuYangfang> getGuanmuYangfangByYangfangCode(String yangfangCode){
        return mDatabase.guanmuYangfangDao().getGuanmuYangfangByYangfangCode(yangfangCode);
    }
    public LiveData<List<YangfangCode>> getAllGuanmuYangfangCodeByYangdiCode(String yangdiCode){
        return mDatabase.guanmuYangfangDao().getAllGuanmuYangfangCodeByYangdiCode(yangdiCode);
    }
    public LiveData<List<GuanmuYangfang>> getAllGuanmuYangfangByYangdiCode(String yangdiCode){
        return mDatabase.guanmuYangfangDao().getAllGuanmuYangfangByYangdiCode(yangdiCode);
    }
    public void insertGuanmuyf(GuanmuYangfang data){
        mAppExecutors.diskIO().execute(()->{
            User currentUser = mDatabase.userDao().getCurrentUserSync();
            if (currentUser != null){
                data.userId = currentUser.id;
                mDatabase.guanmuYangfangDao().insert(data);
            }
        });
    }
    public void updateGuanmuyf(GuanmuYangfang data){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.guanmuYangfangDao().update(data);
        });
    }
    public void deleteGuanmuyf(GuanmuYangfang data){
        mAppExecutors.diskIO().execute(()->mDatabase.guanmuYangfangDao().delete(data));
    }
    public void deleteGuanmuyfByIdRelated(int id){
        mAppExecutors.diskIO().execute(()->{
            GuanmuYangfang tmp = mDatabase.guanmuYangfangDao().getYangfangById(id);
            String guanmuyangfangCode = tmp.yangfangCode;
            mDatabase.guanmuYangfangDao().deleteById(id);
            mDatabase.caobenYangfangDao().deleteByGuanmuyangfangCode(guanmuyangfangCode);
        });
    }



    public LiveData<CaobenYangfang> getCaobenYangfangByYangfangCode(String yangfangCode){
        return mDatabase.caobenYangfangDao().getCaobenYangfangByYangfangCode(yangfangCode);
    }
    public LiveData<List<CaobenYangfang>> getAllCaobenYangfangByYangdiCode(String yangdiCode){
        return mDatabase.caobenYangfangDao().getAllCaobenYangfangByYangdiCode(yangdiCode);
    }
    public void insertCaobenyf(CaobenYangfang data){
        mAppExecutors.diskIO().execute(()->{
            User currentUser = mDatabase.userDao().getCurrentUserSync();
            if (currentUser != null){
                data.userId = currentUser.id;
                mDatabase.caobenYangfangDao().insert(data);
            }
        });
    }
    public void updateCaobenyf(CaobenYangfang data){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.caobenYangfangDao().update(data);
        });
    }
    public void deleteCaobenyf(CaobenYangfang data){
        mAppExecutors.diskIO().execute(()->mDatabase.caobenYangfangDao().delete(data));
    }
    public void deleteCaobenyfByIdRelated(int id){
        mAppExecutors.diskIO().execute(()->mDatabase.caobenYangfangDao().deleteById(id));
    }



    public <T extends BaseYangfang> void insertYangfang(T data){
        if (data instanceof CaobenYangfang){
            insertCaobenyf((CaobenYangfang) data);
        }
        if (data instanceof GuanmuYangfang){
            insertGuanmuyf((GuanmuYangfang) data);
        }
        if (data instanceof QiaomuYangfang){
            insertQiaomuyf((QiaomuYangfang) data);
        }
    }

    public <T extends BaseYangfang> void updateYangfang(T data){
        if (data instanceof CaobenYangfang){
            updateCaobenyf((CaobenYangfang) data);
        }
        if (data instanceof GuanmuYangfang){
            updateGuanmuyf((GuanmuYangfang) data);
        }
        if (data instanceof QiaomuYangfang){
            updateQiaomuyf((QiaomuYangfang) data);
        }
    }

    public <T extends BaseYangfang> void deleteYangfang(T data){
        if (data instanceof CaobenYangfang){
            deleteCaobenyf((CaobenYangfang) data);
        }
        if (data instanceof GuanmuYangfang){
            deleteGuanmuyf((GuanmuYangfang) data);
        }
        if (data instanceof QiaomuYangfang){
            deleteQiaomuyf((QiaomuYangfang) data);
        }
    }

}
