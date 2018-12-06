package com.thcreate.vegsurveyassistant.util;

import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.Network;

public class DeviceStatus {

    public static boolean checkDeviceLocationEnabled(LocationManager locationManager){
        return !(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER));
    }

    public static boolean checkDeviceHasInternet(ConnectivityManager connectivityManager){
        Network[] networks = connectivityManager.getAllNetworks();
        if (networks != null){
            for (Network network: networks) {
                if (connectivityManager.getNetworkInfo(network).isConnected()){
                    return true;
                }
            }
        }
        return false;
    }

}
