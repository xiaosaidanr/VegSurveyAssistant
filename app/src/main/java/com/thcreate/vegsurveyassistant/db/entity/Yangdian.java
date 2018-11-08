package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "yangdian",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = CASCADE
        ),
        indices = {
                @Index("user_id"),
                @Index("yangdian_code")
        }
)
public class Yangdian {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "yangdian_code")
    public String yangdianCode;//样点编码

    @ColumnInfo(name = "qunluo_type")
    public String qunluoType;//群落类型

    @ColumnInfo(name = "dominant_species")
    public String dominantSpecies;//优势种

    @ColumnInfo(name = "qunluo_coverage")
    public String qunluoCoverage;//群落盖度

    @ColumnInfo(name = "qunluo_height")
    public String qunluoHeight;//群落高度

    @ColumnInfo(name = "dixing_zhibei_status")
    public String dixingZhibeiStatus;//地形和植被概况

    @ColumnInfo(name = "region_name")
    public String regionName;//行政地名

    @ColumnInfo(name = "human_activity")
    public String humanActivity;//人类活动

    public String altitude;//海拔

    public String longitude;//经度

    public String latitude;//纬度

    public String investigator;//调查人

    @ColumnInfo(name = "investigate_date")
    public String investigateDate;//调查时间

    public String note;//备注

    @ColumnInfo(name = "create_at")
    public Date createAt;//创建时间

    @ColumnInfo(name = "delete_at")
    public Date deleteAt;//删除时间

    @ColumnInfo(name = "modify_at")
    public Date modifyAt;//修改时间

    @ColumnInfo(name = "upload_at")
    public Date uploadAt;//上传时间

    public Yangdian(@NonNull int userId, @NonNull String yangdianCode){
        this.userId = userId;
        this.yangdianCode = yangdianCode;
    }
}
