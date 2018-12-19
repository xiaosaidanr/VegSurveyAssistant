package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.PlotId;
import com.thcreate.vegsurveyassistant.db.entity.model.HerbSampleplot;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.List;

public class HerbPlotActivityViewModel extends BaseSampleplotActivityViewModel<HerbSampleplot> {

    public HerbPlotActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<SampleplotEntity>> getShrubPlotEntityList(){
        return mSampleplotRepository.getAllShrubSampleplotEntityByLandId(landId);
    }

    public LiveData<List<SampleplotEntity>> getArborPlotEntityList(){
        return mSampleplotRepository.getAllArborSampleplotEntityByLandId(landId);
    }

    @Override
    public HerbSampleplot getPlotData(SampleplotEntity entity){
        return HerbSampleplot.getInstance(entity);
    }

    @Override
    public String getPlotType() {
        return Macro.HERB;
    }

    public LiveData<List<SpeciesEntity>> getSpeciesEntityList(){
        return mSpeciesRepository.getAllHerbSpeciesEntityByPlotId(plotId);
    }

}
