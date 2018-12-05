package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;

import java.util.List;

@Dao
public interface CaobenYangfangDao {

//public interface CaobenYangfangDao extends BaseDao<CaobenYangfang> {

    @Insert
    void insert(CaobenYangfang obj);

    @Update
    void update(CaobenYangfang obj);

    @Delete
    void delete(CaobenYangfang obj);

    @Query("DELETE FROM caoben_yangfang WHERE id = :id")
    void deleteById(int id);

    @Query("DELETE FROM caoben_yangfang WHERE guanmuyangfang_code = :code")
    void deleteByGuanmuyangfangCode(String code);

    @Query("DELETE FROM caoben_yangfang WHERE qiaomuyangfang_code = :code")
    void deleteByQiaomuyangfangCode(String code);

    @Query("DELETE FROM caoben_yangfang")
    void deleteAll();

    @Query("SELECT * FROM caoben_yangfang WHERE id = :id")
    CaobenYangfang getYangfangById(int id);

    @Query("SELECT * FROM caoben_yangfang WHERE yangfang_code = :yangfangCode")
    LiveData<CaobenYangfang> getCaobenYangfangByYangfangCode(String yangfangCode);

    @Query("SELECT * FROM caoben_yangfang WHERE yangdi_code = :yangdiCode ORDER BY id ASC")
    LiveData<List<CaobenYangfang>> getAllCaobenYangfangByYangdiCode(String yangdiCode);

}
