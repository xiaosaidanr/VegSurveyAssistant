package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.model.Samplepoint;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "point",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = CASCADE
        ),
        indices = {
                @Index("user_id"),
                @Index(value = "point_id", unique = true)
        }
)
public class SamplepointEntity extends BaseEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "point_id")
    public String pointId;//样点ID

    public String code;//样点编号

    public String data;//数据

    public String alt;//海拔

    public String lng;//经度

    public String lat;//纬度

    @ColumnInfo(name = "investigator_name")
    public String investigatorName;//调查人

    @ColumnInfo(name = "investigated_at")
    public String investigatedAt;//调查时间

    public SamplepointEntity() {
    }

    @Ignore
    public SamplepointEntity(int userId, @NonNull String pointId){
        this.userId = userId;
        this.pointId = pointId;
    }

    public void initCommonFromPoint(Samplepoint data){
        this.id = data.id;
        this.userId = data.userId;
        this.pointId = data.pointId;
        this.code = data.code;
        this.alt = data.alt;
        this.lat = data.lat;
        this.lng = data.lng;
        this.investigatedAt = data.investigatedAt;
        this.investigatorName = data.investigatorName;
        this.createAt = data.createAt;
        this.updateAt = data.updateAt;
        this.uploadAt = data.uploadAt;
        this.deleteAt = data.deleteAt;
    }

}
