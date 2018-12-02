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
import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;
import com.thcreate.vegsurveyassistant.repository.WuzhongDataRepository;
import com.thcreate.vegsurveyassistant.repository.YangfangDataRepository;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;
import java.util.List;

public class CaobenyangfangActivityViewModel extends BaseYangfangActivityViewModel<CaobenYangfang> {

    public CaobenyangfangActivityViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<CaobenYangfang> getYangfangData(){
        return mYangfangRepository.getCaobenYangfangByYangfangCode(yangfangCode);
    }

    public LiveData<List<CaobenWuzhong>> getWuzhongList(){
        return mWuzhongRepository.getAllCaobenWuzhongByYangfangCode(yangfangCode);
    }

}
