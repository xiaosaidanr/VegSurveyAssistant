package com.thcreate.vegsurveyassistant.db.entity.model;

import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;

import java.util.Date;

abstract public class BaseSampleplot {

    public int id;
    public String landId;//所属样地编码
    public String plotId;//样方编号
    public String type;//样方类型
    public String communityName;//群落名称
    public String plotLength;//样方长
    public String plotWidth;//样方宽
    public String communityCoverage;//群落盖度
    public String communityHeight;//群落高度
    public String lng;//经度
    public String lat;//纬度
    public String investigator;//调查人
    public String investigateDate;//调查时间
    public Date createAt;//创建时间
    public Date updateAt;//修改时间
    public Date uploadAt;//上传时间
    public Date deleteAt;//删除时间

    public BaseSampleplot() {
    }

    public BaseSampleplot(@NonNull String landId, @NonNull String plotId){
        this.landId = landId;
        this.plotId = plotId;
    }

    public BaseSampleplot(int id, @NonNull String landId, @NonNull String plotId){
        this.id = id;
        this.landId = landId;
        this.plotId = plotId;
    }

    abstract public SampleplotEntity getSampleplotEntity();

}
