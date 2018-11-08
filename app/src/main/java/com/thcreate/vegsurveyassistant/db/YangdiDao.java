package com.thcreate.vegsurveyassistant.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface YangdiDao {

    @Insert
    void insert(Yangdi yangdi);

    @Update
    void update(Yangdi yangdi);

    @Delete
    void delete(Yangdi yangdi);

    @Query("DELETE FROM yangdi")
    void deleteAll();

    @Query("SELECT * FROM yangdi WHERE yangdi_code = :yangdiCode")
    LiveData<Yangdi> getYangdiByYangdiCode(String yangdiCode);

    @Query("SELECT * FROM yangdi WHERE user_id = :userId AND type = :type ORDER BY id ASC")
    LiveData<List<Yangdi>> getAllYangdiByUserIdAndType(int userId, String type);

}
