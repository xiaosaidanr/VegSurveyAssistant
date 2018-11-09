package com.thcreate.vegsurveyassistant.repository;

import android.arch.core.util.Function;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;

import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.entity.User;
import com.thcreate.vegsurveyassistant.db.entity.Yangdian;

import java.util.List;

public class YangdianDataRepository {

    private static YangdianDataRepository sINSTANCE;

    private final AppDatabase mDatabase;

    private LiveData<User> mCurrentUser;

    private LiveData<Integer> mCurrentUserId;

    private YangdianDataRepository(final AppDatabase database){
        mDatabase = database;
        mCurrentUser = database.userDao().getCurrentUser();
        mCurrentUserId = Transformations.map(mCurrentUser, new Function<User, Integer>() {
            @Override
            public Integer apply(User input) {
                return input.id;
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

    public LiveData<List<Yangdian>> loadAllYangdian() {
        return mDatabase.yangdianDao().getAllYangdianByUserId(mCurrentUserId.getValue());
    }

}
