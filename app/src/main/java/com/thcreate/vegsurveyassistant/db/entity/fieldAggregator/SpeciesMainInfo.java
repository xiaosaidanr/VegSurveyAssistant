package com.thcreate.vegsurveyassistant.db.entity.fieldAggregator;

import android.arch.persistence.room.ColumnInfo;

public class SpeciesMainInfo {

    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "plot_id")
    public String plotId;

    @ColumnInfo(name = "species_id")
    public String speciesId;

    @ColumnInfo(name = "code")
    public String code;//物种编号

    @ColumnInfo(name = "type")
    public String type;//物种类型

    @ColumnInfo(name = "name")
    public String name;//物种名

}
