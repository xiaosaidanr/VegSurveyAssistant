package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuWuzhong;

public class QiaomuwuzhongActivityViewModel extends BaseWuzhongActivityViewModel<QiaomuWuzhong> {

    public QiaomuwuzhongActivityViewModel(@NonNull Application application) {
        super(application);
    }
    @Override
    public LiveData<QiaomuWuzhong> getWuzhongData() {
        return repository.getQiaomuWuzhongByWuzhongCode(wuzhongCode);
    }

}
