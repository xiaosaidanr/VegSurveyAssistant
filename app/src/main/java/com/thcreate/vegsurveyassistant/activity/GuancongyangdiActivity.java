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
import com.thcreate.vegsurveyassistant.databinding.ActivityGuancongyangdiBinding;
import com.thcreate.vegsurveyassistant.viewmodel.GuancongyangdiActivityViewModel;

public class GuancongyangdiActivity extends AppCompatActivity {

    private GuancongyangdiActivityViewModel mViewModel;

    private ActivityGuancongyangdiBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GuancongyangdiActivityViewModel.Factory factory = new GuancongyangdiActivityViewModel.Factory(
                getApplication(), "testtesttest"
        );
        mViewModel = ViewModelProviders.of(this, factory)
                .get(GuancongyangdiActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_guancongyangdi);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);

//        setContentView(R.layout.activity_guancongyangdi);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mBinding.toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        mBinding.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    public void onAddGuanmuyangfang(View v){
        Intent intent = new Intent(GuancongyangdiActivity.this, GuanmuyangfangActivity.class);
        startActivity(intent);
    }

    public void onAddCaobenyangfang(View v){
        Intent intent = new Intent(GuancongyangdiActivity.this, CaobenyangfangActivity.class);
        startActivity(intent);
    }
}
