package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.thcreate.vegsurveyassistant.db.entity.CaobenWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.CaobenYangfang;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.YangfangCode;

import java.util.List;

public class CaobenyangfangActivityViewModel extends BaseYangfangActivityViewModel<CaobenYangfang> {

    public CaobenyangfangActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<YangfangCode>> getGuanmuyangfangCodeList(){
        return mYangfangRepository.getAllGuanmuYangfangCodeByYangdiCode(yangdiCode);
    }

    public LiveData<List<YangfangCode>> getQiaomuyangfangCodeList(){
        return mYangfangRepository.getAllQiaomuYangfangCodeByYangdiCode(yangdiCode);
    }

    @Override
    public LiveData<CaobenYangfang> getYangfangData(){
        return mYangfangRepository.getCaobenYangfangByYangfangCode(yangfangCode);
    }

    public LiveData<List<CaobenWuzhong>> getWuzhongList(){
        return mWuzhongRepository.getAllCaobenWuzhongByYangfangCode(yangfangCode);
    }

    public void deleteWuzhongById(int id){
        mWuzhongRepository.deleteCaobenwzById(id);
    }

}
