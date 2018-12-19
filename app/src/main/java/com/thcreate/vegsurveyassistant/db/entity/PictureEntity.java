package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.thcreate.vegsurveyassistant.db.entity.model.Picture;

@Entity(
        tableName = "picture"
)
public class PictureEntity extends BaseEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String type;

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
        this.type = data.type;
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
