package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "yangdi",
        foreignKeys = @ForeignKey(
                entity = User.class,
                parentColumns = "id",
                childColumns = "user_id",
                onDelete = CASCADE
        ),
        indices = {
                @Index("user_id"),
                @Index(value = "yangdi_code", unique = true)
        }
)
public class Yangdi {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "yangdi_code")
    public String yangdiCode;//样地编码

    public String type;//样地类型

    @ColumnInfo(name = "region_name")
    public String regionName;//行政区域

    public String poxiang;//坡向

    public String podu;//坡度

    public String powei;//坡位

    public String dimao;//地貌

    @ColumnInfo(name = "soil_feature")
    public String soilFeature;//土壤特征

    @ColumnInfo(name = "human_activity")
    public String humanActivity;//人类活动

    public String longitude;//经度

    public String latitude;//纬度

    @ColumnInfo(name = "create_at")
    public Date createAt;//创建时间

    @ColumnInfo(name = "delete_at")
    public Date deleteAt;//删除时间

    @ColumnInfo(name = "modify_at")
    public Date modifyAt;//修改时间

    @ColumnInfo(name = "upload_at")
    public Date uploadAt;//上传时间

    public Yangdi(@NonNull int userId, @NonNull String yangdiCode, @NonNull String type){
        this.userId = userId;
        this.yangdiCode = yangdiCode;
        this.type = type;
    }

}
