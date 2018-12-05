package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thcreate.vegsurveyassistant.db.entity.QiaomuWuzhong;

import java.util.List;

@Dao
//public interface QiaomuWuzhongDao extends BaseDao<QiaomuWuzhong> {
public interface QiaomuWuzhongDao {


    @Insert
    void insert(QiaomuWuzhong obj);

    @Update
    void update(QiaomuWuzhong obj);

    @Delete
    void delete(QiaomuWuzhong obj);

    @Query("DELETE FROM qiaomu_wuzhong WHERE id = :id")
    void deleteById(int id);

    @Query("DELETE FROM qiaomu_wuzhong")
    void deleteAll();

    @Query("SELECT * FROM qiaomu_wuzhong WHERE wuzhong_code = :wuzhongCode")
    LiveData<QiaomuWuzhong> getQiaomuWuzhongByWuzhongCode(String wuzhongCode);

    @Query("SELECT * FROM qiaomu_wuzhong WHERE yangfang_code = :yangfangCode ORDER BY id ASC")
    LiveData<List<QiaomuWuzhong>> getAllQiaomuWuzhongByYangfangCode(String yangfangCode);

}
