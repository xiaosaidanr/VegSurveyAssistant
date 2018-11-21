package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "caoben_wuzhong",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id"
                ),
//                @ForeignKey(
//                        entity = CaobenYangfang.class,
//                        parentColumns = "yangfang_code",
//                        childColumns = "yangfang_code",
//                        onDelete = CASCADE
//                ),
        },
        indices = {
                @Index("user_id"),
                @Index("yangfang_code"),
                @Index("wuzhong_code")
        }
)
public class CaobenWuzhong {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "yangfang_code")
    public String yangfangCode;//所属样方编号

    @ColumnInfo(name = "wuzhong_code")
    public String wuzhongCode;//标本编号

    @ColumnInfo(name = "wuzhong_name")
    public String wuzhongName;//物种名

    @ColumnInfo(name = "latin_name")
    public String latinName;//拉丁名

    @ColumnInfo(name = "zhucong_count")
    public String zhucongCount;//株丛数

    @ColumnInfo(name = "yingyangzhi_height")
    public String yingyangzhiHeight;//营养枝高度

    @ColumnInfo(name = "shengzhizhi_height")
    public String shengzhizhiHeight;//生殖枝高度

    public String coverage;//盖度

    public String biomass;//生物量

    public String note;//备注

    @ColumnInfo(name = "create_at")
    public Date createAt;//创建时间

    @ColumnInfo(name = "delete_at")
    public Date deleteAt;//删除时间

    @ColumnInfo(name = "modify_at")
    public Date modifyAt;//修改时间

    @ColumnInfo(name = "upload_at")
    public Date uploadAt;//上传时间

    public CaobenWuzhong(@NonNull int userId, @NonNull String yangfangCode, @NonNull String wuzhongCode){
        this.userId = userId;
        this.yangfangCode = yangfangCode;
        this.wuzhongCode = wuzhongCode;
    }

}
