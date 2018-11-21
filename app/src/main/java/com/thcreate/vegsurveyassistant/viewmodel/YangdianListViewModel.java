package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.Yangdian;
import com.thcreate.vegsurveyassistant.repository.YangdianDataRepository;

import java.util.List;

public class YangdianListViewModel extends AndroidViewModel {

    private final YangdianDataRepository mRepository;

    private final MediatorLiveData<List<Yangdian>> mObservableYangdianList;

    public YangdianListViewModel(@NonNull Application application) {
        super(application);

        mObservableYangdianList = new MediatorLiveData<>();
        mObservableYangdianList.setValue(null);

        mRepository = ((BasicApp)application).getYangdianDataRepository();
        LiveData<List<Yangdian>> yangdianList = mRepository.loadAllYangdian();

        mObservableYangdianList.addSource(yangdianList, mObservableYangdianList::setValue);
    }



    public LiveData<List<Yangdian>> getYangdianList(){
        return mObservableYangdianList;
    }
}
