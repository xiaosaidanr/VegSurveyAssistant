package com.thcreate.vegsurveyassistant.activity;

import android.app.DatePickerDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.databinding.ActivityYangdianBinding;
import com.thcreate.vegsurveyassistant.db.entity.Yangdian;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.YangdianActivityViewModel;

import java.util.Calendar;

public class YangdianActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    private static final String YANGDIAN_DATA = "yangdianData";

    private YangdianActivityViewModel mViewModel;
    private ActivityYangdianBinding mBinding;

    private int mAction;
    private String mYangdianCode;

    private TextView dateTextView;
    private EditText longitutdeEditText;
    private EditText latitudeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setIsHandleBackPressed(true);

        initParam(savedInstanceState);
        initBinding(savedInstanceState);
        initLayout();
    }
    private void initParam(Bundle savedInstanceState){
        if (savedInstanceState == null){
            mAction = getIntent().getIntExtra(Macro.ACTION, Macro.ACTION_ADD);
            if (mAction == Macro.ACTION_ADD){
                //TODO userid1
                mYangdianCode = IdGenerator.getId(1, Yangdian.class);
            }
            if (mAction == Macro.ACTION_EDIT){
                mYangdianCode = getIntent().getStringExtra(Macro.YANGDIAN_CODE);
            }
        }
        else {
            mAction = savedInstanceState.getInt(Macro.ACTION);
            mYangdianCode = savedInstanceState.getString(Macro.YANGDIAN_CODE);
        }
    }
    private void initBinding(Bundle savedInstanceState){
        mViewModel = ViewModelProviders.of(this)
                .get(YangdianActivityViewModel.class);
        if (savedInstanceState == null){
            mViewModel.initYangdian(mAction, mYangdianCode, null);
        }
        else {
            mViewModel.initYangdian(mAction, mYangdianCode, (Yangdian)savedInstanceState.getParcelable(YANGDIAN_DATA));
        }
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


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Macro.YANGDIAN_CODE, mYangdianCode);
        if (mAction == Macro.ACTION_ADD || mAction == Macro.ACTION_ADD_RESTORE){
            outState.putInt(Macro.ACTION, Macro.ACTION_ADD_RESTORE);
        }
        if (mAction == Macro.ACTION_EDIT || mAction == Macro.ACTION_EDIT_RESTORE){
            outState.putInt(Macro.ACTION, Macro.ACTION_EDIT_RESTORE);
        }
        outState.putParcelable(YANGDIAN_DATA, mViewModel.yangdian.getValue());
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
