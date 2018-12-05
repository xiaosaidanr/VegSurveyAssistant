package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thcreate.vegsurveyassistant.db.entity.CaobenWuzhong;

import java.util.List;

@Dao
//public interface CaobenWuzhongDao extends BaseDao<CaobenWuzhong> {
public interface CaobenWuzhongDao {

    @Insert
    void insert(CaobenWuzhong obj);

    @Update
    void update(CaobenWuzhong obj);

    @Delete
    void delete(CaobenWuzhong obj);

    @Query("DELETE FROM caoben_wuzhong WHERE id = :id")
    void deleteById(int id);

    @Query("DELETE FROM caoben_wuzhong")
    void deleteAll();

    @Query("SELECT * FROM caoben_wuzhong WHERE wuzhong_code = :wuzhongCode")
    LiveData<CaobenWuzhong> getCaobenWuzhongByWuzhongCode(String wuzhongCode);

    @Query("SELECT * FROM caoben_wuzhong WHERE yangfang_code = :yangfangCode ORDER BY id ASC")
    LiveData<List<CaobenWuzhong>> getAllCaobenWuzhongByYangfangCode(String yangfangCode);
}
