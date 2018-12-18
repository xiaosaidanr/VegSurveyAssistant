package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;

import java.util.Date;

abstract public class BaseSpecies {

    public int id;
    public String plotId;//所属样方ID
    public String speciesId;//物种ID
    public String code;//物种编号
    public String type;//物种类型
    public String name;//物种名
    public String latinName;//拉丁名
    public String note;//备注
    public Date createAt;//创建时间
    public Date updateAt;//修改时间
    public Date uploadAt;//上传时间
    public Date deleteAt;//删除时间

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

    abstract public SpeciesEntity getSpeciesEntity();

}
