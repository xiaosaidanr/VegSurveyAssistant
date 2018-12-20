package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.SamplelandEntity;
import com.thcreate.vegsurveyassistant.repository.SamplelandRepository;

import java.util.List;

public abstract class BaseSamplelandListViewModel extends AndroidViewModel {

    final SamplelandRepository samplelandRepository;

    MediatorLiveData<List<SamplelandEntity>> observableSamplelandEntityList;

    public BaseSamplelandListViewModel(@NonNull Application application) {
        super(application);
        samplelandRepository = ((BasicApp)application).getSamplelandRepository();
        initSamplelandEntityList();
    }

    abstract void initSamplelandEntityList();

    public LiveData<List<SamplelandEntity>> getSamplelandEntityList(){
        return observableSamplelandEntityList;
    }

    public void deleteSamplelandEntityById(int id){
        samplelandRepository.softDeleteSamplelandEntityById(id);
    }

}
