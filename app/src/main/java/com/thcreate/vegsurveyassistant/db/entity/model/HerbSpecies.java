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

    public HerbSpeciesData data;

    public HerbSpecies() {
        this.type = Macro.HERB;
        this.data = new HerbSpeciesData();
    }

    public HerbSpecies(@NonNull String plotId, @NonNull String speciesId){
        super(plotId, speciesId);
        this.type = Macro.HERB;
        this.data = new HerbSpeciesData();
    }

    public HerbSpecies(int id, @NonNull String plotId, @NonNull String speciesId){
        super(id, plotId, speciesId);
        this.type = Macro.HERB;
        this.data = new HerbSpeciesData();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest = writeCommonToParcel(dest);
        dest.writeParcelable(data, PARCELABLE_WRITE_RETURN_VALUE);
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
        data = source.readParcelable(HerbSpeciesData.class.getClassLoader());
    }

    @Override
    public SpeciesEntity getEntity() {
        SpeciesEntity data = new SpeciesEntity();
        data.initCommonFromSpecies(this);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
        data.data = gson.toJson(this.data, HerbSpeciesData.class);
        return data;
    }

    public static HerbSpecies getInstance(SpeciesEntity entity){
        HerbSpecies data = new HerbSpecies();
        data.initCommonFromEntity(entity);
        if (entity.data != null){
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
            data.data = gson.fromJson(entity.data, HerbSpeciesData.class);
        }
//        else {
//            data = new HerbSpecies();
//        }
        return data;
    }

    public static class HerbSpeciesData implements Parcelable {

        public HerbSpeciesData() {
        }

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
        @Expose
        @SerializedName("note")
        public String note;//备注

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(plantCount);
            parcel.writeString(vegetativeBranchHeight);
            parcel.writeString(generativeBranchHeight);
            parcel.writeString(coverage);
            parcel.writeString(biomass);
            parcel.writeString(note);
        }

        public static final Parcelable.Creator<HerbSpeciesData> CREATOR = new Creator<HerbSpeciesData>() {
            @Override
            public HerbSpeciesData createFromParcel(Parcel source) {
                return new HerbSpeciesData(source);
            }

            @Override
            public HerbSpeciesData[] newArray(int size) {
                return new HerbSpeciesData[0];
            }
        };

        public HerbSpeciesData(Parcel source){
            plantCount = source.readString();
            vegetativeBranchHeight = source.readString();
            generativeBranchHeight = source.readString();
            coverage = source.readString();
            biomass = source.readString();
            note = source.readString();
        }

    }



}
