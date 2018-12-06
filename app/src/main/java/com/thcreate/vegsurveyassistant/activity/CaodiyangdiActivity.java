package com.thcreate.vegsurveyassistant.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.adapter.RecyclerViewSwipeDismissController;
import com.thcreate.vegsurveyassistant.adapter.YangfangAdapter;
import com.thcreate.vegsurveyassistant.adapter.ItemClickCallback;
import com.thcreate.vegsurveyassistant.databinding.ActivityCaodiyangdiBinding;
import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.CaodiyangdiActivityViewModel;

public class CaodiyangdiActivity extends BaseYangdiActivity<CaodiyangdiActivityViewModel> {

    private ActivityCaodiyangdiBinding mBinding;

    private YangfangAdapter mCaobenyangfangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initLayout();
    }
    private void initBinding(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_caodiyangdi);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(mBinding.toolbar);

        mBinding.fab.setOnClickListener((v)->{
            mViewModel.save();
            finish();
        });

        RecyclerViewSwipeDismissController controller = new RecyclerViewSwipeDismissController(
                0,
                ItemTouchHelper.LEFT,
                ContextCompat.getDrawable(this, R.drawable.ic_delete_forever));
        controller.setOnDeleteCallback((id, position)->{
            mViewModel.deleteCaobenyfById(id);
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(controller);
        mCaobenyangfangAdapter = new YangfangAdapter<CaobenYangfang>(mYangfangItemClickCallback);
        ((RecyclerView)findViewById(R.id.yangfang_list)).setAdapter(mCaobenyangfangAdapter);
        itemTouchHelper.attachToRecyclerView(findViewById(R.id.yangfang_list));

        subscribeUi();
    }
    private void subscribeUi() {
        mViewModel.getCaobenyangfangList().observe(this, (yangfangList)->{
            if (yangfangList != null) {
                mCaobenyangfangAdapter.setYangfangList(yangfangList);
                mViewModel.caobenyangfangCount.setValue(String.valueOf(yangfangList.size()));
            } else {
                mViewModel.caobenyangfangCount.setValue("0");
            }
            mBinding.executePendingBindings();
        });

        mViewModel.locationLiveData.observe(this, locationData -> {
            if (locationData.isValid){
                ((EditText)findViewById(R.id.longitude_edit_text)).setText(locationData.longitude);
                ((EditText)findViewById(R.id.latitude_edit_text)).setText(locationData.latitude);
                ((EditText)findViewById(R.id.xingzheng_region_edit_text)).setText(locationData.address);
            }
            else {
                Toast.makeText(CaodiyangdiActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private final ItemClickCallback<CaobenYangfang> mYangfangItemClickCallback = (yangfang) -> {
        mViewModel.onGoForward();
        Intent intent = new Intent(CaodiyangdiActivity.this, CaobenyangfangActivity.class);
        intent.putExtra(Macro.YANGDI_CODE, yangfang.yangdiCode);
        intent.putExtra(Macro.YANGDI_TYPE, Macro.YANGDI_TYPE_GRASS);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.YANGFANG_CODE, yangfang.yangfangCode);
        startActivity(intent);
    };


    public void onAddYangfang(View v){
        mViewModel.onGoForward();
        Intent intent = new Intent(CaodiyangdiActivity.this, CaobenyangfangActivity.class);
        intent.putExtra(Macro.YANGDI_CODE, mViewModel.yangdiCode);
        intent.putExtra(Macro.YANGDI_TYPE, Macro.YANGDI_TYPE_GRASS);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        intent.putExtra(Macro.YANGFANG_CODE, mViewModel.generateYangfangCode(CaobenYangfang.class));
        startActivity(intent);
    }

}
