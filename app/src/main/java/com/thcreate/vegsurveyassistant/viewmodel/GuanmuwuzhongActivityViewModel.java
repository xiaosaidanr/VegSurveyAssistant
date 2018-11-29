package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuWuzhong;
import com.thcreate.vegsurveyassistant.repository.WuzhongDataRepository;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;

public class GuanmuwuzhongActivityViewModel extends BaseWuzhongActivityViewModel<GuanmuWuzhong> {

//    private final String mYangfangCode;
//    private final int mAction;
//    private final String mWuzhongCode;
//
//    public LiveData<GuanmuWuzhong> wuzhong;
//
//    private WuzhongDataRepository repository;

//    public GuanmuwuzhongActivityViewModel(@NonNull Application application, int action, String yangfangCode, String wuzhongCode) {
    public GuanmuwuzhongActivityViewModel(@NonNull Application application) {
        super(application);
//        mYangfangCode = yangfangCode;
//        mAction = action;
//        mWuzhongCode = wuzhongCode;
//
//        repository = ((BasicApp)application).getWuzhongDataRepository();
//
//        initWuzhong();
    }
//    private void initWuzhong(){
//        switch (mAction){
//            case Macro.ACTION_ADD:
//                MutableLiveData<GuanmuWuzhong> newData = new MutableLiveData<>();
//                newData.setValue(new GuanmuWuzhong(0, mYangfangCode, mWuzhongCode));
//                wuzhong = newData;
//                break;
//            case Macro.ACTION_EDIT:
//                wuzhong = repository.getGuanmuWuzhongByWuzhongCode(mWuzhongCode);
//                break;
//            default:
//                break;
//        }
//    }


    @Override
    public void getWuzhongDataFromDatabase() {
        wuzhong = repository.getGuanmuWuzhongByWuzhongCode(mWuzhongCode);
    }

//    public void save(){
//        GuanmuWuzhong wuzhongRaw = wuzhong.getValue();
//        if (wuzhongRaw != null){
//            Date dateNow = new Date();
//            wuzhongRaw.modifyAt = dateNow;
//            if (mAction == Macro.ACTION_ADD){
//                wuzhongRaw.createAt = dateNow;
//                repository.insertGuanmuwz(wuzhongRaw);
//            }
//            if (mAction == Macro.ACTION_EDIT){
//                repository.updateGuanmuwz(wuzhongRaw);
//            }
//        }
//    }



//    public static class Factory extends ViewModelProvider.NewInstanceFactory{
//        @NonNull
//        private final Application mApplication;
//        private final String mYangfangCode;
//        private final int mAction;
//        private final String mWuzhongCode;
//        public Factory(@NonNull Application application, int action, String yangfangCode, String wuzhongCode) {
//            mApplication = application;
//            mYangfangCode = yangfangCode;
//            mAction = action;
//            mWuzhongCode = wuzhongCode;
//        }
//        @Override
//        public <T extends ViewModel> T create(Class<T> modelClass) {
//            //noinspection unchecked
//            return (T) new GuanmuwuzhongActivityViewModel(mApplication, mAction, mYangfangCode, mWuzhongCode);
//        }
//    }
}
