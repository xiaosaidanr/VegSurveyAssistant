package com.thcreate.vegsurveyassistant.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "qiaomu_yangfang",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id"
                ),
                @ForeignKey(
                        entity = Yangdi.class,
                        parentColumns = "yangdi_code",
                        childColumns = "yangdi_code",
                        onDelete = CASCADE
                )
        },
        indices = {
                @Index("user_id"),
                @Index("yangdi_code"),
                @Index(value = "yangfang_code", unique = true)
        }
)
public class QiaomuYangfang {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "yangdi_code")
    public String yangdiCode;//所属样地编码

    @ColumnInfo(name = "yangfang_code")
    public String yangfangCode;//样方编码

    @ColumnInfo(name = "qunluo_name")
    public String qunluoName;//群落名称

    public String length;//样方长

    public String width;//样方宽

    @ColumnInfo(name = "qunluo_coverage")
    public String qunluoCoverage;//群落盖度

    @ColumnInfo(name = "qunluo_height")
    public String qunluoHeight;//群落高度

    @ColumnInfo(name = "xiongjing_average")
    public String xiongjingAverage;//平均胸径

    public String longitude;//经度

    public String latitude;//纬度

    public String investigator;//调查人

    @ColumnInfo(name = "investigate_date")
    public String investigateDate;//调查时间

    @ColumnInfo(name = "create_at")
    public Date createAt;//创建时间

    @ColumnInfo(name = "delete_at")
    public Date deleteAt;//删除时间

    @ColumnInfo(name = "modify_at")
    public Date modifyAt;//修改时间

    @ColumnInfo(name = "upload_at")
    public Date uploadAt;//上传时间

    public QiaomuYangfang(@NonNull int userId, @NonNull String yangdiCode, @NonNull String yangfangCode){
        this.userId = userId;
        this.yangdiCode = yangdiCode;
        this.yangfangCode = yangfangCode;
    }
}
