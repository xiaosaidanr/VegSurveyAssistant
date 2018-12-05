package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.Yangdi;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.List;

public class SenlinyangdiListViewModel extends BaseYangdiListViewModel {

    public SenlinyangdiListViewModel(@NonNull Application application) {
        super(application);
    }

    void initYangdiList(){
        observableYangdiList = new MediatorLiveData<>();
        observableYangdiList.setValue(null);
        LiveData<List<Yangdi>> yangdiList = yangdiRepository.loadAllYangdiByType(Macro.YANGDI_TYPE_TREE);
        observableYangdiList.addSource(yangdiList, observableYangdiList::setValue);
    }

}
