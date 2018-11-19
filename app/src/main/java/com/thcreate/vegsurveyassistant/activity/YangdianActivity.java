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
import android.util.Log;
import android.view.View;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivityYangdianBinding;
import com.thcreate.vegsurveyassistant.viewmodel.YangdianActivityViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringJoiner;

public class YangdianActivity extends AppCompatActivity {

    private static final String ADD_ACTION = "add";
    private static final String EDIT_ACTION = "edit";

    private YangdianActivityViewModel mViewModel;
    private ActivityYangdianBinding mBinding;
    private String mAction;
    private String mYangdianCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAction = getIntent().getStringExtra("action");
        if (mAction.equals(ADD_ACTION)){
            mYangdianCode = "1_" + new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date()) + "_yangdian";
        }
        if (mAction.equals(EDIT_ACTION)){
            mYangdianCode = getIntent().getStringExtra("yangdianCode");
        }

        YangdianActivityViewModel.Factory factory = new YangdianActivityViewModel.Factory(
                getApplication(), mYangdianCode);
        mViewModel = ViewModelProviders.of(this, factory)
                .get(YangdianActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_yangdian);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);

        setSupportActionBar(mBinding.toolbar);

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
}
