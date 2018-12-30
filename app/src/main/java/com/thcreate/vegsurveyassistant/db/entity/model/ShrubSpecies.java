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

public class ShrubSpecies extends BaseSpecies implements Parcelable {

    @Expose
    @SerializedName("data")
    public ShrubSpeciesData data;

    public ShrubSpecies() {
        this.type = Macro.SHRUB;
        this.data = new ShrubSpeciesData();
    }

    public ShrubSpecies(@NonNull String plotId, @NonNull String speciesId){
        super(plotId, speciesId);
        this.type = Macro.SHRUB;
        this.data = new ShrubSpeciesData();
    }

    public ShrubSpecies(int id, @NonNull String plotId, @NonNull String speciesId){
        super(id, plotId, speciesId);
        this.type = Macro.SHRUB;
        this.data = new ShrubSpeciesData();
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

    public static final Parcelable.Creator<ShrubSpecies> CREATOR = new Creator<ShrubSpecies>() {
        @Override
        public ShrubSpecies createFromParcel(Parcel source) {
            return new ShrubSpecies(source);
        }

        @Override
        public ShrubSpecies[] newArray(int size) {
            return new ShrubSpecies[0];
        }
    };

    public ShrubSpecies(Parcel source){
        initFromParcelableSource(source);
    }

    public void initFromParcelableSource(Parcel source){
        initCommonFromParcelableSource(source);
        data = source.readParcelable(ShrubSpeciesData.class.getClassLoader());
    }

    @Override
    public SpeciesEntity getEntity() {
        SpeciesEntity data = new SpeciesEntity();
        data.initCommonFromSpecies(this);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
        data.data = gson.toJson(this.data, ShrubSpeciesData.class);
        return data;
    }

    public static ShrubSpecies getInstance(SpeciesEntity entity){
        ShrubSpecies data = new ShrubSpecies();
        data.initCommonFromEntity(entity);
        if (entity.data != null){
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
            data.data = gson.fromJson(entity.data, ShrubSpeciesData.class);
        }
//        else {
//            data = new ShrubSpecies();
//        }
        return data;
    }

    public static class ShrubSpeciesData implements Parcelable {

        public ShrubSpeciesData() {
        }

        @Expose
        @SerializedName("tree_number")
        public String treeNumber;//树号
        @Expose
        @SerializedName("basal_diameter")
        public String basalDiameter;//基径
        @Expose
        @SerializedName("height")
        public String height;//高度
        @Expose
        @SerializedName("canopy_x")
        public String canopyX;//冠幅X
        @Expose
        @SerializedName("canopy_y")
        public String canopyY;//冠幅Y
        @Expose
        @SerializedName("note")
        public String note;//冠幅Y

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(treeNumber);
            parcel.writeString(basalDiameter);
            parcel.writeString(height);
            parcel.writeString(canopyX);
            parcel.writeString(canopyY);
            parcel.writeString(note);
        }

        public static final Parcelable.Creator<ShrubSpeciesData> CREATOR = new Creator<ShrubSpeciesData>() {
            @Override
            public ShrubSpeciesData createFromParcel(Parcel source) {
                return new ShrubSpeciesData(source);
            }

            @Override
            public ShrubSpeciesData[] newArray(int size) {
                return new ShrubSpeciesData[0];
            }
        };

        public ShrubSpeciesData(Parcel source){
            treeNumber = source.readString();
            basalDiameter = source.readString();
            height = source.readString();
            canopyX = source.readString();
            canopyY = source.readString();
            note = source.readString();
        }

    }

}
