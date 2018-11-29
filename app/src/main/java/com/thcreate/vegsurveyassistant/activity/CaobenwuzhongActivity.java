package com.thcreate.vegsurveyassistant.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivityCaobenwuzhongBinding;
import com.thcreate.vegsurveyassistant.db.entity.CaobenWuzhong;
import com.thcreate.vegsurveyassistant.viewmodel.CaobenwuzhongActivityViewModel;

public class CaobenwuzhongActivity extends BaseWuzhongActivity<CaobenWuzhong> {

    CaobenwuzhongActivityViewModel mViewModel;
    ActivityCaobenwuzhongBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding(savedInstanceState);
        initLayout();
    }
    private void initBinding(Bundle savedInstanceState){
        mViewModel = ViewModelProviders.of(this)
                .get(CaobenwuzhongActivityViewModel.class);
        if (savedInstanceState == null){
            mViewModel.initWuzhong(mYangfangCode, mAction, mWuzhongCode, null);
        }
        else {
            mViewModel.initWuzhong(mYangfangCode, mAction, mWuzhongCode, savedInstanceState.getParcelable(WUZHONG_DATA));
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(WUZHONG_DATA, mViewModel.wuzhong.getValue());
    }

    private boolean save(){
        return mViewModel.save();
    }
}
