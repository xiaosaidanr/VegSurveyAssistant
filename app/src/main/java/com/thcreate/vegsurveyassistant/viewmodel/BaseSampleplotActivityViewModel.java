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
import com.thcreate.vegsurveyassistant.db.entity.PictureEntity;
import com.thcreate.vegsurveyassistant.db.entity.PlotPictureEntity;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.BaseSampleplot;
import com.thcreate.vegsurveyassistant.repository.PictureRepository;
import com.thcreate.vegsurveyassistant.repository.SpeciesRepository;
import com.thcreate.vegsurveyassistant.repository.SampleplotRepository;
import com.thcreate.vegsurveyassistant.service.LocationLiveData;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

abstract public class BaseSampleplotActivityViewModel<T extends BaseSampleplot> extends AndroidViewModel {

    abstract public T getPlotData(SampleplotEntity entity);

    abstract public String getPlotType();

    public String generateSpeciesId(){
        return IdGenerator.getId(userId);
    }

    private static final String SAMPLEPLOT_DATA = "sampleplot_data";

    //TODO userid1
    private int userId = 1;

    public String landId;
    public MutableLiveData<String> landType;
    public String action;
    public String plotId;
//    private String plotType;

    public LiveData<T> sampleplot;
    public LocationLiveData locationLiveData;

    public MutableLiveData<String> speciesCount;
    public MutableLiveData<String> pictureCount;

    private Class<T> mClazzT;

    SampleplotRepository mSampleplotRepository;
    SpeciesRepository mSpeciesRepository;
    PictureRepository mPictureRepository;

    public BaseSampleplotActivityViewModel(@NonNull Application application) {
        super(application);
        mSampleplotRepository = ((BasicApp)application).getSampleplotRepository();
        mSpeciesRepository = ((BasicApp)application).getSpeicesRepository();
        mPictureRepository = ((BasicApp)application).getPictureRepository();
        mClazzT = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        speciesCount = new MutableLiveData<>();
        speciesCount.setValue("0");
        pictureCount = new MutableLiveData<>();
        pictureCount.setValue("0");
//        plotType = getPlotType();
        locationLiveData = new LocationLiveData(application);
    }

    public void init(Bundle data){
        landId = data.getString(Macro.SAMPLELAND_ID);
        landType = new MutableLiveData<>();
        landType.setValue(data.getString(Macro.SAMPLELAND_TYPE));
        action = data.getString(Macro.ACTION);
        plotId = data.getString(Macro.SAMPLEPLOT_ID);
        @Nullable T tmp = data.getParcelable(SAMPLEPLOT_DATA);
        initSampleplot(tmp);
    }

    private void initSampleplot(T data){
        switch (action){
            case Macro.ACTION_ADD:
                MutableLiveData<T> tmp1 = new MutableLiveData<>();
                try {
                    data = mClazzT.newInstance();
                    data.landId = landId;
                    data.plotId = plotId;
                    data.type = getPlotType();
                    tmp1.setValue(data);
                    sampleplot = tmp1;
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case Macro.ACTION_ADD_RESTORE:
                MutableLiveData<T> tmp2 = new MutableLiveData<>();
                tmp2.setValue(data);
                sampleplot = tmp2;
                break;
            case Macro.ACTION_EDIT:
                sampleplot = Transformations.map(mSampleplotRepository.getSampleplotEntityByPlotId(plotId), (plotEntity)->{
                    if (plotEntity != null){
                        return getPlotData(plotEntity);
                    }
                    return null;
                });
                break;
            case Macro.ACTION_EDIT_RESTORE:
                MutableLiveData<T> tmp3 = new MutableLiveData<>();
                tmp3.setValue(data);
                sampleplot = tmp3;
                break;
//            case Macro.ACTION_TEMP_SAVE:
//                yangfang = getYangfangData();
//                action = Macro.ACTION_TEMP_SAVE_RESTORE;
//                break;
            case Macro.ACTION_TEMP_SAVE_RESTORE:
                MutableLiveData<T> tmp4 = new MutableLiveData<>();
                tmp4.setValue(data);
                sampleplot = tmp4;
                break;
            default:
                break;
        }
    }

    public LiveData<List<PlotPictureEntity>> getPlotPictureEntityList(){
        return mPictureRepository.loadAllPlotPictureEntityByOwnerId(plotId);
    }

    public void deleteSpeciesEntityById(int id){
        mSpeciesRepository.softDeleteSpeciesEntityById(id);
    }

    public void deletePlotPictureEntityById(int id){
        mPictureRepository.softDeletePlotPictureEntityById(id);
    }

    public void onGoForward(){
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            mSampleplotRepository.insertSampleplotEntity(sampleplot.getValue().getEntity());
            action = Macro.ACTION_TEMP_SAVE;
        }
    }

    public void onCancel(){
        if (action.equals(Macro.ACTION_TEMP_SAVE_RESTORE)||action.equals(Macro.ACTION_TEMP_SAVE)){
            mSampleplotRepository.deleteSampleplotEntity(sampleplot.getValue().getEntity());
        }
    }

    public Bundle onSaveViewModelState(Bundle outState) {
        outState.putString(Macro.SAMPLELAND_ID, landId);
        outState.putString(Macro.SAMPLELAND_TYPE, landType.getValue());
        outState.putString(Macro.SAMPLEPLOT_ID, plotId);
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_ADD_RESTORE);
        }
        if (action.equals(Macro.ACTION_EDIT) || action.equals(Macro.ACTION_EDIT_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_EDIT_RESTORE);
        }
        if (action.equals(Macro.ACTION_TEMP_SAVE) || action.equals(Macro.ACTION_TEMP_SAVE_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_TEMP_SAVE_RESTORE);
        }
        outState.putParcelable(SAMPLEPLOT_DATA, (Parcelable) sampleplot.getValue());
        return outState;
    }

    public void getLocation(){
        locationLiveData.getLocation();
    }

    public boolean save(){
        if (sampleplot == null){
            return false;
        }
        T sampleplotRaw = sampleplot.getValue();
        if (sampleplotRaw == null){
            return false;
        }
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            mSampleplotRepository.insertSampleplotEntity(sampleplotRaw.getEntity());
        }
        if (action.equals(Macro.ACTION_EDIT) || action.equals(Macro.ACTION_EDIT_RESTORE)){
            mSampleplotRepository.updateSampleplotEntity(sampleplotRaw.getEntity());
        }
        if (action.equals(Macro.ACTION_TEMP_SAVE) || action.equals(Macro.ACTION_TEMP_SAVE_RESTORE)){
            mSampleplotRepository.updateSampleplotEntity(sampleplotRaw.getEntity());
        }
        return true;
    }

    public boolean savePicture(String pictureId, String localAddr){
        PlotPictureEntity entity = new PlotPictureEntity();
        if (pictureId == null || localAddr == null){
            return false;
        }
        else {
            entity.ownerId = plotId;
            entity.pictureId = pictureId;
            entity.localAddr = localAddr;
            mPictureRepository.insertPlotPictureEntity(entity);
            return true;
        }
    }

}
