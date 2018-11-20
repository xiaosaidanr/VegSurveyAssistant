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
import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;
import com.thcreate.vegsurveyassistant.repository.YangfangDataRepository;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;

public class CaobenyangfangActivityViewModel extends AndroidViewModel {

    private final int mAction;
    private final String mYangdiCode;
    private final String mYangfangCode;

    public LiveData<CaobenYangfang> yangfang;

    private YangfangDataRepository repository;

    public CaobenyangfangActivityViewModel(@NonNull Application application, int action, String yangdiCode, String yangfangCode) {
        super(application);
        mAction = action;
        mYangdiCode = yangdiCode;
        mYangfangCode = yangfangCode;

        repository = ((BasicApp)application).getYangfangDataRepository();

        initYangfang();
    }
    private void initYangfang(){
        switch (mAction){
            case Macro.ACTION_ADD:
                MutableLiveData<CaobenYangfang> newData = new MutableLiveData<>();
                newData.setValue(new CaobenYangfang(0, mYangdiCode, mYangfangCode));
                yangfang = newData;
                break;
            case Macro.ACTION_EDIT:
                yangfang = repository.getCaobenYangfangByYangfangCode(mYangfangCode);
                break;
            default:
                break;
        }
    }



    public void save(){
        CaobenYangfang yangfangRaw = yangfang.getValue();
        if (yangfangRaw != null){
            Date dateNow = new Date();
            yangfangRaw.modifyAt = dateNow;
            if (mAction == Macro.ACTION_ADD){
                yangfangRaw.createAt = dateNow;
                repository.insertCaobenyf(yangfangRaw);
            }
            if (mAction == Macro.ACTION_EDIT){
                repository.updateCaobenyf(yangfangRaw);
            }
        }
    }



    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        @NonNull
        private final Application mApplication;
        private final int mAction;
        private final String mYangdiCode;
        private final String mYangfangCode;
        public Factory(@NonNull Application application, int action, String yangdiCode, String yangfangCode) {
            mApplication = application;
            mAction = action;
            mYangdiCode = yangdiCode;
            mYangfangCode = yangfangCode;
        }
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CaobenyangfangActivityViewModel(mApplication, mAction, mYangdiCode, mYangfangCode);
        }
    }
}
