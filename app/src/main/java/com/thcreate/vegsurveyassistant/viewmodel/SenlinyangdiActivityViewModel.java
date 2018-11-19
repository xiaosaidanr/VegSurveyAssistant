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
import com.thcreate.vegsurveyassistant.util.Macro;

public class SenlinyangdiActivityViewModel extends AndroidViewModel {

    private int mAction;
    private String mSenlinyangdiCode;

    public MutableLiveData<Yangdi> yangdi;

    public SenlinyangdiActivityViewModel(@NonNull Application application, int action, String senlinyangdiCode) {
        super(application);
        mAction = action;
        mSenlinyangdiCode = senlinyangdiCode;
        if (mAction == Macro.ACTION_ADD){
            yangdi = new MutableLiveData<>();
            yangdi.setValue(new Yangdi(1, mSenlinyangdiCode, "tree"));
        }
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
