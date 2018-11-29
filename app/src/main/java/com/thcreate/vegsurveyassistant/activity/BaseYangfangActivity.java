package com.thcreate.vegsurveyassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.thcreate.vegsurveyassistant.db.entity.BaseYangfang;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.lang.reflect.ParameterizedType;

public class BaseYangfangActivity<T extends BaseYangfang> extends BaseActivity {

    static final String YANGFANG_DATA = "yangfangData";

    String mYangdiCode;
    int mType;
    int mAction;
    String mYangfangCode;
    Class<T> clazzT;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIsHandleBackPressed(true);
        clazzT = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        initParam(savedInstanceState);
    }
    private void initParam(Bundle savedInstanceState){
        if (savedInstanceState == null){
            Intent intent = getIntent();
            mYangdiCode = intent.getStringExtra(Macro.YANGDI_CODE);
            mType = intent.getIntExtra(Macro.YANGDI_TYPE, Macro.TREE);
            mAction = intent.getIntExtra(Macro.ACTION, Macro.ACTION_ADD);
            if (mAction == Macro.ACTION_ADD){
                //TODO userid1
                mYangfangCode = IdGenerator.getId(1, clazzT);
            }
            if (mAction == Macro.ACTION_EDIT){
                mYangfangCode = intent.getStringExtra(Macro.YANGFANG_CODE);
            }
        }
        else {
            mYangdiCode = savedInstanceState.getString(Macro.YANGDI_CODE);
            mType = savedInstanceState.getInt(Macro.YANGDI_TYPE);
            mAction = savedInstanceState.getInt(Macro.ACTION);
            mYangfangCode = savedInstanceState.getString(Macro.YANGFANG_CODE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Macro.YANGDI_CODE, mYangdiCode);
        outState.putInt(Macro.YANGDI_TYPE, mType);
        if (mAction == Macro.ACTION_ADD || mAction == Macro.ACTION_ADD_RESTORE){
            outState.putInt(Macro.ACTION, Macro.ACTION_ADD_RESTORE);
        }
        if (mAction == Macro.ACTION_EDIT || mAction == Macro.ACTION_EDIT_RESTORE){
            outState.putInt(Macro.ACTION, Macro.ACTION_EDIT_RESTORE);
        }
        outState.putString(Macro.YANGFANG_CODE, mYangfangCode);
    }
}
