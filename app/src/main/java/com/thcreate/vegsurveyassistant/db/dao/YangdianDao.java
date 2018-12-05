package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thcreate.vegsurveyassistant.db.entity.Yangdian;

import java.util.List;

@Dao
//public interface YangdianDao extends BaseDao<Yangdian> {
public interface YangdianDao {

    @Insert
    void insert(Yangdian obj);

    @Update
    void update(Yangdian obj);

    @Delete
    void delete(Yangdian obj);

    @Query("DELETE FROM yangdian")
    void deleteAll();

    @Query("DELETE FROM yangdian WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM yangdian WHERE yangdian_code = :yangdianCode")
    LiveData<Yangdian> getYangdianByYangdianCode(String yangdianCode);

    @Query("SELECT * FROM yangdian WHERE user_id = :userId ORDER BY id ASC")
    LiveData<List<Yangdian>> getAllYangdianByUserId(int userId);

}
