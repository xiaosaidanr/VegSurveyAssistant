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
import com.thcreate.vegsurveyassistant.databinding.ActivityGuanmuwuzhongBinding;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.GuanmuwuzhongActivityViewModel;

public class GuanmuwuzhongActivity extends AppCompatActivity {

    private GuanmuwuzhongActivityViewModel mViewModel;
    private ActivityGuanmuwuzhongBinding mBinding;

    private String mYangfangCode;
    private int mAction;
    private String mWuzhongCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
        initBinding();
        initLayout();
    }
    private void initParam(){
        Intent intent = getIntent();
        mYangfangCode = intent.getStringExtra(Macro.GUANMUYANGFANG_CODE);
        mAction = intent.getIntExtra(Macro.ACTION, Macro.ACTION_ADD);
        if (mAction == Macro.ACTION_ADD){
            mWuzhongCode = IdGenerator.getId(1, Macro.GUANMU_WUZHONG);
        }
        else {
            mWuzhongCode = intent.getStringExtra(Macro.GUANMUWUZHONG_CODE);
        }
    }
    private void initBinding(){
        GuanmuwuzhongActivityViewModel.Factory factory = new GuanmuwuzhongActivityViewModel.Factory(
                getApplication(), mAction, mYangfangCode, mWuzhongCode
        );
        mViewModel = ViewModelProviders.of(this, factory).get(GuanmuwuzhongActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_guanmuwuzhong);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(findViewById(R.id.toolbar));
        findViewById(R.id.fab).setOnClickListener((v)->{
            save();
        });
    }



    private void save(){
        mViewModel.save();
        finish();
    }
}
