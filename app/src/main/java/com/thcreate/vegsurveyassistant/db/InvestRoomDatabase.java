package com.thcreate.vegsurveyassistant.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(
        entities = {
                User.class,
                CaodiYangdi.class,
                CaobenYangfang.class,
                CaobenWuzhong.class,
                GuancongYangdi.class,
                GuanmuYangfang.class,
                GuanmuWuzhong.class,
                SenlinYangdi.class,
                QiaomuYangfang.class,
                QiaomuWuzhong.class,
                Yangdian.class
        },
        version = 1,
        exportSchema = false
)
@TypeConverters({Converters.DateConverter.class})
public abstract class InvestRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract CaodiYangdiDao caodiYangdiDao();

    public abstract CaobenYangfangDao caobenYangfangDao();

    public abstract CaobenWuzhongDao caobenWuzhongDao();

    public abstract GuancongYangdiDao guancongYangdiDao();

    public abstract GuanmuYangfangDao guanmuYangfangDao();

    public abstract GuanmuWuzhongDao guanmuWuzhongDao();

    public abstract SenlinYangdiDao senlinYangdiDao();

    public abstract QiaomuYangfangDao qiaomuYangfangDao();

    public abstract QiaomuWuzhongDao qiaomuWuzhongDao();

    public abstract YangdianDao yangdianDao();

    private static volatile InvestRoomDatabase INSTANCE;

    static InvestRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (InvestRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            InvestRoomDatabase.class, "invest_database")
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopluateDbAsync(INSTANCE).execute();
        }
    };

    private static class PopluateDbAsync extends AsyncTask<Void, Void, Void>{

        private final UserDao mDao;

        PopluateDbAsync(InvestRoomDatabase db){
            mDao = db.userDao();
        }

        @Override
        protected Void doInBackground(final Void... params){
            mDao.deleteAll();
            User user = new User("Yuan Jiace");
            mDao.insert(user);
            return null;
        }
    }

}
