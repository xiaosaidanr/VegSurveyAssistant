package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuWuzhong;

public class GuanmuwuzhongActivityViewModel extends BaseWuzhongActivityViewModel<GuanmuWuzhong> {

    public GuanmuwuzhongActivityViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<GuanmuWuzhong> getWuzhongData() {
        return repository.getGuanmuWuzhongByWuzhongCode(wuzhongCode);
    }

}
