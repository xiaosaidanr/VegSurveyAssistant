package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

public class BaseWuzhong extends BaseEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "yangfang_code")
    public String yangfangCode;//所属样方编号

    @ColumnInfo(name = "wuzhong_code")
    public String wuzhongCode;//标本编号

    @ColumnInfo(name = "wuzhong_name")
    public String wuzhongName;//物种名

    @ColumnInfo(name = "latin_name")
    public String latinName;//拉丁名

    public String note;//备注

    public BaseWuzhong() {
    }

    public BaseWuzhong(@NonNull int userId, @NonNull String yangfangCode, @NonNull String wuzhongCode){
        this.userId = userId;
        this.yangfangCode = yangfangCode;
        this.wuzhongCode = wuzhongCode;
    }

}
