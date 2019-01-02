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

public class ArborSampleplot extends BaseSampleplot implements Parcelable {

    @Expose
    @SerializedName("data")
    public ArborPlotData data;

    public ArborSampleplot() {
        this.type = Macro.ARBOR;
        this.data = new ArborPlotData();
    }

    public ArborSampleplot(@NonNull String landId, @NonNull String plotId){
        super(landId, plotId);
        this.type = Macro.ARBOR;
        this.data = new ArborPlotData();
    }

    public ArborSampleplot(int id, @NonNull String landId, @NonNull String plotId){
        super(id ,landId, plotId);
        this.type = Macro.ARBOR;
        this.data = new ArborPlotData();
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

    public static final Parcelable.Creator<ArborSampleplot> CREATOR = new Creator<ArborSampleplot>() {
        @Override
        public ArborSampleplot createFromParcel(Parcel source) {
            return new ArborSampleplot(source);
        }

        @Override
        public ArborSampleplot[] newArray(int size) {
            return new ArborSampleplot[0];
        }
    };

    public ArborSampleplot(Parcel source){
        initCommonFromParcelableSource(source);
        data = source.readParcelable(ArborPlotData.class.getClassLoader());
    }

    @Override
    public SampleplotEntity getEntity() {
        SampleplotEntity data = new SampleplotEntity();
        data.initCommonFromPlot(this);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
        data.data = gson.toJson(this.data, ArborPlotData.class);
        return data;
    }

    public static ArborSampleplot getInstance(SampleplotEntity entity){
        ArborSampleplot data = new ArborSampleplot();
        data.initCommonFromEntity(entity);
        if (entity.data != null){
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
            data.data = gson.fromJson(entity.data, ArborPlotData.class);
        }
        return data;
    }

    public static class ArborPlotData implements Parcelable {

        public ArborPlotData() {
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
        @Expose
        @SerializedName("average_DBH")
        public String averageDBH;//平均胸径

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
            dest.writeString(averageDBH);
        }

        public static final Parcelable.Creator<ArborPlotData> CREATOR = new Parcelable.Creator<ArborPlotData>() {
            @Override
            public ArborPlotData createFromParcel(Parcel source) {
                return new ArborPlotData(source);
            }

            @Override
            public ArborPlotData[] newArray(int size) {
                return new ArborPlotData[0];
            }
        };

        public ArborPlotData(Parcel source){
            communityName = source.readString();
            plotLength = source.readString();
            plotWidth = source.readString();
            communityCoverage = source.readString();
            communityHeight = source.readString();
            averageDBH = source.readString();
        }

    }

}
