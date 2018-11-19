package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.thcreate.vegsurveyassistant.db.entity.CaobenWuzhong;
import com.thcreate.vegsurveyassistant.util.Macro;

public class CaobenwuzhongActivityViewModel extends AndroidViewModel {

    private final String mYangfangCode;
    private final int mAction;
    private final String mWuzhongCode;

    public MutableLiveData<CaobenWuzhong> wuzhong;

    public CaobenwuzhongActivityViewModel(@NonNull Application application, int action, String yangfangCode, String wuzhongCode) {
        super(application);
        mYangfangCode = yangfangCode;
        mAction = action;
        mWuzhongCode = wuzhongCode;
        if (action == Macro.ACTION_ADD){
            wuzhong = new MutableLiveData<>();
            wuzhong.setValue(new CaobenWuzhong(1, mYangfangCode, mWuzhongCode));
        }
    }

    public void OnSave(View v){
        if (wuzhong.getValue().wuzhongCode == null){
            Log.e("testtesttest", "null");
        }
        else {
            Log.e("testtesttest", wuzhong.getValue().wuzhongCode + String.valueOf(wuzhong.getValue().wuzhongCode.isEmpty()));
        }
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        @NonNull
        private final Application mApplication;
        private final String mYangfangCode;
        private final int mAction;
        private final String mWuzhongCode;
        public Factory(@NonNull Application application, int action, String yangfangCode, String wuzhongCode) {
            mApplication = application;
            mYangfangCode = yangfangCode;
            mAction = action;
            mWuzhongCode = wuzhongCode;
        }
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new CaobenwuzhongActivityViewModel(mApplication, mAction, mYangfangCode, mWuzhongCode);
        }
    }
}
