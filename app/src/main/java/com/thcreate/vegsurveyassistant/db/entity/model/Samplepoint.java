package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thcreate.vegsurveyassistant.db.entity.SamplepointEntity;

import java.util.Date;

public class Samplepoint implements Parcelable {

    public int id;
    public int userId;//用户ID
    public String pointId;//样点ID
    public String code;//样点编号
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
    public String alt;//海拔
    public String lng;//经度
    public String lat;//纬度
    public String investigator;//调查人
    public String investigateDate;//调查时间
    @Expose
    @SerializedName("note")
    public String note;//备注
    public Date createAt;//创建时间
    public Date updateAt;//修改时间
    public Date uploadAt;//上传时间
    public Date deleteAt;//删除时间

    public Samplepoint(){

    }

    public Samplepoint(int userId, @NonNull String pointId){
        this.userId = userId;
        this.pointId = pointId;
    }

    public Samplepoint(int id, int userId, @NonNull String pointId){
        this.id = id;
        this.userId = userId;
        this.pointId = pointId;
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
        dest.writeValue(formationType);
        dest.writeValue(dominantSpecies);
        dest.writeValue(communityCoverage);
        dest.writeValue(communityHeight);
        dest.writeValue(topographyVegetationStatus);
        dest.writeValue(administrativeName);
        dest.writeValue(humanActivity);
        dest.writeValue(alt);
        dest.writeValue(lng);
        dest.writeValue(lat);
        dest.writeValue(investigator);
        dest.writeValue(investigateDate);
        dest.writeValue(note);
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
        Object tmpId = source.readValue(getClass().getClassLoader());
        if (tmpId != null){
            id = (int)tmpId;
        }
        Object tmpUserId = source.readValue(getClass().getClassLoader());
        if (tmpUserId != null){
            userId = (int)tmpUserId;
        }
        Object tmpPointId = source.readValue(getClass().getClassLoader());
        if (tmpPointId != null){
            pointId = (String)tmpPointId;
        }
        Object tmpCode = source.readValue(getClass().getClassLoader());
        if (tmpCode != null){
            code = (String)tmpCode;
        }
        Object tmpFormationType = source.readValue(getClass().getClassLoader());
        if (tmpFormationType != null){
            formationType = (String)tmpFormationType;
        }
        Object tmpDominantSpecies = source.readValue(getClass().getClassLoader());
        if (tmpDominantSpecies != null){
            dominantSpecies = (String)tmpDominantSpecies;
        }
        Object tmpCommunityCoverage = source.readValue(getClass().getClassLoader());
        if (tmpCommunityCoverage != null){
            communityCoverage = (String)tmpCommunityCoverage;
        }
        Object tmpCommunityHeight = source.readValue(getClass().getClassLoader());
        if (tmpCommunityHeight != null){
            communityHeight = (String)tmpCommunityHeight;
        }
        Object tmpTopographyVegetationStatus = source.readValue(getClass().getClassLoader());
        if (tmpTopographyVegetationStatus != null){
            topographyVegetationStatus = (String)tmpTopographyVegetationStatus;
        }
        Object tmpAdministrativeName = source.readValue(getClass().getClassLoader());
        if (tmpAdministrativeName != null){
            administrativeName = (String)tmpAdministrativeName;
        }
        Object tmpHumanActivity = source.readValue(getClass().getClassLoader());
        if (tmpHumanActivity != null){
            humanActivity = (String)tmpHumanActivity;
        }
        Object tmpAlt = source.readValue(getClass().getClassLoader());
        if (tmpAlt != null){
            alt = (String)tmpAlt;
        }
        Object tmpLng = source.readValue(getClass().getClassLoader());
        if (tmpLng != null){
            lng = (String)tmpLng;
        }
        Object tmpLat = source.readValue(getClass().getClassLoader());
        if (tmpLat != null){
            lat = (String)tmpLat;
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
        Samplepoint data;
        if (entity.data != null){
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
            data = gson.fromJson(entity.data, Samplepoint.class);
        }
        else {
            data = new Samplepoint();
        }
        data.id = entity.id;
        data.userId = entity.userId;
        data.pointId = entity.pointId;
        data.code = entity.code;
        data.alt = entity.alt;
        data.lat = entity.lat;
        data.lng = entity.lng;
        data.investigateDate = entity.investigateDate;
        data.investigator = entity.investigator;
        data.createAt = entity.createAt;
        data.updateAt = entity.updateAt;
        data.uploadAt = entity.uploadAt;
        data.deleteAt = entity.deleteAt;
        return data;
    }

    public SamplepointEntity getEntity(){
        SamplepointEntity data = new SamplepointEntity();
        data.initCommonFromPoint(this);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
        data.data = gson.toJson(this, Samplepoint.class);
        return data;
    }
}

