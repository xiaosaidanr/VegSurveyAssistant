package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thcreate.vegsurveyassistant.db.entity.SamplelandEntity;

import java.util.List;

@Dao
public interface SamplelandDao {
//public interface SamplelandDao extends BaseDao<SamplelandEntity> {

    @Insert
    void insert(SamplelandEntity obj);

    @Update
    void update(SamplelandEntity obj);

    @Delete
    void delete(SamplelandEntity obj);

    @Query("DELETE FROM sample_land WHERE id = :id")
    void deleteById(int id);

    @Query("DELETE FROM sample_land")
    void deleteAll();

    @Query("SELECT * FROM sample_land WHERE land_id = :landId")
    SamplelandEntity getSamplelandEntityByLandIdSync(String landId);

    @Query("SELECT * FROM sample_land WHERE land_id = :landId")
    LiveData<SamplelandEntity> getSamplelandEntityByLandId(String landId);

    @Query("SELECT * FROM sample_land WHERE user_id = :userId ORDER BY id ASC")
    LiveData<List<SamplelandEntity>> getSamplelandEntityListByUserId(int userId);

    @Query("SELECT * FROM sample_land WHERE user_id = :userId AND type = :type ORDER BY id ASC")
    LiveData<List<SamplelandEntity>> getSamplelandEntityListByUserIdAndType(int userId, String type);

}
