package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;

import java.util.List;

@Dao
public interface SampleplotDao extends BaseDao<SampleplotEntity> {
//public interface SampleplotDao {

//    @Insert
//    void insert(SampleplotEntity obj);
//
//    @Update
//    void update(SampleplotEntity obj);
//
//    @Delete
//    void delete(SampleplotEntity obj);

    @Query("UPDATE plot SET delete_at = :deleteAt WHERE id = :id")
    void softDeleteById(int id, long deleteAt);

    @Query("DELETE FROM plot WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM plot WHERE plot_id = :plotId")
    SampleplotEntity getSampleplotEntityByPlotIdSync(String plotId);

    @Query("SELECT * FROM plot WHERE plot_id = :plotId")
    LiveData<SampleplotEntity> getSampleplotEntityByPlotId(String plotId);

    @Query("SELECT * FROM plot WHERE land_id = :landId AND type = :type AND delete_at IS NULL ORDER BY id ASC")
    LiveData<List<SampleplotEntity>> getSampleplotEntityListByLandIdAndType(String landId, String type);

}
