package com.thcreate.vegsurveyassistant.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CaobenYangfangDao extends BaseDao<CaobenYangfang> {

//    @Insert
//    void insert(CaobenYangfang caobenYangfang);
//
//    @Update
//    void update(CaobenYangfang caobenYangfang);
//
//    @Delete
//    void delete(CaobenYangfang caobenYangfang);

    @Query("DELETE FROM caoben_yangfang")
    void deleteAll();

    @Query("SELECT * FROM caoben_yangfang WHERE yangfang_code = :yangfangCode")
    LiveData<CaobenYangfang> getCaobenYangfangByYangfangCode(String yangfangCode);

    @Query("SELECT * FROM caoben_yangfang WHERE yangdi_code = :yangdiCode ORDER BY id ASC")
    LiveData<List<CaobenYangfang>> getAllCaobenYangfangByYangdiCode(String yangdiCode);

}
