package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuWuzhong;
import com.thcreate.vegsurveyassistant.repository.WuzhongDataRepository;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;

public class QiaomuwuzhongActivityViewModel extends BaseWuzhongActivityViewModel<QiaomuWuzhong> {

    public QiaomuwuzhongActivityViewModel(@NonNull Application application) {
        super(application);
    }
    @Override
    public LiveData<QiaomuWuzhong> getWuzhongData() {
        return repository.getQiaomuWuzhongByWuzhongCode(wuzhongCode);
    }

//    private final String mYangfangCode;
//    private final int mAction;
//    private final String mWuzhongCode;
//
//    public LiveData<QiaomuWuzhong> wuzhong;
//
//    private WuzhongDataRepository repository;
//
//    public QiaomuwuzhongActivityViewModel(@NonNull Application application, int action, String yangfangCode, String wuzhongCode) {
//        super(application);
//        mYangfangCode = yangfangCode;
//        mAction = action;
//        mWuzhongCode = wuzhongCode;
//
//        repository = ((BasicApp)application).getWuzhongDataRepository();
//
//        initWuzhong();
//    }
//    private void initWuzhong(){
//        switch (mAction){
//            case Macro.ACTION_ADD:
//                MutableLiveData<QiaomuWuzhong> newData = new MutableLiveData<>();
//                newData.setValue(new QiaomuWuzhong(0, mYangfangCode, mWuzhongCode));
//                wuzhong = newData;
//                break;
//            case Macro.ACTION_EDIT:
//                wuzhong = repository.getQiaomuWuzhongByWuzhongCode(mWuzhongCode);
//                break;
//            default:
//                break;
//        }
//    }
//
//    public void save(){
//        QiaomuWuzhong wuzhongRaw = wuzhong.getValue();
//        if (wuzhongRaw != null){
//            Date dateNow = new Date();
//            wuzhongRaw.modifyAt = dateNow;
//            if (mAction == Macro.ACTION_ADD){
//                wuzhongRaw.createAt = dateNow;
//                repository.insertQiaomuwz(wuzhongRaw);
//            }
//            if (mAction == Macro.ACTION_EDIT){
//                repository.updateQiaomuwz(wuzhongRaw);
//            }
//        }
//    }
//
//
//
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
//            return (T) new QiaomuwuzhongActivityViewModel(mApplication, mAction, mYangfangCode, mWuzhongCode);
//        }
//    }
}
