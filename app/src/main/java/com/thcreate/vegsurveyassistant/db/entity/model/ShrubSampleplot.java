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

public class ShrubSampleplot extends BaseSampleplot implements Parcelable {

    @Expose
    @SerializedName("data")
    public ShrubPlotData data;

    public ShrubSampleplot() {
        this.type = Macro.SHRUB;
        this.data = new ShrubPlotData();
    }

    public ShrubSampleplot(@NonNull String landId, @NonNull String plotId){
        super(landId, plotId);
        this.type = Macro.SHRUB;
        this.data = new ShrubPlotData();
    }

    public ShrubSampleplot(int id, @NonNull String landId, @NonNull String plotId){
        super(id, landId, plotId);
        this.type = Macro.SHRUB;
        this.data = new ShrubPlotData();
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

    public static final Parcelable.Creator<ShrubSampleplot> CREATOR = new Creator<ShrubSampleplot>() {
        @Override
        public ShrubSampleplot createFromParcel(Parcel source) {
            return new ShrubSampleplot(source);
        }

        @Override
        public ShrubSampleplot[] newArray(int size) {
            return new ShrubSampleplot[0];
        }
    };

    public ShrubSampleplot(Parcel source){
        initCommonFromParcelableSource(source);
        data = source.readParcelable(ShrubPlotData.class.getClassLoader());
    }

    @Override
    public SampleplotEntity getEntity() {
        SampleplotEntity data = new SampleplotEntity();
        data.initCommonFromPlot(this);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
        data.data = gson.toJson(this.data, ShrubPlotData.class);
        return data;
    }

    public static ShrubSampleplot getInstance(SampleplotEntity entity){
        ShrubSampleplot data = new ShrubSampleplot();
        data.initCommonFromEntity(entity);
        if (entity.data != null){
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
            data.data = gson.fromJson(entity.data, ShrubPlotData.class);
        }
        return data;
    }

    public static class ShrubPlotData implements Parcelable {

        public ShrubPlotData() {
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
        @SerializedName("average_basal_diameter")
        public String averageBasalDiameter;//平均基径

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
            dest.writeString(averageBasalDiameter);
        }

        public static final Parcelable.Creator<ShrubPlotData> CREATOR = new Parcelable.Creator<ShrubPlotData>() {
            @Override
            public ShrubPlotData createFromParcel(Parcel source) {
                return new ShrubPlotData(source);
            }

            @Override
            public ShrubPlotData[] newArray(int size) {
                return new ShrubPlotData[0];
            }
        };

        public ShrubPlotData(Parcel source){
            communityName = source.readString();
            plotLength = source.readString();
            plotWidth = source.readString();
            communityCoverage = source.readString();
            communityHeight = source.readString();
            averageBasalDiameter = source.readString();
        }

    }

}
