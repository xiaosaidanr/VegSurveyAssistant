package com.thcreate.vegsurveyassistant.activity;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivityYangdianBinding;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.YangdianActivityViewModel;

import java.util.Calendar;

public class YangdianActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    private YangdianActivityViewModel mViewModel;
    private ActivityYangdianBinding mBinding;

    private TextView dateTextView;
    private EditText longitutdeEditText;
    private EditText latitudeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIsHandleBackPressed(true);
        initViewModel(savedInstanceState);
        initBinding();
        initLayout();
        initOnBackPressed();
    }
    private void initViewModel(Bundle savedInstanceState){
        if (savedInstanceState == null){
            savedInstanceState = new Bundle();
            Intent intent = getIntent();
            savedInstanceState.putString(Macro.ACTION, intent.getStringExtra(Macro.ACTION));
            savedInstanceState.putString(Macro.YANGDIAN_CODE, intent.getStringExtra(Macro.YANGDIAN_CODE));
        }
        mViewModel = ViewModelProviders.of(this).get(YangdianActivityViewModel.class);
        mViewModel.init(savedInstanceState);
    }
    private void initBinding(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_yangdian);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(mBinding.toolbar);
        dateTextView = findViewById(R.id.date_text_view);
        longitutdeEditText = findViewById(R.id.longitude_edit_text);
        latitudeEditText = findViewById(R.id.latitude_edit_text);
        findViewById(R.id.fab).setOnClickListener((v)->{
            save();
            finish();
        });
    }
    private void initOnBackPressed(){
        if (mViewModel.action.equals(Macro.ACTION_EDIT) || mViewModel.action.equals(Macro.ACTION_EDIT_RESTORE)){
            setmAlertDialog("放弃本次样点编辑?", null, null);
        }
        else{
            setmAlertDialog("放弃本次样点添加?", null, null);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = mViewModel.onSaveViewModelState(outState);
        super.onSaveInstanceState(outState);
    }

    public void showDatePickerDialog(View v) {
        Calendar calendar=Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(this,this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
    public void onDateSet(DatePicker view, int year, int month, int day) {
        dateTextView.setText(String.valueOf(String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(day)));
    }

    public void onAutoPosition(View v){
        longitutdeEditText.setText("testtesttest");
        latitudeEditText.setText("testtesttest");
    }

    public boolean save(){
        return mViewModel.save();
    }
}
