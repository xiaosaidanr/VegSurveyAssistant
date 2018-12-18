package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.util.Macro;

public class HerbSpecies extends BaseSpecies implements Parcelable {

    @Expose
    @SerializedName("plant_count")
    public String plantCount;//株丛数
    @Expose
    @SerializedName("vegetative_bunch_height")
    public String vegetativeBranchHeight;//营养枝高度
    @Expose
    @SerializedName("generative_bunch_height")
    public String generativeBranchHeight;//生殖枝高度
    @Expose
    @SerializedName("coverage")
    public String coverage;//盖度
    @Expose
    @SerializedName("biomass")
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
        dest = writeCommonToParcel(dest);
        dest.writeValue(plantCount);
        dest.writeValue(vegetativeBranchHeight);
        dest.writeValue(generativeBranchHeight);
        dest.writeValue(coverage);
        dest.writeValue(biomass);
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
        initCommonFromParcelableSource(source);
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
    }

    @Override
    public SpeciesEntity getEntity() {
        SpeciesEntity data = new SpeciesEntity();
        data.initCommonFromSpecies(this);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
        data.data = gson.toJson(this, HerbSpecies.class);
        return data;
    }

    public static HerbSpecies getInstance(SpeciesEntity entity){
        HerbSpecies data;
        if (entity.data != null){
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
            data = gson.fromJson(entity.data, HerbSpecies.class);
        }
        else {
            data = new HerbSpecies();
        }
        data.initCommonFromEntity(entity);
        return data;
    }
}
