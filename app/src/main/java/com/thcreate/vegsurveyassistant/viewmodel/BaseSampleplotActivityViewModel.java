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
import com.thcreate.vegsurveyassistant.db.entity.PictureEntity;
import com.thcreate.vegsurveyassistant.db.entity.PlotPictureEntity;
import com.thcreate.vegsurveyassistant.db.entity.PlotPlotEntity;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.BaseSampleplot;
import com.thcreate.vegsurveyassistant.repository.PictureRepository;
import com.thcreate.vegsurveyassistant.repository.SpeciesRepository;
import com.thcreate.vegsurveyassistant.repository.SampleplotRepository;
import com.thcreate.vegsurveyassistant.service.LocationLiveData;
import com.thcreate.vegsurveyassistant.service.SessionManager;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.lang.reflect.ParameterizedType;
import java.util.Date;
import java.util.List;

abstract public class BaseSampleplotActivityViewModel<T extends BaseSampleplot> extends AndroidViewModel {

    abstract public T getPlotData(SampleplotEntity entity);

    abstract public String getPlotType();

    public String generateSpeciesId(){
        return IdGenerator.getId(SessionManager.getLoggedInUserId());
    }

    private static final String SAMPLEPLOT_DATA = "sampleplot_data";

    public String landId;
    public MutableLiveData<String> landType;
    public String action;
    public String plotId;

    public LiveData<T> sampleplot;

    public String parentPlotId;
    public String parentPlotType;

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
        locationLiveData = new LocationLiveData(application);
    }

    public void init(Bundle data){
        landId = data.getString(Macro.SAMPLELAND_ID);
        landType = new MutableLiveData<>();
        landType.setValue(data.getString(Macro.SAMPLELAND_TYPE));
        action = data.getString(Macro.ACTION);
        plotId = data.getString(Macro.SAMPLEPLOT_ID);
        if (!(action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_EDIT))){
            parentPlotId = data.getString(Macro.PARENT_PLOT_ID);
            parentPlotType = data.getString(Macro.PARENT_PLOT_TYPE);
        }
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

    public List<PlotPlotEntity> getPlotPlotEntityListByLandIdSync(String landId){
        return mSampleplotRepository.getPlotPlotEntityListByLandIdSync(landId);
    }

    public PlotPlotEntity getPlotPlotEntityByChildIdSync(String childId){
        return mSampleplotRepository.getPlotPlotEntityByChildIdSync(childId);
    }

    public void deleteSpeciesEntityById(int id){
        mSpeciesRepository.softDeleteSpeciesEntityById(id);
    }

    public void deletePlotPictureEntityById(int id){
        mPictureRepository.softDeletePlotPictureEntityById(id);
    }

    public void onGoForward(){
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            SampleplotEntity tempSaveData = sampleplot.getValue().getEntity();
            tempSaveData.deleteAt = new Date();
            mSampleplotRepository.insertSampleplotEntity(tempSaveData);
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

        outState.putString(Macro.PARENT_PLOT_ID, parentPlotId);
        outState.putString(Macro.PARENT_PLOT_TYPE, parentPlotType);

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

    public String save(){
        if (sampleplot == null){
            return getApplication().getString(R.string.plot_save_error);
        }
        T sampleplotRaw = sampleplot.getValue();
        if (sampleplotRaw == null){
            return getApplication().getString(R.string.plot_save_error);
        }
        if (sampleplotRaw.code == null){
            return getApplication().getString(R.string.please_fill_plot_code);
        }
        PlotPlotEntity plotPlotEntity = new PlotPlotEntity(landId, parentPlotId, parentPlotType, plotId, getPlotType());
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            mSampleplotRepository.insertSampleplotEntity(sampleplotRaw.getEntity());
            mSampleplotRepository.insertPlotPlotEntity(plotPlotEntity);
        }
        if (action.equals(Macro.ACTION_EDIT) || action.equals(Macro.ACTION_EDIT_RESTORE)){
            mSampleplotRepository.updateSampleplotEntity(sampleplotRaw.getEntity());
            mSampleplotRepository.updatePlotPlotEntity(plotPlotEntity);
        }
        if (action.equals(Macro.ACTION_TEMP_SAVE) || action.equals(Macro.ACTION_TEMP_SAVE_RESTORE)){
            mSampleplotRepository.updateSampleplotEntity(sampleplotRaw.getEntity());
            mSampleplotRepository.insertPlotPlotEntity(plotPlotEntity);
        }
        return null;
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
