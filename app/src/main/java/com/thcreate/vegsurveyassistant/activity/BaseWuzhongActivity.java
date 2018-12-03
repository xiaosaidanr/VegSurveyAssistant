package com.thcreate.vegsurveyassistant.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.viewmodel.BaseWuzhongActivityViewModel;

import java.lang.reflect.ParameterizedType;

public class BaseWuzhongActivity<U extends BaseWuzhongActivityViewModel> extends BaseActivity {

    Class<U> clazzT;

    U mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIsHandleBackPressed(true);
        clazzT = (Class <U>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        mViewModel = ViewModelProviders.of(this).get(clazzT);
        initViewModel(savedInstanceState);
        initOnBackPressed();
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

    private void initOnBackPressed(){
        if (mViewModel.action.equals(Macro.ACTION_EDIT) || mViewModel.action.equals(Macro.ACTION_EDIT_RESTORE)){
            setmAlertDialog("放弃本次物种编辑?", null, null);
        }
        else{
            setmAlertDialog("放弃本次物种添加?", null, null);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState = mViewModel.onSaveViewModelState(outState);
        super.onSaveInstanceState(outState);
    }

}
