package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user")
public class UserEntity {

    @PrimaryKey
    public int id;

    public String name;//用户名

    public UserEntity(int id, String name){
        this.id = id;
        this.name = name;
    }
}
