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
import com.thcreate.vegsurveyassistant.db.entity.Yangdi;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.List;

public class CaodiyangdiActivityViewModel extends AndroidViewModel {

    private int mAction;
    private String mCaodiyangdiCode;

    public MutableLiveData<Yangdi> yangdi;

    public MutableLiveData<List<CaobenYangfang>> caobenyangfangList;

    public CaodiyangdiActivityViewModel(@NonNull Application application, int action, String caodiyangdiCode) {
        super(application);
        mAction = action;
        mCaodiyangdiCode = caodiyangdiCode;

        if (action == Macro.ACTION_ADD){
            yangdi = new MutableLiveData<>();
            yangdi.setValue(new Yangdi(1, mCaodiyangdiCode, "grass"){
            });
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
        private final String mCaodiyangdiCode;
        public Factory(@NonNull Application application, int action, String caodiyangdiCode) {
            mApplication = application;
            mAction = action;
            mCaodiyangdiCode = caodiyangdiCode;
        }
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CaodiyangdiActivityViewModel(mApplication, mAction, mCaodiyangdiCode);
        }
    }
}
