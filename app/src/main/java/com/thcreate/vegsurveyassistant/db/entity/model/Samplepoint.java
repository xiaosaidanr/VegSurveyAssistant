package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.thcreate.vegsurveyassistant.db.entity.SamplepointEntity;
import com.thcreate.vegsurveyassistant.db.gsonTypeAdapter.DateTypeAdapter;

import java.util.Date;

public class Samplepoint implements Parcelable {

    public int id;
    public int userId;//用户ID
    @Expose
    @SerializedName("point_id")
    public String pointId;//样点ID
    @Expose
    @SerializedName("code")
    public String code;//样点编号
    @Expose
    @SerializedName("data")
    public PointData data;//数据
    @Expose
    @SerializedName("alt")
    public String alt;//海拔
    @Expose
    @SerializedName("lng")
    public String lng;//经度
    @Expose
    @SerializedName("lat")
    public String lat;//纬度
    @Expose
    @SerializedName("investigator_name")
    public String investigatorName;//调查人
    @Expose
    @SerializedName("investigated_at")
    public String investigatedAt;//调查时间
    public Date createAt;//创建时间
    public Date updateAt;//修改时间
    @Expose
    @SerializedName("upload_at")
    @JsonAdapter(DateTypeAdapter.class)
    public Date uploadAt;//上传时间
    public Date deleteAt;//删除时间

    public Samplepoint(){
        this.data = new PointData();
    }

    public Samplepoint(int userId, @NonNull String pointId){
        this.userId = userId;
        this.pointId = pointId;
        this.data = new PointData();
    }

    public Samplepoint(int id, int userId, @NonNull String pointId){
        this.id = id;
        this.userId = userId;
        this.pointId = pointId;
        this.data = new PointData();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeValue(id);
        dest.writeValue(userId);
        dest.writeValue(pointId);
        dest.writeValue(code);
        dest.writeParcelable(data, PARCELABLE_WRITE_RETURN_VALUE);
        dest.writeValue(alt);
        dest.writeValue(lng);
        dest.writeValue(lat);
        dest.writeValue(investigatorName);
        dest.writeValue(investigatedAt);
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

    public static final Parcelable.Creator<Samplepoint> CREATOR = new Creator<Samplepoint>() {
        @Override
        public Samplepoint createFromParcel(Parcel source) {
            return new Samplepoint(source);
        }

        @Override
        public Samplepoint[] newArray(int size) {
            return new Samplepoint[0];
        }
    };

    public Samplepoint(Parcel source){
        id = source.readInt();
        userId = source.readInt();
        pointId = source.readString();
        code = source.readString();
        data = source.readParcelable(PointData.class.getClassLoader());
        alt = source.readString();
        lng = source.readString();
        lat = source.readString();
        investigatorName = source.readString();
        investigatedAt = source.readString();
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

    public static Samplepoint getInstance(SamplepointEntity entity){
        Samplepoint point = new Samplepoint();
        if (entity.data != null){
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
            point.data = gson.fromJson(entity.data, PointData.class);
        }
        point.id = entity.id;
        point.userId = entity.userId;
        point.pointId = entity.pointId;
        point.code = entity.code;
        point.alt = entity.alt;
        point.lat = entity.lat;
        point.lng = entity.lng;
        point.investigatedAt = entity.investigatedAt;
        point.investigatorName = entity.investigatorName;
        point.createAt = entity.createAt;
        point.updateAt = entity.updateAt;
        point.uploadAt = entity.uploadAt;
        point.deleteAt = entity.deleteAt;
        return point;
    }

    public SamplepointEntity getEntity(){
        SamplepointEntity data = new SamplepointEntity();
        data.initCommonFromPoint(this);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
        data.data = gson.toJson(this.data, PointData.class);
        return data;
    }

    public static class PointData implements Parcelable {

        public PointData() {
        }

        @Expose
        @SerializedName("formation_type")
        public String formationType;//群系类型
        @Expose
        @SerializedName("dominant_species")
        public String dominantSpecies;//优势种
        @Expose
        @SerializedName("community_coverage")
        public String communityCoverage;//群落盖度
        @Expose
        @SerializedName("community_height")
        public String communityHeight;//群落高度
        @Expose
        @SerializedName("topography_vegetation_status")
        public String topographyVegetationStatus;//地形和植被概况
        @Expose
        @SerializedName("administrative_name")
        public String administrativeName;//行政地名
        @Expose
        @SerializedName("human_activity")
        public String humanActivity;//人类活动
        @Expose
        @SerializedName("note")
        public String note;//备注

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(formationType);
            dest.writeString(dominantSpecies);
            dest.writeString(communityCoverage);
            dest.writeString(communityHeight);
            dest.writeString(topographyVegetationStatus);
            dest.writeString(administrativeName);
            dest.writeString(humanActivity);
            dest.writeString(note);
        }

        public static final Parcelable.Creator<PointData> CREATOR = new Creator<PointData>() {
            @Override
            public PointData createFromParcel(Parcel source) {
                return new PointData(source);
            }

            @Override
            public PointData[] newArray(int size) {
                return new PointData[0];
            }
        };

        public PointData(Parcel source){
            formationType = source.readString();
            dominantSpecies = source.readString();
            communityCoverage = source.readString();
            communityHeight = source.readString();
            topographyVegetationStatus = source.readString();
            administrativeName = source.readString();
            humanActivity = source.readString();
            note = source.readString();
        }

    }

}

