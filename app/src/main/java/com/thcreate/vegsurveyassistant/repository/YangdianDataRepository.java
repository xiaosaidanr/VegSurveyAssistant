package com.thcreate.vegsurveyassistant.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.dao.UserDao;
import com.thcreate.vegsurveyassistant.db.entity.User;
import com.thcreate.vegsurveyassistant.db.entity.Yangdian;

import java.util.List;

public class YangdianDataRepository {

    private static YangdianDataRepository sINSTANCE;

    private final AppDatabase mDatabase;

//    private LiveData<User> mCurrentUser;

//    public LiveData<Integer> mCurrentUserId;

    public MediatorLiveData<Integer> mCurrentUserId;

//    public class PopluateDbAsync extends AsyncTask<Void, Void, Void> {
//
//        private final UserDao mDao;
//
//        PopluateDbAsync(AppDatabase db){
//            mDao = db.userDao();
//        }
//
//        @Override
//        protected Void doInBackground(final Void... params){
//            mDao.deleteAll();
//            User user = new User("Yuan Jiace", 1);
//            mDao.insert(user);
//            User userNew = mDatabase.userDao().getCurrentUser();
//            Log.d("testtesttest", String.valueOf(userNew.id));
//            return null;
//        }
//    }

    private YangdianDataRepository(final AppDatabase database){
        mDatabase = database;
//        PopluateDbAsync task = new PopluateDbAsync(database);
//        task.execute();
        database.userDao().getAllUser();
        mCurrentUserId = new MediatorLiveData<>();
//        mCurrentUser = database.userDao().getCurrentUser();
//        mCurrentUserId = Transformations.map(mCurrentUser, new Function<User, Integer>() {
//            @Override
//            public Integer apply(User input) {
//                return input.id;
//            }
//        });

        mCurrentUserId.addSource(mDatabase.userDao().getCurrentUser(),
                userEntity -> {
                    if (mDatabase.getDatabaseCreated().getValue() != null) {
                        mCurrentUserId.postValue(userEntity.id);
                    }
                });
    }

    public static YangdianDataRepository getInstance(final AppDatabase database) {
        if (sINSTANCE == null) {
            synchronized (YangdianDataRepository.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new YangdianDataRepository(database);
                }
            }
        }
        return sINSTANCE;
    }



//    public LiveData<List<Yangdian>> loadAllYangdian() {
//        return mDatabase.yangdianDao().getAllYangdianByUserId(mCurrentUserId.getValue());
//    }
//
//    public LiveData<Yangdian> getYangdianByYangdianCode(String yangdianCode){
//        return mDatabase.yangdianDao().getYangdianByYangdianCode(yangdianCode);
//    }
//
//    public void insertYangdian(Yangdian data){
//
//    }
//
//    public void updateYangdian(Yangdian data){
//
//    }

}
