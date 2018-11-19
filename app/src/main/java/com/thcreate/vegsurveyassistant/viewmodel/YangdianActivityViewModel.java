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

import java.util.List;

public class YangdianActivityViewModel extends AndroidViewModel {

    private String mYangdianCode;

    public LiveData<User> user;

    public MutableLiveData<Yangdian> yangdian;

    private YangdianDataRepository repository;

    public YangdianActivityViewModel(@NonNull Application application, final String yangdianCode) {
        super(application);
        mYangdianCode = yangdianCode;

        this.yangdian = new MutableLiveData<>();
        this.yangdian.setValue(new Yangdian(1, mYangdianCode));

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
        private final String mYangdianCode;
        public Factory(@NonNull Application application, String yangdianCode) {
            mApplication = application;
            mYangdianCode = yangdianCode;
        }
        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new YangdianActivityViewModel(mApplication, mYangdianCode);
        }
    }
}
