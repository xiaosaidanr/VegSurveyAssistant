package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

public class Sampleland implements Parcelable {

    public int id;
    public int userId;
    public String landId;//样地编号
    public String type;//样地类型
    public String administrativeRegion;//行政区域
    public String aspect;//坡向
    public String slope;//坡度
    public String slopePosition;//坡位
    public String landform;//地貌
    public String soilCharacteristic;//土壤特征
    public String humanActivity;//人类活动
    public String lng;//经度
    public String lat;//纬度
    public Date createAt;//创建时间
    public Date updateAt;//修改时间
    public Date uploadAt;//上传时间
    public Date deleteAt;//删除时间

    public Sampleland() {
    }

    public Sampleland(int userId, @NonNull String landId, @NonNull String type){
        this.userId = userId;
        this.landId = landId;
        this.type = type;
    }

    public Sampleland(int id, int userId, @NonNull String landId, @NonNull String type){
        this.id = id;
        this.userId = userId;
        this.landId = landId;
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
        dest.writeValue(landId);
        dest.writeValue(type);
        dest.writeValue(administrativeRegion);
        dest.writeValue(aspect);
        dest.writeValue(slope);
        dest.writeValue(slopePosition);
        dest.writeValue(landform);
        dest.writeValue(soilCharacteristic);
        dest.writeValue(humanActivity);
        dest.writeValue(lng);
        dest.writeValue(lat);
        if (createAt == null){
            dest.writeValue(null);
        }
        else {
            dest.writeValue(createAt.getTime());
        }
        if (updateAt == null){
            dest.writeValue(null);
        }
        else {
            dest.writeValue(updateAt.getTime());
        }
        if (uploadAt == null){
            dest.writeValue(null);
        }
        else {
            dest.writeValue(uploadAt.getTime());
        }
        if (deleteAt == null){
            dest.writeValue(null);
        }
        else {
            dest.writeValue(deleteAt.getTime());
        }
    }

    public static final Parcelable.Creator<Sampleland> CREATOR = new Creator<Sampleland>() {
        @Override
        public Sampleland createFromParcel(Parcel source) {
            return new Sampleland(source);
        }

        @Override
        public Sampleland[] newArray(int size) {
            return new Sampleland[0];
        }
    };

    public Sampleland(Parcel source){
        Object tmpId = source.readValue(getClass().getClassLoader());
        if (tmpId != null){
            id = (int)tmpId;
        }
        Object tmpUserId = source.readValue(getClass().getClassLoader());
        if (tmpUserId != null){
            userId = (int)tmpUserId;
        }
        Object tmpLandId = source.readValue(getClass().getClassLoader());
        if (tmpLandId != null){
            landId = (String)tmpLandId;
        }
        Object tmpType = source.readValue(getClass().getClassLoader());
        if (tmpType != null){
            type = (String)tmpType;
        }
        Object tmpAdministrativeRegion = source.readValue(getClass().getClassLoader());
        if (tmpAdministrativeRegion != null){
            administrativeRegion = (String)tmpAdministrativeRegion;
        }
        Object tmpAspect = source.readValue(getClass().getClassLoader());
        if (tmpAspect != null){
            aspect = (String)tmpAspect;
        }
        Object tmpSlope = source.readValue(getClass().getClassLoader());
        if (tmpSlope != null){
            slope = (String)tmpSlope;
        }
        Object tmpSlopePosition = source.readValue(getClass().getClassLoader());
        if (tmpSlopePosition != null){
            slopePosition = (String)tmpSlopePosition;
        }
        Object tmpLandform = source.readValue(getClass().getClassLoader());
        if (tmpLandform != null){
            landform = (String)tmpLandform;
        }
        Object tmpSoilCharacteristic = source.readValue(getClass().getClassLoader());
        if (tmpSoilCharacteristic != null){
            soilCharacteristic = (String)tmpSoilCharacteristic;
        }
        Object tmpHumanActivity = source.readValue(getClass().getClassLoader());
        if (tmpHumanActivity != null){
            humanActivity = (String)tmpHumanActivity;
        }
        Object tmpLng = source.readValue(getClass().getClassLoader());
        if (tmpLng != null){
            lng = (String)tmpLng;
        }
        Object tmpLat = source.readValue(getClass().getClassLoader());
        if (tmpLat != null){
            lat = (String)tmpLat;
        }
        Object tmpCreateAt = source.readValue(getClass().getClassLoader());
        if (tmpCreateAt != null){
            createAt = new Date((Long)tmpCreateAt);
        }
        Object tmpUpdateAt = source.readValue(getClass().getClassLoader());
        if (tmpUpdateAt != null){
            updateAt = new Date((Long)tmpUpdateAt);
        }
        Object tmpUploadAt = source.readValue(getClass().getClassLoader());
        if (tmpUploadAt != null){
            uploadAt = new Date((Long)tmpUploadAt);
        }
        Object tmpDeleteAt = source.readValue(getClass().getClassLoader());
        if (tmpDeleteAt != null){
            deleteAt = new Date((Long)tmpDeleteAt);
        }
    }

}
