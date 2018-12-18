package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thcreate.vegsurveyassistant.db.entity.SamplepointEntity;

import java.util.List;

@Dao
public interface SamplepointDao extends BaseDao<SamplepointEntity> {
//public interface SamplepointDao {

//    @Insert
//    void insert(SamplepointEntity obj);
//
//    @Update
//    void update(SamplepointEntity obj);
//
//    @Delete
//    void delete(SamplepointEntity obj);

    @Query("DELETE FROM point")
    void deleteAll();

    @Query("DELETE FROM point WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM point WHERE point_id = :pointId")
    LiveData<SamplepointEntity> getSamplepointEntityByPointId(String pointId);

    @Query("SELECT * FROM point WHERE user_id = :userId ORDER BY id ASC")
    LiveData<List<SamplepointEntity>> getSamplepointEntityListByUserId(int userId);

}
