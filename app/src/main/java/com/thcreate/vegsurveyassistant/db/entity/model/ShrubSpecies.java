package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.util.Macro;

public class ShrubSpecies extends BaseSpecies implements Parcelable {

    public String treeNumber;//树号
    public String basalDiameter;//基径
    public String height;//高度
    public String canopyX;//冠幅X
    public String canopyY;//冠幅Y

    public ShrubSpecies() {
        this.type = Macro.SHRUB;
    }

    public ShrubSpecies(@NonNull String plotId, @NonNull String speciesId){
        super(plotId, speciesId);
        this.type = Macro.SHRUB;
    }

    public ShrubSpecies(int id, @NonNull String plotId, @NonNull String speciesId){
        super(id, plotId, speciesId);
        this.type = Macro.SHRUB;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest = writeCommonToParcel(dest);
        dest.writeValue(treeNumber);
        dest.writeValue(basalDiameter);
        dest.writeValue(height);
        dest.writeValue(canopyX);
        dest.writeValue(canopyY);
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
        Object tmpTreeNumber = source.readValue(getClass().getClassLoader());
        if (tmpTreeNumber != null){
            treeNumber = (String)tmpTreeNumber;
        }
        Object tmpBasalDiameter = source.readValue(getClass().getClassLoader());
        if (tmpBasalDiameter != null){
            basalDiameter = (String)tmpBasalDiameter;
        }
        Object tmpHeight = source.readValue(getClass().getClassLoader());
        if (tmpHeight != null){
            height = (String)tmpHeight;
        }
        Object tmpCanopyX = source.readValue(getClass().getClassLoader());
        if (tmpCanopyX != null){
            canopyX = (String)tmpCanopyX;
        }
        Object tmpCanopyY = source.readValue(getClass().getClassLoader());
        if (tmpCanopyY != null){
            canopyY = (String)tmpCanopyY;
        }
    }

    @Override
    public SpeciesEntity getSpeciesEntity() {
        return null;
    }
}
