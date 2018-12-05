package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thcreate.vegsurveyassistant.db.entity.Yangdi;

import java.util.List;

@Dao
//public interface YangdiDao extends BaseDao<Yangdi> {
public interface YangdiDao {


    @Insert
    void insert(Yangdi obj);

    @Update
    void update(Yangdi obj);

    @Delete
    void delete(Yangdi obj);

    @Query("DELETE FROM yangdi WHERE id = :id")
    void deleteById(int id);

    @Query("DELETE FROM yangdi")
    void deleteAll();

    @Query("SELECT * FROM yangdi WHERE yangdi_code = :yangdiCode")
    Yangdi getYangdiByYangdiCodeSync(String yangdiCode);

    @Query("SELECT * FROM yangdi WHERE yangdi_code = :yangdiCode")
    LiveData<Yangdi> getYangdiByYangdiCode(String yangdiCode);

    @Query("SELECT * FROM yangdi WHERE user_id = :userId AND type = :type ORDER BY id ASC")
    LiveData<List<Yangdi>> getAllYangdiByUserIdAndType(int userId, String type);

}
