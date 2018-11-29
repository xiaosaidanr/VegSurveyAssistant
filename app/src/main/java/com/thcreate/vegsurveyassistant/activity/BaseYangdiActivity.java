package com.thcreate.vegsurveyassistant.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.thcreate.vegsurveyassistant.db.entity.Yangdi;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;

public class BaseYangdiActivity extends BaseActivity {

    static final String YANGDI_DATA = "yangdiData";

    int mAction;
    String mYangdiCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIsHandleBackPressed(true);
        initParam(savedInstanceState);
    }

    private void initParam(Bundle savedInstanceState){
        if (savedInstanceState == null){
            mAction = getIntent().getIntExtra(Macro.ACTION, Macro.ACTION_ADD);
            if (mAction == Macro.ACTION_ADD){
                //TODO userid1
                mYangdiCode = IdGenerator.getId(1, Yangdi.class);
            }
            if (mAction == Macro.ACTION_EDIT){
                mYangdiCode = getIntent().getStringExtra(Macro.YANGDI_CODE);
            }
        }
        else {
            mAction = savedInstanceState.getInt(Macro.ACTION);
            mYangdiCode = savedInstanceState.getString(Macro.YANGDI_CODE);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mAction == Macro.ACTION_ADD || mAction == Macro.ACTION_ADD_RESTORE){
            outState.putInt(Macro.ACTION, Macro.ACTION_ADD_RESTORE);
        }
        if (mAction == Macro.ACTION_EDIT || mAction == Macro.ACTION_EDIT_RESTORE){
            outState.putInt(Macro.ACTION, Macro.ACTION_EDIT_RESTORE);
        }
        outState.putString(Macro.YANGDI_CODE, mYangdiCode);

    }
}
