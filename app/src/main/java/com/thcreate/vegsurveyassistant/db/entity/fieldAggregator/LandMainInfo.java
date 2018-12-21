package com.thcreate.vegsurveyassistant.db.entity.fieldAggregator;

import android.arch.persistence.room.ColumnInfo;

public class LandMainInfo {

    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "land_id")
    public String landId;

    @ColumnInfo(name = "code")
    public String code;//样地编号

    @ColumnInfo(name = "type")
    public String type;//样地类型

    @ColumnInfo(name = "lng")
    public String lng;//经度

    @ColumnInfo(name = "lat")
    public String lat;//纬度

    @ColumnInfo(name = "alt")
    public String alt;//海拔

}
