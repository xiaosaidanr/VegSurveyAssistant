package com.thcreate.vegsurveyassistant.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "guanmu_wuzhong",
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "user_id"
                ),
                @ForeignKey(
                        entity = GuanmuYangfang.class,
                        parentColumns = "yangfang_code",
                        childColumns = "yangfang_code",
                        onDelete = CASCADE
                ),
        },
        indices = {
                @Index("user_id"),
                @Index("yangfang_code"),
                @Index("wuzhong_code")
        }
)
public class GuanmuWuzhong {

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

    @ColumnInfo(name = "tree_number")
    public String treeNumber;//树号

    public String jijing;//基径

    public String height;//高度

    @ColumnInfo(name = "guanfu_x")
    public String guanfuX;//冠幅X

    @ColumnInfo(name = "guanfu_y")
    public String guanfuY;//冠幅Y

    public String note;//备注

    @ColumnInfo(name = "create_at")
    public Date createAt;//创建时间

    @ColumnInfo(name = "delete_at")
    public Date deleteAt;//删除时间

    @ColumnInfo(name = "modify_at")
    public Date modifyAt;//修改时间

    @ColumnInfo(name = "upload_at")
    public Date uploadAt;//上传时间

    public GuanmuWuzhong(@NonNull int userId, @NonNull String yangfangCode, @NonNull String wuzhongCode){
        this.userId = userId;
        this.yangfangCode = yangfangCode;
        this.wuzhongCode = wuzhongCode;
    }

}
