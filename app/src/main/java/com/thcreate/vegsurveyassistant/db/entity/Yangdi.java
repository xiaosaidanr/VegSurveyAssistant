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
public class Yangdi extends BaseEntity implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "yangdi_code")
    public String yangdiCode;//样地编码

    public String type;//样地类型

    @ColumnInfo(name = "xingzheng_region")
    public String xingzhengRegion;//行政区域

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

    public Yangdi() {
    }

    @Ignore
    public Yangdi(@NonNull int userId, @NonNull String yangdiCode, @NonNull String type){
        this.userId = userId;
        this.yangdiCode = yangdiCode;
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(userId);
        dest.writeValue(yangdiCode);
        dest.writeValue(type);
        dest.writeValue(xingzhengRegion);
        dest.writeValue(poxiang);
        dest.writeValue(podu);
        dest.writeValue(powei);
        dest.writeValue(dimao);
        dest.writeValue(soilFeature);
        dest.writeValue(humanActivity);
        dest.writeValue(longitude);
        dest.writeValue(latitude);
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

    public static final Parcelable.Creator<Yangdi> CREATOR = new Creator<Yangdi>() {
        @Override
        public Yangdi createFromParcel(Parcel source) {
            return new Yangdi(source);
        }

        @Override
        public Yangdi[] newArray(int size) {
            return new Yangdi[0];
        }
    };

    @Ignore
    public Yangdi(Parcel source){
        Object tmpId = source.readValue(getClass().getClassLoader());
        if (tmpId != null){
            id = (int)tmpId;
        }
        Object tmpUserId = source.readValue(getClass().getClassLoader());
        if (tmpUserId != null){
            userId = (int)tmpUserId;
        }
        Object tmpYangdiCode = source.readValue(getClass().getClassLoader());
        if (tmpYangdiCode != null){
            yangdiCode = (String)tmpYangdiCode;
        }
        Object tmpType = source.readValue(getClass().getClassLoader());
        if (tmpType != null){
            type = (String)tmpType;
        }
        Object tmpXingzhengRegion = source.readValue(getClass().getClassLoader());
        if (tmpXingzhengRegion != null){
            xingzhengRegion = (String)tmpXingzhengRegion;
        }
        Object tmpPoxiang = source.readValue(getClass().getClassLoader());
        if (tmpPoxiang != null){
            poxiang = (String)tmpPoxiang;
        }
        Object tmpPodu = source.readValue(getClass().getClassLoader());
        if (tmpPodu != null){
            podu = (String)tmpPodu;
        }
        Object tmpPowei = source.readValue(getClass().getClassLoader());
        if (tmpPowei != null){
            powei = (String)tmpPowei;
        }
        Object tmpDimao = source.readValue(getClass().getClassLoader());
        if (tmpDimao != null){
            dimao = (String)tmpDimao;
        }
        Object tmpSoilFeature = source.readValue(getClass().getClassLoader());
        if (tmpSoilFeature != null){
            soilFeature = (String)tmpSoilFeature;
        }
        Object tmpHumanActivity = source.readValue(getClass().getClassLoader());
        if (tmpHumanActivity != null){
            humanActivity = (String)tmpHumanActivity;
        }
        Object tmpLongitude = source.readValue(getClass().getClassLoader());
        if (tmpLongitude != null){
            longitude = (String)tmpLongitude;
        }
        Object tmpLatitude = source.readValue(getClass().getClassLoader());
        if (tmpLatitude != null){
            latitude = (String)tmpLatitude;
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
