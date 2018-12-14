package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class ArborLandActivityViewModel extends BaseSamplelandActivityViewModel {

    public MutableLiveData<Boolean> canAddShrubPlot;

    public MutableLiveData<Boolean> canAddHerbPlot;

    public MutableLiveData<String> arborPlotCount;

    public MutableLiveData<String> shrubPlotCount;

    public MutableLiveData<String> herbPlotCount;

    public ArborLandActivityViewModel(@NonNull Application application) {
        super(application);
        canAddShrubPlot = new MutableLiveData<>();
        canAddShrubPlot.setValue(false);
        canAddHerbPlot = new MutableLiveData<>();
        canAddHerbPlot.setValue(false);

        arborPlotCount = new MutableLiveData<>();
        shrubPlotCount = new MutableLiveData<>();
        herbPlotCount = new MutableLiveData<>();
        arborPlotCount.setValue("0");
        shrubPlotCount.setValue("0");
        herbPlotCount.setValue("0");
    }

}
