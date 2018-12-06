package com.thcreate.vegsurveyassistant.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.thcreate.vegsurveyassistant.util.DeviceStatus;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.BaseYangdiActivityViewModel;

import java.lang.reflect.ParameterizedType;

public class BaseYangdiActivity<U extends BaseYangdiActivityViewModel> extends BaseActivity {

    U mViewModel;

    Class<U> clazzU;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIsHandleBackPressed(true);
        clazzU = (Class <U>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        mViewModel = ViewModelProviders.of(this).get(clazzU);
        initViewModel(savedInstanceState);
        initOnBackPressed();
    }

    private void initViewModel(@Nullable Bundle savedInstanceState){
        if (savedInstanceState == null){
            savedInstanceState = new Bundle();
            Intent intent = getIntent();
            savedInstanceState.putString(Macro.ACTION, intent.getStringExtra(Macro.ACTION));
            savedInstanceState.putString(Macro.YANGDI_CODE, intent.getStringExtra(Macro.YANGDI_CODE));
            savedInstanceState.putString(Macro.YANGDI_TYPE, intent.getStringExtra(Macro.YANGDI_TYPE));
        }
        mViewModel.init(savedInstanceState);
    }

    private void initOnBackPressed(){
        if (mViewModel.action.equals(Macro.ACTION_EDIT) || mViewModel.action.equals(Macro.ACTION_EDIT_RESTORE)){
            setmAlertDialog("放弃本次样地编辑?", null, null);
        }
        else{
            setmAlertDialog("放弃本次样地添加?", null, null);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = mViewModel.onSaveViewModelState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPositiveButtonPressed() {
        mViewModel.onCancel();
        super.onPositiveButtonPressed();
    }


    public void onAutoLocation(View v){
        StringBuffer hint = new StringBuffer();
        if (!DeviceStatus.checkDeviceLocationEnabled((LocationManager)getSystemService(getApplicationContext().LOCATION_SERVICE))){
            hint.append("未打开位置开关，");
        }
        if (!DeviceStatus.checkDeviceHasInternet((ConnectivityManager)getSystemService(getApplicationContext().CONNECTIVITY_SERVICE))){
            hint.append("无网络连接，");
        }
        if (hint.length() > 0){
            hint.append("可能导致定位失败或定位不准");
            Toast.makeText(this, hint.toString(), Toast.LENGTH_SHORT).show();
        }
        mViewModel.getLocation();
    }

}
