package com.thcreate.vegsurveyassistant.activity;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivityCaobenwuzhongBinding;
import com.thcreate.vegsurveyassistant.viewmodel.BindingUtils;
import com.thcreate.vegsurveyassistant.viewmodel.CaobenwuzhongActivityViewModel;

public class CaobenwuzhongActivity extends AppCompatActivity {

    private CaobenwuzhongActivityViewModel mViewModel;
    private ActivityCaobenwuzhongBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CaobenwuzhongActivityViewModel.Factory factory = new CaobenwuzhongActivityViewModel.Factory(
                getApplication(), "testtesttest", "testtesttest"
        );
        mViewModel = ViewModelProviders.of(this, factory)
                .get(CaobenwuzhongActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_caobenwuzhong);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);


//        setContentView(R.layout.activity_caobenwuzhong);
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
}
