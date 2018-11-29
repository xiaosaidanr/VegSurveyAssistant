package com.thcreate.vegsurveyassistant.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.io.Serializable;
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
public class Yangdian extends BaseEntity implements Parcelable {
//public class Yangdian extends BaseEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "user_id")
    public int userId;

    @ColumnInfo(name = "yangdian_code")
    public String yangdianCode;//样点编码

    @ColumnInfo(name = "qunxi_type")
    public String qunxiType;//群系类型

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

    public Yangdian(@NonNull int userId, @NonNull String yangdianCode){
        this.userId = userId;
        this.yangdianCode = yangdianCode;
    }



    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeValue(id);
        dest.writeValue(userId);
        dest.writeValue(yangdianCode);
        dest.writeValue(qunxiType);
        dest.writeValue(dominantSpecies);
        dest.writeValue(qunluoCoverage);
        dest.writeValue(qunluoHeight);
        dest.writeValue(dixingZhibeiStatus);
        dest.writeValue(regionName);
        dest.writeValue(humanActivity);
        dest.writeValue(altitude);
        dest.writeValue(longitude);
        dest.writeValue(latitude);
        dest.writeValue(investigator);
        dest.writeValue(investigateDate);
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

    public static final Parcelable.Creator<Yangdian> CREATOR = new Creator<Yangdian>() {
        @Override
        public Yangdian createFromParcel(Parcel source) {
            return new Yangdian(source);
        }

        @Override
        public Yangdian[] newArray(int size) {
            return new Yangdian[0];
        }
    };

    public Yangdian(Parcel source){
        Object tmpId = source.readValue(getClass().getClassLoader());
        if (tmpId != null){
            id = (int)tmpId;
        }
        Object tmpUserId = source.readValue(getClass().getClassLoader());
        if (tmpUserId != null){
            userId = (int)tmpUserId;
        }
        Object tmpYangdianCode = source.readValue(getClass().getClassLoader());
        if (tmpYangdianCode != null){
            yangdianCode = (String)tmpYangdianCode;
        }
        Object tmpQunxiType = source.readValue(getClass().getClassLoader());
        if (tmpQunxiType != null){
            qunxiType = (String)tmpQunxiType;
        }
        Object tmpDominantSpecies = source.readValue(getClass().getClassLoader());
        if (tmpDominantSpecies != null){
            dominantSpecies = (String)tmpDominantSpecies;
        }
        Object tmpQunluoCoverage = source.readValue(getClass().getClassLoader());
        if (tmpQunluoCoverage != null){
            qunluoCoverage = (String)tmpQunluoCoverage;
        }
        Object tmpQunluoHeight = source.readValue(getClass().getClassLoader());
        if (tmpQunluoHeight != null){
            qunluoHeight = (String)tmpQunluoHeight;
        }
        Object tmpDixingZhibeiStatus = source.readValue(getClass().getClassLoader());
        if (tmpDixingZhibeiStatus != null){
            dixingZhibeiStatus = (String)tmpDixingZhibeiStatus;
        }
        Object tmpRegionName = source.readValue(getClass().getClassLoader());
        if (tmpRegionName != null){
            regionName = (String)tmpRegionName;
        }
        Object tmpHumanActivity = source.readValue(getClass().getClassLoader());
        if (tmpHumanActivity != null){
            humanActivity = (String)tmpHumanActivity;
        }
        Object tmpAltitude = source.readValue(getClass().getClassLoader());
        if (tmpAltitude != null){
            altitude = (String)tmpAltitude;
        }
        Object tmpLongitude = source.readValue(getClass().getClassLoader());
        if (tmpLongitude != null){
            longitude = (String)tmpLongitude;
        }
        Object tmpLatitude = source.readValue(getClass().getClassLoader());
        if (tmpLatitude != null){
            latitude = (String)tmpLatitude;
        }
        Object tmpInvestigator = source.readValue(getClass().getClassLoader());
        if (tmpInvestigator != null){
            investigator = (String)tmpInvestigator;
        }
        Object tmpInvestigateDate = source.readValue(getClass().getClassLoader());
        if (tmpInvestigateDate != null){
            investigateDate = (String)tmpInvestigateDate;
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
