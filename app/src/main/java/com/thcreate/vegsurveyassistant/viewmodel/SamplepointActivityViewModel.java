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
import com.thcreate.vegsurveyassistant.db.entity.SamplepointEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.Samplepoint;
import com.thcreate.vegsurveyassistant.repository.SamplepointRepository;
import com.thcreate.vegsurveyassistant.service.LocationLiveData;
import com.thcreate.vegsurveyassistant.service.SessionManager;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;

public class SamplepointActivityViewModel extends AndroidViewModel {

    private static final String SAMPLEPOINT_DATA = "samplepointData";

    public String pointId;
    public String action;

//    public LiveData<UserEntity> user;

    public LiveData<Samplepoint> samplepoint;
    public LocationLiveData locationLiveData;

    private SamplepointRepository mSamplepointRepository;

    public SamplepointActivityViewModel(@NonNull Application application) {
        super(application);
        mSamplepointRepository = ((BasicApp)application).getSamplepointRepository();
        locationLiveData = new LocationLiveData(application);
    }

    public void init(Bundle data){
        action = data.getString(Macro.ACTION);
        pointId = data.getString(Macro.SAMPLEPOINT_ID);
        @Nullable Samplepoint tmp = data.getParcelable(SAMPLEPOINT_DATA);
        initSamplepoint(tmp);
    }

    private void initSamplepoint(Samplepoint restoredData){
        switch (action){
            case Macro.ACTION_ADD:
                MutableLiveData<Samplepoint> tmp1 = new MutableLiveData<>();
                tmp1.setValue(new Samplepoint(SessionManager.getLoggedInUserId(), pointId));
                samplepoint = tmp1;
                break;
            case Macro.ACTION_ADD_RESTORE:
                MutableLiveData<Samplepoint> tmp2 = new MutableLiveData<>();
                tmp2.setValue(restoredData);
                samplepoint = tmp2;
                break;
            case Macro.ACTION_EDIT:
                samplepoint = Transformations.map(mSamplepointRepository.loadSamplepointByPointId(pointId),(data)->{
                    if (data!=null){
                        return Samplepoint.getInstance(data);
                    }
                    return null;
                });
                break;
            case Macro.ACTION_EDIT_RESTORE:
                MutableLiveData<Samplepoint> tmp4 = new MutableLiveData<>();
                tmp4.setValue(restoredData);
                samplepoint = tmp4;
                break;
            default:
                break;
        }
    }

    public Bundle onSaveViewModelState(Bundle outState){
        outState.putString(Macro.SAMPLEPOINT_ID, pointId);
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_ADD_RESTORE);
        }
        if (action.equals(Macro.ACTION_EDIT) || action.equals(Macro.ACTION_EDIT_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_EDIT_RESTORE);
        }
        outState.putParcelable(SAMPLEPOINT_DATA, samplepoint.getValue());
        return outState;
    }

    public void getLocation(){
        locationLiveData.getLocation();
    }

    public String save(){
        if (samplepoint == null){
            return getApplication().getString(R.string.point_save_error);
        }
        Samplepoint samplepointRaw = samplepoint.getValue();
        if (samplepointRaw == null){
            return getApplication().getString(R.string.point_save_error);
        }
        if (samplepointRaw.code == null){
            return getApplication().getString(R.string.please_fill_point_code);
        }
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            mSamplepointRepository.insertSamplepoint(samplepointRaw.getEntity());
        }
        if (action.equals(Macro.ACTION_EDIT) || action.equals(Macro.ACTION_EDIT_RESTORE)){
            mSamplepointRepository.updateSamplepoint(samplepointRaw.getEntity());
        }
        return null;
    }

}
