package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.util.Macro;

public class ArborSpecies extends BaseSpecies implements Parcelable {

    @Expose
    @SerializedName("data")
    public ArborSpeciesData data;

    public ArborSpecies() {
        this.type = Macro.ARBOR;
        this.data = new ArborSpeciesData();
    }

    public ArborSpecies(@NonNull String plotId, @NonNull String speciesId){
        super(plotId, speciesId);
        this.type = Macro.ARBOR;
        this.data = new ArborSpeciesData();
    }

    public ArborSpecies(int id, @NonNull String plotId, @NonNull String speciesId){
        super(id, plotId, speciesId);
        this.type = Macro.ARBOR;
        this.data = new ArborSpeciesData();
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

    public static final Parcelable.Creator<ArborSpecies> CREATOR = new Parcelable.Creator<ArborSpecies>() {
        @Override
        public ArborSpecies createFromParcel(Parcel source) {
            return new ArborSpecies(source);
        }

        @Override
        public ArborSpecies[] newArray(int size) {
            return new ArborSpecies[0];
        }
    };

    public ArborSpecies(Parcel source){
        initCommonFromParcelableSource(source);
        data = source.readParcelable(ArborSpeciesData.class.getClassLoader());
    }

    @Override
    public SpeciesEntity getEntity() {
        SpeciesEntity data = new SpeciesEntity();
        data.initCommonFromSpecies(this);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
        data.data = gson.toJson(this.data, ArborSpeciesData.class);
        return data;
    }

    public static ArborSpecies getInstance(SpeciesEntity entity){
        ArborSpecies data = new ArborSpecies();
        data.initCommonFromEntity(entity);
        if (entity.data != null){
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
            data.data = gson.fromJson(entity.data, ArborSpeciesData.class);
        }
//        else {
//            data = new ArborSpecies();
//        }
        return data;
    }

    public static class ArborSpeciesData implements Parcelable {

        public ArborSpeciesData() {
        }

        @Expose
        @SerializedName("tree_number")
        public String treeNumber;//树号
        @Expose
        @SerializedName("DBH")
        public String DBH;//胸径
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
        public String note;//备注

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(treeNumber);
            parcel.writeString(DBH);
            parcel.writeString(height);
            parcel.writeString(canopyX);
            parcel.writeString(canopyY);
            parcel.writeString(note);
        }

        public static final Parcelable.Creator<ArborSpeciesData> CREATOR = new Parcelable.Creator<ArborSpeciesData>() {
            @Override
            public ArborSpeciesData createFromParcel(Parcel source) {
                return new ArborSpeciesData(source);
            }

            @Override
            public ArborSpeciesData[] newArray(int size) {
                return new ArborSpeciesData[0];
            }
        };

        public ArborSpeciesData(Parcel source){
            treeNumber = source.readString();
            DBH = source.readString();
            height = source.readString();
            canopyX = source.readString();
            canopyY = source.readString();
            note = source.readString();
        }

    }

}
