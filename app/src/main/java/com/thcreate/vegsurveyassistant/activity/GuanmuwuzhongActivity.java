package com.thcreate.vegsurveyassistant.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivityGuanmuwuzhongBinding;
import com.thcreate.vegsurveyassistant.viewmodel.GuanmuwuzhongActivityViewModel;

public class GuanmuwuzhongActivity extends BaseWuzhongActivity<GuanmuwuzhongActivityViewModel> {

    private ActivityGuanmuwuzhongBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initLayout();
    }
    private void initBinding(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_guanmuwuzhong);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(findViewById(R.id.toolbar));
        findViewById(R.id.fab).setOnClickListener((v)->{
            save();
            finish();
        });
    }

    private boolean save(){
        return mViewModel.save();
    }
}
