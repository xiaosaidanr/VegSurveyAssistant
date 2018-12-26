package com.thcreate.vegsurveyassistant.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivityShrubspeciesBinding;
import com.thcreate.vegsurveyassistant.viewmodel.ShrubSpeciesActivityViewModel;

public class ShrubSpeciesActivity extends BaseSpeciesActivity<ShrubSpeciesActivityViewModel> {

    private ActivityShrubspeciesBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initLayout();
    }
    private void initBinding(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_shrubspecies);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(findViewById(R.id.toolbar));
    }

}
