package com.thcreate.vegsurveyassistant.activity;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.thcreate.vegsurveyassistant.R;
import com.thcreate.vegsurveyassistant.adapter.YangfangAdapter;
import com.thcreate.vegsurveyassistant.adapter.ItemClickCallback;
import com.thcreate.vegsurveyassistant.databinding.ActivityCaodiyangdiBinding;
import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;
import com.thcreate.vegsurveyassistant.db.entity.Yangdi;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.CaodiyangdiActivityViewModel;

import java.util.List;

public class CaodiyangdiActivity extends BaseYangdiActivity<CaodiyangdiActivityViewModel> {

    private ActivityCaodiyangdiBinding mBinding;

    private EditText longitutdeEditText;
    private EditText latitudeEditText;
    private TextView caobenyangfangCountTextView;

    private YangfangAdapter mCaobenyangfangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initBinding();
        initLayout();
    }
    private void initBinding(){
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_caodiyangdi);
        mBinding.setViewmodel(mViewModel);
        mBinding.setLifecycleOwner(this);
    }
    private void initLayout(){
        setSupportActionBar(findViewById(R.id.toolbar));

        longitutdeEditText = findViewById(R.id.longitude_edit_text);
        latitudeEditText = findViewById(R.id.latitude_edit_text);
        caobenyangfangCountTextView = findViewById(R.id.caobenyangfang_count_text_view);

        findViewById(R.id.fab).setOnClickListener((v)->{
            save();
            finish();
        });

        mCaobenyangfangAdapter = new YangfangAdapter<CaobenYangfang>(mYangfangItemClickCallback);
        ((RecyclerView)findViewById(R.id.yangfang_list)).setAdapter(mCaobenyangfangAdapter);
        subscribeUi(mViewModel.getCaobenyangfangList());
    }
    private void subscribeUi(LiveData<List<CaobenYangfang>> liveData) {
        liveData.observe(this, (yangfangList)->{
            if (yangfangList != null) {
                mCaobenyangfangAdapter.setYangfangList(yangfangList);
                caobenyangfangCountTextView.setText(String.valueOf(yangfangList.size()));
            } else {
                caobenyangfangCountTextView.setText("0");
            }
            mBinding.executePendingBindings();
        });
    }
    private final ItemClickCallback<CaobenYangfang> mYangfangItemClickCallback = (yangfang) -> {
        Intent intent = new Intent(CaodiyangdiActivity.this, CaobenyangfangActivity.class);
        intent.putExtra(Macro.YANGDI_CODE, yangfang.yangdiCode);
        intent.putExtra(Macro.YANGDI_TYPE, Macro.YANGDI_TYPE_GRASS);
        intent.putExtra(Macro.ACTION, Macro.ACTION_EDIT);
        intent.putExtra(Macro.YANGFANG_CODE, yangfang.yangfangCode);
        startActivity(intent);
    };

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelable(YANGDI_DATA, mViewModel.yangdi.getValue());
//    }

    public void onAddYangfang(View v){
        Intent intent = new Intent(CaodiyangdiActivity.this, CaobenyangfangActivity.class);
        intent.putExtra(Macro.YANGDI_CODE, mViewModel.yangdiCode);
        intent.putExtra(Macro.YANGDI_TYPE, Macro.YANGDI_TYPE_GRASS);
        intent.putExtra(Macro.ACTION, Macro.ACTION_ADD);
        intent.putExtra(Macro.YANGFANG_CODE, mViewModel.generateYangfangCode(CaobenYangfang.class));
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
