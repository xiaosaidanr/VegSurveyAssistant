package com.thcreate.vegsurveyassistant.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivityGuanmuyangfangBinding;
import com.thcreate.vegsurveyassistant.viewmodel.GuanmuyangfangActivityViewModel;

public class GuanmuyangfangActivity extends AppCompatActivity {

    private GuanmuyangfangActivityViewModel mViewModel;

    private ActivityGuanmuyangfangBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GuanmuyangfangActivityViewModel.Factory factory = new GuanmuyangfangActivityViewModel.Factory(
                getApplication(), "testtesttest"
        );
        mViewModel = ViewModelProviders.of(this, factory)
                .get(GuanmuyangfangActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_guanmuyangfang);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);

//        setContentView(R.layout.activity_guanmuyangfang);
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

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new CaobenyangfangActivity.DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void onAddWuzhong(View v){
        Intent intent = new Intent(GuanmuyangfangActivity.this, GuanmuwuzhongActivity.class);
        startActivity(intent);
    }
}
