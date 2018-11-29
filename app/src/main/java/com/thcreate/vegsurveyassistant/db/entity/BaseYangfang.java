package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

public class BaseYangfang extends BaseEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "yangdi_code")
    public String yangdiCode;//所属样地编码

    @ColumnInfo(name = "yangfang_code")
    public String yangfangCode;//样方编码

    @ColumnInfo(name = "qunluo_name")
    public String qunluoName;//群落名称

    public String length;//样方长

    public String width;//样方宽

    @ColumnInfo(name = "qunluo_coverage")
    public String qunluoCoverage;//群落盖度

    @ColumnInfo(name = "qunluo_height")
    public String qunluoHeight;//群落高度

    public String longitude;//经度

    public String latitude;//纬度

    public String investigator;//调查人

    @ColumnInfo(name = "investigate_date")
    public String investigateDate;//调查时间

    public BaseYangfang() {
    }

    public BaseYangfang(@NonNull int userId, @NonNull String yangdiCode, @NonNull String yangfangCode){
        this.userId = userId;
        this.yangdiCode = yangdiCode;
        this.yangfangCode = yangfangCode;
    }

}
