package com.thcreate.vegsurveyassistant.ViewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.thcreate.vegsurveyassistant.db.entity.Yangdian;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    public LiveData<List<Yangdian>> yangdianList;

    public MainActivityViewModel(){

    }

}
