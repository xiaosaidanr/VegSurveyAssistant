package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.PlotMainInfo;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.SpeciesMainInfo;
import com.thcreate.vegsurveyassistant.db.entity.model.HerbSampleplot;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.ArrayList;
import java.util.List;

public class HerbPlotActivityViewModel extends BaseSampleplotActivityViewModel<HerbSampleplot> {

    public HerbPlotActivityViewModel(@NonNull Application application) {
        super(application);
    }

//    public LiveData<List<PlotMainInfo>> getShrubPlotEntityList(){
//        return mSampleplotRepository.getAllShrubSampleplotEntityByLandId(landId);
//    }
//
//    public LiveData<List<PlotMainInfo>> getArborPlotEntityList(){
//        return mSampleplotRepository.getAllArborSampleplotEntityByLandId(landId);
//    }

    public List<PlotMainInfo> getArborAndShrubPlotEntityListSync(){
        ArrayList<PlotMainInfo> result = new ArrayList<>();
        List<PlotMainInfo> arborPlotEntityList = mSampleplotRepository.getAllArborSampleplotEntityByLandIdSync(landId);
        if (arborPlotEntityList != null){
            result.addAll(arborPlotEntityList);
        }
        List<PlotMainInfo> shrubPlotEntityList = mSampleplotRepository.getAllShrubSampleplotEntityByLandIdSync(landId);
        if (shrubPlotEntityList != null){
            result.addAll(shrubPlotEntityList);
        }
        return result;
    }

//    public List<PlotMainInfo> getShrubPlotEntityListSync(){
//        return mSampleplotRepository.getAllShrubSampleplotEntityByLandIdSync(landId);
//    }

    @Override
    public HerbSampleplot getPlotData(SampleplotEntity entity){
        return HerbSampleplot.getInstance(entity);
    }

    @Override
    public String getPlotType() {
        return Macro.HERB;
    }

    public LiveData<List<SpeciesMainInfo>> getSpeciesEntityList(){
        return mSpeciesRepository.getAllHerbSpeciesEntityByPlotId(plotId);
    }

}
