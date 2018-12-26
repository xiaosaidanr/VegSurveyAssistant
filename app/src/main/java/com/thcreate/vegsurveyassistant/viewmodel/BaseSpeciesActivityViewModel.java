package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.BaseSpecies;
import com.thcreate.vegsurveyassistant.repository.SpeciesRepository;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.lang.reflect.ParameterizedType;
import java.util.Date;

abstract public class BaseSpeciesActivityViewModel<T extends BaseSpecies> extends AndroidViewModel {

    private static final String SPECIES_DATA = "speciesData";

    abstract public String getSpeciesType();
    abstract T getSpeciesData(SpeciesEntity entity);

    //TODO userid1
    private int userId = 1;

    protected String plotId;
    public String action;
    protected String speciesId;

    public LiveData<T> species;

    private Class<T> clazz;

    protected SpeciesRepository repository;

    public BaseSpeciesActivityViewModel(@NonNull Application application) {
        super(application);
        repository = ((BasicApp)application).getSpeicesRepository();
        clazz = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void init(Bundle data){
        plotId = data.getString(Macro.SAMPLEPLOT_ID);
        action = data.getString(Macro.ACTION);
        speciesId = data.getString(Macro.SPECIES_ID);
        @Nullable T tmp = data.getParcelable(SPECIES_DATA);
        initSpecies(tmp);

    }

    private void initSpecies(T restoredData){
        switch (action){
            case Macro.ACTION_ADD:
                MutableLiveData<T> tmp1 = new MutableLiveData<>();
                try {
                    T data = clazz.newInstance();
                    data.plotId = plotId;
                    data.speciesId = speciesId;
                    data.type = getSpeciesType();
                    tmp1.setValue(data);
                    species = tmp1;
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case Macro.ACTION_ADD_RESTORE:
                MutableLiveData<T> tmp2 = new MutableLiveData<>();
                tmp2.setValue(restoredData);
                species = tmp2;
                break;
            case Macro.ACTION_EDIT:
                species = Transformations.map(repository.getSpeciesEntityBySpeciesId(speciesId), speciesEntity->{
                    if (speciesEntity != null){
                        return getSpeciesData(speciesEntity);
                    }
                    return null;
                });
                break;
            case Macro.ACTION_EDIT_RESTORE:
                MutableLiveData<T> tmp3 = new MutableLiveData<>();
                tmp3.setValue(restoredData);
                species = tmp3;
                break;
            default:
                break;
        }
    }

    public Bundle onSaveViewModelState(Bundle outState){
        outState.putString(Macro.SAMPLEPLOT_ID, plotId);
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_ADD_RESTORE);
        }
        if (action.equals(Macro.ACTION_EDIT) || action.equals(Macro.ACTION_EDIT_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_EDIT_RESTORE);
        }
        outState.putString(Macro.SPECIES_ID, speciesId);
        outState.putParcelable(SPECIES_DATA, (Parcelable) species.getValue());
        return outState;
    }

    public String save(){
        if (species == null){
            return getApplication().getString(R.string.species_save_error);
        }
        T speciesRaw = species.getValue();
        if (speciesRaw == null){
            return getApplication().getString(R.string.species_save_error);
        }
        if (speciesRaw.code == null){
            return getApplication().getString(R.string.please_fill_species_code);
        }
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            repository.insertSpeciesEntity(speciesRaw.getEntity());
        }
        if (action.equals(Macro.ACTION_EDIT) || action.equals(Macro.ACTION_EDIT_RESTORE)){
            repository.updateSpeciesEntity(speciesRaw.getEntity());
        }
        return null;
    }

}
