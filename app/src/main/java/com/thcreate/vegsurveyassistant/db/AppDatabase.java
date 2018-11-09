package com.thcreate.vegsurveyassistant.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.db.converter.DateConverter;
import com.thcreate.vegsurveyassistant.db.dao.CaobenWuzhongDao;
import com.thcreate.vegsurveyassistant.db.dao.CaobenYangfangDao;
import com.thcreate.vegsurveyassistant.db.dao.GuanmuWuzhongDao;
import com.thcreate.vegsurveyassistant.db.dao.GuanmuYangfangDao;
import com.thcreate.vegsurveyassistant.db.dao.QiaomuWuzhongDao;
import com.thcreate.vegsurveyassistant.db.dao.QiaomuYangfangDao;
import com.thcreate.vegsurveyassistant.db.dao.UserDao;
import com.thcreate.vegsurveyassistant.db.dao.YangdiDao;
import com.thcreate.vegsurveyassistant.db.dao.YangdianDao;
import com.thcreate.vegsurveyassistant.db.entity.CaobenWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.User;
import com.thcreate.vegsurveyassistant.db.entity.Yangdi;
import com.thcreate.vegsurveyassistant.db.entity.Yangdian;

@Database(
        entities = {
                User.class,
                CaobenYangfang.class,
                CaobenWuzhong.class,
                GuanmuYangfang.class,
                GuanmuWuzhong.class,
                QiaomuYangfang.class,
                QiaomuWuzhong.class,
                Yangdi.class,
                Yangdian.class
        },
        version = 1,
        exportSchema = false
)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static volatile AppDatabase sINSTANCE;

    @VisibleForTesting
    private static final String DATABASE_NAME = "invest-db";

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public abstract UserDao userDao();

    public abstract YangdiDao yangdiDao();

    public abstract CaobenYangfangDao caobenYangfangDao();

    public abstract CaobenWuzhongDao caobenWuzhongDao();

    public abstract GuanmuYangfangDao guanmuYangfangDao();

    public abstract GuanmuWuzhongDao guanmuWuzhongDao();

    public abstract QiaomuYangfangDao qiaomuYangfangDao();

    public abstract QiaomuWuzhongDao qiaomuWuzhongDao();

    public abstract YangdianDao yangdianDao();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors){
        if (sINSTANCE == null){
            synchronized (AppDatabase.class){
                if (sINSTANCE == null){
                    sINSTANCE = buildDatabase(context.getApplicationContext(), executors);
                    sINSTANCE.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sINSTANCE;
    }

    private static AppDatabase buildDatabase(final Context appContext,
                                             final AppExecutors executors){
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                AppDatabase database = AppDatabase.getInstance(appContext, executors);
                                // Generate the data for pre-population
                                database.userDao().deleteAll();
                                User user = new User("13521936487", 1);
                                database.userDao().insert(user);
                                // notify that the database was created and it's ready to be used
                                database.setDatabaseCreated();
                            }
                        });
                    }
                })
                .build();
    }

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

//    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
//        @Override
//        public void onOpen(@NonNull SupportSQLiteDatabase db) {
//            super.onOpen(db);
//            new PopluateDbAsync(sINSTANCE).execute();
//        }
//    };
//
//    private static class PopluateDbAsync extends AsyncTask<Void, Void, Void>{
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
//            User user = new User("Yuan Jiace");
//            mDao.insert(user);
//            return null;
//        }
//    }

}
