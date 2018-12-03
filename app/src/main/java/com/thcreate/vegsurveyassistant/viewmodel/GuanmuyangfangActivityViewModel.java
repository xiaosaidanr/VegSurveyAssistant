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
import com.thcreate.vegsurveyassistant.db.entity.GuanmuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.YangfangCode;
import com.thcreate.vegsurveyassistant.repository.WuzhongDataRepository;
import com.thcreate.vegsurveyassistant.repository.YangfangDataRepository;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;
import java.util.List;

public class GuanmuyangfangActivityViewModel extends BaseYangfangActivityViewModel<GuanmuYangfang> {

    public GuanmuyangfangActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<YangfangCode>> getQiaomuyangfangCodeList(){
        return mYangfangRepository.getAllQiaomuYangfangCodeByYangdiCode(yangdiCode);
    }

    @Override
    public LiveData<GuanmuYangfang> getYangfangData() {
        return mYangfangRepository.getGuanmuYangfangByYangfangCode(yangfangCode);
    }

    public LiveData<List<GuanmuWuzhong>> getWuzhongList(){
        return mWuzhongRepository.getAllGuanmuWuzhongByYangfangCode(yangfangCode);
    }

}
