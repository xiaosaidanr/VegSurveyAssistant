package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "user")
public class User {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;//用户名

    public String phone;//手机号码

    @ColumnInfo(name = "is_current_user")
    public int isCurrentUser;

    public User(@NonNull String phone, @NonNull int isCurrentUser){
        this.phone = phone;
        this.isCurrentUser = isCurrentUser;
    }
}
