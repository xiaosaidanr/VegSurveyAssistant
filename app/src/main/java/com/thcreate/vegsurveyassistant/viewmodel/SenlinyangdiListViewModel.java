package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.Yangdi;
import com.thcreate.vegsurveyassistant.repository.YangdiDataRepository;

import java.util.List;

public class SenlinyangdiListViewModel extends AndroidViewModel {

    private final YangdiDataRepository mRepository;

    private final MediatorLiveData<List<Yangdi>> mObservableYangdiList;

    public SenlinyangdiListViewModel(@NonNull Application application) {
        super(application);

        mObservableYangdiList = new MediatorLiveData<>();
        mObservableYangdiList.setValue(null);

        mRepository = ((BasicApp)application).getYangdiDataRepository();
        LiveData<List<Yangdi>> yangdiList = mRepository.loadAllYangdiByType("tree");

        mObservableYangdiList.addSource(yangdiList, mObservableYangdiList::setValue);
    }



    public LiveData<List<Yangdi>> getYangdiList(){
        return mObservableYangdiList;
    }

}
