package com.thcreate.vegsurveyassistant.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.adapter.PictureAdapter;
import com.thcreate.vegsurveyassistant.adapter.RecyclerViewSwipeDismissController;
import com.thcreate.vegsurveyassistant.adapter.SpeciesAdapter;
import com.thcreate.vegsurveyassistant.adapter.ItemClickCallback;
import com.thcreate.vegsurveyassistant.databinding.ActivityHerbplotBinding;
import com.thcreate.vegsurveyassistant.db.entity.PlotPictureEntity;
import com.thcreate.vegsurveyassistant.db.entity.PlotPlotEntity;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.PlotMainInfo;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.SpeciesMainInfo;
import com.thcreate.vegsurveyassistant.model.PlotPlotRelationData;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.HerbPlotActivityViewModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HerbPlotActivity extends BaseSampleplotActivity<HerbPlotActivityViewModel> implements DatePickerDialog.OnDateSetListener, AppCompatSpinner.OnItemSelectedListener {

    private ActivityHerbplotBinding mBinding;

    private AppCompatSpinner mAppCompatSpinner;
    private ArrayAdapter<PlotPlotRelationData> parentPlotCodeSpinnerAdapter;
    private PlotPlotRelationTask mTask;


    private SpeciesAdapter mSpeciesAdapter;
    private PictureAdapter mPictureAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initLayout();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initBinding(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_herbplot);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(mBinding.toolbar);

        mAppCompatSpinner = findViewById(R.id.belong_plot_code_spinner);
        mAppCompatSpinner.setOnItemSelectedListener(this);

        if (mViewModel.landType.getValue().equals(Macro.SAMPLELAND_TYPE_TREE) || mViewModel.landType.getValue().equals(Macro.SAMPLELAND_TYPE_BUSH)){
            parentPlotCodeSpinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<>());
            mTask = new PlotPlotRelationTask(this);
            mTask.execute();
            mAppCompatSpinner.setAdapter(parentPlotCodeSpinnerAdapter);
        }

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
                mSpeciesAdapter.setDataList(dataList);
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

    private final ItemClickCallback<PlotPictureEntity> mPictureClickCallback = (data)->{
        Intent intent = new Intent(HerbPlotActivity.this, PicturePreviewActivity.class);
        intent.putExtra(Macro.IMAGE_PATH, data.localAddr);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        PlotPlotRelationData tmp = parentPlotCodeSpinnerAdapter.getItem(position);
        mViewModel.parentPlotId = tmp.childId;
        mViewModel.parentPlotType = tmp.childType;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mTask != null){
            mTask.cancel(true);
        }
    }

    private static class PlotPlotRelationTask extends AsyncTask<Void, Void, List<PlotPlotRelationData>> {
        private WeakReference<HerbPlotActivity> weakReferenceActivty;
        public PlotPlotRelationTask(HerbPlotActivity activity) {
            weakReferenceActivty = new WeakReference<>(activity);
        }
        @Override
        protected List<PlotPlotRelationData> doInBackground(Void... params) {
            ArrayList<PlotPlotRelationData> result = new ArrayList<>();
            try {
                HerbPlotActivity activity = weakReferenceActivty.get();
                result.add(new PlotPlotRelationData(null, null, null));
                Map<String,PlotMainInfo> allUpperPlotMainInfoMap = new HashMap<>();
                Map<String, PlotPlotEntity> allPlotPlotEntityMap = new HashMap<>();
                List<PlotMainInfo> arborAndShrubPlotInfoList = activity.mViewModel.getArborAndShrubPlotEntityListSync();
                if (arborAndShrubPlotInfoList == null){
                    return result;
                }
                for (PlotMainInfo item:
                        arborAndShrubPlotInfoList) {
                    allUpperPlotMainInfoMap.put(item.plotId, item);
                }
                List<PlotPlotEntity> plotPlotEntityList = activity.mViewModel.getPlotPlotEntityListByLandIdSync(activity.mViewModel.landId);
                if (plotPlotEntityList != null && plotPlotEntityList.size() > 0){
                    for (PlotPlotEntity item:
                            plotPlotEntityList) {
                        allPlotPlotEntityMap.put(item.childId, item);
                    }
                }
                for (PlotMainInfo item:
                        arborAndShrubPlotInfoList) {
                    PlotPlotRelationData plotPlotRelationData = new PlotPlotRelationData(item.plotId, item.code, item.type);
                    if (allPlotPlotEntityMap.containsKey(item.plotId)){
                        PlotPlotEntity plotPlotEntity = allPlotPlotEntityMap.get(item.plotId);
                        plotPlotRelationData.parentId = plotPlotEntity.parentId;
                        plotPlotRelationData.parentType = plotPlotEntity.parentType;
                        if (allUpperPlotMainInfoMap.containsKey(plotPlotEntity.parentId)){
                            PlotMainInfo plotMainInfo = allUpperPlotMainInfoMap.get(plotPlotEntity.parentId);
                            plotPlotRelationData.parentCode = plotMainInfo.code;
                        }
                    }
                    result.add(plotPlotRelationData);
                }
                if (activity.mViewModel.action.equals(Macro.ACTION_ADD) || activity.mViewModel.action.equals(Macro.ACTION_EDIT)){
                    PlotPlotEntity data = activity.mViewModel.getPlotPlotEntityByChildIdSync(activity.mViewModel.plotId);
                    if (data != null){
                        activity.mViewModel.parentPlotId = data.parentId;
                        activity.mViewModel.parentPlotType = data.parentType;
                    }
                }
            }
            catch (Exception e){
                e.printStackTrace();
                result.clear();
            }
            return result;
        }
        @Override
        protected void onPostExecute(List<PlotPlotRelationData> result) {
            super.onPostExecute(result);
            HerbPlotActivity activity;
            if((activity = weakReferenceActivty.get())!=null){
                if (activity.parentPlotCodeSpinnerAdapter != null){
                    int selectedId = -1;
                    for (int i = 0; i < result.size(); i++) {
                        PlotPlotRelationData item = result.get(i);
                        if (item.childId != null){
                            if (item.childId.equals(activity.mViewModel.parentPlotId)){
                                selectedId = i;
                            }
                        }
                        activity.parentPlotCodeSpinnerAdapter.add(item);

                    }
                    activity.mAppCompatSpinner.setSelection(selectedId, true);
                    activity.parentPlotCodeSpinnerAdapter.notifyDataSetChanged();
                }
            }
        }
    }

}
