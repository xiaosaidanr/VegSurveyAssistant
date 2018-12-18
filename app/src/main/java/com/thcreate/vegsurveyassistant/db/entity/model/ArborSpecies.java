package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.util.Macro;

public class ArborSpecies extends BaseSpecies implements Parcelable {

    public String treeNumber;//树号
    public String DBH;//胸径
    public String height;//高度
    public String canopyX;//冠幅X
    public String canopyY;//冠幅Y

    public ArborSpecies() {
        this.type = Macro.ARBOR;
    }

    public ArborSpecies(@NonNull String plotId, @NonNull String speciesId){
        super(plotId, speciesId);
        this.type = Macro.ARBOR;
    }

    public ArborSpecies(int id, @NonNull String plotId, @NonNull String speciesId){
        super(id, plotId, speciesId);
        this.type = Macro.ARBOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest = writeCommonToParcel(dest);
        dest.writeValue(treeNumber);
        dest.writeValue(DBH);
        dest.writeValue(height);
        dest.writeValue(canopyX);
        dest.writeValue(canopyY);
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
        Object tmpTreeNumber = source.readValue(getClass().getClassLoader());
        if (tmpTreeNumber != null){
            treeNumber = (String)tmpTreeNumber;
        }
        Object tmpDBH = source.readValue(getClass().getClassLoader());
        if (tmpDBH != null){
            DBH = (String)tmpDBH;
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
