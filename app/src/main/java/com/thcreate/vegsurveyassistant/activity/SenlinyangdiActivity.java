package com.thcreate.vegsurveyassistant.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.adapter.ItemClickCallback;
import com.thcreate.vegsurveyassistant.adapter.RecyclerViewSwipeDismissController;
import com.thcreate.vegsurveyassistant.adapter.YangfangAdapter;
import com.thcreate.vegsurveyassistant.databinding.ActivitySenlinyangdiBinding;
import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuYangfang;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.SenlinyangdiActivityViewModel;

public class SenlinyangdiActivity extends BaseYangdiActivity<SenlinyangdiActivityViewModel> {

    private ActivitySenlinyangdiBinding mBinding;

    private EditText longitutdeEditText;
    private EditText latitudeEditText;

    private YangfangAdapter mQiaomuyangfangAdapter;
    private YangfangAdapter mGuanmuyangfangAdapter;
    private YangfangAdapter mCaobenyangfangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initLayout();
    }
    private void initBinding(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_senlinyangdi);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(mBinding.toolbar);

        longitutdeEditText = findViewById(R.id.longitude_edit_text);
        latitudeEditText = findViewById(R.id.latitude_edit_text);

        mBinding.fab.setOnClickListener((v)->{
            save();
            finish();
        });

        RecyclerViewSwipeDismissController qiaomuyangfangItemController = new RecyclerViewSwipeDismissController(
                0,
                ItemTouchHelper.LEFT,
                ContextCompat.getDrawable(this, R.drawable.ic_delete_forever));
        qiaomuyangfangItemController.setOnDeleteCallback((id, position)->{
            mViewModel.deleteQiaomuyfById(id);
        });
        ItemTouchHelper qiaomuyangfangItemTouchHelper = new ItemTouchHelper(qiaomuyangfangItemController);
        mQiaomuyangfangAdapter = new YangfangAdapter<QiaomuYangfang>(mQiaomuyangfangItemClickCallback);
        ((RecyclerView)findViewById(R.id.qiaomuyangfang_list)).setAdapter(mQiaomuyangfangAdapter);
        qiaomuyangfangItemTouchHelper.attachToRecyclerView(findViewById(R.id.qiaomuyangfang_list));

        RecyclerViewSwipeDismissController guanmuyangfangItemController = new RecyclerViewSwipeDismissController(
                0,
                ItemTouchHelper.LEFT,
                ContextCompat.getDrawable(this, R.drawable.ic_delete_forever));
        guanmuyangfangItemController.setOnDeleteCallback((id, position)->{
            mViewModel.deleteGuanmuyfById(id);
        });
        ItemTouchHelper guanmuyangfangItemTouchHelper = new ItemTouchHelper(guanmuyangfangItemController);
        mGuanmuyangfangAdapter = new YangfangAdapter<GuanmuYangfang>(mGuanmuyangfangItemClickCallback);
        ((RecyclerView)findViewById(R.id.guanmuyangfang_list)).setAdapter(mGuanmuyangfangAdapter);
        guanmuyangfangItemTouchHelper.attachToRecyclerView(findViewById(R.id.guanmuyangfang_list));

        RecyclerViewSwipeDismissController caobenyangfangItemController = new RecyclerViewSwipeDismissController(
                0,
                ItemTouchHelper.LEFT,
                ContextCompat.getDrawable(this, R.drawable.ic_delete_forever));
        caobenyangfangItemController.setOnDeleteCallback((id, position)->{
            mViewModel.deleteCaobenyfById(id);
        });
        ItemTouchHelper caobenyangfangItemTouchHelper = new ItemTouchHelper(caobenyangfangItemController);
        mCaobenyangfangAdapter = new YangfangAdapter<CaobenYangfang>(mCaobenyangfangItemClickCallback);
        ((RecyclerView)findViewById(R.id.caobenyangfang_list)).setAdapter(mCaobenyangfangAdapter);
        caobenyangfangItemTouchHelper.attachToRecyclerView(findViewById(R.id.caobenyangfang_list));

        subscribeUi();
    }

    private void subscribeUi() {
        // Update the list when the data changes
        mViewModel.getQiaomuyangfangList().observe(this, (qiaomuyangfangList)->{
            if (qiaomuyangfangList != null) {
                mQiaomuyangfangAdapter.setYangfangList(qiaomuyangfangList);
                mViewModel.qiaomuyangfangCount.setValue(String.valueOf(qiaomuyangfangList.size()));
                if (qiaomuyangfangList.size() == 0){
                    mViewModel.canAddGuanmuyangfang.setValue(false);
                }
                else {
                    mViewModel.canAddGuanmuyangfang.setValue(true);
                }
            } else {
                mViewModel.qiaomuyangfangCount.setValue("0");
                mViewModel.canAddGuanmuyangfang.setValue(false);
            }
            mBinding.executePendingBindings();
        });
        mViewModel.getGuanmuyangfangList().observe(this, (guanmuyangfangList)->{
            if (guanmuyangfangList != null) {
                mGuanmuyangfangAdapter.setYangfangList(guanmuyangfangList);
                mViewModel.guanmuyangfangCount.setValue(String.valueOf(guanmuyangfangList.size()));
                if (guanmuyangfangList.size() == 0){
                    mViewModel.canAddCaobenyangfang.setValue(false);
                }
                else {
                    mViewModel.canAddCaobenyangfang.setValue(true);
                }
            } else {
                mViewModel.guanmuyangfangCount.setValue("0");
                mViewModel.canAddCaobenyangfang.setValue(false);
            }
            mBinding.executePendingBindings();
        });
        mViewModel.getCaobenyangfangList().observe(this, (caobenyangfangList)->{
            if (caobenyangfangList != null) {
                mCaobenyangfangAdapter.setYangfangList(caobenyangfangList);
                mViewModel.caobenyangfangCount.setValue(String.valueOf(caobenyangfangList.size()));
            } else {
                mViewModel.caobenyangfangCount.setValue("0");
            }
            mBinding.executePendingBindings();
        });
    }
    private final ItemClickCallback<QiaomuYangfang> mQiaomuyangfangItemClickCallback = (yangfang) -> {
        mViewModel.onGoForward();
        Intent intent = new Intent(SenlinyangdiActivity.this, QiaomuyangfangActivity.class);
        intent.putExtra(Macro.YANGDI_CODE, yangfang.yangdiCode);
        intent.putExtra(Macro.YANGDI_TYPE, Macro.YANGDI_TYPE_TREE);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.YANGFANG_CODE, yangfang.yangfangCode);
        startActivity(intent);
    };
    private final ItemClickCallback<GuanmuYangfang> mGuanmuyangfangItemClickCallback = (yangfang) -> {
        mViewModel.onGoForward();
        Intent intent = new Intent(SenlinyangdiActivity.this, GuanmuyangfangActivity.class);
        intent.putExtra(Macro.YANGDI_CODE, yangfang.yangdiCode);
        intent.putExtra(Macro.YANGDI_TYPE, Macro.YANGDI_TYPE_TREE);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.YANGFANG_CODE, yangfang.yangfangCode);
        startActivity(intent);
    };
    private final ItemClickCallback<CaobenYangfang> mCaobenyangfangItemClickCallback = (yangfang) -> {
        mViewModel.onGoForward();
        Intent intent = new Intent(SenlinyangdiActivity.this, CaobenyangfangActivity.class);
        intent.putExtra(Macro.YANGDI_CODE, yangfang.yangdiCode);
        intent.putExtra(Macro.YANGDI_TYPE, Macro.YANGDI_TYPE_TREE);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.YANGFANG_CODE, yangfang.yangfangCode);
        startActivity(intent);
    };


    public void onAddQiaomuyangfang(View v){
        mViewModel.onGoForward();
        Intent intent = new Intent(SenlinyangdiActivity.this, QiaomuyangfangActivity.class);
        intent.putExtra(Macro.YANGDI_CODE, mViewModel.yangdiCode);
        intent.putExtra(Macro.YANGDI_TYPE, Macro.YANGDI_TYPE_TREE);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        intent.putExtra(Macro.YANGFANG_CODE, mViewModel.generateYangfangCode(QiaomuYangfang.class));
        startActivity(intent);
    }
    public void onAddGuanmuyangfang(View v){
        mViewModel.onGoForward();
        Intent intent = new Intent(SenlinyangdiActivity.this, GuanmuyangfangActivity.class);
        intent.putExtra(Macro.YANGDI_CODE, mViewModel.yangdiCode);
        intent.putExtra(Macro.YANGDI_TYPE, Macro.YANGDI_TYPE_TREE);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        intent.putExtra(Macro.YANGFANG_CODE, mViewModel.generateYangfangCode(GuanmuYangfang.class));
        startActivity(intent);
    }
    public void onAddCaobenyangfang(View v){
        mViewModel.onGoForward();
        Intent intent = new Intent(SenlinyangdiActivity.this, CaobenyangfangActivity.class);
        intent.putExtra(Macro.YANGDI_CODE, mViewModel.yangdiCode);
        intent.putExtra(Macro.YANGDI_TYPE, Macro.YANGDI_TYPE_TREE);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        intent.putExtra(Macro.YANGFANG_CODE, mViewModel.generateYangfangCode(CaobenYangfang.class));
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
