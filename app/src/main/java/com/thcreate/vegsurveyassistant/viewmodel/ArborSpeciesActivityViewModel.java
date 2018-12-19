package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.ArborSpecies;
import com.thcreate.vegsurveyassistant.util.Macro;

public class ArborSpeciesActivityViewModel extends BaseSpeciesActivityViewModel<ArborSpecies> {

    public ArborSpeciesActivityViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public ArborSpecies getSpeciesData(SpeciesEntity entity) {
        return ArborSpecies.getInstance(entity);
    }

    @Override
    public String getSpeciesType() {
        return Macro.ARBOR;
    }
}
