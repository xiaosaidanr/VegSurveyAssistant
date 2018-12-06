package com.thcreate.vegsurveyassistant.service;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;

public class ContinuousLocationLiveData extends LiveData<ContinuousLocationLiveData.LocationDataRaw> {

    private LocationService mLocationService;
    private BDAbstractLocationListener mCallback;

    public ContinuousLocationLiveData(Context context) {

        mLocationService = new LocationService(context);
        LocationClientOption option = mLocationService.getDefaultLocationClientOption();
        option.setOpenAutoNotifyMode();
        mLocationService.setLocationOption(option);
        mCallback = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                ContinuousLocationLiveData.LocationDataRaw tmp = new ContinuousLocationLiveData.LocationDataRaw(bdLocation);
                postValue(tmp);
            }
        };
        mLocationService.start();

    }

    @Override
    protected void onActive() {
        mLocationService.registerListener(mCallback);
    }

    @Override
    protected void onInactive() {
        mLocationService.unregisterListener(mCallback);
        mLocationService.stop();
    }

    public static class LocationDataRaw {
        public double longitude;
        public double latitude;
        public boolean isValid = false;

        public LocationDataRaw(BDLocation bdLocation) {

            longitude = bdLocation.getLongitude();
            latitude = bdLocation.getLatitude();
            if (bdLocation.getAddrStr() == null){
                isValid = false;
            }
            else{
                isValid = true;
            }

        }
    }

}
