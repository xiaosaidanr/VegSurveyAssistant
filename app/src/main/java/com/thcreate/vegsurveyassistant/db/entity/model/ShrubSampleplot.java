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

public class ShrubSampleplot extends BaseSampleplot implements Parcelable {

    @Expose
    @SerializedName("arbor_plot_id")
    public String arborPlotId;//所属乔木样方ID
    @Expose
    @SerializedName("average_basal_diameter")
    public String averageBasalDiameter;//平均基径

    public ShrubSampleplot() {
        this.type = Macro.SHRUB;
    }

    public ShrubSampleplot(@NonNull String landId, @NonNull String plotId){
        super(landId, plotId);
        this.type = Macro.SHRUB;
    }

    public ShrubSampleplot(int id, @NonNull String landId, @NonNull String plotId){
        super(id, landId, plotId);
        this.type = Macro.SHRUB;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest = writeCommonToParcel(dest);
        dest.writeValue(arborPlotId);
        dest.writeValue(averageBasalDiameter);
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
        Object tmpArborPlotId = source.readValue(getClass().getClassLoader());
        if (tmpArborPlotId != null){
            arborPlotId = (String)tmpArborPlotId;
        }
        Object tmpAverageBasalDiameter = source.readValue(getClass().getClassLoader());
        if (tmpAverageBasalDiameter != null){
            averageBasalDiameter = (String)tmpAverageBasalDiameter;
        }
    }

    @Override
    public SampleplotEntity getEntity() {
        SampleplotEntity data = new SampleplotEntity();
        data.initCommonFromPlot(this);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
        data.data = gson.toJson(this, ShrubSampleplot.class);
        return data;
    }

    public static ShrubSampleplot getInstance(SampleplotEntity entity){
        ShrubSampleplot data;
        if (entity.data != null){
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
            data = gson.fromJson(entity.data, ShrubSampleplot.class);
        }
        else {
            data = new ShrubSampleplot();
        }
        data.initCommonFromEntity(entity);
        return data;
    }

}
