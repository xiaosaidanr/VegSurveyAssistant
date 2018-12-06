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
import com.thcreate.vegsurveyassistant.db.entity.Yangdi;
import com.thcreate.vegsurveyassistant.db.entity.Yangdian;
import com.thcreate.vegsurveyassistant.repository.YangdiDataRepository;
import com.thcreate.vegsurveyassistant.repository.YangdianDataRepository;
import com.thcreate.vegsurveyassistant.service.ContinuousLocationLiveData;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.List;

public class NearbyFragmentViewModel extends AndroidViewModel {

    public ContinuousLocationLiveData myContinuousLocationData;

    private YangdiDataRepository mYangdiDataRepository;
    private YangdianDataRepository mYangdianDataRepository;

    private final BitmapDescriptor mMarkerIcon;

    public NearbyFragmentViewModel(@NonNull Application application) {
        super(application);
        myContinuousLocationData = new ContinuousLocationLiveData(application);
        mYangdiDataRepository = ((BasicApp)application).getYangdiDataRepository();
        mYangdianDataRepository = ((BasicApp)application).getYangdianDataRepository();
        mMarkerIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_mapmarker);
    }

    public LiveData<List<Yangdi>> getYangdiList(){
        return mYangdiDataRepository.loadAllYangdi();
    }

    public LiveData<List<Yangdian>> getYangdianList(){
        return mYangdianDataRepository.loadAllYangdian();
    }

    public @Nullable MarkerOptions getMarkerOptionFromData(Yangdi yangdi){
        if (yangdi == null){
            return null;
        }
        if (yangdi.latitude == null || yangdi.longitude == null){
            return null;
        }
        LatLng position = new LatLng(Double.valueOf(yangdi.latitude), Double.valueOf(yangdi.longitude));
        Bundle data = new Bundle();
        data.putString(Macro.ACTION, Macro.ACTION_EDIT);
        data.putString(Macro.YANGDI_CODE, yangdi.yangdiCode);
        data.putString(Macro.YANGDI_TYPE, yangdi.type);
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
