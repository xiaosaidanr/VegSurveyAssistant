package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.LandMainInfo;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.PointMainInfo;
import com.thcreate.vegsurveyassistant.repository.SamplelandRepository;
import com.thcreate.vegsurveyassistant.repository.SamplepointRepository;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.ArrayList;
import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {

    private SamplelandRepository mLandRepository;
    private SamplepointRepository mPointRepository;

    MediatorLiveData<List<LandMainInfo>> mObservableLandList;
    MediatorLiveData<List<PointMainInfo>> mObservablePointList;

    MediatorLiveData<List<LandMainInfo>> mObservableGrassLandList;
    MediatorLiveData<List<LandMainInfo>> mObservableBushLandList;
    MediatorLiveData<List<LandMainInfo>> mObservableTreeLandList;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mLandRepository = ((BasicApp)application).getSamplelandRepository();
        mPointRepository = ((BasicApp)application).getSamplepointRepository();
        init();
    }

    private void init(){

        mObservableLandList = new MediatorLiveData<>();
        mObservableLandList.setValue(null);
        mObservableLandList.addSource(mLandRepository.loadAllSamplelandEntity(), mObservableLandList::setValue);

        mObservablePointList = new MediatorLiveData<>();
        mObservablePointList.setValue(null);
        mObservablePointList.addSource(mPointRepository.loadAllSamplepoint(), mObservablePointList::setValue);

        mObservableGrassLandList = new MediatorLiveData<>();
        mObservableGrassLandList.setValue(null);
        mObservableGrassLandList.addSource(mObservableLandList, (dataList)->{
            ArrayList<LandMainInfo> tmp = new ArrayList<>();
            for (LandMainInfo item:
                    dataList) {
                if (item.type.equals(Macro.SAMPLELAND_TYPE_GRASS)){
                    tmp.add(item);
                }
            }
            mObservableGrassLandList.setValue(tmp);
        });

        mObservableBushLandList = new MediatorLiveData<>();
        mObservableBushLandList.setValue(null);
        mObservableBushLandList.addSource(mObservableLandList, (dataList)->{
            ArrayList<LandMainInfo> tmp = new ArrayList<>();
            for (LandMainInfo item:
                    dataList) {
                if (item.type.equals(Macro.SAMPLELAND_TYPE_BUSH)){
                    tmp.add(item);
                }
            }
            mObservableBushLandList.setValue(tmp);
        });

        mObservableTreeLandList = new MediatorLiveData<>();
        mObservableTreeLandList.setValue(null);
        mObservableTreeLandList.addSource(mObservableLandList, (dataList)->{
            ArrayList<LandMainInfo> tmp = new ArrayList<>();
            for (LandMainInfo item:
                    dataList) {
                if (item.type.equals(Macro.SAMPLELAND_TYPE_TREE)){
                    tmp.add(item);
                }
            }
            mObservableTreeLandList.setValue(tmp);
        });

    }

    public LiveData<List<LandMainInfo>> getLandList(){
        return mObservableLandList;
    }

    public LiveData<List<LandMainInfo>> getGrassLandList(){
        return mObservableGrassLandList;
    }

    public LiveData<List<LandMainInfo>> getBushLandList(){
        return mObservableBushLandList;
    }

    public LiveData<List<LandMainInfo>> getTreeLandList(){
        return mObservableTreeLandList;
    }

    public LiveData<List<PointMainInfo>> getPointList(){
        return mObservablePointList;
    }

    public void softDeletePointById(int id){
        mPointRepository.softDeleteSamplepointById(id);
    }

    public void softDeleteLandById(int id){
        mLandRepository.softDeleteSamplelandEntityById(id);
    }

}
