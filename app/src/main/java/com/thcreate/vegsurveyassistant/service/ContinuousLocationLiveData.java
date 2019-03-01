package com.thcreate.vegsurveyassistant.service;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;

public class ContinuousLocationLiveData extends LiveData<ContinuousLocationLiveData.LocationDataRaw> {

    private LocationService mLocationService;
    private BDAbstractLocationListener mCallback;

    private LocationMode mMode;
    private int mFailAttempCount;

    public ContinuousLocationLiveData(Context context) {

        mLocationService = new LocationService(context);
        mCallback = new BDAbstractLocationListener() {
            @Override
            public void onReceiveLocation(BDLocation bdLocation) {
                ContinuousLocationLiveData.LocationDataRaw data = new ContinuousLocationLiveData.LocationDataRaw(bdLocation);
                switch (mMode){
                    case ATTEMP:
                        onReceiveLocationInAttempLocationMode(data);
                        break;
                    case LOWFREQUENCY:
                        onReceiveLocationInLowFrequencyLocationMode(data);
                        break;
                    case CONTINUOUS:
                        onReceiveLocationInContinuousLocationMode(data);
                        break;
                    default:
                        break;
                }
            }
        };
        setAttempLocationMode();
    }

    private void setAttempLocationMode(){
        mLocationService.stop();
        mFailAttempCount = 0;
        mMode = LocationMode.ATTEMP;
        LocationClientOption option = mLocationService.getDefaultLocationClientOption();
        option.setScanSpan(3000);
        mLocationService.setLocationOption(option);
        mLocationService.start();
    }

    private void onReceiveLocationInAttempLocationMode(ContinuousLocationLiveData.LocationDataRaw data){
        if (data.isValid){
            setContinuousLocationMode();
            postValue(data);
        }
        else {
            mFailAttempCount += 1;
            if (mFailAttempCount >= 5){
                setLowFrequencyLocationMode();
                postValue(data);
            }
        }
    }

    private void setLowFrequencyLocationMode(){
        mLocationService.stop();
        mMode = LocationMode.LOWFREQUENCY;
        LocationClientOption option = mLocationService.getDefaultLocationClientOption();
        option.setScanSpan(20000);
        mLocationService.setLocationOption(option);
        mLocationService.start();
    }

    private void onReceiveLocationInLowFrequencyLocationMode(ContinuousLocationLiveData.LocationDataRaw data){
        if (data.isValid){
            setContinuousLocationMode();
            postValue(data);
        }
    }

    private void setContinuousLocationMode(){
        mLocationService.stop();
        mMode = LocationMode.CONTINUOUS;
        LocationClientOption option = mLocationService.getDefaultLocationClientOption();
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者，该模式下开发者无需再关心定位间隔是多少，定位SDK本身发现位置变化就会及时回调给开发者
        option.setOpenAutoNotifyMode();
        //设置打开自动回调位置模式，该开关打开后，期间只要定位SDK检测到位置变化就会主动回调给开发者
        option.setOpenAutoNotifyMode(3000,1, LocationClientOption.LOC_SENSITIVITY_HIGHT);
        mLocationService.setLocationOption(option);
        mLocationService.start();
    }

    private void onReceiveLocationInContinuousLocationMode(ContinuousLocationLiveData.LocationDataRaw data){
        if (!data.isValid){
            setAttempLocationMode();
        }
        postValue(data);
    }

    @Override
    protected void onActive() {
        if (!mLocationService.isStart()){
            mLocationService.start();
        }
        mLocationService.registerListener(mCallback);
    }

    @Override
    protected void onInactive() {
        mLocationService.unregisterListener(mCallback);
        mLocationService.stop();
    }

    private enum LocationMode {
        ATTEMP,
        LOWFREQUENCY,
        CONTINUOUS
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
