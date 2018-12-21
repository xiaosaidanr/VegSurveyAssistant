package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thcreate.vegsurveyassistant.db.entity.SamplelandEntity;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;

import java.util.Date;

public class Sampleland implements Parcelable {

    public int id;
    public int userId;//用户ID
    public String landId;//样地ID
    public String code;//样地编号
    public String type;//样地类型
    @Expose
    @SerializedName("administrative_region")
    public String administrativeRegion;//行政区域
    @Expose
    @SerializedName("aspect")
    public String aspect;//坡向
    @Expose
    @SerializedName("slope")
    public String slope;//坡度
    @Expose
    @SerializedName("slope_position")
    public String slopePosition;//坡位
    @Expose
    @SerializedName("landform")
    public String landform;//地貌
    @Expose
    @SerializedName("soil_characteristic")
    public String soilCharacteristic;//土壤特征
    @Expose
    @SerializedName("human_activity")
    public String humanActivity;//人类活动
    public String lng;//经度
    public String lat;//纬度
    public String alt;//海拔
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
        dest.writeValue(code);
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
        dest.writeValue(alt);
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
        Object tmpCode = source.readValue(getClass().getClassLoader());
        if (tmpCode != null){
            code = (String)tmpCode;
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
        Object tmpAlt = source.readValue(getClass().getClassLoader());
        if (tmpAlt != null){
            alt = (String)tmpAlt;
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

    public static Sampleland getInstance(SamplelandEntity entity){
        Sampleland data;
        if (entity.data != null){
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
            data = gson.fromJson(entity.data, Sampleland.class);
        }
        else {
            data = new Sampleland();
        }
        data.initCommonFromEntity(entity);
        return data;
    }

    public void initCommonFromEntity(SamplelandEntity entity){
        this.id = entity.id;
        this.userId = entity.userId;
        this.landId = entity.landId;
        this.code = entity.code;
        this.type = entity.type;
        this.lng = entity.lng;
        this.lat = entity.lat;
        this.alt = entity.alt;
        this.createAt = entity.createAt;
        this.updateAt = entity.updateAt;
        this.uploadAt = entity.uploadAt;
        this.deleteAt = entity.deleteAt;
    }

    public SamplelandEntity getEntity(){
        SamplelandEntity data = new SamplelandEntity();
        data.initCommonFromLand(this);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
        data.data = gson.toJson(this, Sampleland.class);
        return data;
    }

}
