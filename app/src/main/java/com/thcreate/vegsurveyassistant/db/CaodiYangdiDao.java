package com.thcreate.vegsurveyassistant.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface CaodiYangdiDao {

    @Insert
    void insert(CaodiYangdi caodiYangdi);

    @Update
    void update(CaodiYangdi caodiYangdi);

    @Delete
    void delete(CaodiYangdi caodiYangdi);

    @Query("DELETE FROM caodi_yangdi")
    void deleteAll();

    @Query("SELECT * FROM guancong_yangdi WHERE user_id = :userId ORDER BY id ASC")
    LiveData<List<CaodiYangdi>> getAllCaodiYangdiByUserId(int userId);

}
