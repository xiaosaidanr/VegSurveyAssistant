package com.thcreate.vegsurveyassistant.db.entity.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.thcreate.vegsurveyassistant.db.entity.LandPictureEntity;
import com.thcreate.vegsurveyassistant.db.entity.PictureEntity;
import com.thcreate.vegsurveyassistant.db.entity.PlotPictureEntity;
import com.thcreate.vegsurveyassistant.db.entity.PointPictureEntity;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesPictureEntity;
import com.thcreate.vegsurveyassistant.db.gsonTypeAdapter.DateTypeAdapter;

import java.util.Date;

public class Picture {

    public int id;
    @Expose
    @SerializedName("picture_id")
    public String pictureId;//图片ID
    @Expose
    @SerializedName("type")
    public String type;
    @Expose
    @SerializedName("owner_id")
    public String ownerId;
    public String localAddr;
    @Expose
    @SerializedName("url")
    public String url;
    @Expose(serialize = false)
    @SerializedName("created_at")
    @JsonAdapter(DateTypeAdapter.class)
    public Date createAt;//创建时间
    @Expose(serialize = false)
    @SerializedName("updated_at")
    @JsonAdapter(DateTypeAdapter.class)
    public Date updateAt;//修改时间
    public Date uploadAt;//上传时间
    public Date deleteAt;//删除时间

    private <T extends PictureEntity> void init(T entity) {
        this.id = entity.id;
        this.pictureId = entity.pictureId;
        this.ownerId = entity.ownerId;
        this.localAddr = entity.localAddr;
        this.url = entity.url;
        this.createAt = entity.createAt;
        this.updateAt = entity.updateAt;
        this.uploadAt = entity.uploadAt;
        this.deleteAt = entity.deleteAt;
    }
    public Picture(PictureEntity entity){
        init(entity);
    }
    public Picture(PointPictureEntity entity){
        this.type = "point";
        init(entity);
    }
    public Picture(LandPictureEntity entity){
        this.type = "land";
        init(entity);
    }
    public Picture(PlotPictureEntity entity){
        this.type = "plot";
        init(entity);
    }
    public Picture(SpeciesPictureEntity entity){
        this.type = "species";
        init(entity);
    }


}
