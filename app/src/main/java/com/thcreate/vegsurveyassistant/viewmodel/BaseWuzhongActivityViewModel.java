package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.BaseWuzhong;
import com.thcreate.vegsurveyassistant.repository.WuzhongDataRepository;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.lang.reflect.ParameterizedType;
import java.util.Date;

abstract public class BaseWuzhongActivityViewModel<T extends BaseWuzhong> extends AndroidViewModel {

    protected String mYangfangCode;
    protected String mAction;
    protected String mWuzhongCode;

    public LiveData<T> wuzhong;

    protected Class<T> clazz;

    protected WuzhongDataRepository repository;

    public BaseWuzhongActivityViewModel(@NonNull Application application) {
        super(application);
        repository = ((BasicApp)application).getWuzhongDataRepository();
        clazz = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void initWuzhong(String yangfangCode, String action, String wuzhongCode, T restoredData){
        mYangfangCode = yangfangCode;
        mAction = action;
        mWuzhongCode = wuzhongCode;

        switch (mAction){
            case Macro.ACTION_ADD:
                MutableLiveData<T> tmp1 = new MutableLiveData<>();
                try {
                    T data = clazz.newInstance();
                    //TODO userid1
                    data.userId = 1;
                    data.yangfangCode = mYangfangCode;
                    data.wuzhongCode = mWuzhongCode;
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
//                wuzhong = repository.getCaobenWuzhongByWuzhongCode(mWuzhongCode);
                getWuzhongDataFromDatabase();
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

    abstract public void getWuzhongDataFromDatabase();

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
        if (mAction.equals(Macro.ACTION_ADD) || mAction.equals(Macro.ACTION_ADD_RESTORE)){
            wuzhongRaw.createAt = dateNow;
            repository.insertWuzhong(wuzhongRaw);
        }
        if (mAction.equals(Macro.ACTION_EDIT) || mAction.equals(Macro.ACTION_EDIT_RESTORE)){
            repository.updateWuzhong(wuzhongRaw);
        }
        return true;
    }

}
