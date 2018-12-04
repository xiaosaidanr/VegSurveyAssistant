package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

public class GuancongyangdiActivityViewModel extends BaseYangdiActivityViewModel {

    public MediatorLiveData<Boolean> canAddCaobenyangfang;

    public GuancongyangdiActivityViewModel(@NonNull Application application) {
        super(application);
        canAddCaobenyangfang = new MediatorLiveData<>();
        canAddCaobenyangfang.setValue(false);
    }

}
