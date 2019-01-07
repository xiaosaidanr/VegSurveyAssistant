package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

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

    @Query("SELECT id, plot_id, species_id, code, type, name FROM species WHERE plot_id = :plotId AND type = :type AND delete_at IS NULL ORDER BY id ASC")
    LiveData<List<SpeciesMainInfo>> getSpeciesMainInfoListByPlotIdAndType(String plotId, String type);

    @Query("SELECT * FROM species " +
            "WHERE delete_at IS NOT NULL " +//数据已被软删除
            "AND upload_at IS NOT NULL " +//数据已被上传到服务器
            "AND upload_at < update_at " +//数据上传后有更新
            "AND species_id LIKE :speciesIdLimit")//根据所属用户id查找species_id
    List<SpeciesEntity> getSpeciesEntityListNeedDeleteRemote(String speciesIdLimit);

    @Query("DELETE FROM species " +
            "WHERE delete_at IS NOT NULL " +//数据已被软删除
            "AND upload_at IS NULL " +//数据未被上传到服务器
            "AND species_id LIKE :speciesIdLimit")//根据所属用户id查找species_id
    void deleteSpeciesEntitiesNeedDeleteLocal(String speciesIdLimit);

    @Query("SELECT * FROM species " +
            "WHERE delete_at IS NULL " +//数据未被软删除
            "AND upload_at IS NULL " +//数据未被上传到服务器
            "AND species_id LIKE :speciesIdLimit")//根据所属用户id查找species_id
    List<SpeciesEntity> getSpeciesEntityListNeedAddRemote(String speciesIdLimit);

    @Query("SELECT * FROM species " +
            "WHERE delete_at IS NULL " +//数据未被软删除
            "AND upload_at IS NOT NULL " +//数据已被上传到服务器
            "AND upload_at < update_at " +//数据上传后有更新
            "AND species_id LIKE :speciesIdLimit")//根据所属用户id查找species_id
    List<SpeciesEntity> getSpeciesEntityListNeedUpdateRemote(String speciesIdLimit);

}
