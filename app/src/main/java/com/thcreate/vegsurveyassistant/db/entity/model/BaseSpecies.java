package com.thcreate.vegsurveyassistant.db.entity.model;

import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;

import java.util.Date;

abstract public class BaseSpecies {

    public int id;
    public String plotId;//所属样方编号
    public String speciesId;//物种编号
    public String type;//物种类型
    public String name;//物种名
    public String latinName;//拉丁名
    public String note;//备注
    public Date createAt;//创建时间
    public Date updateAt;//修改时间
    public Date uploadAt;//上传时间
    public Date deleteAt;//删除时间

    public BaseSpecies() {
    }

    public BaseSpecies(@NonNull String plotId, @NonNull String speciesId){
        this.plotId = plotId;
        this.speciesId = speciesId;
    }

    public BaseSpecies(int id, @NonNull String plotId, @NonNull String speciesId){
        this.id = id;
        this.plotId = plotId;
        this.speciesId = speciesId;
    }

    abstract public SpeciesEntity getSpeciesEntity();

}
