package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.db.entity.SamplelandEntity;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.PlotMainInfo;
import com.thcreate.vegsurveyassistant.db.entity.model.HerbSampleplot;
import com.thcreate.vegsurveyassistant.db.entity.model.Sampleland;
import com.thcreate.vegsurveyassistant.repository.SamplelandRepository;
import com.thcreate.vegsurveyassistant.repository.SampleplotRepository;
import com.thcreate.vegsurveyassistant.service.LocationLiveData;
import com.thcreate.vegsurveyassistant.service.SessionManager;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;
import java.util.List;

public class BaseSamplelandActivityViewModel extends AndroidViewModel {

    private static final String SAMPLELAND_DATA = "samplelandData";

    public String action;
    public String landId;
    protected String landType;

    public LiveData<Sampleland> sampleland;
    public LocationLiveData locationLiveData;

    private final SamplelandRepository mSamplelandRepository;
    private final SampleplotRepository mSampleplotRepository;

    public BaseSamplelandActivityViewModel(@NonNull Application application) {
        super(application);
        mSamplelandRepository = ((BasicApp)application).getSamplelandRepository();
        mSampleplotRepository = ((BasicApp)application).getSampleplotRepository();
        locationLiveData = new LocationLiveData(application);
    }

    public void init(Bundle data){
        action = data.getString(Macro.ACTION);
        landId = data.getString(Macro.SAMPLELAND_ID);
        landType = data.getString(Macro.SAMPLELAND_TYPE);
        @Nullable Sampleland tmp = data.getParcelable(SAMPLELAND_DATA);
        initSampleland(tmp);
    }

    private void initSampleland(@Nullable Sampleland restoredData){
        switch (action){
            case Macro.ACTION_ADD:
                MutableLiveData<Sampleland> tmp1 = new MutableLiveData<>();
                tmp1.setValue(new Sampleland(SessionManager.getLoggedInUserId(), landId, landType));
                sampleland = tmp1;
                break;
            case Macro.ACTION_ADD_RESTORE:
                MutableLiveData<Sampleland> tmp2 = new MutableLiveData<>();
                tmp2.setValue(restoredData);
                sampleland = tmp2;
                break;
            case Macro.ACTION_EDIT:
                sampleland = Transformations.map(mSamplelandRepository.getSamplelandEntityByLandId(landId), data->{
                    if (data != null){
                        return Sampleland.getInstance(data);
                    }
                    return null;
                });
                break;
            case Macro.ACTION_EDIT_RESTORE:
                MutableLiveData<Sampleland> tmp3 = new MutableLiveData<>();
                tmp3.setValue(restoredData);
                sampleland = tmp3;
                break;
//            case Macro.ACTION_TEMP_SAVE:
//                yangdi = mYangdiRepository.getYangdiByYangdiCode(yangdiCode);
//                action = Macro.ACTION_TEMP_SAVE_RESTORE;
//                break;
            case Macro.ACTION_TEMP_SAVE_RESTORE:
                MutableLiveData<Sampleland> tmp4 = new MutableLiveData<>();
                tmp4.setValue(restoredData);
                sampleland = tmp4;
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
            mSamplelandRepository.insertSamplelandEntity(sampleland.getValue().getEntity());
            action = Macro.ACTION_TEMP_SAVE;
        }
    }

    public void onCancel(){
        if (action.equals(Macro.ACTION_TEMP_SAVE)||action.equals(Macro.ACTION_TEMP_SAVE_RESTORE)){
            mSamplelandRepository.deleteSamplelandEntity(sampleland.getValue().getEntity());
        }
    }

    public Bundle onSaveViewModelState(Bundle outState){
        outState.putString(Macro.SAMPLELAND_ID, landId);
        outState.putString(Macro.SAMPLELAND_TYPE, landType);
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_ADD_RESTORE);
        }
        if (action.equals(Macro.ACTION_EDIT) || action.equals(Macro.ACTION_EDIT_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_EDIT_RESTORE);
        }
        if (action.equals(Macro.ACTION_TEMP_SAVE) || action.equals(Macro.ACTION_TEMP_SAVE_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_TEMP_SAVE_RESTORE);
        }
        outState.putParcelable(SAMPLELAND_DATA, sampleland.getValue());
        return outState;
    }

    public LiveData<List<PlotMainInfo>> getHerbSampleplotEntityList(){
        return mSampleplotRepository.getAllHerbSampleplotEntityByLandId(landId);
    }
    public LiveData<List<PlotMainInfo>> getShrubSampleplotEntityList(){
        return mSampleplotRepository.getAllShrubSampleplotEntityByLandId(landId);
    }
    public LiveData<List<PlotMainInfo>> getArborSampleplotEntityList(){
        return mSampleplotRepository.getAllArborSampleplotEntityByLandId(landId);
    }

    public void deleteSampleplotEntityById(int id){
        mSampleplotRepository.softDeleteSampleplotEntityById(id);
    }

//    public void deleteGuanmuyfById(int id){
//        mYangfangRepository.deleteGuanmuyfByIdRelated(id);
//    }
//
//    public void deleteQiaomuyfById(int id){
//        mYangfangRepository.deleteQiaomuyfByIdRelated(id);
//    }

    public String generateSampleplotId(){
        return IdGenerator.getId(SessionManager.getLoggedInUserId());
    }

    public String save(){
        if (sampleland == null){
            return getApplication().getString(R.string.land_save_error);
        }
        Sampleland samplelandRaw = sampleland.getValue();
        if (samplelandRaw == null){
            return getApplication().getString(R.string.land_save_error);
        }
        if (samplelandRaw.code == null){
            return getApplication().getString(R.string.please_fill_land_code);
        }
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            mSamplelandRepository.insertSamplelandEntity(samplelandRaw.getEntity());
        }
        if (action.equals(Macro.ACTION_EDIT) || action.equals(Macro.ACTION_EDIT_RESTORE)){
            mSamplelandRepository.updateSamplelandEntity(samplelandRaw.getEntity());
        }
        if (action.equals(Macro.ACTION_TEMP_SAVE) || action.equals(Macro.ACTION_TEMP_SAVE_RESTORE)){
            mSamplelandRepository.updateSamplelandEntity(samplelandRaw.getEntity());
        }
        return null;
    }
}
