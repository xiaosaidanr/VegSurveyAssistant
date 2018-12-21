package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SamplelandEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.LandMainInfo;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.List;

public class HerbLandListViewModel extends BaseSamplelandListViewModel {

    public HerbLandListViewModel(@NonNull Application application) {
        super(application);
    }

    void initSamplelandEntityList(){
        observableSamplelandEntityList = new MediatorLiveData<>();
        observableSamplelandEntityList.setValue(null);
        LiveData<List<LandMainInfo>> samplelandEntityList = samplelandRepository.loadAllSamplelandEntityByType(Macro.SAMPLELAND_TYPE_GRASS);
        observableSamplelandEntityList.addSource(samplelandEntityList, observableSamplelandEntityList::setValue);
    }

}
