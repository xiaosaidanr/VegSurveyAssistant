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

//    public LiveData<User> user;

    public LiveData<Yangdian> yangdian;

    private YangdianDataRepository repository;

//    public YangdianActivityViewModel(@NonNull Application application, final int action, final String yangdianCode) {
    public YangdianActivityViewModel(@NonNull Application application) {

        super(application);
//        mAction = action;
//        mYangdianCode = yangdianCode;

        repository = ((BasicApp)application).getYangdianDataRepository();
//        user = repository.getCurrentUser();

//        mAction = Macro.ACTION_EDIT;

//        initYangdian();

    }

    public void initYangdian(int action, String yangdianCode, Yangdian restoredData){
        mAction = action;
        mYangdianCode = yangdianCode;

        switch (mAction){
            case Macro.ACTION_ADD:
                MutableLiveData<Yangdian> tmp1 = new MutableLiveData<>();
                //TODO userid1
                tmp1.setValue(new Yangdian(1, mYangdianCode));
                yangdian = tmp1;
                break;
            case Macro.ACTION_ADD_RESTORE:
                MutableLiveData<Yangdian> tmp2 = new MutableLiveData<>();
                tmp2.setValue(restoredData);
                yangdian = tmp2;
                break;
            case Macro.ACTION_EDIT:
                yangdian = repository.getYangdianByYangdianCode(mYangdianCode);
                break;
            case Macro.ACTION_EDIT_RESTORE:
                MutableLiveData<Yangdian> tmp3 = new MutableLiveData<>();
                tmp3.setValue(restoredData);
                yangdian = tmp3;
                break;
            default:
                break;
        }
    }

    public boolean save(){
        if (yangdian == null){
            return false;
        }
        Yangdian yangdianRaw = yangdian.getValue();
        if (yangdianRaw == null){
            return false;
        }
        Date dateNow = new Date();
        yangdianRaw.modifyAt = dateNow;
        if (mAction == Macro.ACTION_ADD || mAction == Macro.ACTION_ADD_RESTORE){
            yangdianRaw.createAt = dateNow;
            repository.insertYangdian(yangdianRaw);
        }
        if (mAction == Macro.ACTION_EDIT || mAction == Macro.ACTION_EDIT_RESTORE){
            repository.updateYangdian(yangdianRaw);
        }
        return true;
    }



//    public static class Factory extends ViewModelProvider.NewInstanceFactory {
//        @NonNull
//        private final Application mApplication;
//        private final int mAction;
//        private final String mYangdianCode;
//        public Factory(@NonNull Application application, int action, String yangdianCode) {
//            mApplication = application;
//            mAction = action;
//            mYangdianCode = yangdianCode;
//        }
//        @Override
//        public <T extends ViewModel> T create(Class<T> modelClass) {
//            //noinspection unchecked
//            return (T) new YangdianActivityViewModel(mApplication, mAction, mYangdianCode);
//        }
//    }
}
