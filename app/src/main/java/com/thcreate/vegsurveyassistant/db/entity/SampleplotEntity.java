package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.model.BaseSampleplot;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "plot",
        foreignKeys = @ForeignKey(
                entity = SamplelandEntity.class,
                parentColumns = "land_id",
                childColumns = "land_id",
                onDelete = CASCADE
        ),
        indices = {
                @Index("land_id"),
                @Index(value = "plot_id", unique = true),
                @Index("type")
        }
)
public class SampleplotEntity extends BaseEntity {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "land_id")
    public String landId;//所属样地ID

    @ColumnInfo(name = "plot_id")
    public String plotId;//样方ID

    public String code;//样方编号

    public String type;//样方类型

    public String data;//数据

    public String lng;//经度

    public String lat;//纬度

    public String alt;//海拔

    @ColumnInfo(name = "investigator_name")
    public String investigatorName;//调查人

    @ColumnInfo(name = "investigated_at")
    public String investigatedAt;//调查时间

    public SampleplotEntity() {
    }

    @Ignore
    public SampleplotEntity(@NonNull String landId, @NonNull String plotId){
        this.landId = landId;
        this.plotId = plotId;
    }

    public <T extends BaseSampleplot> void initCommonFromPlot(T data){
        this.id = data.id;
        this.landId = data.landId;
        this.plotId = data.plotId;
        this.code = data.code;
        this.type = data.type;
        this.lng = data.lng;
        this.lat = data.lat;
        this.alt = data.alt;
        this.investigatedAt = data.investigatedAt;
        this.investigatorName = data.investigatorName;
        this.createAt = data.createAt;
        this.updateAt = data.updateAt;
        this.uploadAt = data.uploadAt;
        this.deleteAt = data.deleteAt;
    }

}
