package com.thcreate.vegsurveyassistant.activity;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivityQiaomuyangfangBinding;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.QiaomuyangfangActivityViewModel;

import java.util.Calendar;

public class QiaomuyangfangActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private QiaomuyangfangActivityViewModel mViewModel;
    private ActivityQiaomuyangfangBinding mBinding;

    private int mAction;
    private String mYangdiCode;
    private String mYangfangCode;

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
        Intent intent = getIntent();

        mYangdiCode = intent.getStringExtra(Macro.YANGDI_CODE);

        mAction = intent.getIntExtra(Macro.ACTION, Macro.ACTION_ADD);
        if (mAction == Macro.ACTION_ADD){
            mYangfangCode = IdGenerator.getId(1, Macro.QIAOMU_YANGFANG);
        }
        if (mAction == Macro.ACTION_EDIT){
            mYangfangCode = intent.getStringExtra(Macro.QIAOMUYANGFANG_CODE);
        }
    }
    private void initBinding(){
        QiaomuyangfangActivityViewModel.Factory factory = new QiaomuyangfangActivityViewModel.Factory(
                getApplication(), mAction, mYangdiCode, mYangfangCode
        );
        mViewModel = ViewModelProviders.of(this, factory)
                .get(QiaomuyangfangActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_qiaomuyangfang);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(findViewById(R.id.toolbar));

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
        TextView textView = findViewById(R.id.date_selected);
        if (textView != null){
            textView.setText(String.valueOf(String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(day)));
        }
    }



    public void onAddWuzhong(View v){
        Intent intent = new Intent(QiaomuyangfangActivity.this, QiaomuwuzhongActivity.class);
        intent.putExtra(Macro.QIAOMUYANGFANG_CODE, mYangfangCode);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        startActivity(intent);
    }



    public void onAutoPosition(View v){
        longitutdeEditText.setText("testtesttest");
        latitudeEditText.setText("testtesttest");
    }



    private void save(){
        mViewModel.save();
        finish();
    }
}
