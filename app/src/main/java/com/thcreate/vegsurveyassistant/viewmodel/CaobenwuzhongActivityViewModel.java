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
import com.thcreate.vegsurveyassistant.db.entity.CaobenWuzhong;
import com.thcreate.vegsurveyassistant.repository.WuzhongDataRepository;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;

public class CaobenwuzhongActivityViewModel extends BaseWuzhongActivityViewModel<CaobenWuzhong> {

//    private String mYangfangCode;
//    private int mAction;
//    private String mWuzhongCode;
//
//    public LiveData<CaobenWuzhong> wuzhong;

//    private WuzhongDataRepository repository;

    public CaobenwuzhongActivityViewModel(@NonNull Application application) {
        super(application);
//        mYangfangCode = yangfangCode;
//        mAction = action;
//        mWuzhongCode = wuzhongCode;

//        repository = ((BasicApp)application).getWuzhongDataRepository();

//        initWuzhong();
    }
//    public void initWuzhong(String yangfangCode, int action, String wuzhongCode, CaobenWuzhong restoredData){
//        mYangfangCode = yangfangCode;
//        mAction = action;
//        mWuzhongCode = wuzhongCode;
//
//        switch (mAction){
//            case Macro.ACTION_ADD:
//                MutableLiveData<CaobenWuzhong> tmp1 = new MutableLiveData<>();
//                tmp1.setValue(new CaobenWuzhong(1, mYangfangCode, mWuzhongCode));
//                wuzhong = tmp1;
//                break;
//            case Macro.ACTION_ADD_RESTORE:
//                MutableLiveData<CaobenWuzhong> tmp2 = new MutableLiveData<>();
//                tmp2.setValue(restoredData);
//                wuzhong = tmp2;
//                break;
//            case Macro.ACTION_EDIT:
//                wuzhong = repository.getCaobenWuzhongByWuzhongCode(mWuzhongCode);
//                break;
//            case Macro.ACTION_EDIT_RESTORE:
//                MutableLiveData<CaobenWuzhong> tmp3 = new MutableLiveData<>();
//                tmp3.setValue(restoredData);
//                wuzhong = tmp3;
//                break;
//            default:
//                break;
//        }
//    }


    @Override
    public LiveData<CaobenWuzhong> getWuzhongData() {
        return repository.getCaobenWuzhongByWuzhongCode(wuzhongCode);
    }

//    public boolean save(){
//        if (wuzhong == null){
//            return false;
//        }
//        CaobenWuzhong wuzhongRaw = wuzhong.getValue();
//        if (wuzhongRaw == null){
//            return false;
//        }
//        Date dateNow = new Date();
//        wuzhongRaw.modifyAt = dateNow;
//        if (mAction == Macro.ACTION_ADD || mAction == Macro.ACTION_ADD_RESTORE){
//            wuzhongRaw.createAt = dateNow;
//            repository.insertCaobenwz(wuzhongRaw);
//        }
//        if (mAction == Macro.ACTION_EDIT || mAction == Macro.ACTION_EDIT_RESTORE){
//            repository.updateCaobenwz(wuzhongRaw);
//        }
//        return true;
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
//            return (T) new CaobenwuzhongActivityViewModel(mApplication, mAction, mYangfangCode, mWuzhongCode);
//        }
//    }
}
