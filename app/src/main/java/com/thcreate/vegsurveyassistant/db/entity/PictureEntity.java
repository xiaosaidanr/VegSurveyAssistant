package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.thcreate.vegsurveyassistant.db.entity.model.Picture;

public class PictureEntity extends BaseEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "picture_id")
    public String pictureId;

    @ColumnInfo(name = "owner_id")
    public String ownerId;

    @ColumnInfo(name = "local_addr")
    public String localAddr;

    public String url;

    public PictureEntity() {
    }

    @Ignore
    public PictureEntity(Picture data){
        this.id = data.id;
        this.pictureId = data.pictureId;
        this.ownerId = data.ownerId;
        this.localAddr = data.localAddr;
        this.url = data.url;
        this.createAt = data.createAt;
        this.updateAt = data.updateAt;
        this.uploadAt = data.uploadAt;
        this.deleteAt = data.deleteAt;
    }

    public Picture getPicture(){
        return new Picture(this);
    }
}
