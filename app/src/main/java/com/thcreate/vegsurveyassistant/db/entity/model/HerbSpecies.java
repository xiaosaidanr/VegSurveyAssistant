package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;

public class HerbSpecies extends BaseSpecies implements Parcelable {

    public String plantCount;//株丛数
    public String vegetativeBranchHeight;//营养枝高度
    public String generativeBranchHeight;//生殖枝高度
    public String coverage;//盖度
    public String biomass;//生物量

    public HerbSpecies() {
        this.type = Macro.HERB;
    }

    public HerbSpecies(@NonNull String plotId, @NonNull String speciesId){
        super(plotId, speciesId);
        this.type = Macro.HERB;
    }

    public HerbSpecies(int id, @NonNull String plotId, @NonNull String speciesId){
        super(id, plotId, speciesId);
        this.type = Macro.HERB;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(plotId);
        dest.writeValue(speciesId);
        dest.writeValue(type);
        dest.writeValue(name);
        dest.writeValue(latinName);
        dest.writeValue(plantCount);
        dest.writeValue(vegetativeBranchHeight);
        dest.writeValue(generativeBranchHeight);
        dest.writeValue(coverage);
        dest.writeValue(biomass);
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

    public static final Parcelable.Creator<HerbSpecies> CREATOR = new Creator<HerbSpecies>() {
        @Override
        public HerbSpecies createFromParcel(Parcel source) {
            return new HerbSpecies(source);
        }

        @Override
        public HerbSpecies[] newArray(int size) {
            return new HerbSpecies[0];
        }
    };

    public HerbSpecies(Parcel source){
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
        Object tmpPlantCount = source.readValue(getClass().getClassLoader());
        if (tmpPlantCount != null){
            plantCount = (String)tmpPlantCount;
        }
        Object tmpVegetativeBranchHeight = source.readValue(getClass().getClassLoader());
        if (tmpVegetativeBranchHeight != null){
            vegetativeBranchHeight = (String)tmpVegetativeBranchHeight;
        }
        Object tmpGenerativeBranchHeight = source.readValue(getClass().getClassLoader());
        if (tmpGenerativeBranchHeight != null){
            generativeBranchHeight = (String)tmpGenerativeBranchHeight;
        }
        Object tmpCoverage = source.readValue(getClass().getClassLoader());
        if (tmpCoverage != null){
            coverage = (String)tmpCoverage;
        }
        Object tmpBiomass = source.readValue(getClass().getClassLoader());
        if (tmpBiomass != null){
            biomass = (String)tmpBiomass;
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

    @Override
    public SpeciesEntity getSpeciesEntity() {
        return null;
    }
}
