package com.thcreate.vegsurveyassistant.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.thcreate.vegsurveyassistant.AppExecutors;
import com.thcreate.vegsurveyassistant.db.converter.DateConverter;
import com.thcreate.vegsurveyassistant.db.dao.PictureDao;
import com.thcreate.vegsurveyassistant.db.dao.SamplelandDao;
import com.thcreate.vegsurveyassistant.db.dao.SampleplotDao;
import com.thcreate.vegsurveyassistant.db.dao.SpeciesDao;
import com.thcreate.vegsurveyassistant.db.dao.UserDao;
import com.thcreate.vegsurveyassistant.db.dao.SamplepointDao;
import com.thcreate.vegsurveyassistant.db.entity.PictureEntity;
import com.thcreate.vegsurveyassistant.db.entity.SamplelandEntity;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.SamplepointEntity;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.User;

@Database(
        entities = {
                User.class,
                SamplelandEntity.class,
                SampleplotEntity.class,
                SpeciesEntity.class,
                SamplepointEntity.class,
//                PictureEntity.class
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

    public abstract SamplelandDao samplelandDao();

    public abstract SampleplotDao sampleplotDao();

    public abstract SpeciesDao speciesDao();

    public abstract SamplepointDao samplepointDao();

//    public abstract PictureDao pictureDao();

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
        AppDatabase database = Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(()->{
                            AppDatabase database = AppDatabase.getInstance(appContext, executors);
                            User user = database.userDao().getCurrentUserSync();
                            if (user == null){
                                User newUser = new User("13512345678", 1);
                                database.userDao().insert(newUser);
                            }
                        });
                    }
                })
                .build();
        return database;
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

}
