package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.ArborSampleplot;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.List;

public class ArborPlotActivityViewModel extends BaseSampleplotActivityViewModel<ArborSampleplot> {

    public ArborPlotActivityViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public ArborSampleplot getPlotData(SampleplotEntity entity) {
        return ArborSampleplot.getInstance(entity);
    }

    @Override
    public String getPlotType() {
        return Macro.ARBOR;
    }

    public LiveData<List<SpeciesEntity>> getSpeciesEntityList(){
        return mSpeciesRepository.getAllArborSpeciesEntityByPlotId(plotId);
    }

}
