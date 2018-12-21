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
import com.thcreate.vegsurveyassistant.adapter.ItemClickCallback;
import com.thcreate.vegsurveyassistant.adapter.RecyclerViewSwipeDismissController;
import com.thcreate.vegsurveyassistant.adapter.SampleplotAdapter;
import com.thcreate.vegsurveyassistant.databinding.ActivityShrublandBinding;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.PlotMainInfo;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.ShrubLandActivityViewModel;

public class ShrubLandActivity extends BaseSamplelandActivity<ShrubLandActivityViewModel> {

    private ActivityShrublandBinding mBinding;

    private SampleplotAdapter mShrubPlotAdapter;
    private SampleplotAdapter mHerbPlotAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initLayout();
    }
    private void initBinding(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_shrubland);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(mBinding.toolbar);

        mBinding.fab.setOnClickListener((v)->{
            mViewModel.save();
            finish();
        });

        RecyclerViewSwipeDismissController shrubplotItemController = new RecyclerViewSwipeDismissController(
                0,
                ItemTouchHelper.LEFT,
                ContextCompat.getDrawable(this, R.drawable.ic_delete_forever));
        shrubplotItemController.setOnDeleteCallback((id, position)->{
            mViewModel.deleteSampleplotEntityById(id);
        });
        ItemTouchHelper shrubplotItemTouchHelper = new ItemTouchHelper(shrubplotItemController);
        mShrubPlotAdapter = new SampleplotAdapter(mShrubPlotItemClickCallback);
        ((RecyclerView)findViewById(R.id.shrubplot_list)).setAdapter(mShrubPlotAdapter);
        shrubplotItemTouchHelper.attachToRecyclerView(findViewById(R.id.shrubplot_list));

        RecyclerViewSwipeDismissController herbplotItemController = new RecyclerViewSwipeDismissController(
                0,
                ItemTouchHelper.LEFT,
                ContextCompat.getDrawable(this, R.drawable.ic_delete_forever));
        herbplotItemController.setOnDeleteCallback((id, position)->{
            mViewModel.deleteSampleplotEntityById(id);
        });
        ItemTouchHelper herbplotItemTouchHelper = new ItemTouchHelper(herbplotItemController);
        mHerbPlotAdapter = new SampleplotAdapter(mHerbPlotItemClickCallback);
        ((RecyclerView)findViewById(R.id.herbplot_list)).setAdapter(mHerbPlotAdapter);
        herbplotItemTouchHelper.attachToRecyclerView(findViewById(R.id.herbplot_list));

        subscribeUi();
    }
    private void subscribeUi() {
        mViewModel.getShrubSampleplotEntityList().observe(this, (dataList)->{
            if (dataList != null) {
                mShrubPlotAdapter.setDataList(dataList);
                mViewModel.shrubPlotCount.setValue(String.valueOf(dataList.size()));
//                if (dataList.size() == 0){
//                    mViewModel.canAddHerbPlot.setValue(false);
//                }
//                else {
//                    mViewModel.canAddHerbPlot.setValue(true);
//                }
            } else {
                mViewModel.shrubPlotCount.setValue("0");
//                mViewModel.canAddHerbPlot.setValue(false);
            }
            mBinding.executePendingBindings();
        });
        mViewModel.getHerbSampleplotEntityList().observe(this, (dataList)->{
            if (dataList != null) {
                mHerbPlotAdapter.setDataList(dataList);
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
                Toast.makeText(ShrubLandActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private final ItemClickCallback<PlotMainInfo> mShrubPlotItemClickCallback = (data) -> {
        mViewModel.onGoForward();
        Intent intent = new Intent(ShrubLandActivity.this, ShrubPlotActivity.class);
        intent.putExtra(Macro.SAMPLELAND_ID, data.landId);
        intent.putExtra(Macro.SAMPLELAND_TYPE, Macro.SAMPLELAND_TYPE_BUSH);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.SAMPLEPLOT_ID, data.plotId);
        startActivity(intent);
    };
    private final ItemClickCallback<PlotMainInfo> mHerbPlotItemClickCallback = (data) -> {
        mViewModel.onGoForward();
        Intent intent = new Intent(ShrubLandActivity.this, HerbPlotActivity.class);
        intent.putExtra(Macro.SAMPLELAND_ID, data.landId);
        intent.putExtra(Macro.SAMPLELAND_TYPE, Macro.SAMPLELAND_TYPE_BUSH);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.SAMPLEPLOT_ID, data.plotId);
        startActivity(intent);
    };

    public void onAddShrubPlot(View v){
        mViewModel.onGoForward();
        Intent intent = new Intent(ShrubLandActivity.this, ShrubPlotActivity.class);
        intent.putExtra(Macro.SAMPLELAND_ID, mViewModel.landId);
        intent.putExtra(Macro.SAMPLELAND_TYPE, Macro.SAMPLELAND_TYPE_BUSH);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        intent.putExtra(Macro.SAMPLEPLOT_ID, mViewModel.generateSampleplotId());
        startActivity(intent);
    }
    public void onAddHerbPlot(View v){
        mViewModel.onGoForward();
        Intent intent = new Intent(ShrubLandActivity.this, HerbPlotActivity.class);
        intent.putExtra(Macro.SAMPLELAND_ID, mViewModel.landId);
        intent.putExtra(Macro.SAMPLELAND_TYPE, Macro.SAMPLELAND_TYPE_BUSH);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        intent.putExtra(Macro.SAMPLEPLOT_ID, mViewModel.generateSampleplotId());
        startActivity(intent);
    }

}
