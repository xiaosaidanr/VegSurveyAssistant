package com.thcreate.vegsurveyassistant.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.adapter.RecyclerViewSwipeDismissController;
import com.thcreate.vegsurveyassistant.adapter.SpeciesAdapter;
import com.thcreate.vegsurveyassistant.adapter.ItemClickCallback;
import com.thcreate.vegsurveyassistant.databinding.ActivityHerbplotBinding;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.PlotMainInfo;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.SpeciesMainInfo;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.HerbPlotActivityViewModel;

import java.util.ArrayList;
import java.util.Calendar;

public class HerbPlotActivity extends BaseSampleplotActivity<HerbPlotActivityViewModel> implements DatePickerDialog.OnDateSetListener {

    private ActivityHerbplotBinding mBinding;

    private ArrayAdapter<String> arborplotCodeSpinnerAdapter;
    private ArrayAdapter<String> shrubplotCodeSpinnerAdapter;

    private SpeciesAdapter mSpeciesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initLayout();
    }
    private void initBinding(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_herbplot);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(mBinding.toolbar);

        if (mViewModel.landType.getValue().equals(Macro.SAMPLELAND_TYPE_TREE)){
            AppCompatSpinner pAppCompatSpinner = findViewById(R.id.belong_arborplot_code_spinner);
            arborplotCodeSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<String>());
            pAppCompatSpinner.setAdapter(arborplotCodeSpinnerAdapter);
        }
        if (mViewModel.landType.getValue().equals(Macro.SAMPLELAND_TYPE_TREE) || mViewModel.landType.getValue().equals(Macro.SAMPLELAND_TYPE_BUSH)){
            AppCompatSpinner pAppCompatSpinner = findViewById(R.id.belong_shrubplot_code_spinner);
            shrubplotCodeSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<String>());
            pAppCompatSpinner.setAdapter(shrubplotCodeSpinnerAdapter);
        }

        mBinding.fab.setOnClickListener((v)->{
            mViewModel.save();
            finish();
        });

        RecyclerViewSwipeDismissController controller = new RecyclerViewSwipeDismissController(
                0,
                ItemTouchHelper.LEFT,
                ContextCompat.getDrawable(this, R.drawable.ic_delete_forever));
        controller.setOnDeleteCallback((id, position)->{
            mViewModel.deleteSpeciesEntityById(id);
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(controller);
        mSpeciesAdapter = new SpeciesAdapter(mItemClickCallback);
        ((RecyclerView)findViewById(R.id.species_list)).setAdapter(mSpeciesAdapter);
        itemTouchHelper.attachToRecyclerView(findViewById(R.id.species_list));

        subscribeUi();
    }
    private void subscribeUi() {
        mViewModel.getSpeciesEntityList().observe(this, (dataList)->{
            if (dataList != null) {
                mSpeciesAdapter.setDataList(dataList);
                mViewModel.speciesCount.setValue(String.valueOf(dataList.size()));
            } else {
                mViewModel.speciesCount.setValue("0");
            }
            mBinding.executePendingBindings();
        });
        if (arborplotCodeSpinnerAdapter != null){
            mViewModel.getArborPlotEntityList().observe(this, (dataList)->{
                if (dataList != null){
                    if (dataList.size()>0){
                        arborplotCodeSpinnerAdapter.clear();
                        for (PlotMainInfo item: dataList){
                            arborplotCodeSpinnerAdapter.add(item.plotId);
                        }
                        arborplotCodeSpinnerAdapter.notifyDataSetChanged();
                    }
                }
            });
        }
        if (shrubplotCodeSpinnerAdapter != null){
            mViewModel.getShrubPlotEntityList().observe(this, (dataList)->{
                if (dataList != null){
                    if (dataList.size()>0){
                        shrubplotCodeSpinnerAdapter.clear();
                        for (PlotMainInfo item: dataList){
                            shrubplotCodeSpinnerAdapter.add(item.plotId);
                        }
                        shrubplotCodeSpinnerAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        mViewModel.locationLiveData.observe(this, locationData -> {
            if (locationData.isValid){
                ((EditText)findViewById(R.id.longitude_edit_text)).setText(locationData.longitude);
                ((EditText)findViewById(R.id.latitude_edit_text)).setText(locationData.latitude);
            }
            else {
                Toast.makeText(HerbPlotActivity.this, "定位失败", Toast.LENGTH_SHORT).show();
            }

        });
    }
    private final ItemClickCallback<SpeciesMainInfo> mItemClickCallback = (data) -> {
        mViewModel.onGoForward();
        Intent intent = new Intent(HerbPlotActivity.this, HerbSpeciesActivity.class);
        intent.putExtra(Macro.SAMPLEPLOT_ID, data.plotId);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.SPECIES_ID, data.speciesId);
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
        Intent intent = new Intent(HerbPlotActivity.this, HerbSpeciesActivity.class);
        intent.putExtra(Macro.SAMPLEPLOT_ID, mViewModel.plotId);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        intent.putExtra(Macro.SPECIES_ID, mViewModel.generateSpeciesId());
        startActivity(intent);
    }

}
