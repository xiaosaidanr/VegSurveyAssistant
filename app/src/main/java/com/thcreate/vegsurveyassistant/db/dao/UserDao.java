package com.thcreate.vegsurveyassistant.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.thcreate.vegsurveyassistant.db.entity.User;

import java.util.List;

@Dao
public interface UserDao extends BaseDao<User>{

    String IS_NOT_CURRENT_USER = "0";
    String IS_CURRENT_USER = "1";

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user ORDER BY id ASC")
    LiveData<List<User>> getAllUser();

    @Query("SELECT * FROM user WHERE is_current_user = " + IS_CURRENT_USER)
    LiveData<User> getCurrentUser();

}
