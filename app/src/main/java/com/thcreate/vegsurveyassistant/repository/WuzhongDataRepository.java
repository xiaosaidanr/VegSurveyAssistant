package com.thcreate.vegsurveyassistant.repository;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.entity.BaseWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.CaobenWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.User;

import java.util.List;

public class WuzhongDataRepository {

    private static WuzhongDataRepository sINSTANCE;

    private final AppDatabase mDatabase;
    private final Context mApplicationContext;
    private final AppExecutors mAppExecutors;

    private WuzhongDataRepository(final Context context, final AppDatabase database, final AppExecutors appExecutors){
        mApplicationContext = context;
        mDatabase = database;
        mAppExecutors = appExecutors;
    }
    public static WuzhongDataRepository getInstance(final Context context, final AppDatabase database, final AppExecutors appExecutors) {
        if (sINSTANCE == null) {
            synchronized (WuzhongDataRepository.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new WuzhongDataRepository(context, database, appExecutors);
                }
            }
        }
        return sINSTANCE;
    }



    public LiveData<QiaomuWuzhong> getQiaomuWuzhongByWuzhongCode(String wuzhongCode){
        return mDatabase.qiaomuWuzhongDao().getQiaomuWuzhongByWuzhongCode(wuzhongCode);
    }
    public LiveData<List<QiaomuWuzhong>> getAllQiaomuWuzhongByYangfangCode(String yangfangCode){
        return mDatabase.qiaomuWuzhongDao().getAllQiaomuWuzhongByYangfangCode(yangfangCode);
    }
    public void insertQiaomuwz(QiaomuWuzhong data){
        mAppExecutors.diskIO().execute(()->{
            User currentUser = mDatabase.userDao().getCurrentUserSync();
            if (currentUser != null){
                data.userId = currentUser.id;
                mDatabase.qiaomuWuzhongDao().insert(data);
            }
        });
    }
    public void updateQiaomuwz(QiaomuWuzhong data){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.qiaomuWuzhongDao().update(data);
        });
    }



    public LiveData<GuanmuWuzhong> getGuanmuWuzhongByWuzhongCode(String wuzhongCode){
        return mDatabase.guanmuWuzhongDao().getGuanmuWuzhongByWuzhongCode(wuzhongCode);
    }
    public LiveData<List<GuanmuWuzhong>> getAllGuanmuWuzhongByYangfangCode(String yangfangCode){
        return mDatabase.guanmuWuzhongDao().getAllGuanmuWuzhongByYangfangCode(yangfangCode);
    }
    public void insertGuanmuwz(GuanmuWuzhong data){
        mAppExecutors.diskIO().execute(()->{
            User currentUser = mDatabase.userDao().getCurrentUserSync();
            if (currentUser != null){
                data.userId = currentUser.id;
                mDatabase.guanmuWuzhongDao().insert(data);
            }
        });
    }
    public void updateGuanmuwz(GuanmuWuzhong data){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.guanmuWuzhongDao().update(data);
        });
    }



    public LiveData<CaobenWuzhong> getCaobenWuzhongByWuzhongCode(String wuzhongCode){
        return mDatabase.caobenWuzhongDao().getCaobenWuzhongByWuzhongCode(wuzhongCode);
    }
    public LiveData<List<CaobenWuzhong>> getAllCaobenWuzhongByYangfangCode(String yangfangCode){
        return mDatabase.caobenWuzhongDao().getAllCaobenWuzhongByYangfangCode(yangfangCode);
    }
    public void insertCaobenwz(CaobenWuzhong data){
        mAppExecutors.diskIO().execute(()->{
            User currentUser = mDatabase.userDao().getCurrentUserSync();
            if (currentUser != null){
                data.userId = currentUser.id;
                mDatabase.caobenWuzhongDao().insert(data);
            }
        });
    }
    public void updateCaobenwz(CaobenWuzhong data){
        mAppExecutors.diskIO().execute(()->{
            mDatabase.caobenWuzhongDao().update(data);
        });
    }


    public <T extends BaseWuzhong> void insertWuzhong(T data){
        if (data instanceof CaobenWuzhong){
            insertCaobenwz((CaobenWuzhong) data);
        }
        if (data instanceof GuanmuWuzhong){
            insertGuanmuwz((GuanmuWuzhong) data);
        }
        if (data instanceof QiaomuWuzhong){
            insertQiaomuwz((QiaomuWuzhong) data);
        }
    }

    public <T extends BaseWuzhong> void updateWuzhong(T data){
        if (data instanceof CaobenWuzhong){
            updateCaobenwz((CaobenWuzhong) data);
        }
        if (data instanceof GuanmuWuzhong){
            updateGuanmuwz((GuanmuWuzhong) data);
        }
        if (data instanceof QiaomuWuzhong){
            updateQiaomuwz((QiaomuWuzhong) data);
        }
    }
}
