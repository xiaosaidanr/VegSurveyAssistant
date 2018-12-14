package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.ShrubSpecies;
import com.thcreate.vegsurveyassistant.util.Macro;

public class ShrubSpeciesActivityViewModel extends BaseSpeciesActivityViewModel<ShrubSpecies> {

    public ShrubSpeciesActivityViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public ShrubSpecies getSpeciesData(SpeciesEntity entity) {
        //TODO data transfor
        return new ShrubSpecies();
    }

    @Override
    public String getSpeciesType() {
        return Macro.SHRUB;
    }
}
