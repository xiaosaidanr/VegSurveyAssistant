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
import com.thcreate.vegsurveyassistant.db.entity.BaseWuzhong;
import com.thcreate.vegsurveyassistant.repository.WuzhongDataRepository;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.lang.reflect.ParameterizedType;
import java.util.Date;

abstract public class BaseWuzhongActivityViewModel<T extends BaseWuzhong> extends AndroidViewModel {

    private static final String WUZHONG_DATA = "wuzhongData";

    //TODO userid1
    private int userId = 1;

    protected String yangfangCode;
    public String action;
    protected String wuzhongCode;

    public LiveData<T> wuzhong;

    private Class<T> clazz;

    protected WuzhongDataRepository repository;

    public BaseWuzhongActivityViewModel(@NonNull Application application) {
        super(application);
        repository = ((BasicApp)application).getWuzhongDataRepository();
        clazz = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void init(Bundle data){
        yangfangCode = data.getString(Macro.YANGFANG_CODE);
        action = data.getString(Macro.ACTION);
        wuzhongCode = data.getString(Macro.WUZHONG_CODE);
        @Nullable T tmp = data.getParcelable(WUZHONG_DATA);
        initWuzhong(tmp);

    }

    private void initWuzhong(T restoredData){
        switch (action){
            case Macro.ACTION_ADD:
                MutableLiveData<T> tmp1 = new MutableLiveData<>();
                try {
                    T data = clazz.newInstance();
                    data.userId = userId;
                    data.yangfangCode = yangfangCode;
                    data.wuzhongCode = wuzhongCode;
                    tmp1.setValue(data);
                    wuzhong = tmp1;
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case Macro.ACTION_ADD_RESTORE:
                MutableLiveData<T> tmp2 = new MutableLiveData<>();
                tmp2.setValue(restoredData);
                wuzhong = tmp2;
                break;
            case Macro.ACTION_EDIT:
                wuzhong = getWuzhongData();
                break;
            case Macro.ACTION_EDIT_RESTORE:
                MutableLiveData<T> tmp3 = new MutableLiveData<>();
                tmp3.setValue(restoredData);
                wuzhong = tmp3;
                break;
            default:
                break;
        }
    }

    public Bundle onSaveViewModelState(Bundle outState){
        outState.putString(Macro.YANGFANG_CODE, yangfangCode);
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_ADD_RESTORE);
        }
        if (action.equals(Macro.ACTION_EDIT) || action.equals(Macro.ACTION_EDIT_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_EDIT_RESTORE);
        }
        outState.putString(Macro.WUZHONG_CODE, wuzhongCode);
        outState.putParcelable(WUZHONG_DATA, (Parcelable) wuzhong.getValue());
        return outState;
    }

    abstract LiveData<T> getWuzhongData();

    public boolean save(){
        if (wuzhong == null){
            return false;
        }
        T wuzhongRaw = wuzhong.getValue();
        if (wuzhongRaw == null){
            return false;
        }
        Date dateNow = new Date();
        wuzhongRaw.modifyAt = dateNow;
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            wuzhongRaw.createAt = dateNow;
            repository.insertWuzhong(wuzhongRaw);
        }
        if (action.equals(Macro.ACTION_EDIT) || action.equals(Macro.ACTION_EDIT_RESTORE)){
            repository.updateWuzhong(wuzhongRaw);
        }
        return true;
    }

}
