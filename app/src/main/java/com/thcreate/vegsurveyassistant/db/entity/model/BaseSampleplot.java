package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.gsonTypeAdapter.DateTypeAdapter;

import java.util.Date;

abstract public class BaseSampleplot {

    public int id;
    public String landId;//所属样地ID
    @Expose
    @SerializedName("plot_id")
    public String plotId;//样方ID
    @Expose
    @SerializedName("code")
    public String code;//样方编号
    @Expose
    @SerializedName("type")
    public String type;//样方类型
    @Expose
    @SerializedName("lng")
    public String lng;//经度
    @Expose
    @SerializedName("lat")
    public String lat;//纬度
    @Expose
    @SerializedName("alt")
    public String alt;//海拔
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

    public BaseSampleplot() {
    }

    public BaseSampleplot(@NonNull String landId, @NonNull String plotId){
        this.landId = landId;
        this.plotId = plotId;
    }

    public BaseSampleplot(int id, @NonNull String landId, @NonNull String plotId){
        this.id = id;
        this.landId = landId;
        this.plotId = plotId;
    }

    public Parcel writeCommonToParcel(Parcel dest){
        dest.writeInt(id);
        dest.writeString(landId);
        dest.writeString(plotId);
        dest.writeString(code);
        dest.writeString(type);
        dest.writeString(lng);
        dest.writeString(lat);
        dest.writeString(alt);
        dest.writeString(investigatorName);
        dest.writeString(investigatedAt);
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
        return dest;
    }

    public void initCommonFromParcelableSource(Parcel source){
        id = source.readInt();
        landId = source.readString();
        plotId = source.readString();
        code = source.readString();
        type = source.readString();
        lng = source.readString();
        lat = source.readString();
        alt = source.readString();
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

    public void initCommonFromEntity(SampleplotEntity entity){
        this.id = entity.id;
        this.landId = entity.landId;
        this.plotId = entity.plotId;
        this.code = entity.code;
        this.type = entity.type;
        this.lng = entity.lng;
        this.lat = entity.lat;
        this.alt = entity.alt;
        this.investigatedAt = entity.investigatedAt;
        this.investigatorName = entity.investigatorName;
        this.createAt = entity.createAt;
        this.updateAt = entity.updateAt;
        this.uploadAt = entity.uploadAt;
        this.deleteAt = entity.deleteAt;
    }

    abstract public SampleplotEntity getEntity();

}
