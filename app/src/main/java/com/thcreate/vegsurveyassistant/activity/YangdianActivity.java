package com.thcreate.vegsurveyassistant.activity;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivityYangdianBinding;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.YangdianActivityViewModel;

import java.util.Calendar;

public class YangdianActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private YangdianActivityViewModel mViewModel;
    private ActivityYangdianBinding mBinding;

    private int mAction;
    private String mYangdianCode;

    private TextView dateText;
    private EditText longitutdeEditText;
    private EditText latitudeEditText;

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
            mYangdianCode = IdGenerator.getId(1, Macro.YANGDIAN);
        }
        if (mAction == Macro.ACTION_EDIT){
            mYangdianCode = getIntent().getStringExtra(Macro.YANGDIAN_CODE);
        }
    }
    private void initBinding(){
        YangdianActivityViewModel.Factory factory = new YangdianActivityViewModel.Factory(
                getApplication(), mAction, mYangdianCode);
        mViewModel = ViewModelProviders.of(this, factory)
                .get(YangdianActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_yangdian);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(mBinding.toolbar);
        dateText = findViewById(R.id.date_selected);
        longitutdeEditText = findViewById(R.id.longitude_edit_text);
        latitudeEditText = findViewById(R.id.latitude_edit_text);
        findViewById(R.id.fab).setOnClickListener((v)->{
            save();
        });
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
        dateText.setText(String.valueOf(String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(day)));
    }



    public void onAutoPosition(View v){
        longitutdeEditText.setText("testtesttest");
        latitudeEditText.setText("testtesttest");
    }



    public void save(){
        mViewModel.Save();
        finish();
    }
}
