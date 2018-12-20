package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.SamplepointEntity;
import com.thcreate.vegsurveyassistant.repository.SamplepointRepository;

import java.util.List;

public class SamplepointListViewModel extends AndroidViewModel {

    private final SamplepointRepository mRepository;

    private final MediatorLiveData<List<SamplepointEntity>> mObservableSamplepointList;

    public SamplepointListViewModel(@NonNull Application application) {
        super(application);

        mObservableSamplepointList = new MediatorLiveData<>();
        mObservableSamplepointList.setValue(null);

        mRepository = ((BasicApp)application).getSamplepointRepository();
        LiveData<List<SamplepointEntity>> samplepointEntityList = mRepository.loadAllSamplepoint();

        mObservableSamplepointList.addSource(samplepointEntityList, mObservableSamplepointList::setValue);
    }



    public LiveData<List<SamplepointEntity>> getSamplepointList(){
        return mObservableSamplepointList;
    }

    public void deleteSamplepointById(int id){
        mRepository.deleteSamplepointById(id);
    }
}