package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.SpeciesMainInfo;

import java.util.List;

@Dao
public interface SpeciesDao extends BaseDao<SpeciesEntity>{

    @Query("UPDATE species SET delete_at = :deleteAt, update_at = :deleteAt WHERE id = :id")
    void softDeleteById(int id, long deleteAt);

    @Query("DELETE FROM species WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM species WHERE species_id = :speciesId")
    LiveData<SpeciesEntity> getSpeciesEntityBySpeciesId(String speciesId);

//    @Query("SELECT * FROM species WHERE plot_id = :plotId AND type = :type AND delete_at IS NULL ORDER BY id ASC")
//    LiveData<List<SpeciesEntity>> getSpeciesEntityListByPlotIdAndType(String plotId, String type);

    @Query("SELECT * FROM species WHERE plot_id = :plotId AND type = :type AND delete_at IS NULL ORDER BY id ASC")
    LiveData<List<SpeciesMainInfo>> getSpeciesMainInfoListByPlotIdAndType(String plotId, String type);

}
