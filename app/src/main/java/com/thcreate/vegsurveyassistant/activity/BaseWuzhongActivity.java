package com.thcreate.vegsurveyassistant.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.BaseWuzhongActivityViewModel;

import java.lang.reflect.ParameterizedType;

public class BaseWuzhongActivity<U extends BaseWuzhongActivityViewModel> extends BaseActivity {

//    static final String WUZHONG_DATA = "wuzhongData";

//    String mYangfangCode;
//    String mAction;
//    String mWuzhongCode;
    Class<U> clazzT;

    U mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIsHandleBackPressed(true);
        clazzT = (Class <U>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        mViewModel = ViewModelProviders.of(this).get(clazzT);
//        initParam(savedInstanceState);
        initViewModel(savedInstanceState);
    }

    private void initViewModel(@Nullable Bundle savedInstanceState){
        if (savedInstanceState == null){
            savedInstanceState = new Bundle();
            Intent intent = getIntent();
            savedInstanceState.putString(Macro.YANGFANG_CODE, intent.getStringExtra(Macro.YANGFANG_CODE));
            savedInstanceState.putString(Macro.ACTION, intent.getStringExtra(Macro.ACTION));
            savedInstanceState.putString(Macro.WUZHONG_CODE, intent.getStringExtra(Macro.WUZHONG_CODE));
        }
        mViewModel.init(savedInstanceState);
    }

//    private void initParam(Bundle savedInstanceState){
//        if (savedInstanceState == null){
//            Intent intent = getIntent();
//            mYangfangCode = intent.getStringExtra(Macro.YANGFANG_CODE);
//            mAction = intent.getStringExtra(Macro.ACTION);
//            if (mAction.equals(Macro.ACTION_ADD)){
//                mWuzhongCode = IdGenerator.getId(1, clazzT);
//            }
//            else {
//                mWuzhongCode = intent.getStringExtra(Macro.WUZHONG_CODE);
//            }
//        }
//        else {
//            mYangfangCode = savedInstanceState.getString(Macro.YANGFANG_CODE);
//            mAction = savedInstanceState.getString(Macro.ACTION);
//            mWuzhongCode = savedInstanceState.getString(Macro.WUZHONG_CODE);
//        }
//    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = mViewModel.onSaveViewModelState(outState);
        super.onSaveInstanceState(outState);
//        outState.putString(Macro.YANGFANG_CODE, mYangfangCode);
//        if (mAction.equals(Macro.ACTION_ADD) || mAction.equals(Macro.ACTION_ADD_RESTORE)){
//            outState.putString(Macro.ACTION, Macro.ACTION_ADD_RESTORE);
//        }
//        if (mAction.equals(Macro.ACTION_EDIT) || mAction.equals(Macro.ACTION_EDIT_RESTORE)){
//            outState.putString(Macro.ACTION, Macro.ACTION_EDIT_RESTORE);
//        }
//        outState.putString(Macro.WUZHONG_CODE, mWuzhongCode);
//        outState.putParcelable(WUZHONG_DATA, (Parcelable) mViewModel.wuzhong.getValue());
    }

}
