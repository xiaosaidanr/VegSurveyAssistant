package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;

public class HerbSampleplot extends BaseSampleplot implements Parcelable {

    public HerbPlotData data;

    public HerbSampleplot() {
        this.type = Macro.HERB;
        this.data = new HerbPlotData();
    }

    public HerbSampleplot(@NonNull String landId, @NonNull String plotId){
        super(landId, plotId);
        this.type = Macro.HERB;
        this.data = new HerbPlotData();
    }

    public HerbSampleplot(int id, @NonNull String landId, @NonNull String plotId){
        super(id, landId, plotId);
        this.type = Macro.HERB;
        this.data = new HerbPlotData();
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

    public static final Parcelable.Creator<HerbSampleplot> CREATOR = new Creator<HerbSampleplot>() {
        @Override
        public HerbSampleplot createFromParcel(Parcel source) {
            return new HerbSampleplot(source);
        }

        @Override
        public HerbSampleplot[] newArray(int size) {
            return new HerbSampleplot[0];
        }
    };

    public HerbSampleplot(Parcel source){
        initCommonFromParcelableSource(source);
        data = source.readParcelable(HerbPlotData.class.getClassLoader());
    }

    @Override
    public SampleplotEntity getEntity() {
        SampleplotEntity data = new SampleplotEntity();
        data.initCommonFromPlot(this);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
        data.data = gson.toJson(this.data, HerbPlotData.class);
        return data;
    }

    public static HerbSampleplot getInstance(SampleplotEntity entity){
        HerbSampleplot data = new HerbSampleplot();
        data.initCommonFromEntity(entity);
        if (entity.data != null){
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
            data.data = gson.fromJson(entity.data, HerbPlotData.class);
        }
        return data;
    }

    public static class HerbPlotData implements Parcelable {

        public HerbPlotData() {
        }

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(communityName);
            dest.writeString(plotLength);
            dest.writeString(plotWidth);
            dest.writeString(communityCoverage);
            dest.writeString(communityHeight);
        }

        public static final Parcelable.Creator<HerbPlotData> CREATOR = new Parcelable.Creator<HerbPlotData>() {
            @Override
            public HerbPlotData createFromParcel(Parcel source) {
                return new HerbPlotData(source);
            }

            @Override
            public HerbPlotData[] newArray(int size) {
                return new HerbPlotData[0];
            }
        };

        public HerbPlotData(Parcel source){
            communityName = source.readString();
            plotLength = source.readString();
            plotWidth = source.readString();
            communityCoverage = source.readString();
            communityHeight = source.readString();
        }

    }

}
