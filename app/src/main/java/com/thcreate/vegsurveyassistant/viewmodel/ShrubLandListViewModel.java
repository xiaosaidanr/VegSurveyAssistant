package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SamplelandEntity;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.List;

public class ShrubLandListViewModel extends BaseSamplelandListViewModel {

    public ShrubLandListViewModel(@NonNull Application application) {
        super(application);
    }

    void initSamplelandEntityList(){
        observableSamplelandEntityList = new MediatorLiveData<>();
        observableSamplelandEntityList.setValue(null);
        LiveData<List<SamplelandEntity>> samplelandEntityList = samplelandRepository.loadAllSamplelandEntityByType(Macro.SAMPLELAND_TYPE_BUSH);
        observableSamplelandEntityList.addSource(samplelandEntityList, observableSamplelandEntityList::setValue);
    }

}
