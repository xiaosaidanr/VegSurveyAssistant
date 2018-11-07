package com.thcreate.vegsurveyassistant.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface SenlinYangdiDao {

    @Insert
    void insert(SenlinYangdi senlinYangdi);

    @Update
    void update(SenlinYangdi senlinYangdi);

    @Delete
    void delete(SenlinYangdi senlinYangdi);

    @Query("DELETE FROM senlin_yangdi")
    void deleteAll();

    @Query("SELECT * FROM senlin_yangdi WHERE user_id = :userId ORDER BY id ASC")
    LiveData<List<SenlinYangdi>> getAllSenlinYangdiByUserId(int userId);

}
