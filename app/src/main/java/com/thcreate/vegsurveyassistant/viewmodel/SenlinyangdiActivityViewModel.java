package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.thcreate.vegsurveyassistant.db.entity.Yangdi;

public class SenlinyangdiActivityViewModel extends AndroidViewModel {

    private String mSenlinyangdiCode;

    public MutableLiveData<Yangdi> yangdi;

    public SenlinyangdiActivityViewModel(@NonNull Application application, String senlinyangdiCode) {
        super(application);
        mSenlinyangdiCode = senlinyangdiCode;
        yangdi = new MutableLiveData<>();
        yangdi.setValue(new Yangdi(0, "testtesttest", "bush"));
    }

    public void OnSave(View v){
        if (yangdi.getValue().powei == null){
            Log.e("testtesttest", "null");
        }
        else {
            Log.e("testtesttest", yangdi.getValue().powei + String.valueOf(yangdi.getValue().powei.isEmpty()));
        }
    }



    public static class Factory extends ViewModelProvider.NewInstanceFactory {
        @NonNull
        private final Application mApplication;
        private final String mSenlinyangdiCode;
        public Factory(@NonNull Application application, String senlinyangdiCode) {
            mApplication = application;
            mSenlinyangdiCode = senlinyangdiCode;
        }
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new SenlinyangdiActivityViewModel(mApplication, mSenlinyangdiCode);
        }
    }

}
