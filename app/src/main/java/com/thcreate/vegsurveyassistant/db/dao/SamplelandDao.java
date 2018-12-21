package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.thcreate.vegsurveyassistant.db.entity.SamplelandEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.LandMainInfo;

import java.util.List;

@Dao
public interface SamplelandDao extends BaseDao<SamplelandEntity> {

    @Query("UPDATE land SET delete_at = :deleteAt, update_at = :deleteAt WHERE id = :id")
    void softDeleteById(int id, long deleteAt);

    @Query("DELETE FROM land WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM land WHERE land_id = :landId")
    SamplelandEntity getSamplelandEntityByLandIdSync(String landId);

    @Query("SELECT * FROM land WHERE land_id = :landId")
    LiveData<SamplelandEntity> getSamplelandEntityByLandId(String landId);

//    @Query("SELECT * FROM land WHERE user_id = :userId AND delete_at IS NULL ORDER BY id ASC")
//    LiveData<List<SamplelandEntity>> getSamplelandEntityListByUserId(int userId);
//
//    @Query("SELECT * FROM land WHERE user_id = :userId AND type = :type AND delete_at IS NULL ORDER BY id ASC")
//    LiveData<List<SamplelandEntity>> getSamplelandEntityListByUserIdAndType(int userId, String type);

    @Query("SELECT * FROM land WHERE user_id = :userId AND delete_at IS NULL ORDER BY id ASC")
    LiveData<List<LandMainInfo>> getLandMainInfoListByUserId(int userId);

    @Query("SELECT * FROM land WHERE user_id = :userId AND type = :type AND delete_at IS NULL ORDER BY id ASC")
    LiveData<List<LandMainInfo>> getLandMainInfoListByUserIdAndType(int userId, String type);

}
