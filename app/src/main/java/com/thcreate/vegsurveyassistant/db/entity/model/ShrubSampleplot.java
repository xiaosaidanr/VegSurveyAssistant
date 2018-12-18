package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;

public class ShrubSampleplot extends BaseSampleplot implements Parcelable {

    public String arborPlotId;//所属乔木样方ID
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
    public SampleplotEntity getSampleplotEntity() {
        return null;
    }
}
