package com.thcreate.vegsurveyassistant.db.entity.model;

import com.thcreate.vegsurveyassistant.db.entity.PictureEntity;

import java.util.Date;

public class Picture {

    public int id;
    public String pictureId;//图片ID
    public String type;
    public String ownerId;
    public String localAddr;
    public String url;
    public Date createAt;//创建时间
    public Date updateAt;//修改时间
    public Date uploadAt;//上传时间
    public Date deleteAt;//删除时间

    public Picture() {
    }

    public Picture(PictureEntity entity){
        this.id = entity.id;
        this.pictureId = entity.pictureId;
        this.type = entity.type;
        this.ownerId = entity.ownerId;
        this.localAddr = entity.localAddr;
        this.url = entity.url;
        this.createAt = entity.createAt;
        this.updateAt = entity.updateAt;
        this.uploadAt = entity.uploadAt;
        this.deleteAt = entity.deleteAt;
    }

    public PictureEntity getEntity(){
        return new PictureEntity(this);
    }

//    public static Picture getInstance(PictureEntity entity){
//        return new Picture(entity);
//    }
}
