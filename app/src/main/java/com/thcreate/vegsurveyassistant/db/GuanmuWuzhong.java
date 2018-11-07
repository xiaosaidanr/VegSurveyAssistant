package com.thcreate.vegsurveyassistant.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "guanmu_wuzhong")
public class GuanmuWuzhong {

    @PrimaryKey(autoGenerate = true)
    public int id;

}
