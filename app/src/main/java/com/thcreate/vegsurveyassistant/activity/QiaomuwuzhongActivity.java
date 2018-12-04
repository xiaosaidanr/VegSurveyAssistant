package com.thcreate.vegsurveyassistant.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivityQiaomuwuzhongBinding;
import com.thcreate.vegsurveyassistant.viewmodel.QiaomuwuzhongActivityViewModel;

public class QiaomuwuzhongActivity extends BaseWuzhongActivity<QiaomuwuzhongActivityViewModel> {

    private ActivityQiaomuwuzhongBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initLayout();
    }
    private void initBinding(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_qiaomuwuzhong);
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
