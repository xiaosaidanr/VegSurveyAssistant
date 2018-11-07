package com.thcreate.vegsurveyassistant.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "qiaomu_yangfang")
public class QiaomuYangfang {

    @PrimaryKey(autoGenerate = true)
    public int id;

}
