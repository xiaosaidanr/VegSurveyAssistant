package com.thcreate.vegsurveyassistant.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuWuzhong;
import com.thcreate.vegsurveyassistant.db.entity.GuanmuYangfang;
import com.thcreate.vegsurveyassistant.db.entity.fieldAggregator.YangfangCode;
import java.util.List;

public class GuanmuyangfangActivityViewModel extends BaseYangfangActivityViewModel<GuanmuYangfang> {

    public GuanmuyangfangActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<YangfangCode>> getQiaomuyangfangCodeList(){
        return mYangfangRepository.getAllQiaomuYangfangCodeByYangdiCode(yangdiCode);
    }

    @Override
    public LiveData<GuanmuYangfang> getYangfangData() {
        return mYangfangRepository.getGuanmuYangfangByYangfangCode(yangfangCode);
    }

    public LiveData<List<GuanmuWuzhong>> getWuzhongList(){
        return mWuzhongRepository.getAllGuanmuWuzhongByYangfangCode(yangfangCode);
    }

}
