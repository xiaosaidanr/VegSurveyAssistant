package com.thcreate.vegsurveyassistant.db.entity.fieldAggregator;

import android.arch.persistence.room.ColumnInfo;

public class PointMainInfo {

    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "point_id")
    public String pointId;//样点ID

    @ColumnInfo(name = "code")
    public String code;//样点编号

    @ColumnInfo(name = "alt")
    public String alt;//海拔

    @ColumnInfo(name = "lng")
    public String lng;//经度

    @ColumnInfo(name = "lat")
    public String lat;//纬度

}
