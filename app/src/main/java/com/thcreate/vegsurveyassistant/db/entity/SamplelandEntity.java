package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.model.Sampleland;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "land",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = CASCADE
        ),
        indices = {
                @Index("user_id"),
                @Index(value = "land_id", unique = true),
                @Index("type")
        }
)
public class SamplelandEntity extends BaseEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "land_id")
    public String landId;//样地ID

    public String code;//样地编号

    public String type;//样地类型

    public String data;//数据

    public String lng;//经度

    public String lat;//纬度

    public String alt;//海拔

    public SamplelandEntity() {
    }

    @Ignore
    public SamplelandEntity(int userId, @NonNull String landId, @NonNull String type){
        this.userId = userId;
        this.landId = landId;
        this.type = type;
    }

    public void initCommonFromLand(Sampleland data){
        this.id = data.id;
        this.userId = data.userId;
        this.landId = data.landId;
        this.code = data.code;
        this.type = data.type;
        this.lng = data.lng;
        this.lat = data.lat;
        this.alt = data.alt;
        this.createAt = data.createAt;
        this.updateAt = data.updateAt;
        this.uploadAt = data.uploadAt;
        this.deleteAt = data.deleteAt;
    }

}
