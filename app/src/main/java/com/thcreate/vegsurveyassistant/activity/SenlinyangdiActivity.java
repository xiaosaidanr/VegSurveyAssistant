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
import android.widget.TextView;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.adapter.ItemClickCallback;
import com.thcreate.vegsurveyassistant.adapter.YangfangAdapter;
import com.thcreate.vegsurveyassistant.databinding.ActivitySenlinyangdiBinding;
import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.Yangdi;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.SenlinyangdiActivityViewModel;

import java.util.List;

public class SenlinyangdiActivity extends BaseYangdiActivity {

    private static final String TYPE = "tree";

    private SenlinyangdiActivityViewModel mViewModel;
    private ActivitySenlinyangdiBinding mBinding;

    private EditText longitutdeEditText;
    private EditText latitudeEditText;
    private TextView qiaomuyangfangCountTextView;
    private TextView guanmuyangfangCountTextView;
    private TextView caobenyangfangCountTextView;

    private YangfangAdapter mQiaomuyangfangAdapter;
    private YangfangAdapter mGuanmuyangfangAdapter;
    private YangfangAdapter mCaobenyangfangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding(savedInstanceState);
        initLayout();
    }
    private void initBinding(Bundle savedInstanceState){
        mViewModel = ViewModelProviders.of(this).get(SenlinyangdiActivityViewModel.class);
        if (savedInstanceState == null){
            mViewModel.initYangdi(mAction, mYangdiCode, TYPE, null);
        }
        else {
            mViewModel.initYangdi(mAction, mYangdiCode, TYPE, savedInstanceState.getParcelable(YANGDI_DATA));
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_senlinyangdi);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(findViewById(R.id.toolbar));

        longitutdeEditText = findViewById(R.id.longitude_edit_text);
        latitudeEditText = findViewById(R.id.latitude_edit_text);
        qiaomuyangfangCountTextView = findViewById(R.id.qiaomuyangfang_count_text_view);
        guanmuyangfangCountTextView = findViewById(R.id.guanmuyangfang_count_text_view);
        caobenyangfangCountTextView = findViewById(R.id.caobenyangfang_count_text_view);

        findViewById(R.id.fab).setOnClickListener((v)->{
            save();
            finish();
        });

        mQiaomuyangfangAdapter = new YangfangAdapter<QiaomuYangfang>(mQiaomuyangfangItemClickCallback);
        ((RecyclerView)findViewById(R.id.qiaomuyangfang_list)).setAdapter(mQiaomuyangfangAdapter);
        mGuanmuyangfangAdapter = new YangfangAdapter<GuanmuYangfang>(mGuanmuyangfangItemClickCallback);
        ((RecyclerView)findViewById(R.id.guanmuyangfang_list)).setAdapter(mGuanmuyangfangAdapter);
        mCaobenyangfangAdapter = new YangfangAdapter<CaobenYangfang>(mCaobenyangfangItemClickCallback);
        ((RecyclerView)findViewById(R.id.caobenyangfang_list)).setAdapter(mCaobenyangfangAdapter);
        subscribeUi(mViewModel.getQiaomuyangfangList(), mViewModel.getGuanmuyangfangList(), mViewModel.getCaobenyangfangList());
    }

    private void subscribeUi(LiveData<List<QiaomuYangfang>> qiaomuyangfangLiveData,
                             LiveData<List<GuanmuYangfang>> guanmuyangfangLiveData,
                             LiveData<List<CaobenYangfang>> caobenyangfangLiveData) {
        // Update the list when the data changes
        qiaomuyangfangLiveData.observe(this, (qiaomuyangfangList)->{
            if (qiaomuyangfangList != null) {
                mQiaomuyangfangAdapter.setYangfangList(qiaomuyangfangList);
                qiaomuyangfangCountTextView.setText(String.valueOf(qiaomuyangfangList.size()));
            } else {
                qiaomuyangfangCountTextView.setText("0");
            }
            mBinding.executePendingBindings();
        });
        guanmuyangfangLiveData.observe(this, (guanmuyangfangList)->{
            if (guanmuyangfangList != null) {
                mGuanmuyangfangAdapter.setYangfangList(guanmuyangfangList);
                guanmuyangfangCountTextView.setText(String.valueOf(guanmuyangfangList.size()));

            } else {
                guanmuyangfangCountTextView.setText("0");
            }
            mBinding.executePendingBindings();
        });
        caobenyangfangLiveData.observe(this, (caobenyangfangList)->{
            if (caobenyangfangList != null) {
                mCaobenyangfangAdapter.setYangfangList(caobenyangfangList);
                caobenyangfangCountTextView.setText(String.valueOf(caobenyangfangList.size()));
            } else {
                caobenyangfangCountTextView.setText("0");
            }
            mBinding.executePendingBindings();
        });
    }
    private final ItemClickCallback<QiaomuYangfang> mQiaomuyangfangItemClickCallback = (yangfang) -> {
        Intent intent = new Intent(SenlinyangdiActivity.this, QiaomuyangfangActivity.class);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.YANGDI_CODE, yangfang.yangdiCode);
        intent.putExtra(Macro.YANGFANG_CODE, yangfang.yangfangCode);
        startActivity(intent);
    };
    private final ItemClickCallback<GuanmuYangfang> mGuanmuyangfangItemClickCallback = (yangfang) -> {
        Intent intent = new Intent(SenlinyangdiActivity.this, GuanmuyangfangActivity.class);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.YANGDI_CODE, yangfang.yangdiCode);
        intent.putExtra(Macro.YANGFANG_CODE, yangfang.yangfangCode);
        startActivity(intent);
    };
    private final ItemClickCallback<CaobenYangfang> mCaobenyangfangItemClickCallback = (yangfang) -> {
        Intent intent = new Intent(SenlinyangdiActivity.this, CaobenyangfangActivity.class);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.YANGDI_CODE, yangfang.yangdiCode);
        intent.putExtra(Macro.YANGFANG_CODE, yangfang.yangfangCode);
        startActivity(intent);
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(YANGDI_DATA, mViewModel.yangdi.getValue());
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

    private boolean save(){
        return mViewModel.save();
    }
}
