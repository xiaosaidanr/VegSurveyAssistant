package com.thcreate.vegsurveyassistant.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivityCaobenwuzhongBinding;
import com.thcreate.vegsurveyassistant.viewmodel.CaobenwuzhongActivityViewModel;

public class CaobenwuzhongActivity extends BaseWuzhongActivity<CaobenwuzhongActivityViewModel> {

    ActivityCaobenwuzhongBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initLayout();
    }
    private void initBinding(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_caobenwuzhong);
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
