package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;

public class ArborSampleplot extends BaseSampleplot implements Parcelable {

    public String averageDBH;//平均胸径

    public ArborSampleplot() {
        this.type = Macro.ARBOR;
    }

    public ArborSampleplot(@NonNull String landId, @NonNull String plotId){
        super(landId, plotId);
        this.type = Macro.ARBOR;
    }

    public ArborSampleplot(int id, @NonNull String landId, @NonNull String plotId){
        super(id ,landId, plotId);
        this.type = Macro.ARBOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest = writeCommonToParcel(dest);
        dest.writeValue(averageDBH);
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
        Object tmpAverageDBH = source.readValue(getClass().getClassLoader());
        if (tmpAverageDBH != null){
            averageDBH = (String)tmpAverageDBH;
        }
    }

    @Override
    public SampleplotEntity getSampleplotEntity() {
        return null;
    }
}
