package com.thcreate.vegsurveyassistant.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

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
public abstract class InvestRoomDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public abstract CaobenYangfangDao caobenYangfangDao();

    public abstract CaobenWuzhongDao caobenWuzhongDao();

    public abstract GuanmuYangfangDao guanmuYangfangDao();

    public abstract GuanmuWuzhongDao guanmuWuzhongDao();

    public abstract QiaomuYangfangDao qiaomuYangfangDao();

    public abstract QiaomuWuzhongDao qiaomuWuzhongDao();

    public abstract YangdiDao yangdiDao();

    public abstract YangdianDao yangdianDao();

    private static volatile InvestRoomDatabase INSTANCE;

    private static final String DATABASE_NAME = "invest_database";

    static InvestRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (InvestRoomDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            InvestRoomDatabase.class, InvestRoomDatabase.DATABASE_NAME)
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
