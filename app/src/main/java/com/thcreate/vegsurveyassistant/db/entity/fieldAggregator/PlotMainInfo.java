package com.thcreate.vegsurveyassistant.db.entity.fieldAggregator;

import android.arch.persistence.room.ColumnInfo;

public class PlotMainInfo {

    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "land_id")
    public String landId;//所属样地ID

    @ColumnInfo(name = "plot_id")
    public String plotId;//样方ID

    @ColumnInfo(name = "code")
    public String code;//样方编号

    @ColumnInfo(name = "type")
    public String type;//样方类型

}
