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

public class CaodiyangdiActivityViewModel extends AndroidViewModel {

    private String mCaodiyangdiCode;

    public MutableLiveData<Yangdi> yangdi;

    public CaodiyangdiActivityViewModel(@NonNull Application application, String caodiyangdiCode) {
        super(application);
        mCaodiyangdiCode = caodiyangdiCode;

        yangdi = new MutableLiveData<>();
        yangdi.setValue(new Yangdi(0, "testtesttest", "grass"){
        });
//        yangdi.getValue().powei = "下坡位";
    }

    public void OnSave(View v){
//        yangdi.getValue().powei = "上坡位";
//        yangdi.notify();
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
        private final String mCaodiyangdiCode;
        public Factory(@NonNull Application application, String caodiyangdiCode) {
            mApplication = application;
            mCaodiyangdiCode = caodiyangdiCode;
        }
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CaodiyangdiActivityViewModel(mApplication, mCaodiyangdiCode);
        }
    }
}
