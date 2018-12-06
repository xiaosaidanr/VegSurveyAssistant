package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.Yangdi;
import com.thcreate.vegsurveyassistant.repository.YangdiDataRepository;
import com.thcreate.vegsurveyassistant.repository.YangfangDataRepository;
import com.thcreate.vegsurveyassistant.service.LocationLiveData;
import com.thcreate.vegsurveyassistant.util.DeviceStatus;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;
import java.util.List;

public class BaseYangdiActivityViewModel extends AndroidViewModel {

    private static final String YANGDI_DATA = "yangdiData";

    //TODO userid1
    private int userId = 1;

    public String action;
    public String yangdiCode;
    protected String yangdiType;

    public LiveData<Yangdi> yangdi;
    public LocationLiveData locationLiveData;

    private final YangdiDataRepository mYangdiRepository;
    private final YangfangDataRepository mYangfangRepository;

    public BaseYangdiActivityViewModel(@NonNull Application application) {
        super(application);
        mYangdiRepository = ((BasicApp)application).getYangdiDataRepository();
        mYangfangRepository = ((BasicApp)application).getYangfangDataRepository();
        locationLiveData = new LocationLiveData(application);
    }

    public void init(Bundle data){
        action = data.getString(Macro.ACTION);
        yangdiCode = data.getString(Macro.YANGDI_CODE);
        yangdiType = data.getString(Macro.YANGDI_TYPE);
        @Nullable Yangdi tmp = data.getParcelable(YANGDI_DATA);
        initYangdi(tmp);
    }

    private void initYangdi(@Nullable Yangdi restoredData){
        switch (action){
            case Macro.ACTION_ADD:
                MutableLiveData<Yangdi> tmp1 = new MutableLiveData<>();
                tmp1.setValue(new Yangdi(userId, yangdiCode, yangdiType));
                yangdi = tmp1;
                break;
            case Macro.ACTION_ADD_RESTORE:
                MutableLiveData<Yangdi> tmp2 = new MutableLiveData<>();
                tmp2.setValue(restoredData);
                yangdi = tmp2;
                break;
            case Macro.ACTION_EDIT:
                yangdi = mYangdiRepository.getYangdiByYangdiCode(yangdiCode);
                break;
            case Macro.ACTION_EDIT_RESTORE:
                MutableLiveData<Yangdi> tmp3 = new MutableLiveData<>();
                tmp3.setValue(restoredData);
                yangdi = tmp3;
                break;
//            case Macro.ACTION_TEMP_SAVE:
//                yangdi = mYangdiRepository.getYangdiByYangdiCode(yangdiCode);
//                action = Macro.ACTION_TEMP_SAVE_RESTORE;
//                break;
            case Macro.ACTION_TEMP_SAVE_RESTORE:
                MutableLiveData<Yangdi> tmp4 = new MutableLiveData<>();
                tmp4.setValue(restoredData);
                yangdi = tmp4;
                break;
            default:
                break;
        }
    }


    public void getLocation(){
        locationLiveData.getLocation();
    }

    public void onGoForward(){
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            mYangdiRepository.insertYangdi(yangdi.getValue());
            action = Macro.ACTION_TEMP_SAVE;
        }
    }

    public void onCancel(){
        if (action.equals(Macro.ACTION_TEMP_SAVE)||action.equals(Macro.ACTION_TEMP_SAVE_RESTORE)){
            mYangdiRepository.deleteYangdi(yangdi.getValue());
        }
    }

    public Bundle onSaveViewModelState(Bundle outState){
        outState.putString(Macro.YANGDI_CODE, yangdiCode);
        outState.putString(Macro.YANGDI_TYPE, yangdiType);
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_ADD_RESTORE);
        }
        if (action.equals(Macro.ACTION_EDIT) || action.equals(Macro.ACTION_EDIT_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_EDIT_RESTORE);
        }
        if (action.equals(Macro.ACTION_TEMP_SAVE) || action.equals(Macro.ACTION_TEMP_SAVE_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_TEMP_SAVE_RESTORE);
        }
        outState.putParcelable(YANGDI_DATA, yangdi.getValue());
        return outState;
    }

    public LiveData<List<CaobenYangfang>> getCaobenyangfangList(){
        return mYangfangRepository.getAllCaobenYangfangByYangdiCode(yangdiCode);
    }
    public LiveData<List<GuanmuYangfang>> getGuanmuyangfangList(){
        return mYangfangRepository.getAllGuanmuYangfangByYangdiCode(yangdiCode);
    }
    public LiveData<List<QiaomuYangfang>> getQiaomuyangfangList(){
        return mYangfangRepository.getAllQiaomuYangfangByYangdiCode(yangdiCode);
    }

    public void deleteCaobenyfById(int id){
        mYangfangRepository.deleteCaobenyfByIdRelated(id);
    }

    public void deleteGuanmuyfById(int id){
        mYangfangRepository.deleteGuanmuyfByIdRelated(id);
    }

    public void deleteQiaomuyfById(int id){
        mYangfangRepository.deleteQiaomuyfByIdRelated(id);
    }

    public <U> String generateYangfangCode(Class<U> modelClass){
        return IdGenerator.getId(userId, modelClass);
    }

    public boolean save(){
        if (yangdi == null){
            return false;
        }
        Yangdi yangdiRaw = yangdi.getValue();
        if (yangdiRaw == null){
            return false;
        }
        Date dateNow = new Date();
        yangdiRaw.modifyAt = dateNow;
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            yangdiRaw.createAt = dateNow;
            mYangdiRepository.insertYangdi(yangdiRaw);
        }
        if (action.equals(Macro.ACTION_EDIT) || action.equals(Macro.ACTION_EDIT_RESTORE)){
            mYangdiRepository.updateYangdi(yangdiRaw);
        }
        if (action.equals(Macro.ACTION_TEMP_SAVE) || action.equals(Macro.ACTION_TEMP_SAVE_RESTORE)){
            yangdiRaw.createAt = dateNow;
            mYangdiRepository.updateYangdi(yangdiRaw);
        }
        return true;
    }
}
