package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.thcreate.vegsurveyassistant.db.entity.SamplelandEntity;

import java.util.List;

@Dao
//public interface SamplelandDao {
public interface SamplelandDao extends BaseDao<SamplelandEntity> {

//    @Insert
//    void insert(SamplelandEntity obj);
//
//    @Update
//    void update(SamplelandEntity obj);
//
//    @Delete
//    void delete(SamplelandEntity obj);

    @Query("DELETE FROM land WHERE id = :id")
    void deleteById(int id);

    @Query("DELETE FROM land")
    void deleteAll();

    @Query("SELECT * FROM land WHERE land_id = :landId")
    SamplelandEntity getSamplelandEntityByLandIdSync(String landId);

    @Query("SELECT * FROM land WHERE land_id = :landId")
    LiveData<SamplelandEntity> getSamplelandEntityByLandId(String landId);

    @Query("SELECT * FROM land WHERE user_id = :userId ORDER BY id ASC")
    LiveData<List<SamplelandEntity>> getSamplelandEntityListByUserId(int userId);

    @Query("SELECT * FROM land WHERE user_id = :userId AND type = :type ORDER BY id ASC")
    LiveData<List<SamplelandEntity>> getSamplelandEntityListByUserIdAndType(int userId, String type);

}
