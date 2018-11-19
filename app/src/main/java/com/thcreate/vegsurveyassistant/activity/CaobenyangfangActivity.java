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
import com.thcreate.vegsurveyassistant.databinding.ActivityCaobenyangfangBinding;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.CaobenyangfangActivityViewModel;

import java.util.Calendar;

public class CaobenyangfangActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private CaobenyangfangActivityViewModel mViewModel;
    private ActivityCaobenyangfangBinding mBinding;

    private int mAction;
    private String mYangdiCode;
    private String mYangfangCode;

    private boolean mHasBelongQiaomuyangfang = false;
    private boolean mHasBelongGuanmuyangfang = false;
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

        int type = intent.getIntExtra(Macro.YANGDI_TYPE, Macro.TREE);
        if (type == Macro.TREE){
            mHasBelongQiaomuyangfang = true;
            mHasBelongGuanmuyangfang = true;
        }
        else if (type == Macro.BUSH){
            mHasBelongGuanmuyangfang = true;
        }

        mAction = intent.getIntExtra(Macro.ACTION, Macro.ACTION_ADD);
        if (mAction == Macro.ACTION_ADD){
            mYangfangCode = IdGenerator.getId(1, Macro.CAOBEN_YANGFANG);
        }
        if (mAction == Macro.ACTION_EDIT){
            mYangfangCode = intent.getStringExtra(Macro.CAOBENYANGFANG_CODE);
        }
    }
    private void initBinding(){
        CaobenyangfangActivityViewModel.Factory factory = new CaobenyangfangActivityViewModel.Factory(
                getApplication(), mAction, mYangdiCode, mYangfangCode
        );
        mViewModel = ViewModelProviders.of(this, factory)
                .get(CaobenyangfangActivityViewModel.class);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_caobenyangfang);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(findViewById(R.id.toolbar));

        if(!mHasBelongQiaomuyangfang){
            findViewById(R.id.belong_qiaomuyangfang_code_textview).setVisibility(View.GONE);
            findViewById(R.id.belong_qiaomuyangfang_code_edittext).setVisibility(View.GONE);
        }
        if (!mHasBelongGuanmuyangfang){
            findViewById(R.id.belong_guanmuyangfang_code_textview).setVisibility(View.GONE);
            findViewById(R.id.belong_guanmuyangfang_code_edittext).setVisibility(View.GONE);
        }

        longitutdeEditText = findViewById(R.id.longitude_edit_text);
        latitudeEditText = findViewById(R.id.latitude_edit_text);
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
        Intent intent = new Intent(CaobenyangfangActivity.this, CaobenwuzhongActivity.class);
        intent.putExtra(Macro.CAOBENYANGFANG_CODE, mYangfangCode);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        startActivity(intent);
    }

    public void onAutoPosition(View v){
        longitutdeEditText.setText("testtesttest");
        latitudeEditText.setText("testtesttest");
    }
}
