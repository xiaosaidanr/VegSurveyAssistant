package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class SenlinyangdiActivityViewModel extends BaseYangdiActivityViewModel {

    public MutableLiveData<Boolean> canAddGuanmuyangfang;

    public MutableLiveData<Boolean> canAddCaobenyangfang;

    public MutableLiveData<String> qiaomuyangfangCount;

    public MutableLiveData<String> guanmuyangfangCount;

    public MutableLiveData<String> caobenyangfangCount;

    public SenlinyangdiActivityViewModel(@NonNull Application application) {
        super(application);
        canAddGuanmuyangfang = new MutableLiveData<>();
        canAddGuanmuyangfang.setValue(false);
        canAddCaobenyangfang = new MutableLiveData<>();
        canAddCaobenyangfang.setValue(false);

        qiaomuyangfangCount = new MutableLiveData<>();
        guanmuyangfangCount = new MutableLiveData<>();
        caobenyangfangCount = new MutableLiveData<>();
        qiaomuyangfangCount.setValue("0");
        guanmuyangfangCount.setValue("0");
        caobenyangfangCount.setValue("0");
    }

}
