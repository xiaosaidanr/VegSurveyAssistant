package com.thcreate.vegsurveyassistant.db.entity.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;

public class ShrubSampleplot extends BaseSampleplot implements Parcelable {

    public String arborPlotId;//所属乔木样方编码
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
        dest.writeValue(id);
        dest.writeValue(landId);
        dest.writeValue(plotId);
        dest.writeValue(type);
        dest.writeValue(arborPlotId);
        dest.writeValue(communityName);
        dest.writeValue(plotLength);
        dest.writeValue(plotWidth);
        dest.writeValue(communityCoverage);
        dest.writeValue(communityHeight);
        dest.writeValue(averageBasalDiameter);
        dest.writeValue(lng);
        dest.writeValue(lat);
        dest.writeValue(investigator);
        dest.writeValue(investigateDate);
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
        Object tmpId = source.readValue(getClass().getClassLoader());
        if (tmpId != null){
            id = (int)tmpId;
        }
        Object tmpLandId = source.readValue(getClass().getClassLoader());
        if (tmpLandId != null){
            landId = (String)tmpLandId;
        }
        Object tmpPlotId = source.readValue(getClass().getClassLoader());
        if (tmpPlotId != null){
            plotId = (String)tmpPlotId;
        }
        Object tmpType = source.readValue(getClass().getClassLoader());
        if (tmpType != null){
            type = (String)tmpType;
        }
        Object tmpArborPlotId = source.readValue(getClass().getClassLoader());
        if (tmpArborPlotId != null){
            arborPlotId = (String)tmpArborPlotId;
        }
        Object tmpCommunityName = source.readValue(getClass().getClassLoader());
        if (tmpCommunityName != null){
            communityName = (String)tmpCommunityName;
        }
        Object tmpPlotLength = source.readValue(getClass().getClassLoader());
        if (tmpPlotLength != null){
            plotLength = (String)tmpPlotLength;
        }
        Object tmpPlotWidth = source.readValue(getClass().getClassLoader());
        if (tmpPlotWidth != null){
            plotWidth = (String)tmpPlotWidth;
        }
        Object tmpCommunityCoverage = source.readValue(getClass().getClassLoader());
        if (tmpCommunityCoverage != null){
            communityCoverage = (String)tmpCommunityCoverage;
        }
        Object tmpCommunityHeight = source.readValue(getClass().getClassLoader());
        if (tmpCommunityHeight != null){
            communityHeight = (String)tmpCommunityHeight;
        }
        Object tmpAverageBasalDiameter = source.readValue(getClass().getClassLoader());
        if (tmpAverageBasalDiameter != null){
            averageBasalDiameter = (String)tmpAverageBasalDiameter;
        }
        Object tmpLng = source.readValue(getClass().getClassLoader());
        if (tmpLng != null){
            lng = (String)tmpLng;
        }
        Object tmpLat = source.readValue(getClass().getClassLoader());
        if (tmpLat != null){
            lat = (String)tmpLat;
        }
        Object tmpInvestigator = source.readValue(getClass().getClassLoader());
        if (tmpInvestigator != null){
            investigator = (String)tmpInvestigator;
        }
        Object tmpInvestigateDate = source.readValue(getClass().getClassLoader());
        if (tmpInvestigateDate != null){
            investigateDate = (String)tmpInvestigateDate;
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
    public SampleplotEntity getSampleplotEntity() {
        return null;
    }
}
