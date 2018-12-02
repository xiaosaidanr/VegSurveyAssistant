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

}
