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

//    @Expose
//    @SerializedName("arbor_plot_id")
//    public String arborPlotId;//所属乔木样方ID
//    @Expose
//    @SerializedName("shrub_plot_id")
//    public String shrubPlotId;//所属灌木样方ID

    public HerbSampleplot() {
        this.type = Macro.HERB;
    }

    public HerbSampleplot(@NonNull String landId, @NonNull String plotId){
        super(landId, plotId);
        this.type = Macro.HERB;
    }

    public HerbSampleplot(int id, @NonNull String landId, @NonNull String plotId){
        super(id, landId, plotId);
        this.type = Macro.HERB;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest = writeCommonToParcel(dest);
//        dest.writeValue(arborPlotId);
//        dest.writeValue(shrubPlotId);
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
//        Object tmpArborPlotId = source.readValue(getClass().getClassLoader());
//        if (tmpArborPlotId != null){
//            arborPlotId = (String)tmpArborPlotId;
//        }
//        Object tmpShrubPlotId = source.readValue(getClass().getClassLoader());
//        if (tmpShrubPlotId != null){
//            shrubPlotId = (String)tmpShrubPlotId;
//        }
    }

    @Override
    public SampleplotEntity getEntity() {
        SampleplotEntity data = new SampleplotEntity();
        data.initCommonFromPlot(this);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();
        data.data = gson.toJson(this, HerbSampleplot.class);
        return data;
    }

    public static HerbSampleplot getInstance(SampleplotEntity entity){
        HerbSampleplot data;
        if (entity.data != null){
            Gson gson = new GsonBuilder()
                    .excludeFieldsWithoutExposeAnnotation()
                    .serializeNulls()
                    .create();
            data = gson.fromJson(entity.data, HerbSampleplot.class);
        }
        else {
            data = new HerbSampleplot();
        }
        data.initCommonFromEntity(entity);
        return data;
    }
}
