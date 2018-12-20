package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class ShrubLandActivityViewModel extends BaseSamplelandActivityViewModel {

//    public MutableLiveData<Boolean> canAddHerbPlot;

    public MutableLiveData<String> shrubPlotCount;

    public MutableLiveData<String> herbPlotCount;

    public ShrubLandActivityViewModel(@NonNull Application application) {
        super(application);
//        canAddHerbPlot = new MutableLiveData<>();
//        canAddHerbPlot.setValue(false);

        shrubPlotCount = new MutableLiveData<>();
        herbPlotCount = new MutableLiveData<>();
        shrubPlotCount.setValue("0");
        herbPlotCount.setValue("0");
    }

}
