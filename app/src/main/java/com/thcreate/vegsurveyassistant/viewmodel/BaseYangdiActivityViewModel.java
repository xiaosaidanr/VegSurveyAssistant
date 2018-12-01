package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.Yangdi;
import com.thcreate.vegsurveyassistant.repository.YangdiDataRepository;
import com.thcreate.vegsurveyassistant.repository.YangfangDataRepository;
import com.thcreate.vegsurveyassistant.util.IdGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;
import java.util.List;

public class BaseYangdiActivityViewModel extends AndroidViewModel {

    //TODO userid1
    private int userId = 1;

    protected String mAction;
    protected String mYangdiCode;
    protected String mType;

    public LiveData<Yangdi> yangdi;

    protected final YangdiDataRepository mYangdiRepository;
    protected final YangfangDataRepository mYangfangRepository;

    public BaseYangdiActivityViewModel(@NonNull Application application) {
        super(application);

        mYangdiRepository = ((BasicApp)application).getYangdiDataRepository();
        mYangfangRepository = ((BasicApp)application).getYangfangDataRepository();
    }

    public void initYangdi(String action, String yangdiCode, String type, @Nullable Yangdi restoredData){
        mAction = action;
        mYangdiCode = yangdiCode;
        mType = type;

        switch (mAction){
            case Macro.ACTION_ADD:
                MutableLiveData<Yangdi> tmp1 = new MutableLiveData<>();
                tmp1.setValue(new Yangdi(userId, mYangdiCode, mType));
                yangdi = tmp1;
                break;
            case Macro.ACTION_ADD_RESTORE:
                MutableLiveData<Yangdi> tmp2 = new MutableLiveData<>();
                tmp2.setValue(restoredData);
                yangdi = tmp2;
                break;
            case Macro.ACTION_EDIT:
                yangdi = mYangdiRepository.getYangdiByYangdiCode(yangdiCode);
                break;
            case Macro.ACTION_EDIT_RESTORE:
                MutableLiveData<Yangdi> tmp3 = new MutableLiveData<>();
                tmp3.setValue(restoredData);
                yangdi = tmp3;
                break;
            default:
                break;
        }
    }

    public LiveData<List<CaobenYangfang>> getCaobenyangfangList(){
        return mYangfangRepository.getAllCaobenYangfangByYangdiCode(mYangdiCode);
    }
    public LiveData<List<GuanmuYangfang>> getGuanmuyangfangList(){
        return mYangfangRepository.getAllGuanmuYangfangByYangdiCode(mYangdiCode);
    }
    public LiveData<List<QiaomuYangfang>> getQiaomuyangfangList(){
        return mYangfangRepository.getAllQiaomuYangfangByYangdiCode(mYangdiCode);
    }

    public <U> String generateYangfangCode(Class<U> modelClass){
        return IdGenerator.getId(userId, modelClass);
    }

    public boolean save(){
        if (yangdi == null){
            return false;
        }
        Yangdi yangdiRaw = yangdi.getValue();
        if (yangdiRaw == null){
            return false;
        }
        Date dateNow = new Date();
        yangdiRaw.modifyAt = dateNow;
        if (mAction.equals(Macro.ACTION_ADD) || mAction.equals(Macro.ACTION_ADD_RESTORE)){
            yangdiRaw.createAt = dateNow;
            mYangdiRepository.insertYangdi(yangdiRaw);
        }
        if (mAction.equals(Macro.ACTION_EDIT) || mAction.equals(Macro.ACTION_EDIT_RESTORE)){
            mYangdiRepository.updateYangdi(yangdiRaw);
        }
        return true;
    }
}
