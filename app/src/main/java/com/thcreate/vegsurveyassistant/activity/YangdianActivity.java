package com.thcreate.vegsurveyassistant.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivityYangdianBinding;
import com.thcreate.vegsurveyassistant.viewmodel.YangdianActivityViewModel;

public class YangdianActivity extends AppCompatActivity {

    private YangdianActivityViewModel mViewModel;
    private ActivityYangdianBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        YangdianActivityViewModel.Factory factory = new YangdianActivityViewModel.Factory(
                getApplication(), "testtesttesttesttesttesttesttesttesttesttesttest");
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
