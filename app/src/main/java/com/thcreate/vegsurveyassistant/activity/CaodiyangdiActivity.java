package com.thcreate.vegsurveyassistant.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.adapter.CaobenyangfangAdapter;
import com.thcreate.vegsurveyassistant.adapter.ItemClickCallback;
import com.thcreate.vegsurveyassistant.databinding.ActivityCaodiyangdiBinding;
import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.CaodiyangdiActivityViewModel;

import java.util.List;

public class CaodiyangdiActivity extends AppCompatActivity {

    private CaodiyangdiActivityViewModel mViewModel;
    private ActivityCaodiyangdiBinding mBinding;

    private int mAction;
    private String mYangdiCode;

    private EditText longitutdeEditText;
    private EditText latitudeEditText;

    private CaobenyangfangAdapter mCaobenyangfangAdapter;

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
        CaodiyangdiActivityViewModel.Factory factory = new CaodiyangdiActivityViewModel.Factory(
                getApplication(), mAction, mYangdiCode);
        mViewModel = ViewModelProviders.of(this, factory)
                .get(CaodiyangdiActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_caodiyangdi);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(findViewById(R.id.toolbar));
        longitutdeEditText = findViewById(R.id.longitude_edit_text);
        latitudeEditText = findViewById(R.id.latitude_edit_text);
        findViewById(R.id.fab).setOnClickListener((v)->{
            save();
        });

        mCaobenyangfangAdapter = new CaobenyangfangAdapter(mYangfangItemClickCallback);
        ((RecyclerView)findViewById(R.id.yangfang_list)).setAdapter(mCaobenyangfangAdapter);
        subscribeUi(mViewModel.getCaobenyangfangList());
    }
    private void subscribeUi(LiveData<List<CaobenYangfang>> liveData) {
        // Update the list when the data changes
        liveData.observe(this, (caobenyangfangList)->{
            if (caobenyangfangList != null) {
//                mViewModel.
                mCaobenyangfangAdapter.setCaobenyangfangList(caobenyangfangList);
            } else {
//                mBinding.setIsLoading(true);
            }
            // espresso does not know how to wait for data binding's loop so we execute changes
            // sync.
            mBinding.executePendingBindings();
        });
    }

    private final ItemClickCallback<CaobenYangfang> mYangfangItemClickCallback = (yangfang) -> {
        Intent intent = new Intent(CaodiyangdiActivity.this, CaobenyangfangActivity.class);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.YANGDI_CODE, yangfang.yangdiCode);
        intent.putExtra(Macro.CAOBENYANGFANG_CODE, yangfang.yangfangCode);
        startActivity(intent);
    };



    public void onAddYangfang(View v){
        Intent intent = new Intent(CaodiyangdiActivity.this, CaobenyangfangActivity.class);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        intent.putExtra(Macro.YANGDI_TYPE, Macro.GRASS);
        intent.putExtra(Macro.YANGDI_CODE, mYangdiCode);
        startActivity(intent);
    }



    public void onAutoPosition(View v){
        longitutdeEditText.setText("testtesttest");
        latitudeEditText.setText("testtesttest");
    }



    private void save(){
        mViewModel.save();
        finish();
    }
}
