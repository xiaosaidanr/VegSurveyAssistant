package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thcreate.vegsurveyassistant.db.entity.PictureEntity;
import com.thcreate.vegsurveyassistant.db.entity.PlotPictureEntity;

import java.util.List;

@Dao
public interface PictureDao {

    //plot related picture database operation
    @Insert
    void insertPlotPicture(PlotPictureEntity entity);
    @Delete
    void deletePlotPicture(PlotPictureEntity entity);
    @Query("UPDATE plot_picture SET delete_at = :deleteAt, update_at = :deleteAt WHERE id = :id")
    void softDeletePlotPictureById(int id, long deleteAt);
    @Update
    void updatePlotPicture(PlotPictureEntity entity);
    @Query("SELECT * FROM plot_picture WHERE owner_id = :ownerId AND delete_at IS NULL")
    LiveData<List<PlotPictureEntity>> getPlotPictureEntityListByOwnerId(String ownerId);
    @Query("SELECT * FROM plot_picture WHERE delete_at IS NOT NULL AND owner_id LIKE :ownerIdLimit")
    List<PlotPictureEntity> getPlotPictureEntityListNeedDelete(String ownerIdLimit);
    @Query("SELECT * FROM plot_picture WHERE delete_at IS NULL AND upload_at IS NULL AND owner_id LIKE :ownerIdLimit")
    List<PlotPictureEntity> getPlotPictureEntityListNeedAddRemote(String ownerIdLimit);
    @Query("SELECT * FROM plot_picture WHERE delete_at IS NULL")
    List<PlotPictureEntity> getAllAlivePlotPictureEntity();
    @Query("SELECT * FROM plot_picture WHERE picture_id = :pictureId")
    PlotPictureEntity getPlotPictureEntityByPictureIdSync(String pictureId);
    @Query("SELECT * FROM plot_picture WHERE url = :url")
    PlotPictureEntity getPlotPictureEntityByUrlSync(String url);
}
