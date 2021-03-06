package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;

import java.util.Date;

public class BaseEntity {

    @ColumnInfo(name = "create_at")
    public Date createAt;//创建时间

    @ColumnInfo(name = "update_at")
    public Date updateAt;//修改时间

    @ColumnInfo(name = "upload_at")
    public Date uploadAt;//上传时间

    @ColumnInfo(name = "delete_at")
    public Date deleteAt;//删除时间

}
