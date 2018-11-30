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
import com.thcreate.vegsurveyassistant.db.dao.QiaomuYangfangDao;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuYangfang;
import com.thcreate.vegsurveyassistant.repository.WuzhongDataRepository;
import com.thcreate.vegsurveyassistant.repository.YangfangDataRepository;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;
import java.util.List;

public class QiaomuyangfangActivityViewModel extends BaseYangfangActivityViewModel<QiaomuYangfang> {

    public QiaomuyangfangActivityViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<QiaomuYangfang> getYangfangData() {
        return mYangfangRepository.getQiaomuYangfangByYangfangCode(yangfangCode);
    }

    public LiveData<List<QiaomuWuzhong>> getWuzhongList(){
        return mWuzhongRepository.getAllQiaomuWuzhongByYangfangCode(yangfangCode);
    }

    //    private final String mYangdiCode;
//    private final int mAction;
//    private final String mYangfangCode;
//
//    public LiveData<QiaomuYangfang> yangfang;
//
//    private YangfangDataRepository mYangfangRepository;
//    private WuzhongDataRepository mWuzhongRepository;
//
//    public QiaomuyangfangActivityViewModel(@NonNull Application application, int action, String yangdiCode, String yangfangCode) {
//        super(application);
//        mYangdiCode = yangdiCode;
//        mAction = action;
//        mYangfangCode = yangfangCode;
//
//        mYangfangRepository = ((BasicApp)application).getYangfangDataRepository();
//        mWuzhongRepository = ((BasicApp)application).getWuzhongDataRepository();
//
//        initYangfang();
//    }
//    private void initYangfang(){
//        switch (mAction){
//            case Macro.ACTION_ADD:
//                MutableLiveData<QiaomuYangfang> newData = new MutableLiveData<>();
//                newData.setValue(new QiaomuYangfang(0, mYangdiCode, mYangfangCode));
//                yangfang = newData;
//                break;
//            case Macro.ACTION_EDIT:
//                yangfang = mYangfangRepository.getQiaomuYangfangByYangfangCode(mYangfangCode);
//                break;
//            default:
//                break;
//        }
//    }
//
//
//
//    public LiveData<List<QiaomuWuzhong>> getQiaomuwuzhongList(){
//        return mWuzhongRepository.getAllQiaomuWuzhongByYangfangCode(mYangfangCode);
//    }
//
//
//
//    public void save(){
//        QiaomuYangfang yangfangRaw = yangfang.getValue();
//        if (yangfangRaw != null){
//            Date dateNow = new Date();
//            yangfangRaw.modifyAt = dateNow;
//            if (mAction == Macro.ACTION_ADD){
//                yangfangRaw.createAt = dateNow;
//                mYangfangRepository.insertQiaomuyf(yangfangRaw);
//            }
//            if (mAction == Macro.ACTION_EDIT){
//                mYangfangRepository.updateQiaomuyf(yangfangRaw);
//            }
//        }
//    }
//
//
//
//    public static class Factory extends ViewModelProvider.NewInstanceFactory{
//        @NonNull
//        private final Application mApplication;
//        private final String mYangdiCode;
//        private final int mAction;
//        private final String mYangfangCode;
//        public Factory(@NonNull Application application, int action, String yangdiCode, String yangfangCode) {
//            mApplication = application;
//            mYangdiCode = yangdiCode;
//            mAction = action;
//            mYangfangCode = yangfangCode;
//        }
//        @Override
//        public <T extends ViewModel> T create(Class<T> modelClass) {
//            //noinspection unchecked
//            return (T) new QiaomuyangfangActivityViewModel(mApplication, mAction, mYangdiCode, mYangfangCode);
//        }
//    }
}
