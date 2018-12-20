package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.thcreate.vegsurveyassistant.db.entity.PictureEntity;

import java.util.List;

@Dao
public interface PictureDao extends BaseDao<PictureEntity> {

    @Query("UPDATE picture SET delete_at = :deleteAt, update_at = :deleteAt WHERE id = :id")
    void softDeleteById(int id, long deleteAt);

    @Query("SELECT * FROM picture WHERE type = :type AND owner_id = :ownerId AND delete_at IS NOT NULL")
    LiveData<List<PictureEntity>> getPictureEntityListByTypeAndOwnerId(String type, String ownerId);

}
