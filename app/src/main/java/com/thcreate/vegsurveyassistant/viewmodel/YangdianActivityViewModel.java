package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.Yangdian;
import com.thcreate.vegsurveyassistant.repository.YangdianDataRepository;
import com.thcreate.vegsurveyassistant.util.Macro;

import java.util.Date;

public class YangdianActivityViewModel extends AndroidViewModel {

    private static final String YANGDIAN_DATA = "yangdianData";

    //TODO userid1
    private int userId = 1;

    public String yangdianCode;
    public String action;

//    public LiveData<User> user;

    public LiveData<Yangdian> yangdian;

    private YangdianDataRepository repository;

    public YangdianActivityViewModel(@NonNull Application application) {
        super(application);
        repository = ((BasicApp)application).getYangdianDataRepository();
    }

    public void init(Bundle data){
        action = data.getString(Macro.ACTION);
        yangdianCode = data.getString(Macro.YANGDIAN_CODE);
        @Nullable Yangdian tmp = data.getParcelable(YANGDIAN_DATA);
        initYangdian(tmp);
    }

    private void initYangdian(Yangdian restoredData){
        switch (action){
            case Macro.ACTION_ADD:
                MutableLiveData<Yangdian> tmp1 = new MutableLiveData<>();
                tmp1.setValue(new Yangdian(userId, yangdianCode));
                yangdian = tmp1;
                break;
            case Macro.ACTION_ADD_RESTORE:
                MutableLiveData<Yangdian> tmp2 = new MutableLiveData<>();
                tmp2.setValue(restoredData);
                yangdian = tmp2;
                break;
            case Macro.ACTION_EDIT:
                yangdian = repository.getYangdianByYangdianCode(yangdianCode);
                break;
            case Macro.ACTION_EDIT_RESTORE:
                MutableLiveData<Yangdian> tmp3 = new MutableLiveData<>();
                tmp3.setValue(restoredData);
                yangdian = tmp3;
                break;
            default:
                break;
        }
    }

    public Bundle onSaveViewModelState(Bundle outState){
        outState.putString(Macro.YANGDIAN_CODE, yangdianCode);
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_ADD_RESTORE);
        }
        if (action.equals(Macro.ACTION_EDIT) || action.equals(Macro.ACTION_EDIT_RESTORE)){
            outState.putString(Macro.ACTION, Macro.ACTION_EDIT_RESTORE);
        }
        outState.putParcelable(YANGDIAN_DATA, yangdian.getValue());
        return outState;
    }

    public boolean save(){
        if (yangdian == null){
            return false;
        }
        Yangdian yangdianRaw = yangdian.getValue();
        if (yangdianRaw == null){
            return false;
        }
        Date dateNow = new Date();
        yangdianRaw.modifyAt = dateNow;
        if (action.equals(Macro.ACTION_ADD) || action.equals(Macro.ACTION_ADD_RESTORE)){
            yangdianRaw.createAt = dateNow;
            repository.insertYangdian(yangdianRaw);
        }
        if (action.equals(Macro.ACTION_EDIT) || action.equals(Macro.ACTION_EDIT_RESTORE)){
            repository.updateYangdian(yangdianRaw);
        }
        return true;
    }

}
