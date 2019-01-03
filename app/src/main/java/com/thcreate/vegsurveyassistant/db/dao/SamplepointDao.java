package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.thcreate.vegsurveyassistant.db.entity.SamplepointEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.PointMainInfo;

import java.util.List;

@Dao
public interface SamplepointDao extends BaseDao<SamplepointEntity> {

    @Query("UPDATE point SET delete_at = :deleteAt, update_at = :deleteAt WHERE id = :id")
    void softDeleteById(int id, long deleteAt);

    @Query("DELETE FROM point WHERE id = :id")
    void deleteById(int id);

    @Query("SELECT * FROM point WHERE point_id = :pointId")
    LiveData<SamplepointEntity> getSamplepointEntityByPointId(String pointId);

//    @Query("SELECT * FROM point WHERE user_id = :userId AND delete_at IS NULL ORDER BY id ASC")
//    LiveData<List<SamplepointEntity>> getSamplepointEntityListByUserId(int userId);

    @Query("SELECT id, point_id, code, alt, lng, lat FROM point WHERE user_id = :userId AND delete_at IS NULL ORDER BY id ASC")
    LiveData<List<PointMainInfo>> getPointMainInfoListByUserId(int userId);

    @Query("SELECT * FROM point " +
            "WHERE user_id = :userId " +
            "AND delete_at IS NOT NULL " +//数据已被软删除
            "AND upload_at IS NOT NULL " +//数据已被上传到服务器
            "AND upload_at < update_at")//数据上传后有更新
    List<SamplepointEntity> getSamplepointEntityListNeedDeleteRemote(int userId);

    @Query("DELETE FROM point " +
            "WHERE user_id = :userId " +
            "AND delete_at IS NOT NULL " +//数据已被软删除
            "AND upload_at IS NULL")//数据未被上传到服务器
    void deleteSamplepointEntitiesNeedDeleteLocal(int userId);

    @Query("SELECT * FROM point " +
            "WHERE user_id = :userId " +
            "AND delete_at IS NULL " +//数据未被软删除
            "AND upload_at IS NULL")//数据未被上传到服务器
    List<SamplepointEntity> getSamplepointEntityListNeedAddRemote(int userId);

    @Query("SELECT * FROM point " +
            "WHERE user_id = :userId " +
            "AND delete_at IS NULL " +//数据未被软删除
            "AND upload_at IS NOT NULL " +//数据已被上传到服务器
            "AND upload_at < update_at")//数据上传后有更新
    List<SamplepointEntity> getSamplepointEntityListNeedUpdateRemote(int userId);

}
