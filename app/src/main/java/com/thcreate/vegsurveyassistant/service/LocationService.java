package com.thcreate.vegsurveyassistant.service;

import android.arch.lifecycle.LifecycleObserver;
import android.content.Context;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

public class LocationService implements LifecycleObserver {

    private LocationClient mClient = null;
    private LocationClientOption mOption, mDiyOption;
    private Object mObjLock = new Object();

    public LocationService(Context context){
        synchronized (mObjLock){
            if (mClient == null){
                mClient = new LocationClient(context);
                mClient.setLocOption(getDefaultLocationClientOption());
            }
        }
    }

    public boolean registerListener(BDAbstractLocationListener listener){
        boolean isSuccess = false;
        if (listener != null){
            mClient.registerLocationListener(listener);
            isSuccess = true;
        }
        return isSuccess;
    }

    public void unregisterListener(BDAbstractLocationListener listener){
        if (listener != null){
            mClient.unRegisterLocationListener(listener);
        }
    }

    public boolean setLocationOption(LocationClientOption option){
        boolean isSuccess = false;
        if (option != null){
            if (mClient.isStarted()){
                mClient.stop();
            }
            mDiyOption = option;
            mClient.setLocOption(option);
            isSuccess = true;
        }
        return isSuccess;

    }

    public LocationClientOption getDefaultLocationClientOption(){
        if(mOption == null){
            mOption = new LocationClientOption();
            mOption.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
            mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
            mOption.setScanSpan(0);//可选，默认0，即仅定位一次，设置发起连续定位请求的间隔需要大于等于1000ms才是有效的
            mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
            mOption.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
            mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
            mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
            mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
            mOption.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
            mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
            mOption.setOpenGps(true);//可选，默认false，设置是否开启Gps定位
            mOption.setIsNeedAltitude(false);//可选，默认false，设置定位时是否需要海拔信息，默认不需要，除基础定位版本都可用
        }
        return mOption;
    }

    public LocationClientOption getOption(){
        if (mDiyOption == null){
            mDiyOption = new LocationClientOption();
        }
        return mDiyOption;
    }

    public void start(){
        synchronized (mObjLock){
            if (mClient != null && !mClient.isStarted()){
                mClient.start();
            }
        }
    }

    public void stop(){
        synchronized (mObjLock){
            if (mClient != null && mClient.isStarted()){
                mClient.stop();
            }
        }
    }

    public boolean isStart(){
        if (mClient == null){
            return false;
        }
        else {
            return mClient.isStarted();
        }
    }

}
