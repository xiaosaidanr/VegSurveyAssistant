package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
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
public class CaobenWuzhong extends BaseWuzhong implements Parcelable {

    @ColumnInfo(name = "zhucong_count")
    public String zhucongCount;//株丛数

    @ColumnInfo(name = "yingyangzhi_height")
    public String yingyangzhiHeight;//营养枝高度

    @ColumnInfo(name = "shengzhizhi_height")
    public String shengzhizhiHeight;//生殖枝高度

    public String coverage;//盖度

    public String biomass;//生物量

    public CaobenWuzhong() {
    }

    public CaobenWuzhong(@NonNull int userId, @NonNull String yangfangCode, @NonNull String wuzhongCode){
        super(userId, yangfangCode, wuzhongCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userId);
        dest.writeValue(yangfangCode);
        dest.writeValue(wuzhongCode);
        dest.writeValue(wuzhongName);
        dest.writeValue(latinName);
        dest.writeValue(zhucongCount);
        dest.writeValue(yingyangzhiHeight);
        dest.writeValue(shengzhizhiHeight);
        dest.writeValue(coverage);
        dest.writeValue(biomass);
        dest.writeValue(note);
        if (createAt == null){
            dest.writeValue(null);
        }
        else {
            dest.writeValue(createAt.getTime());
        }
        if (deleteAt == null){
            dest.writeValue(null);
        }
        else {
            dest.writeValue(deleteAt.getTime());
        }
        if (modifyAt == null){
            dest.writeValue(null);
        }
        else {
            dest.writeValue(modifyAt.getTime());
        }
        if (uploadAt == null){
            dest.writeValue(null);
        }
        else {
            dest.writeValue(uploadAt.getTime());
        }
    }

    public static final Parcelable.Creator<CaobenWuzhong> CREATOR = new Creator<CaobenWuzhong>() {
        @Override
        public CaobenWuzhong createFromParcel(Parcel source) {
            return new CaobenWuzhong(source);
        }

        @Override
        public CaobenWuzhong[] newArray(int size) {
            return new CaobenWuzhong[0];
        }
    };

    public CaobenWuzhong(Parcel source){
        Object tmpId = source.readValue(getClass().getClassLoader());
        if (tmpId != null){
            id = (int)tmpId;
        }
        Object tmpUserId = source.readValue(getClass().getClassLoader());
        if (tmpUserId != null){
            userId = (int)tmpUserId;
        }
        Object tmpYangfangCode = source.readValue(getClass().getClassLoader());
        if (tmpYangfangCode != null){
            yangfangCode = (String)tmpYangfangCode;
        }
        Object tmpWuzhongCode = source.readValue(getClass().getClassLoader());
        if (tmpWuzhongCode != null){
            wuzhongCode = (String)tmpWuzhongCode;
        }
        Object tmpWuzhongName = source.readValue(getClass().getClassLoader());
        if (tmpWuzhongName != null){
            wuzhongName = (String)tmpWuzhongName;
        }
        Object tmpLatinName = source.readValue(getClass().getClassLoader());
        if (tmpLatinName != null){
            latinName = (String)tmpLatinName;
        }
        Object tmpZhucongCount = source.readValue(getClass().getClassLoader());
        if (tmpZhucongCount != null){
            zhucongCount = (String)tmpZhucongCount;
        }
        Object tmpYingyangzhiHeight = source.readValue(getClass().getClassLoader());
        if (tmpYingyangzhiHeight != null){
            yingyangzhiHeight = (String)tmpYingyangzhiHeight;
        }
        Object tmpShengzhizhiHeight = source.readValue(getClass().getClassLoader());
        if (tmpShengzhizhiHeight != null){
            shengzhizhiHeight = (String)tmpShengzhizhiHeight;
        }
        Object tmpCoverage = source.readValue(getClass().getClassLoader());
        if (tmpCoverage != null){
            coverage = (String)tmpCoverage;
        }
        Object tmpBiomass = source.readValue(getClass().getClassLoader());
        if (tmpBiomass != null){
            biomass = (String)tmpBiomass;
        }
        Object tmpNote = source.readValue(getClass().getClassLoader());
        if (tmpNote != null){
            note = (String)tmpNote;
        }
        Object tmpCreateAt = source.readValue(getClass().getClassLoader());
        if (tmpCreateAt != null){
            createAt = new Date((Long)tmpCreateAt);
        }
        Object tmpDeleteAt = source.readValue(getClass().getClassLoader());
        if (tmpDeleteAt != null){
            deleteAt = new Date((Long)tmpDeleteAt);
        }
        Object tmpModifyAt = source.readValue(getClass().getClassLoader());
        if (tmpModifyAt != null){
            modifyAt = new Date((Long)tmpModifyAt);
        }
        Object tmpUploadAt = source.readValue(getClass().getClassLoader());
        if (tmpUploadAt != null){
            uploadAt = new Date((Long)tmpUploadAt);
        }
    }
}
