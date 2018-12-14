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
import com.thcreate.vegsurveyassistant.adapter.SampleplotAdapter;
import com.thcreate.vegsurveyassistant.adapter.ItemClickCallback;
import com.thcreate.vegsurveyassistant.databinding.ActivityHerblandBinding;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.HerbLandActivityViewModel;

public class HerbLandActivity extends BaseSamplelandActivity<HerbLandActivityViewModel> {

    private ActivityHerblandBinding mBinding;

    private SampleplotAdapter mSampleplotAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initLayout();
    }
    private void initBinding(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_herbland);
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
            mViewModel.deleteSampleplotEntityById(id);
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(controller);
        mSampleplotAdapter = new SampleplotAdapter(mItemClickCallback);
        ((RecyclerView)findViewById(R.id.sampleplot_list)).setAdapter(mSampleplotAdapter);
        itemTouchHelper.attachToRecyclerView(findViewById(R.id.sampleplot_list));

        subscribeUi();
    }
    private void subscribeUi() {
        mViewModel.getHerbSampleplotEntityList().observe(this, (dataList)->{
            if (dataList != null) {
                mSampleplotAdapter.setDataList(dataList);
                mViewModel.herbPlotCount.setValue(String.valueOf(dataList.size()));
            } else {
                mViewModel.herbPlotCount.setValue("0");
            }
            mBinding.executePendingBindings();
        });

        mViewModel.locationLiveData.observe(this, locationData -> {
            if (locationData.isValid){
                ((EditText)findViewById(R.id.longitude_edit_text)).setText(locationData.longitude);
                ((EditText)findViewById(R.id.latitude_edit_text)).setText(locationData.latitude);
                ((EditText)findViewById(R.id.administrative_region_edit_text)).setText(locationData.address);
            }
            else {
                Toast.makeText(HerbLandActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private final ItemClickCallback<SampleplotEntity> mItemClickCallback = (data) -> {
        mViewModel.onGoForward();
        Intent intent = new Intent(HerbLandActivity.this, HerbPlotActivity.class);
        intent.putExtra(Macro.SAMPLELAND_ID, data.landId);
        intent.putExtra(Macro.SAMPLELAND_TYPE, Macro.SAMPLELAND_TYPE_GRASS);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.SAMPLEPLOT_ID, data.plotId);
        startActivity(intent);
    };


    public void onAddSampleplot(View v){
        mViewModel.onGoForward();
        Intent intent = new Intent(HerbLandActivity.this, HerbPlotActivity.class);
        intent.putExtra(Macro.SAMPLELAND_ID, mViewModel.landId);
        intent.putExtra(Macro.SAMPLELAND_TYPE, Macro.SAMPLELAND_TYPE_GRASS);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        intent.putExtra(Macro.SAMPLEPLOT_ID, mViewModel.generateSampleplotId());
        startActivity(intent);
    }

}
