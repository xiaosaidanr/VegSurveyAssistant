package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.HerbSpecies;
import com.thcreate.vegsurveyassistant.util.Macro;

public class HerbSpeciesActivityViewModel extends BaseSpeciesActivityViewModel<HerbSpecies> {

    public HerbSpeciesActivityViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public HerbSpecies getSpeciesData(SpeciesEntity entity) {
        //TODO data transfor
        return new HerbSpecies();
    }

    @Override
    public String getSpeciesType() {
        return Macro.HERB;
    }
}
