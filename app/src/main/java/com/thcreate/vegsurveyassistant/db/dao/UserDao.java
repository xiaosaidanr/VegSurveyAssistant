package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.thcreate.vegsurveyassistant.db.entity.User;

import java.util.List;

@Dao
public interface UserDao extends BaseDao<User>{

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user ORDER BY id ASC")
    LiveData<List<User>> getAllUser();

    @Query("SELECT * FROM user LIMIT 1")
    LiveData<User> getOneUser();

}
