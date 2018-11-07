package com.thcreate.vegsurveyassistant.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface YangdianDao {

    @Insert
    void insert(Yangdian yangdian);

    @Update
    void update(Yangdian yangdian);

    @Delete
    void delete(Yangdian yangdian);

    @Query("DELETE FROM yangdian")
    void deleteAll();

    @Query("SELECT * FROM yangdian WHERE user_id = :userId ORDER BY id ASC")
    LiveData<List<Yangdian>> getAllYangdiansByUserId(int userId);

}
