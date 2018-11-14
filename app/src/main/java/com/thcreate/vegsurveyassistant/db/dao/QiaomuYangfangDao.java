package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thcreate.vegsurveyassistant.db.entity.QiaomuYangfang;

import java.util.List;

@Dao
//public interface QiaomuYangfangDao extends BaseDao<QiaomuYangfang> {
public interface QiaomuYangfangDao {


    @Insert
    void insert(QiaomuYangfang obj);

    @Update
    void update(QiaomuYangfang obj);

    @Delete
    void delete(QiaomuYangfang obj);

    @Query("DELETE FROM qiaomu_yangfang")
    void deleteAll();

    @Query("SELECT * FROM qiaomu_yangfang WHERE yangfang_code = :yangfangCode")
    LiveData<QiaomuYangfang> getQiaomuYangfangByYangfangCode(String yangfangCode);

    @Query("SELECT * FROM qiaomu_yangfang WHERE yangdi_code = :yangdiCode ORDER BY id ASC")
    LiveData<List<QiaomuYangfang>> getAllQiaomuYangfangByYangdiCode(String yangdiCode);

}
