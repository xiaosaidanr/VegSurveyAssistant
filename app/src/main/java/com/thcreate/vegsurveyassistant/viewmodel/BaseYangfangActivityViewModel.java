package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.BaseYangfang;
import com.thcreate.vegsurveyassistant.repository.WuzhongDataRepository;
import com.thcreate.vegsurveyassistant.repository.YangfangDataRepository;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.lang.reflect.ParameterizedType;
import java.util.Date;

abstract public class BaseYangfangActivityViewModel<T extends BaseYangfang> extends AndroidViewModel {

    protected String mYangdiCode;
    protected int mAction;
    protected String mYangfangCode;

    public LiveData<T> yangfang;

    protected Class<T> clazz;

    protected YangfangDataRepository mYangfangRepository;

    protected WuzhongDataRepository mWuzhongRepository;

    public BaseYangfangActivityViewModel(@NonNull Application application) {
        super(application);
        mYangfangRepository = ((BasicApp)application).getYangfangDataRepository();
        mWuzhongRepository = ((BasicApp)application).getWuzhongDataRepository();
        clazz = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public void initYangfang(String yangidCode, int action, String yangfangCode, T restoredData){
        mYangdiCode = yangidCode;
        mAction = action;
        mYangfangCode = yangfangCode;


        switch (mAction){
            case Macro.ACTION_ADD:
                MutableLiveData<T> tmp1 = new MutableLiveData<>();
                try {
                    T data = clazz.newInstance();
                    //TODO userid1
                    data.userId = 1;
                    data.yangdiCode = mYangdiCode;
                    data.yangfangCode = mYangfangCode;
                    tmp1.setValue(data);
                    yangfang = tmp1;
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case Macro.ACTION_ADD_RESTORE:
                MutableLiveData<T> tmp2 = new MutableLiveData<>();
                tmp2.setValue(restoredData);
                yangfang = tmp2;
                break;
            case Macro.ACTION_EDIT:
//                yangfang = mYangfangRepository.getCaobenYangfangByYangfangCode(mYangfangCode);
                getYangfangDataFromDatabase();
                break;
            case Macro.ACTION_EDIT_RESTORE:
                MutableLiveData<T> tmp3 = new MutableLiveData<>();
                tmp3.setValue(restoredData);
                yangfang = tmp3;
                break;
            default:
                break;
        }
    }

    abstract public void getYangfangDataFromDatabase();

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
        if (mAction == Macro.ACTION_ADD || mAction == Macro.ACTION_ADD_RESTORE){
            yangfangRaw.createAt = dateNow;
            mYangfangRepository.insertYangfang(yangfangRaw);
        }
        if (mAction == Macro.ACTION_EDIT || mAction == Macro.ACTION_EDIT_RESTORE){
            mYangfangRepository.updateYangfang(yangfangRaw);
        }
        return true;
    }
}
