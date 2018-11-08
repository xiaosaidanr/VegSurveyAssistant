package com.thcreate.vegsurveyassistant.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CaobenWuzhongDao extends BaseDao<CaobenWuzhong> {

//    @Insert
//    void insert(CaobenWuzhong caobenWuzhong);
//
//    @Update
//    void update(CaobenWuzhong caobenWuzhong);
//
//    @Delete
//    void delete(CaobenWuzhong caobenWuzhong);

    @Query("DELETE FROM caoben_wuzhong")
    void deleteAll();

    @Query("SELECT * FROM caoben_wuzhong WHERE wuzhong_code = :wuzhongCode")
    LiveData<CaobenWuzhong> getCaobenWuzhongByWuzhongCode(String wuzhongCode);

    @Query("SELECT * FROM caoben_wuzhong WHERE yangfang_code = :yangfangCode ORDER BY id ASC")
    LiveData<List<CaobenWuzhong>> getAllCaobenWuzhongByYangfangCode(String yangfangCode);
}
