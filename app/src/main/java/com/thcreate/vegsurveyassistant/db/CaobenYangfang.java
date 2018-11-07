package com.thcreate.vegsurveyassistant.db;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "caoben_yangfang")
public class CaobenYangfang {

    @PrimaryKey(autoGenerate = true)
    public int id;

}
