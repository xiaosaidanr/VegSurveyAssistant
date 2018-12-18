package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;

public class HerbSampleplot extends BaseSampleplot implements Parcelable {

    public String arborPlotId;//所属乔木样方ID
    public String shrubPlotId;//所属灌木样方ID

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
        dest.writeValue(arborPlotId);
        dest.writeValue(shrubPlotId);
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
        Object tmpArborPlotId = source.readValue(getClass().getClassLoader());
        if (tmpArborPlotId != null){
            arborPlotId = (String)tmpArborPlotId;
        }
        Object tmpShrubPlotId = source.readValue(getClass().getClassLoader());
        if (tmpShrubPlotId != null){
            shrubPlotId = (String)tmpShrubPlotId;
        }
    }

    @Override
    public SampleplotEntity getSampleplotEntity() {
        return null;
    }
}
