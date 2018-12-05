package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.thcreate.vegsurveyassistant.db.entity.QiaomuWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.QiaomuYangfang;
import java.util.List;

public class QiaomuyangfangActivityViewModel extends BaseYangfangActivityViewModel<QiaomuYangfang> {

    public QiaomuyangfangActivityViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public LiveData<QiaomuYangfang> getYangfangData() {
        return mYangfangRepository.getQiaomuYangfangByYangfangCode(yangfangCode);
    }

    public LiveData<List<QiaomuWuzhong>> getWuzhongList(){
        return mWuzhongRepository.getAllQiaomuWuzhongByYangfangCode(yangfangCode);
    }

    public void deleteWuzhongById(int id){
        mWuzhongRepository.deleteQiaomuwzById(id);
    }

}
