package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class GuancongyangdiActivityViewModel extends BaseYangdiActivityViewModel {

    public MediatorLiveData<Boolean> canAddCaobenyangfang;

    public MutableLiveData<String> guanmuyangfangCount;

    public MutableLiveData<String> caobenyangfangCount;

    public GuancongyangdiActivityViewModel(@NonNull Application application) {
        super(application);
        canAddCaobenyangfang = new MediatorLiveData<>();
        canAddCaobenyangfang.setValue(false);

        guanmuyangfangCount = new MutableLiveData<>();
        caobenyangfangCount = new MutableLiveData<>();
        guanmuyangfangCount.setValue("0");
        caobenyangfangCount.setValue("0");
    }

}
