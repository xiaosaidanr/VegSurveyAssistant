package com.thcreate.vegsurveyassistant.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.BaseYangfangActivityViewModel;

import java.lang.reflect.ParameterizedType;

public class BaseYangfangActivity<U extends BaseYangfangActivityViewModel> extends BaseActivity {

//    static final String YANGFANG_DATA = "yangfangData";

//    String mYangdiCode;
//    int mType;
//    int mAction;
//    String mYangfangCode;

    U mViewModel;

//    Class<T> clazzT;
    Class<U> clazzU;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIsHandleBackPressed(true);
//        clazzT = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        clazzU = (Class <U>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        mViewModel = ViewModelProviders.of(this).get(clazzU);
        initViewModel(savedInstanceState);
    }
    private void initViewModel(Bundle savedInstanceState){
        if (savedInstanceState == null){
            savedInstanceState = new Bundle();
            Intent intent = getIntent();
            savedInstanceState.putString(Macro.YANGDI_CODE, intent.getStringExtra(Macro.YANGDI_CODE));
            savedInstanceState.putString(Macro.YANGDI_TYPE, intent.getStringExtra(Macro.YANGDI_TYPE));
            savedInstanceState.putString(Macro.ACTION, intent.getStringExtra(Macro.ACTION));
            savedInstanceState.putString(Macro.YANGFANG_CODE, intent.getStringExtra(Macro.YANGFANG_CODE));
        }
        mViewModel.init(savedInstanceState);
//        if (savedInstanceState == null){
//            Intent intent = getIntent();
//            mYangdiCode = intent.getStringExtra(Macro.YANGDI_CODE);
//            mType = intent.getIntExtra(Macro.YANGDI_TYPE, Macro.TREE);
//            mAction = intent.getIntExtra(Macro.ACTION, Macro.ACTION_ADD);
//            if (mAction == Macro.ACTION_ADD){
//                //TODO userid1
//                mYangfangCode = IdGenerator.getId(1, clazzT);
//            }
//            if (mAction == Macro.ACTION_EDIT){
//                mYangfangCode = intent.getStringExtra(Macro.YANGFANG_CODE);
//            }
//        }
//        else {
//            mYangdiCode = savedInstanceState.getString(Macro.YANGDI_CODE);
//            mType = savedInstanceState.getInt(Macro.YANGDI_TYPE);
//            mAction = savedInstanceState.getInt(Macro.ACTION);
//            mYangfangCode = savedInstanceState.getString(Macro.YANGFANG_CODE);
//        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = mViewModel.onSaveViewModelState(outState);
        super.onSaveInstanceState(outState);

//        outState.putString(Macro.YANGDI_CODE, mYangdiCode);
//        outState.putInt(Macro.YANGDI_TYPE, mType);
//        outState.putString(Macro.YANGFANG_CODE, mYangfangCode);
//        if (mAction == Macro.ACTION_ADD || mAction == Macro.ACTION_ADD_RESTORE){
//            outState.putInt(Macro.ACTION, Macro.ACTION_ADD_RESTORE);
//        }
//        if (mAction == Macro.ACTION_EDIT || mAction == Macro.ACTION_EDIT_RESTORE){
//            outState.putInt(Macro.ACTION, Macro.ACTION_EDIT_RESTORE);
//        }
    }
}
