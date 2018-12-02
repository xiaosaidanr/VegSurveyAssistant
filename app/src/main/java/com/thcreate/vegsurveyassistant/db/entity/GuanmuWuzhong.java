package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
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
public class GuanmuWuzhong extends BaseWuzhong implements Parcelable {

    @ColumnInfo(name = "tree_number")
    public String treeNumber;//树号

    public String jijing;//基径

    public String height;//高度

    @ColumnInfo(name = "guanfu_x")
    public String guanfuX;//冠幅X

    @ColumnInfo(name = "guanfu_y")
    public String guanfuY;//冠幅Y

    public GuanmuWuzhong() {
    }

    @Ignore
    public GuanmuWuzhong(@NonNull int userId, @NonNull String yangfangCode, @NonNull String wuzhongCode){
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
        dest.writeValue(treeNumber);
        dest.writeValue(jijing);
        dest.writeValue(height);
        dest.writeValue(guanfuX);
        dest.writeValue(guanfuY);
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

    public static final Parcelable.Creator<GuanmuWuzhong> CREATOR = new Creator<GuanmuWuzhong>() {
        @Override
        public GuanmuWuzhong createFromParcel(Parcel source) {
            return new GuanmuWuzhong(source);
        }

        @Override
        public GuanmuWuzhong[] newArray(int size) {
            return new GuanmuWuzhong[0];
        }
    };

    @Ignore
    public GuanmuWuzhong(Parcel source){
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
        Object tmpTreeNumber = source.readValue(getClass().getClassLoader());
        if (tmpTreeNumber != null){
            treeNumber = (String)tmpTreeNumber;
        }
        Object tmpJijing = source.readValue(getClass().getClassLoader());
        if (tmpJijing != null){
            jijing = (String)tmpJijing;
        }
        Object tmpHeight = source.readValue(getClass().getClassLoader());
        if (tmpHeight != null){
            height = (String)tmpHeight;
        }
        Object tmpGuanfuX = source.readValue(getClass().getClassLoader());
        if (tmpGuanfuX != null){
            guanfuX = (String)tmpGuanfuX;
        }
        Object tmpGuanfuY = source.readValue(getClass().getClassLoader());
        if (tmpGuanfuY != null){
            guanfuY = (String)tmpGuanfuY;
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
