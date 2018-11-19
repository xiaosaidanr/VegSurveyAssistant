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

import java.util.List;

public class YangdianActivityViewModel extends AndroidViewModel {

    private String mYangdianCode;
    private int mAction;

    public LiveData<User> user;

    public MutableLiveData<Yangdian> yangdian;

    private YangdianDataRepository repository;

    public YangdianActivityViewModel(@NonNull Application application, final int action, final String yangdianCode) {
        super(application);
        mAction = action;
        mYangdianCode = yangdianCode;

        if (mAction == Macro.ACTION_ADD){
            this.yangdian = new MutableLiveData<>();
            this.yangdian.setValue(new Yangdian(1, mYangdianCode));
        }

        repository = ((BasicApp)application).getYangdianDataRepository();
        user = repository.getCurrentUser();
    }

    public void OnSave(View v){
        Log.d("testtesttest", yangdian.getValue().yangdianCode);
        if (yangdian.getValue().investigateDate == null){
            Log.d("testtesttest", "null");
        }
        else {
            Log.d("testtesttest", yangdian.getValue().investigateDate + String.valueOf(yangdian.getValue().investigateDate.isEmpty()));
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
