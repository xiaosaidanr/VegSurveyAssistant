package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Query;

import com.thcreate.vegsurveyassistant.db.entity.PictureEntity;

import java.util.List;

@Dao
public interface PictureDao extends BaseDao<PictureEntity> {

    @Query("DELETE FROM picture WHERE id = :id")
    void deleteById(int id);

    @Query("UPDATE picture SET delete_at = :deleteAt, update_at = :deleteAt WHERE id = :id")
    void softDeleteById(int id, long deleteAt);

    //TODO softDeleteByPictureId
    void softDeleteByPictureId(String pictureId, long deleteAt);

    @Query("UPDATE picture SET delete_at = :deleteAt, update_at = :deleteAt WHERE type = :type AND owner_id = :ownerId")
    void softDeletePictureEntityByTypeAndOwnerId(String type, String ownerId, long deleteAt);

    @Query("UPDATE picture SET delete_at = :deleteAt, update_at = :deleteAt")
    void softDeleteAll(long deleteAt);

    @Query("SELECT * FROM picture WHERE type = :type AND owner_id = :ownerId AND delete_at IS NOT NULL")
    LiveData<List<PictureEntity>> getPictureEntityListByTypeAndOwnerId(String type, String ownerId);

}
