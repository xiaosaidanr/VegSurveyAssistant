package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.thcreate.vegsurveyassistant.db.entity.PlotPlotEntity;

import java.util.List;

@Dao
public interface PlotPlotDao extends BaseDao<PlotPlotEntity> {

    @Query("SELECT * FROM plot_plot WHERE child_id = :childId LIMIT 1")
    PlotPlotEntity getPlotPlotEntityByChildIdSync(String childId);

    @Query("SELECT * FROM plot_plot WHERE child_id = :childId LIMIT 1")
    LiveData<PlotPlotEntity> getPlotPlotEntityByChildId(String childId);

    @Query("SELECT * FROM plot_plot WHERE parent_id = :parentId")
    List<PlotPlotEntity> getPlotPlotEntityListByParentIdSync(String parentId);

    @Query("SELECT * FROM plot_plot WHERE parent_id = :parentId")
    LiveData<List<PlotPlotEntity>> getPlotPlotEntityListByParentId(String parentId);

    @Query("SELECT * FROM plot_plot WHERE land_id = :landId")
    List<PlotPlotEntity> getPlotPlotEntityListByLandIdSync(String landId);

    @Query("SELECT * FROM plot_plot WHERE land_id = :landId")
    LiveData<List<PlotPlotEntity>> getPlotPlotEntityListByLandId(String landId);

    @Query("DELETE FROM plot_plot WHERE child_id = :childId")
    void deletePlotPlotEntityByChildId(String childId);

}
