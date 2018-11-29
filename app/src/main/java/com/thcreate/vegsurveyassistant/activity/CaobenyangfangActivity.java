package com.thcreate.vegsurveyassistant.activity;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.adapter.WuzhongAdapter;
import com.thcreate.vegsurveyassistant.adapter.ItemClickCallback;
import com.thcreate.vegsurveyassistant.databinding.ActivityCaobenyangfangBinding;
import com.thcreate.vegsurveyassistant.db.entity.CaobenWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.CaobenyangfangActivityViewModel;

import java.util.Calendar;
import java.util.List;

public class CaobenyangfangActivity extends BaseYangfangActivity<CaobenYangfang> implements DatePickerDialog.OnDateSetListener {

    private CaobenyangfangActivityViewModel mViewModel;
    private ActivityCaobenyangfangBinding mBinding;

    private EditText longitutdeEditText;
    private EditText latitudeEditText;
    private TextView wuzhongCountTextView;

    private WuzhongAdapter mWuzhongAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding(savedInstanceState);
        initLayout();
    }
    private void initBinding(Bundle savedInstanceState){
        mViewModel = ViewModelProviders.of(this).get(CaobenyangfangActivityViewModel.class);
        if (savedInstanceState == null){
            mViewModel.initYangfang(mYangdiCode, mAction, mYangfangCode, null);
        }
        else {
            mViewModel.initYangfang(mYangdiCode, mAction, mYangfangCode, savedInstanceState.getParcelable(YANGFANG_DATA));
        }
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_caobenyangfang);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(findViewById(R.id.toolbar));

        if (mType == Macro.GRASS){
            findViewById(R.id.belong_qiaomuyangfang_code_textview).setVisibility(View.GONE);
            findViewById(R.id.belong_qiaomuyangfang_code_edittext).setVisibility(View.GONE);
            findViewById(R.id.belong_guanmuyangfang_code_textview).setVisibility(View.GONE);
            findViewById(R.id.belong_guanmuyangfang_code_edittext).setVisibility(View.GONE);
        }
        else if (mType == Macro.BUSH){
            findViewById(R.id.belong_qiaomuyangfang_code_textview).setVisibility(View.GONE);
            findViewById(R.id.belong_qiaomuyangfang_code_edittext).setVisibility(View.GONE);
        }

        longitutdeEditText = findViewById(R.id.longitude_edit_text);
        latitudeEditText = findViewById(R.id.latitude_edit_text);
        wuzhongCountTextView = findViewById(R.id.wuzhong_count_text_view);

        findViewById(R.id.fab).setOnClickListener((v)->{
            save();
            finish();
        });

        mWuzhongAdapter = new WuzhongAdapter<CaobenWuzhong>(mWuzhongItemClickCallback);
        ((RecyclerView)findViewById(R.id.wuzhong_list)).setAdapter(mWuzhongAdapter);
        subscribeUi(mViewModel.getCaobenwuzhongList());
    }
    private void subscribeUi(LiveData<List<CaobenWuzhong>> liveData) {
        liveData.observe(this, (wuzhongList)->{
            if (wuzhongList != null) {
                mWuzhongAdapter.setWuzhongList(wuzhongList);
                wuzhongCountTextView.setText(String.valueOf(wuzhongList.size()));
            } else {
                wuzhongCountTextView.setText("0");
            }
            mBinding.executePendingBindings();
        });
    }
    private final ItemClickCallback<CaobenWuzhong> mWuzhongItemClickCallback = (wuzhong) -> {
        Intent intent = new Intent(CaobenyangfangActivity.this, CaobenwuzhongActivity.class);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.YANGFANG_CODE, wuzhong.yangfangCode);
        intent.putExtra(Macro.WUZHONG_CODE, wuzhong.wuzhongCode);
        startActivity(intent);
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(YANGFANG_DATA, mViewModel.yangfang.getValue());
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
        TextView textView = findViewById(R.id.date_text_view);
        if (textView != null){
            textView.setText(String.valueOf(String.valueOf(year)+"-"+String.valueOf(month+1)+"-"+String.valueOf(day)));
        }
    }

    public void onAddWuzhong(View v){
        Intent intent = new Intent(CaobenyangfangActivity.this, CaobenwuzhongActivity.class);
        intent.putExtra(Macro.YANGFANG_CODE, mYangfangCode);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        startActivity(intent);
    }

    public void onAutoPosition(View v){
        longitutdeEditText.setText("testtesttest");
        latitudeEditText.setText("testtesttest");
    }

    private boolean save(){
        return mViewModel.save();
    }
}
