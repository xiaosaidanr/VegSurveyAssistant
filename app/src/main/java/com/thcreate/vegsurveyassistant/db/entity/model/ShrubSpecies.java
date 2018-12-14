package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;

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
        dest.writeValue(id);
        dest.writeValue(plotId);
        dest.writeValue(speciesId);
        dest.writeValue(type);
        dest.writeValue(name);
        dest.writeValue(latinName);
        dest.writeValue(treeNumber);
        dest.writeValue(basalDiameter);
        dest.writeValue(height);
        dest.writeValue(canopyX);
        dest.writeValue(canopyY);
        dest.writeValue(note);
        if (createAt == null){
            dest.writeValue(null);
        }
        else {
            dest.writeValue(createAt.getTime());
        }
        if (updateAt == null){
            dest.writeValue(null);
        }
        else {
            dest.writeValue(updateAt.getTime());
        }
        if (uploadAt == null){
            dest.writeValue(null);
        }
        else {
            dest.writeValue(uploadAt.getTime());
        }
        if (deleteAt == null){
            dest.writeValue(null);
        }
        else {
            dest.writeValue(deleteAt.getTime());
        }
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
        Object tmpId = source.readValue(getClass().getClassLoader());
        if (tmpId != null){
            id = (int)tmpId;
        }
        Object tmpPlotId = source.readValue(getClass().getClassLoader());
        if (tmpPlotId != null){
            plotId = (String)tmpPlotId;
        }
        Object tmpSpeciesId = source.readValue(getClass().getClassLoader());
        if (tmpSpeciesId != null){
            speciesId = (String)tmpSpeciesId;
        }
        Object tmpType = source.readValue(getClass().getClassLoader());
        if (tmpType != null){
            type = (String)tmpType;
        }
        Object tmpName = source.readValue(getClass().getClassLoader());
        if (tmpName != null){
            name = (String)tmpName;
        }
        Object tmpLatinName = source.readValue(getClass().getClassLoader());
        if (tmpLatinName != null){
            latinName = (String)tmpLatinName;
        }
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
        Object tmpNote = source.readValue(getClass().getClassLoader());
        if (tmpNote != null){
            note = (String)tmpNote;
        }
        Object tmpCreateAt = source.readValue(getClass().getClassLoader());
        if (tmpCreateAt != null){
            createAt = new Date((Long)tmpCreateAt);
        }
        Object tmpUpdateAt = source.readValue(getClass().getClassLoader());
        if (tmpUpdateAt != null){
            updateAt = new Date((Long)tmpUpdateAt);
        }
        Object tmpUploadAt = source.readValue(getClass().getClassLoader());
        if (tmpUploadAt != null){
            uploadAt = new Date((Long)tmpUploadAt);
        }
        Object tmpDeleteAt = source.readValue(getClass().getClassLoader());
        if (tmpDeleteAt != null){
            deleteAt = new Date((Long)tmpDeleteAt);
        }
    }

    @Override
    public SpeciesEntity getSpeciesEntity() {
        return null;
    }
}
