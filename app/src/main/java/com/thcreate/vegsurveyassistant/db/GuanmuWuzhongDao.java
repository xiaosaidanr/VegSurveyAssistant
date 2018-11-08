package com.thcreate.vegsurveyassistant.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GuanmuWuzhongDao extends BaseDao<GuanmuWuzhong> {

//    @Insert
//    void insert(GuanmuWuzhong guanmuWuzhong);
//
//    @Update
//    void update(GuanmuWuzhong guanmuWuzhong);
//
//    @Delete
//    void delete(GuanmuWuzhong guanmuWuzhong);

    @Query("DELETE FROM guanmu_wuzhong")
    void deleteAll();

    @Query("SELECT * FROM guanmu_wuzhong WHERE wuzhong_code = :wuzhongCode")
    LiveData<GuanmuWuzhong> getGuanmuWuzhongByWuzhongCode(String wuzhongCode);

    @Query("SELECT * FROM guanmu_wuzhong WHERE yangfang_code = :yangfangCode ORDER BY id ASC")
    LiveData<List<GuanmuWuzhong>> getAllGuanmuWuzhongByYangfangCode(String yangfangCode);

}
