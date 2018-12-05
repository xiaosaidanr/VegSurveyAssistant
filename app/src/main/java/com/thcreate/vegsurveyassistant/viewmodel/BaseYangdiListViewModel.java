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

public abstract class BaseYangdiListViewModel extends AndroidViewModel {

    final YangdiDataRepository yangdiRepository;

    MediatorLiveData<List<Yangdi>> observableYangdiList;

    public BaseYangdiListViewModel(@NonNull Application application) {
        super(application);
        yangdiRepository = ((BasicApp)application).getYangdiDataRepository();
        initYangdiList();
    }

    abstract void initYangdiList();

    public LiveData<List<Yangdi>> getYangdiList(){
        return observableYangdiList;
    }

    public void deleteYangdiById(int id){
        yangdiRepository.deleteYangdiById(id);
    }

}
