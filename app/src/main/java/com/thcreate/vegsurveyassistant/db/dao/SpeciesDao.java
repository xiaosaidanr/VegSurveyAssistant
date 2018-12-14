package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;

import java.util.List;

@Dao
//public interface SpeciesDao extends BaseDao<SpeciesEntity>
public interface SpeciesDao {

    @Insert
    void insert(SpeciesEntity obj);

    @Update
    void update(SpeciesEntity obj);

    @Delete
    void delete(SpeciesEntity obj);

    @Query("DELETE FROM species WHERE id = :id")
    void deleteById(int id);

    @Query("DELETE FROM species")
    void deleteAll();

    @Query("SELECT * FROM species WHERE species_id = :speciesId")
    LiveData<SpeciesEntity> getSpeciesEntityBySpeciesId(String speciesId);

    @Query("SELECT * FROM species WHERE plot_id = :plotId AND type = :type ORDER BY id ASC")
    LiveData<List<SpeciesEntity>> getSpeciesEntityListByPlotIdAndType(String plotId, String type);

}
