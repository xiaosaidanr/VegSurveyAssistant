package com.thcreate.vegsurveyassistant.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface GuancongYangdiDao {

    @Insert
    void insert(GuancongYangdi guancongYangdi);

    @Update
    void update(GuancongYangdi guancongYangdi);

    @Delete
    void delete(GuancongYangdi guancongYangdi);

    @Query("DELETE FROM guancong_yangdi")
    void deleteAll();

    @Query("SELECT * FROM guancong_yangdi WHERE user_id = :userId ORDER BY id ASC")
    LiveData<List<GuancongYangdi>> getAllGuancongYangdiByUserId(int userId);

}
