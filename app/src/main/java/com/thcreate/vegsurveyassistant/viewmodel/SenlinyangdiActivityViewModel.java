package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

public class SenlinyangdiActivityViewModel extends BaseYangdiActivityViewModel {

    public MediatorLiveData<Boolean> canAddGuanmuyangfang;

    public MediatorLiveData<Boolean> canAddCaobenyangfang;

    public SenlinyangdiActivityViewModel(@NonNull Application application) {
        super(application);
        canAddGuanmuyangfang = new MediatorLiveData<>();
        canAddGuanmuyangfang.setValue(false);
        canAddCaobenyangfang = new MediatorLiveData<>();
        canAddCaobenyangfang.setValue(false);
    }

}
