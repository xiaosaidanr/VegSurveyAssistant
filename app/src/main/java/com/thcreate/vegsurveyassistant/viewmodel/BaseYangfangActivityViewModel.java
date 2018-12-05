package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.BaseYangfang;
import com.thcreate.vegsurveyassistant.repository.WuzhongDataRepository;
import com.thcreate.vegsurveyassistant.repository.YangfangDataRepository;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.lang.reflect.ParameterizedType;
import java.util.Date;

abstract public class BaseYangfangActivityViewModel<T extends BaseYangfang> extends AndroidViewModel {

    abstract public LiveData<T> getYangfangData();

    public <U> String generateWuzhongCode(Class<U> modelClass){
        return IdGenerator.getId(userId, modelClass);
    }

    private static final String YANGFANG_DATA = "yangfang_data";

    //TODO userid1
    private int userId = 1;

    public String yangdiCode;
    public MutableLiveData<String> yangdiType;
    public String action;
    public String yangfangCode;

    public LiveData<T> yangfang;

    public MutableLiveData<String> wuzhongCount;

    private Class<T> mClazzT;

    YangfangDataRepository mYangfangRepository;

    WuzhongDataRepository mWuzhongRepository;

    public BaseYangfangActivityViewModel(@NonNull Application application) {
        super(application);
        mYangfangRepository = ((BasicApp)application).getYangfangDataRepository();
        mWuzhongRepository = ((BasicApp)application).getWuzhongDataRepository();
        mClazzT = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        wuzhongCount = new MutableLiveData<>();
        wuzhongCount.setValue("0");
    }

    public void init(Bundle data){
        yangdiCode = data.getString(Macro.YANGDI_CODE);
        yangdiType = new MutableLiveData<>();
        yangdiType.setValue(data.getString(Macro.YANGDI_TYPE));
        action = data.getString(Macro.ACTION);
        yangfangCode = data.getString(Macro.YANGFANG_CODE);
        @Nullable T tmp = data.getParcelable(YANGFANG_DATA);
        initYangfang(tmp);
    }

    private void initYangfang(T data){
        switch (action){
            case Macro.ACTION_ADD:
                MutableLiveData<T> tmp1 = new MutableLiveData<>();
                try {
                    data = mClazzT.newInstance();
                    data.userId = userId;
                    data.yangdiCode = yangdiCode;
                    data.yangfangCode = yangfangCode;
                    tmp1.setValue(data);
                    yangfang = tmp1;
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case Macro.ACTION_ADD_RESTORE:
                MutableLiveData<T> tmp2 = new MutableLiveData<>();
                tmp2.setValue(data);
                yangfang = tmp2;
                break;
            case Macro.ACTION_EDIT:
                yangfang = getYangfangData();
                break;
            case Macro.ACTION_EDIT_RESTORE:
                MutableLiveData<T> tmp3 = new MutableLiveData<>();
                tmp3.setValue(data);
                yangfang = tmp3;
                break;
//            case Macro.ACTION_TEMP_SAVE:
//                yangfang = getYangfangData();
//                action = Macro.ACTION_TEMP_SAVE_RESTORE;
//                break;
            case Macro.ACTION_TEMP_SAVE_RESTORE:
                MutableLiveData<T> tmp4 = new MutableLiveData<>();
                tmp4.setValue(data);
                yangfang = tmp4;
                break;
            default:
                break;
        }
    }

    public void onGoForward(){
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            mYangfangRepository.insertYangfang(yangfang.getValue());
            action = Macro.ACTION_TEMP_SAVE;
        }
    }

    public void onCancel(){
        if (action.equals(Macro.ACTION_TEMP_SAVE_RESTORE)||action.equals(Macro.ACTION_TEMP_SAVE)){
            mYangfangRepository.deleteYangfang(yangfang.getValue());
        }
    }

    public Bundle onSaveViewModelState(Bundle outState) {
        outState.putString(Macro.YANGDI_CODE, yangdiCode);
        outState.putString(Macro.YANGDI_TYPE, yangdiType.getValue());
        outState.putString(Macro.YANGFANG_CODE, yangfangCode);
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_ADD_RESTORE);
        }
        if (action.equals(Macro.ACTION_EDIT) || action.equals(Macro.ACTION_EDIT_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_EDIT_RESTORE);
        }
        if (action.equals(Macro.ACTION_TEMP_SAVE) || action.equals(Macro.ACTION_TEMP_SAVE_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_TEMP_SAVE_RESTORE);
        }
        outState.putParcelable(YANGFANG_DATA, (Parcelable) yangfang.getValue());
        return outState;
    }

    public boolean save(){
        if (yangfang == null){
            return false;
        }
        T yangfangRaw = yangfang.getValue();
        if (yangfangRaw == null){
            return false;
        }
        Date dateNow = new Date();
        yangfangRaw.modifyAt = dateNow;
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            yangfangRaw.createAt = dateNow;
            mYangfangRepository.insertYangfang(yangfangRaw);
        }
        if (action.equals(Macro.ACTION_EDIT) || action.equals(Macro.ACTION_EDIT_RESTORE)){
            mYangfangRepository.updateYangfang(yangfangRaw);
        }
        if (action.equals(Macro.ACTION_TEMP_SAVE) || action.equals(Macro.ACTION_TEMP_SAVE_RESTORE)){
            yangfangRaw.createAt = dateNow;
            mYangfangRepository.updateYangfang(yangfangRaw);
        }
        return true;
    }
}
