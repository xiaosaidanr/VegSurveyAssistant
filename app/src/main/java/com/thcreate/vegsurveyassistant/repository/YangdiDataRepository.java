package com.thcreate.vegsurveyassistant.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.content.Context;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.db.AppDatabase;
import com.thcreate.vegsurveyassistant.db.entity.User;
import com.thcreate.vegsurveyassistant.db.entity.Yangdi;

import java.util.List;

public class YangdiDataRepository {

    private static YangdiDataRepository sINSTANCE;

    private final AppDatabase mDatabase;
    private final Context mApplicationContext;
    private final AppExecutors mAppExecutors;

    private LiveData<User> mCurrentUser;

    private LiveData<Integer> mCurrentUserId;

    private YangdiDataRepository(final Context context, final AppDatabase database, final AppExecutors appExecutors){
        mApplicationContext = context;
        mDatabase = database;
        mAppExecutors = appExecutors;

        mCurrentUser = mDatabase.userDao().getCurrentUserAsync();
        mCurrentUserId = Transformations.map(mCurrentUser, user->{
            if (user != null){
                return user.id;
            }
            else {
                return null;
            }
        });
    }
    public static YangdiDataRepository getInstance(final Context context, final AppDatabase database, final AppExecutors appExecutors) {
        if (sINSTANCE == null) {
            synchronized (WuzhongDataRepository.class) {
                if (sINSTANCE == null) {
                    sINSTANCE = new YangdiDataRepository(context, database, appExecutors);
                }
            }
        }
        return sINSTANCE;
    }


    /**
     * 根据type参数返回当前用户下所有的相应类型的样地数据
     *
     * @param type "tree"代表森林 "bush"代表灌丛 "grass"代表草地
     * @return List<Yangdi> wrap by LiveData
     */
    public LiveData<List<Yangdi>> loadAllYangdiByType(String type) {
        return Transformations.switchMap(mCurrentUserId, id -> {
            if (id != null){
                return mDatabase.yangdiDao().getAllYangdiByUserIdAndType(id, type);
            }
            else{
                return null;
            }
        });
    }
    public LiveData<Yangdi> getYangdiByYangdiCode(String yangdiCode){
        return mDatabase.yangdiDao().getYangdiByYangdiCode(yangdiCode);
    }
    public void insertYangdi(Yangdi data){
        mAppExecutors.diskIO().execute(()->{
            User currentUser = mDatabase.userDao().getCurrentUserSync();
            if (currentUser != null){
                data.userId = currentUser.id;
                mDatabase.yangdiDao().insert(data);
            }
        });
    }
    public void updateYangdi(Yangdi data){
        mAppExecutors.diskIO().execute(()-> {
            if (data.id == 0){
                Yangdi tmp = mDatabase.yangdiDao().getYangdiByYangdiCodeSync(data.yangdiCode);
                if (tmp != null){
                    data.id = tmp.id;
                    mDatabase.yangdiDao().update(data);
                }
            }
            else {
                mDatabase.yangdiDao().update(data);
            }
        });
    }
    public void deleteYangdi(Yangdi data){
        mAppExecutors.diskIO().execute(()-> {
            if (data.id == 0){
                Yangdi tmp = mDatabase.yangdiDao().getYangdiByYangdiCodeSync(data.yangdiCode);
                if (tmp != null){
                    data.id = tmp.id;
                    mDatabase.yangdiDao().delete(data);
                }

            }
            else {
                mDatabase.yangdiDao().delete(data);
            }
        });
    }
    public void deleteYangdiById(int id){
        mAppExecutors.diskIO().execute(()-> mDatabase.yangdiDao().deleteById(id));
    }
}
