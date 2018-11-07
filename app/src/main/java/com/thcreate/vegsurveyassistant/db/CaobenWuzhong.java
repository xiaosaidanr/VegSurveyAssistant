package com.thcreate.vegsurveyassistant.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "caoben_wuzhong")
public class CaobenWuzhong {

    @PrimaryKey(autoGenerate = true)
    public int id;

}
