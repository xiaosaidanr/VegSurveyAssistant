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
import com.thcreate.vegsurveyassistant.util.Macro;

public class CaobenyangfangActivityViewModel extends AndroidViewModel {

    private final int mAction;
    private final String mYangdiCode;
    private final String mYangfangCode;

    public MutableLiveData<CaobenYangfang> yangfang;

    public CaobenyangfangActivityViewModel(@NonNull Application application, int action, String yangdiCode, String yangfangCode) {
        super(application);
        mAction = action;
        mYangdiCode = yangdiCode;
        mYangfangCode = yangfangCode;

        if (action == Macro.ACTION_ADD){
            yangfang = new MutableLiveData<>();
            yangfang.setValue(new CaobenYangfang(1, mYangdiCode, mYangfangCode));
        }
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
