package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;

public class CaobenyangfangActivityViewModel extends AndroidViewModel {

    private final String mYangfangCode;

    public MutableLiveData<CaobenYangfang> yangfang;

    public Boolean hasBelongQiaomuyangfang;

    public Boolean hasBelongGuanmuyangfang;

    public CaobenyangfangActivityViewModel(@NonNull Application application, String yangfangCode) {
        super(application);
        mYangfangCode = yangfangCode;

        yangfang = new MutableLiveData<>();
        yangfang.setValue(new CaobenYangfang(1, "testtesttest", "testtesttest"));

        hasBelongQiaomuyangfang = false;

        hasBelongGuanmuyangfang = false;
    }

    public void OnSave(View v){
        if (yangfang.getValue().yangfangCode == null){
            Log.e("testtesttest", "null");
        }
        else {
            Log.e("testtesttest", yangfang.getValue().yangfangCode + String.valueOf(yangfang.getValue().yangfangCode.isEmpty()));
        }
    }



    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        @NonNull
        private final Application mApplication;
        private final String mYangfangCode;
        public Factory(@NonNull Application application, String yangfangCode) {
            mApplication = application;
            mYangfangCode = yangfangCode;
        }
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CaobenyangfangActivityViewModel(mApplication, mYangfangCode);
        }
    }
}
