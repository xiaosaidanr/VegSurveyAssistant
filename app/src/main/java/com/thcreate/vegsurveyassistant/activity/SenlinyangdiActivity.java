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
import android.widget.EditText;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivitySenlinyangdiBinding;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.SenlinyangdiActivityViewModel;

public class SenlinyangdiActivity extends AppCompatActivity {

    private SenlinyangdiActivityViewModel mViewModel;
    private ActivitySenlinyangdiBinding mBinding;

    private int mAction;
    private String mYangdiCode;

    private EditText longitutdeEditText;
    private EditText latitudeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParam();
        initBinding();
        initLayout();
    }
    private void initParam(){
        mAction = getIntent().getIntExtra(Macro.ACTION, Macro.ACTION_ADD);
        if (mAction == Macro.ACTION_ADD){
            mYangdiCode = IdGenerator.getId(1, Macro.YANGDI);
        }
        if (mAction == Macro.ACTION_EDIT){
            mYangdiCode = getIntent().getStringExtra(Macro.YANGDI_CODE);
        }
    }
    private void initBinding(){
        SenlinyangdiActivityViewModel.Factory factory = new SenlinyangdiActivityViewModel.Factory(
                getApplication(), mAction, mYangdiCode
        );
        mViewModel = ViewModelProviders.of(this, factory)
                .get(SenlinyangdiActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_senlinyangdi);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(findViewById(R.id.toolbar));
        longitutdeEditText = findViewById(R.id.longitude_edit_text);
        latitudeEditText = findViewById(R.id.latitude_edit_text);
    }



    public void onAddQiaomuyangfang(View v){
        Intent intent = new Intent(SenlinyangdiActivity.this, QiaomuyangfangActivity.class);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        intent.putExtra(Macro.YANGDI_TYPE, Macro.TREE);
        intent.putExtra(Macro.YANGDI_CODE, mYangdiCode);
        startActivity(intent);
    }
    public void onAddGuanmuyangfang(View v){
        Intent intent = new Intent(SenlinyangdiActivity.this, GuanmuyangfangActivity.class);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        intent.putExtra(Macro.YANGDI_TYPE, Macro.TREE);
        intent.putExtra(Macro.YANGDI_CODE, mYangdiCode);
        startActivity(intent);
    }
    public void onAddCaobenyangfang(View v){
        Intent intent = new Intent(SenlinyangdiActivity.this, CaobenyangfangActivity.class);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        intent.putExtra(Macro.YANGDI_TYPE, Macro.TREE);
        intent.putExtra(Macro.YANGDI_CODE, mYangdiCode);
        startActivity(intent);
    }



    public void onAutoPosition(View v){
        longitutdeEditText.setText("testtesttest");
        latitudeEditText.setText("testtesttest");
    }
}
