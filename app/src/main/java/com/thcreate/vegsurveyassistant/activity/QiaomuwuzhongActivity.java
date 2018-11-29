package com.thcreate.vegsurveyassistant.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivityQiaomuwuzhongBinding;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuWuzhong;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.QiaomuwuzhongActivityViewModel;

public class QiaomuwuzhongActivity extends BaseWuzhongActivity<QiaomuWuzhong> {

    private QiaomuwuzhongActivityViewModel mViewModel;
    private ActivityQiaomuwuzhongBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding(savedInstanceState);
        initLayout();
    }
    private void initBinding(Bundle savedInstanceState){
        mViewModel = ViewModelProviders.of(this).get(QiaomuwuzhongActivityViewModel.class);
        if (savedInstanceState == null){
            mViewModel.initWuzhong(mYangfangCode, mAction, mWuzhongCode, null);
        }
        else {
            mViewModel.initWuzhong(mYangfangCode, mAction, mWuzhongCode, savedInstanceState.getParcelable(WUZHONG_DATA));
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(WUZHONG_DATA, mViewModel.wuzhong.getValue());
    }

    private boolean save(){
        return mViewModel.save();
    }
}
