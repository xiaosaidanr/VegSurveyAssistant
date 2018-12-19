package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.ShrubSampleplot;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.List;

public class ShrubPlotActivityViewModel extends BaseSampleplotActivityViewModel<ShrubSampleplot> {

    public ShrubPlotActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<SampleplotEntity>> getArborPlotEntityList(){
        return mSampleplotRepository.getAllArborSampleplotEntityByLandId(landId);
    }

    @Override
    public ShrubSampleplot getPlotData(SampleplotEntity entity) {
        return ShrubSampleplot.getInstance(entity);
    }

    @Override
    public String getPlotType() {
        return Macro.SHRUB;
    }

    public LiveData<List<SpeciesEntity>> getSpeciesEntityList(){
        return mSpeciesRepository.getAllShrubSpeciesEntityByPlotId(plotId);
    }

}
