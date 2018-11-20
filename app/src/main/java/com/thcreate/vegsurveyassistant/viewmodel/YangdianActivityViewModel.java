package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.databinding.Bindable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.User;
import com.thcreate.vegsurveyassistant.db.entity.Yangdian;
import com.thcreate.vegsurveyassistant.repository.YangdianDataRepository;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;
import java.util.List;

public class YangdianActivityViewModel extends AndroidViewModel {

    private String mYangdianCode;
    private int mAction;

    public LiveData<User> user;

    public LiveData<Yangdian> yangdian;

    private YangdianDataRepository repository;

    public YangdianActivityViewModel(@NonNull Application application, final int action, final String yangdianCode) {
        super(application);
        mAction = action;
        mYangdianCode = yangdianCode;

        repository = ((BasicApp)application).getYangdianDataRepository();
        user = repository.getCurrentUser();

//        mAction = Macro.ACTION_EDIT;

        initYangdian();

    }

    private void initYangdian(){
        switch (mAction){
            case Macro.ACTION_ADD:
                MutableLiveData<Yangdian> newData = new MutableLiveData<>();
                newData.setValue(new Yangdian(0, mYangdianCode));
                yangdian = newData;
                break;
            case Macro.ACTION_EDIT:
                yangdian = repository.getYangdianByYangdianCode(mYangdianCode);
                break;
            default:
                break;
        }
    }

    public void Save(){
        Yangdian yangdianRaw = yangdian.getValue();
        if (yangdianRaw != null){
            Date dateNow = new Date();
            yangdianRaw.modifyAt = dateNow;
            if (mAction == Macro.ACTION_ADD){
                yangdianRaw.createAt = dateNow;
                repository.insertYangdian(yangdianRaw);
            }
            if (mAction == Macro.ACTION_EDIT){
                repository.updateYangdian(yangdianRaw);
            }
        }
    }



    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application mApplication;
        private final int mAction;
        private final String mYangdianCode;
        public Factory(@NonNull Application application, int action, String yangdianCode) {
            mApplication = application;
            mAction = action;
            mYangdianCode = yangdianCode;
        }
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new YangdianActivityViewModel(mApplication, mAction, mYangdianCode);
        }
    }
}
