package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;

import java.util.Date;

abstract public class BaseSampleplot {

    public int id;
    public String landId;//所属样地ID
    public String plotId;//样方ID
    public String code;//样方编号
    public String type;//样方类型
    @Expose
    @SerializedName("community_name")
    public String communityName;//群落名称
    @Expose
    @SerializedName("plot_length")
    public String plotLength;//样方长
    @Expose
    @SerializedName("plot_width")
    public String plotWidth;//样方宽
    @Expose
    @SerializedName("community_coverage")
    public String communityCoverage;//群落盖度
    @Expose
    @SerializedName("community_height")
    public String communityHeight;//群落高度
    public String lng;//经度
    public String lat;//纬度
    public String alt;//海拔
    public String investigatorName;//调查人
    public String investigatedAt;//调查时间
    public Date createAt;//创建时间
    public Date updateAt;//修改时间
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
        dest.writeValue(id);
        dest.writeValue(landId);
        dest.writeValue(plotId);
        dest.writeValue(code);
        dest.writeValue(type);
        dest.writeValue(communityName);
        dest.writeValue(plotLength);
        dest.writeValue(plotWidth);
        dest.writeValue(communityCoverage);
        dest.writeValue(communityHeight);
        dest.writeValue(lng);
        dest.writeValue(lat);
        dest.writeValue(alt);
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
        return dest;
    }

    public void initCommonFromParcelableSource(Parcel source){
        Object tmpId = source.readValue(getClass().getClassLoader());
        if (tmpId != null){
            id = (int)tmpId;
        }
        Object tmpLandId = source.readValue(getClass().getClassLoader());
        if (tmpLandId != null){
            landId = (String)tmpLandId;
        }
        Object tmpPlotId = source.readValue(getClass().getClassLoader());
        if (tmpPlotId != null){
            plotId = (String)tmpPlotId;
        }
        Object tmpCode = source.readValue(getClass().getClassLoader());
        if (tmpCode != null){
            code = (String)tmpCode;
        }
        Object tmpType = source.readValue(getClass().getClassLoader());
        if (tmpType != null){
            type = (String)tmpType;
        }
        Object tmpCommunityName = source.readValue(getClass().getClassLoader());
        if (tmpCommunityName != null){
            communityName = (String)tmpCommunityName;
        }
        Object tmpPlotLength = source.readValue(getClass().getClassLoader());
        if (tmpPlotLength != null){
            plotLength = (String)tmpPlotLength;
        }
        Object tmpPlotWidth = source.readValue(getClass().getClassLoader());
        if (tmpPlotWidth != null){
            plotWidth = (String)tmpPlotWidth;
        }
        Object tmpCommunityCoverage = source.readValue(getClass().getClassLoader());
        if (tmpCommunityCoverage != null){
            communityCoverage = (String)tmpCommunityCoverage;
        }
        Object tmpCommunityHeight = source.readValue(getClass().getClassLoader());
        if (tmpCommunityHeight != null){
            communityHeight = (String)tmpCommunityHeight;
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
        Object tmpInvestigatorName = source.readValue(getClass().getClassLoader());
        if (tmpInvestigatorName != null){
            investigatorName = (String)tmpInvestigatorName;
        }
        Object tmpInvestigatedAt = source.readValue(getClass().getClassLoader());
        if (tmpInvestigatedAt != null){
            investigatedAt = (String)tmpInvestigatedAt;
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