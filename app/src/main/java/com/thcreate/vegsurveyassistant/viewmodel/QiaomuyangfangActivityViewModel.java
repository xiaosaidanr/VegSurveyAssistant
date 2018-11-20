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
import com.thcreate.vegsurveyassistant.db.entity.QiaomuYangfang;
import com.thcreate.vegsurveyassistant.repository.YangfangDataRepository;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;

public class QiaomuyangfangActivityViewModel extends AndroidViewModel {

    private final String mYangdiCode;
    private final int mAction;
    private final String mYangfangCode;

    public LiveData<QiaomuYangfang> yangfang;

    private YangfangDataRepository repository;

    public QiaomuyangfangActivityViewModel(@NonNull Application application, int action, String yangdiCode, String yangfangCode) {
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
                MutableLiveData<QiaomuYangfang> newData = new MutableLiveData<>();
                newData.setValue(new QiaomuYangfang(0, mYangdiCode, mYangfangCode));
                yangfang = newData;
                break;
            case Macro.ACTION_EDIT:
                yangfang = repository.getQiaomuYangfangByYangfangCode(mYangfangCode);
                break;
            default:
                break;
        }
    }



    public void save(){
        QiaomuYangfang yangfangRaw = yangfang.getValue();
        if (yangfangRaw != null){
            Date dateNow = new Date();
            yangfangRaw.modifyAt = dateNow;
            if (mAction == Macro.ACTION_ADD){
                yangfangRaw.createAt = dateNow;
                repository.insertQiaomuyf(yangfangRaw);
            }
            if (mAction == Macro.ACTION_EDIT){
                repository.updateQiaomuyf(yangfangRaw);
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
            return (T) new QiaomuyangfangActivityViewModel(mApplication, mAction, mYangdiCode, mYangfangCode);
        }
    }
}
