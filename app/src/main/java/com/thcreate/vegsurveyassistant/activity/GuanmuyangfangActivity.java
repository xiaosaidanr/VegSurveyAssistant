package com.thcreate.vegsurveyassistant.activity;

import android.app.DatePickerDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.adapter.ItemClickCallback;
import com.thcreate.vegsurveyassistant.adapter.WuzhongAdapter;
import com.thcreate.vegsurveyassistant.databinding.ActivityGuanmuyangfangBinding;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.YangfangCode;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.GuanmuyangfangActivityViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GuanmuyangfangActivity extends BaseYangfangActivity<GuanmuyangfangActivityViewModel> implements DatePickerDialog.OnDateSetListener {

    private ActivityGuanmuyangfangBinding mBinding;

    private EditText longitutdeEditText;
    private EditText latitudeEditText;

    private ArrayAdapter<String> qiaomuyangfangCodeSpinnerAdapter;

    private WuzhongAdapter mWuzhongAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initLayout();
    }
    private void initBinding(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_guanmuyangfang);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(findViewById(R.id.toolbar));

        if (mViewModel.yangdiType.getValue().equals(Macro.YANGDI_TYPE_TREE)){
            AppCompatSpinner pAppCompatSpinner = findViewById(R.id.belong_qiaomuyangfang_code_spinner);
            qiaomuyangfangCodeSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, new ArrayList<String>());
            pAppCompatSpinner.setAdapter(qiaomuyangfangCodeSpinnerAdapter);
            mViewModel.getQiaomuyangfangCodeList().observe(this, (dataList)->{
                if (dataList != null){
                    if (dataList.size()>0){
                        qiaomuyangfangCodeSpinnerAdapter.clear();
                        for (YangfangCode item: dataList){
                            qiaomuyangfangCodeSpinnerAdapter.add(item.yangfangCode);
                        }
                        qiaomuyangfangCodeSpinnerAdapter.notifyDataSetChanged();
                    }
                }
            });
        }

        longitutdeEditText = findViewById(R.id.longitude_edit_text);
        latitudeEditText = findViewById(R.id.latitude_edit_text);

        findViewById(R.id.fab).setOnClickListener((v)->{
            save();
            finish();
        });

        mWuzhongAdapter = new WuzhongAdapter<GuanmuWuzhong>(mWuzhongItemClickCallback);
        ((RecyclerView)findViewById(R.id.wuzhong_list)).setAdapter(mWuzhongAdapter);
        subscribeUi(mViewModel.getWuzhongList());
    }
    private void subscribeUi(LiveData<List<GuanmuWuzhong>> liveData) {
        liveData.observe(this, (wuzhongList)->{
            if (wuzhongList != null) {
                mWuzhongAdapter.setWuzhongList(wuzhongList);
                mViewModel.wuzhongCount.setValue(String.valueOf(wuzhongList.size()));
            } else {
                mViewModel.wuzhongCount.setValue("0");
            }
            mBinding.executePendingBindings();
        });
    }
    private final ItemClickCallback<GuanmuWuzhong> mWuzhongItemClickCallback = (wuzhong) -> {
        mViewModel.onGoForward();
        Intent intent = new Intent(GuanmuyangfangActivity.this, GuanmuwuzhongActivity.class);
        intent.putExtra(Macro.YANGFANG_CODE, wuzhong.yangfangCode);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.WUZHONG_CODE, wuzhong.wuzhongCode);
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

    public void onAddWuzhong(View v){
        mViewModel.onGoForward();
        Intent intent = new Intent(GuanmuyangfangActivity.this, GuanmuwuzhongActivity.class);
        intent.putExtra(Macro.YANGFANG_CODE, mViewModel.yangfangCode);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        intent.putExtra(Macro.WUZHONG_CODE, mViewModel.generateWuzhongCode(GuanmuWuzhong.class));
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
