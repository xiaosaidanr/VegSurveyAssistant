package com.thcreate.vegsurveyassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.thcreate.vegsurveyassistant.db.entity.BaseWuzhong;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.lang.reflect.ParameterizedType;

public class BaseWuzhongActivity<T extends BaseWuzhong> extends BaseActivity {

    static final String WUZHONG_DATA = "wuzhongData";

    String mYangfangCode;
    int mAction;
    String mWuzhongCode;
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
            mYangfangCode = intent.getStringExtra(Macro.YANGFANG_CODE);
            mAction = intent.getIntExtra(Macro.ACTION, Macro.ACTION_ADD);
            if (mAction == Macro.ACTION_ADD){
                //TODO userid1
                mWuzhongCode = IdGenerator.getId(1, clazzT);
            }
            else {
                mWuzhongCode = intent.getStringExtra(Macro.WUZHONG_CODE);
            }
        }
        else {
            mYangfangCode = savedInstanceState.getString(Macro.YANGFANG_CODE);
            mAction = savedInstanceState.getInt(Macro.ACTION);
            mWuzhongCode = savedInstanceState.getString(Macro.WUZHONG_CODE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Macro.YANGFANG_CODE, mYangfangCode);
        if (mAction == Macro.ACTION_ADD || mAction == Macro.ACTION_ADD_RESTORE){
            outState.putInt(Macro.ACTION, Macro.ACTION_ADD_RESTORE);
        }
        if (mAction == Macro.ACTION_EDIT || mAction == Macro.ACTION_EDIT_RESTORE){
            outState.putInt(Macro.ACTION, Macro.ACTION_EDIT_RESTORE);
        }
        outState.putString(Macro.WUZHONG_CODE, mWuzhongCode);
    }

}
