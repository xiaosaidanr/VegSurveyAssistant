package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.thcreate.vegsurveyassistant.db.entity.UserEntity;

import java.util.List;

@Dao
//public interface UserDao extends BaseDao<UserEntity>{
public interface UserDao {

//    String IS_NOT_CURRENT_USER = "0";
//    String IS_CURRENT_USER = "1";

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserEntity obj);

    @Update
    void update(UserEntity obj);

    @Delete
    void delete(UserEntity obj);

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user ORDER BY id ASC")
    LiveData<List<UserEntity>> getAllUser();

    @Query("SELECT * FROM user WHERE id = :id")
    UserEntity getUserById(int id);

//    @Query("SELECT * FROM user WHERE is_current_user = 1")
//    LiveData<UserEntity> getCurrentUserAsync();
//
//    @Query("SELECT * FROM user WHERE is_current_user = 1")
//    UserEntity getCurrentUserSync();

}
