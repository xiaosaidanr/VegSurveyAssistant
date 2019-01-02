package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.thcreate.vegsurveyassistant.db.entity.SamplelandEntity;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.gsonTypeAdapter.DateTypeAdapter;

import java.util.Date;

public class Sampleland implements Parcelable {

    public int id;
    public int userId;//用户ID
    @Expose
    @SerializedName("land_id")
    public String landId;//样地ID
    @Expose
    @SerializedName("code")
    public String code;//样地编号
    @Expose
    @SerializedName("type")
    public String type;//样地类型
    @Expose
    @SerializedName("data")
    public LandData data;//数据
    @Expose
    @SerializedName("lng")
    public String lng;//经度
    @Expose
    @SerializedName("lat")
    public String lat;//纬度
    @Expose
    @SerializedName("alt")
    public String alt;//海拔
    public Date createAt;//创建时间
    public Date updateAt;//修改时间
    @Expose
    @SerializedName("upload_at")
    @JsonAdapter(DateTypeAdapter.class)
    public Date uploadAt;//上传时间
    public Date deleteAt;//删除时间

    public Sampleland() {
        this.data = new LandData();
    }

    public Sampleland(int userId, @NonNull String landId, @NonNull String type){
        this.userId = userId;
        this.landId = landId;
        this.type = type;
        this.data = new LandData();
    }

    public Sampleland(int id, int userId, @NonNull String landId, @NonNull String type){
        this.id = id;
        this.userId = userId;
        this.landId = landId;
        this.type = type;
        this.data = new LandData();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeString(landId);
        dest.writeString(code);
        dest.writeString(type);
        dest.writeParcelable(data, PARCELABLE_WRITE_RETURN_VALUE);
        dest.writeString(lng);
        dest.writeString(lat);
        dest.writeString(alt);
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
        id = source.readInt();
        userId = source.readInt();
        landId = source.readString();
        code = source.readString();
        type = source.readString();
        data = source.readParcelable(LandData.class.getClassLoader());
        lng = source.readString();
        lat = source.readString();
        alt = source.readString();
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
        Sampleland data = new Sampleland();
        data.initCommonFromEntity(entity);
        if (entity.data != null){
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
            data.data = gson.fromJson(entity.data, LandData.class);
        }
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
        data.data = gson.toJson(this.data, LandData.class);
        return data;
    }

    public static class LandData implements Parcelable {

        public LandData() {
        }

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(administrativeRegion);
            parcel.writeString(aspect);
            parcel.writeString(slope);
            parcel.writeString(slopePosition);
            parcel.writeString(landform);
            parcel.writeString(soilCharacteristic);
            parcel.writeString(humanActivity);
        }

        public static final Parcelable.Creator<LandData> CREATOR = new Creator<LandData>() {
            @Override
            public LandData createFromParcel(Parcel source) {
                return new LandData(source);
            }

            @Override
            public LandData[] newArray(int size) {
                return new LandData[0];
            }
        };

        public LandData(Parcel source){
            administrativeRegion = source.readString();
            aspect = source.readString();
            slope = source.readString();
            slopePosition = source.readString();
            landform = source.readString();
            soilCharacteristic = source.readString();
            humanActivity = source.readString();
        }

    }

}
