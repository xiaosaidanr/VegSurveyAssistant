package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.model.LatLng;
import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.db.entity.SamplelandEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.LandMainInfo;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.PointMainInfo;
import com.thcreate.vegsurveyassistant.repository.SamplelandRepository;
import com.thcreate.vegsurveyassistant.repository.SamplepointRepository;
import com.thcreate.vegsurveyassistant.service.ContinuousLocationLiveData;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.List;

public class NearbyFragmentViewModel extends AndroidViewModel {

    public ContinuousLocationLiveData myContinuousLocationData;

    private final BitmapDescriptor mMarkerIcon;

    public NearbyFragmentViewModel(@NonNull Application application) {
        super(application);
        myContinuousLocationData = new ContinuousLocationLiveData(application);
        mMarkerIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_mapmarker);
    }

    public @Nullable MarkerOptions getMarkerOptionFromData(LandMainInfo land){
        if (land == null){
            return null;
        }
        if (land.lat == null || land.lng == null){
            return null;
        }
        LatLng position = new LatLng(Double.valueOf(land.lat), Double.valueOf(land.lng));
        Bundle data = new Bundle();
        data.putString(Macro.ACTION, Macro.ACTION_EDIT);
        data.putString(Macro.SAMPLELAND_ID, land.landId);
        data.putString(Macro.SAMPLELAND_TYPE, land.type);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .icon(mMarkerIcon)
                .draggable(false)
                .extraInfo(data);
        return markerOptions;
    }

    @Override
    protected void onCleared() {
        try {
            mMarkerIcon.recycle();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        super.onCleared();
        //回收bitmap资源
    }
}
