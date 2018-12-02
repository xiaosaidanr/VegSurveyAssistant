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

    public CaobenwuzhongActivityViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<CaobenWuzhong> getWuzhongData() {
        return repository.getCaobenWuzhongByWuzhongCode(wuzhongCode);
    }

}
