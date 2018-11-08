package com.thcreate.vegsurveyassistant.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GuanmuYangfangDao extends BaseDao<GuanmuYangfang> {

//    @Insert
//    void insert(GuanmuYangfang guanmuYangfang);
//
//    @Update
//    void update(GuanmuYangfang guanmuYangfang);
//
//    @Delete
//    void delete(GuanmuYangfang guanmuYangfang);

    @Query("DELETE FROM guanmu_yangfang")
    void deleteAll();

    @Query("SELECT * FROM guanmu_yangfang WHERE yangfang_code = :yangfangCode")
    LiveData<GuanmuYangfang> getGuanmuYangfangByYangfangCode(String yangfangCode);

    @Query("SELECT * FROM guanmu_yangfang WHERE yangdi_code = :yangdiCode ORDER BY id ASC")
    LiveData<List<GuanmuYangfang>> getAllGuanmuYangfangByYangdiCode(String yangdiCode);

}
