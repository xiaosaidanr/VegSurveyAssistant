package com.thcreate.vegsurveyassistant.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.thcreate.vegsurveyassistant.db.entity.Yangdian;

import java.util.List;

public class YangdianActivityViewModel extends ViewModel {

    private MutableLiveData<String> mYangdianCode = new MutableLiveData<>();

//    private MutableLiveData<Yangdian> mYangdianData;

    public YangdianActivityViewModel(String yangdianCode){
        mYangdianCode.setValue(yangdianCode);
    }

}
