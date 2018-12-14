package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class HerbLandActivityViewModel extends BaseSamplelandActivityViewModel {

    public MutableLiveData<String> herbPlotCount;

    public HerbLandActivityViewModel(@NonNull Application application) {
        super(application);
        herbPlotCount = new MutableLiveData<>();
        herbPlotCount.setValue("0");
    }

}
