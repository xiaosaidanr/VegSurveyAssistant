package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.gsonTypeAdapter.DateTypeAdapter;

import java.util.Date;
import java.util.Map;

public class BaseSpecies {

    public int id;
    public String plotId;//所属样方ID
    @Expose
    @SerializedName("species_id")
    public String speciesId;//物种ID
    @Expose
    @SerializedName("code")
    public String code;//物种编号
    @Expose
    @SerializedName("type")
    public String type;//物种类型
    @Expose
    @SerializedName("name")
    public String name;//物种名
    @Expose
    @SerializedName("latin_name")
    public String latinName;//拉丁名
    public Date createAt;//创建时间
    public Date updateAt;//修改时间
    @Expose
    @SerializedName("upload_at")
    @JsonAdapter(DateTypeAdapter.class)
    public Date uploadAt;//上传时间
    public Date deleteAt;//删除时间

    @Expose
    @SerializedName("data")
    public Map<String, String> genericData;

    public BaseSpecies() {
    }

    public BaseSpecies(@NonNull String plotId, @NonNull String speciesId){
        this.plotId = plotId;
        this.speciesId = speciesId;
    }

    public BaseSpecies(int id, @NonNull String plotId, @NonNull String speciesId){
        this.id = id;
        this.plotId = plotId;
        this.speciesId = speciesId;
    }

    public Parcel writeCommonToParcel(Parcel dest) {
        dest.writeValue(id);
        dest.writeValue(plotId);
        dest.writeValue(speciesId);
        dest.writeValue(code);
        dest.writeValue(type);
        dest.writeValue(name);
        dest.writeValue(latinName);
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
        Object tmpPlotId = source.readValue(getClass().getClassLoader());
        if (tmpPlotId != null){
            plotId = (String)tmpPlotId;
        }
        Object tmpSpeciesId = source.readValue(getClass().getClassLoader());
        if (tmpSpeciesId != null){
            speciesId = (String)tmpSpeciesId;
        }
        Object tmpCode = source.readValue(getClass().getClassLoader());
        if (tmpCode != null){
            code = (String)tmpCode;
        }
        Object tmpType = source.readValue(getClass().getClassLoader());
        if (tmpType != null){
            type = (String)tmpType;
        }
        Object tmpName = source.readValue(getClass().getClassLoader());
        if (tmpName != null){
            name = (String)tmpName;
        }
        Object tmpLatinName = source.readValue(getClass().getClassLoader());
        if (tmpLatinName != null){
            latinName = (String)tmpLatinName;
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

    public void initCommonFromEntity(SpeciesEntity entity){
        this.id = entity.id;
        this.plotId = entity.plotId;
        this.speciesId = entity.speciesId;
        this.code = entity.code;
        this.type = entity.type;
        this.name = entity.name;
        this.latinName = entity.latinName;
        this.createAt = entity.createAt;
        this.updateAt = entity.updateAt;
        this.uploadAt = entity.uploadAt;
        this.deleteAt = entity.deleteAt;
    }

    public SpeciesEntity getEntity(){
        SpeciesEntity data = new SpeciesEntity();
        data.initCommonFromSpecies(this);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
        data.data = gson.toJson(this.genericData);
        return data;
    }

    public static BaseSpecies getInstance(SpeciesEntity entity){
        BaseSpecies data = new BaseSpecies();
        data.initCommonFromEntity(entity);
        if (entity.data != null){
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
            data.genericData = gson.fromJson(entity.data, Map.class);
        }
        return data;
    }

}
