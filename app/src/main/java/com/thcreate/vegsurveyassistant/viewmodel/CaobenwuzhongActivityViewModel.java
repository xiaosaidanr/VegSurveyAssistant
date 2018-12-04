package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.thcreate.vegsurveyassistant.db.entity.CaobenWuzhong;

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
