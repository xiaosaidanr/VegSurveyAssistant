package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.PlotMainInfo;

import java.util.List;

@Dao
public interface SampleplotDao extends BaseDao<SampleplotEntity> {

    @Query("UPDATE plot SET delete_at = :deleteAt, update_at = :deleteAt WHERE id = :id")
    void softDeleteById(int id, long deleteAt);

    @Query("DELETE FROM plot WHERE id = :id")
    void deleteById(int id);

    @Query("DELETE FROM plot WHERE plot_id = :plotId")
    void deleteByPlotId(String plotId);

    @Query("SELECT * FROM plot WHERE id = :id")
    SampleplotEntity getSampleplotEntityByIdSync(int id);

    @Query("SELECT * FROM plot WHERE plot_id = :plotId")
    SampleplotEntity getSampleplotEntityByPlotIdSync(String plotId);

    @Query("SELECT * FROM plot WHERE plot_id = :plotId")
    LiveData<SampleplotEntity> getSampleplotEntityByPlotId(String plotId);

//    @Query("SELECT * FROM plot WHERE land_id = :landId AND type = :type AND delete_at IS NULL ORDER BY id ASC")
//    LiveData<List<SampleplotEntity>> getSampleplotEntityListByLandIdAndType(String landId, String type);

    @Query("SELECT id, land_id, plot_id, code, type FROM plot WHERE land_id = :landId AND type = :type AND delete_at IS NULL ORDER BY id ASC")
    List<PlotMainInfo> getPlotMainInfoListByLandIdAndTypeSync(String landId, String type);

    @Query("SELECT id, land_id, plot_id, code, type FROM plot WHERE land_id = :landId AND type = :type AND delete_at IS NULL ORDER BY id ASC")
    LiveData<List<PlotMainInfo>> getPlotMainInfoListByLandIdAndType(String landId, String type);

    @Query("SELECT * FROM plot " +
            "WHERE delete_at IS NOT NULL " +//数据已被软删除
            "AND upload_at IS NOT NULL " +//数据已被上传到服务器
            "AND upload_at < update_at " +//数据上传后有更新
            "AND plot_id LIKE :plotIdLimit")//根据所属用户id查找plot_id
    List<SampleplotEntity> getSampleplotEntityListNeedDeleteRemote(String plotIdLimit);

    @Query("SELECT * FROM plot " +
            "WHERE delete_at IS NOT NULL " +//数据已被软删除
            "AND upload_at IS NULL " +//数据未被上传到服务器
            "AND plot_id LIKE :plotIdLimit")//根据所属用户id查找species_id
    List<SampleplotEntity> getSampleplotEntitiesNeedDeleteLocal(String plotIdLimit);

    @Query("SELECT * FROM plot " +
            "WHERE delete_at IS NULL " +//数据未被软删除
            "AND upload_at IS NULL " +//数据未被上传到服务器
            "AND land_id LIKE :plotIdLimit")//根据所属用户id查找species_id
    List<SampleplotEntity> getSampleplotEntityListNeedAddRemote(String plotIdLimit);

    @Query("SELECT * FROM plot " +
            "WHERE delete_at IS NULL " +//数据未被软删除
            "AND upload_at IS NOT NULL " +//数据已被上传到服务器
            "AND upload_at < update_at " +//数据上传后有更新
            "AND land_id LIKE :plotIdLimit")//根据所属用户id查找species_id
    List<SampleplotEntity> getSampleplotEntityListNeedUpdateRemote(String plotIdLimit);

    @Query("UPDATE plot SET upload_at = :uploadAt WHERE plot_id = :plotId")
    void updateSampleplotEntityUploadAtByPlotId(String plotId, long uploadAt);

    @Query("SELECT * FROM plot " +
            "WHERE delete_at IS NULL " +//数据未被软删除
            "AND land_id = :landId")
    List<SampleplotEntity> getNotDeletedSampleplotEntityListByLandId(String landId);

    @Query("SELECT land_id FROM plot WHERE plot_id = :plotId")
    String getLandIdByPlotId(String plotId);

}
