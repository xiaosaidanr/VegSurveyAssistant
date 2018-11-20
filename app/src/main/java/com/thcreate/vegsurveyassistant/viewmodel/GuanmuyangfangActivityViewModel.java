package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuYangfang;
import com.thcreate.vegsurveyassistant.repository.YangfangDataRepository;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;

public class GuanmuyangfangActivityViewModel extends AndroidViewModel {

    private final String mYangdiCode;
    private final int mAction;
    private final String mYangfangCode;

    public LiveData<GuanmuYangfang> yangfang;

    private YangfangDataRepository repository;

    public GuanmuyangfangActivityViewModel(@NonNull Application application, int action, String yangdiCode, String yangfangCode) {
        super(application);
        mYangdiCode = yangdiCode;
        mAction = action;
        mYangfangCode = yangfangCode;

        repository = ((BasicApp)application).getYangfangDataRepository();

        initYangfang();
    }
    private void initYangfang(){
        switch (mAction){
            case Macro.ACTION_ADD:
                MutableLiveData<GuanmuYangfang> newData = new MutableLiveData<>();
                newData.setValue(new GuanmuYangfang(0, mYangdiCode, mYangfangCode));
                yangfang = newData;
                break;
            case Macro.ACTION_EDIT:
                yangfang = repository.getGuanmuYangfangByYangfangCode(mYangfangCode);
                break;
            default:
                break;
        }
    }



    public void save(){
        GuanmuYangfang yangfangRaw = yangfang.getValue();
        if (yangfangRaw != null){
            Date dateNow = new Date();
            yangfangRaw.modifyAt = dateNow;
            if (mAction == Macro.ACTION_ADD){
                yangfangRaw.createAt = dateNow;
                repository.insertGuanmuyf(yangfangRaw);
            }
            if (mAction == Macro.ACTION_EDIT){
                repository.updateGuanmuyf(yangfangRaw);
            }
        }
    }



    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        @NonNull
        private final Application mApplication;
        private final String mYangdiCode;
        private final int mAction;
        private final String mYangfangCode;
        public Factory(@NonNull Application application, int action, String yangdiCode, String yangfangCode) {
            mApplication = application;
            mYangdiCode = yangdiCode;
            mAction = action;
            mYangfangCode = yangfangCode;
        }
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new GuanmuyangfangActivityViewModel(mApplication, mAction, mYangdiCode, mYangfangCode);
        }
    }
}
