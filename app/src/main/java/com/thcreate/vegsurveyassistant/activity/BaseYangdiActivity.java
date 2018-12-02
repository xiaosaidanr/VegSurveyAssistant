package com.thcreate.vegsurveyassistant.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.thcreate.vegsurveyassistant.db.entity.Yangdi;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.BaseYangdiActivityViewModel;

import java.lang.reflect.ParameterizedType;

public class BaseYangdiActivity<U extends BaseYangdiActivityViewModel> extends BaseActivity {

    U mViewModel;

    Class<U> clazzU;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIsHandleBackPressed(true);
        clazzU = (Class <U>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        mViewModel = ViewModelProviders.of(this).get(clazzU);
        initViewModel(savedInstanceState);
    }

    private void initViewModel(@Nullable Bundle savedInstanceState){
        if (savedInstanceState == null){
            savedInstanceState = new Bundle();
            Intent intent = getIntent();
            savedInstanceState.putString(Macro.ACTION, intent.getStringExtra(Macro.ACTION));
            savedInstanceState.putString(Macro.YANGDI_CODE, intent.getStringExtra(Macro.YANGDI_CODE));
            savedInstanceState.putString(Macro.YANGDI_TYPE, intent.getStringExtra(Macro.YANGDI_TYPE));
        }
        mViewModel.init(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = mViewModel.onSaveViewModelState(outState);
        super.onSaveInstanceState(outState);
    }
}
