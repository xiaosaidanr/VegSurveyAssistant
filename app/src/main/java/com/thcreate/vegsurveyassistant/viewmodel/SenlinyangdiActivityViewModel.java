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
import com.thcreate.vegsurveyassistant.db.entity.Yangdi;
import com.thcreate.vegsurveyassistant.repository.YangdiDataRepository;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;

public class SenlinyangdiActivityViewModel extends AndroidViewModel {

    private int mAction;
    private String mSenlinyangdiCode;

    public LiveData<Yangdi> yangdi;

    private YangdiDataRepository repository;

    public SenlinyangdiActivityViewModel(@NonNull Application application, int action, String senlinyangdiCode) {
        super(application);
        mAction = action;
        mSenlinyangdiCode = senlinyangdiCode;

        repository = ((BasicApp)application).getYangdiDataRepository();

        initYangdi();
    }
    private void initYangdi(){
        switch (mAction){
            case Macro.ACTION_ADD:
                MutableLiveData<Yangdi> newData = new MutableLiveData<>();
                newData.setValue(new Yangdi(0, mSenlinyangdiCode, "tree"));
                yangdi = newData;
                break;
            case Macro.ACTION_EDIT:
                yangdi = repository.getYangdiByYangdiCode(mSenlinyangdiCode);
                break;
            default:
                break;
        }
    }



    public void save(){
        Yangdi yangdiRaw = yangdi.getValue();
        if (yangdiRaw != null){
            Date dateNow = new Date();
            yangdiRaw.modifyAt = dateNow;
            if (mAction == Macro.ACTION_ADD){
                yangdiRaw.createAt = dateNow;
                repository.insertYangdi(yangdiRaw);
            }
            if (mAction == Macro.ACTION_EDIT){
                repository.updateYangdi(yangdiRaw);
            }
        }
    }



    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application mApplication;
        private final int mAction;
        private final String mSenlinyangdiCode;
        public Factory(@NonNull Application application, int action, String senlinyangdiCode) {
            mApplication = application;
            mAction = action;
            mSenlinyangdiCode = senlinyangdiCode;
        }
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new SenlinyangdiActivityViewModel(mApplication, mAction, mSenlinyangdiCode);
        }
    }

}
