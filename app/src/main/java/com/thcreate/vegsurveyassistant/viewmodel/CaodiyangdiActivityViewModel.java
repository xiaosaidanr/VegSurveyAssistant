package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

public class CaodiyangdiActivityViewModel extends BaseYangdiActivityViewModel {

    public MutableLiveData<String> caobenyangfangCount;

    public CaodiyangdiActivityViewModel(@NonNull Application application) {
        super(application);
        caobenyangfangCount = new MutableLiveData<>();
        caobenyangfangCount.setValue("0");
    }

}
