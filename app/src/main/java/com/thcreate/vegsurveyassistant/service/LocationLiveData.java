package com.thcreate.vegsurveyassistant.service;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;

public class LocationLiveData extends LiveData<LocationLiveData.LocationData> {

    private LocationService mLocationService;
    private BDAbstractLocationListener mCallback;

    public LocationLiveData(Context context) {

        mLocationService = new LocationService(context);

        mCallback = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                LocationLiveData.LocationData tmp = new LocationLiveData.LocationData(bdLocation);
                postValue(tmp);
                mLocationService.stop();
            }
        };

    }

    public void getLocation(){
        if (!mLocationService.isStart()){
            mLocationService.start();
        }
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


    public static class LocationData {
        public String longitude = null;
        public String latitude = null;
        public boolean isValid = false;
        public String address = null;

        public LocationData(BDLocation bdLocation) {

            longitude = String.valueOf(bdLocation.getLongitude());
            latitude = String.valueOf(bdLocation.getLatitude());
            address = bdLocation.getAddrStr();
            if (address == null){
                isValid = false;
            }
            else{
                isValid = true;
            }

        }
    }

}
