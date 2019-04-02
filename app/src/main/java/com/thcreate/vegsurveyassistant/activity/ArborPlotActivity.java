package com.thcreate.vegsurveyassistant.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.adapter.ItemClickCallback;
import com.thcreate.vegsurveyassistant.adapter.PictureAdapter;
import com.thcreate.vegsurveyassistant.adapter.RecyclerViewSwipeDismissController;
import com.thcreate.vegsurveyassistant.adapter.SpeciesAdapter;
import com.thcreate.vegsurveyassistant.databinding.ActivityArborplotBinding;
import com.thcreate.vegsurveyassistant.db.entity.PlotPictureEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.SpeciesMainInfo;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.ArborPlotActivityViewModel;

import java.util.Calendar;

public class ArborPlotActivity extends BaseSampleplotActivity<ArborPlotActivityViewModel> implements DatePickerDialog.OnDateSetListener {

    private ActivityArborplotBinding mBinding;

    private SpeciesAdapter mAdapter;
    private PictureAdapter mPictureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initLayout();
    }
    private void initBinding(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_arborplot);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(mBinding.toolbar);

        RecyclerViewSwipeDismissController controller = new RecyclerViewSwipeDismissController(
                0,
                ItemTouchHelper.LEFT,
                ContextCompat.getDrawable(this, R.drawable.ic_delete_forever));
        controller.setOnDeleteCallback((id, position)->{
            mViewModel.deleteSpeciesEntityById(id);
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(controller);
        mAdapter = new SpeciesAdapter(mItemClickCallback);
        ((RecyclerView)findViewById(R.id.species_list)).setAdapter(mAdapter);
        itemTouchHelper.attachToRecyclerView(findViewById(R.id.species_list));

        RecyclerViewSwipeDismissController pictureController = new RecyclerViewSwipeDismissController(
                0,
                ItemTouchHelper.LEFT,
                ContextCompat.getDrawable(this, R.drawable.ic_delete_forever));
        pictureController.setOnDeleteCallback((id, position)->{
            mViewModel.deletePlotPictureEntityById(id);
        });
        ItemTouchHelper pictureItemTouchHelper = new ItemTouchHelper(pictureController);
        mPictureAdapter = new PictureAdapter(mPictureClickCallback);
        ((RecyclerView)findViewById(R.id.picture_list)).setAdapter(mPictureAdapter);
        pictureItemTouchHelper.attachToRecyclerView(findViewById(R.id.picture_list));

        subscribeUi();
    }
    private void subscribeUi() {
        // Update the list when the data changes
        mViewModel.getPlotPictureEntityList().observe(this, (dataList)->{
            if (dataList != null){
                mPictureAdapter.setDataList(dataList);
                mViewModel.pictureCount.setValue(String.valueOf(dataList.size()));
            }
            else {
                mViewModel.pictureCount.setValue("0");
            }
            mBinding.executePendingBindings();
        });
        mViewModel.getSpeciesEntityList().observe(this, (dataList)->{
            if (dataList != null) {
                mAdapter.setDataList(dataList);
                mViewModel.speciesCount.setValue(String.valueOf(dataList.size()));
            } else {
                mViewModel.speciesCount.setValue("0");
            }
            mBinding.executePendingBindings();
        });

        mViewModel.locationLiveData.observe(this, locationData -> {
            if (locationData.isValid){
                ((EditText)findViewById(R.id.longitude_edit_text)).setText(locationData.longitude);
                ((EditText)findViewById(R.id.latitude_edit_text)).setText(locationData.latitude);
            }
            else {
                Toast.makeText(ArborPlotActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private final ItemClickCallback<SpeciesMainInfo> mItemClickCallback = (data) -> {
        mViewModel.onGoForward();
        Intent intent = new Intent(ArborPlotActivity.this, ArborSpeciesActivity.class);
        intent.putExtra(Macro.SAMPLEPLOT_ID, data.plotId);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.SPECIES_ID, data.speciesId);
        startActivity(intent);
    };

    private final ItemClickCallback<PlotPictureEntity> mPictureClickCallback = (data)->{
        Intent intent = new Intent(ArborPlotActivity.this, PlotPicturePreviewActivity.class);
        intent.putExtra(Macro.IMAGE_LOCAL_PATH, data.localAddr);
        intent.putExtra(Macro.IMAGE_REMOTE_PATH, data.url);
        startActivity(intent);
    };

    public void showDatePickerDialog(View v) {
        Calendar calendar=Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
    public void onDateSet(DatePicker view, int year, int month, int day) {
        TextView textView = findViewById(R.id.date_text_view);
        if (textView != null){
            textView.setText(String.valueOf(String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(day)));
        }
    }

    public void onAddSpecies(View v){
        mViewModel.onGoForward();
        Intent intent = new Intent(ArborPlotActivity.this, ArborSpeciesActivity.class);
        intent.putExtra(Macro.SAMPLEPLOT_ID, mViewModel.plotId);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        intent.putExtra(Macro.SPECIES_ID, mViewModel.generateSpeciesId());
        startActivity(intent);
    }

}
