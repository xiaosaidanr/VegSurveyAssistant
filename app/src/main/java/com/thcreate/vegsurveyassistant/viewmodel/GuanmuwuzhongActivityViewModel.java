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

    public GuanmuwuzhongActivityViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<GuanmuWuzhong> getWuzhongData() {
        return repository.getGuanmuWuzhongByWuzhongCode(wuzhongCode);
    }

}
