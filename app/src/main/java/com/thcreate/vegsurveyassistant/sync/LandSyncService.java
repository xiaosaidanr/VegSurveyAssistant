package com.thcreate.vegsurveyassistant.sync;
import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.SamplelandEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.BaseSampleplot;
import com.thcreate.vegsurveyassistant.db.entity.model.Sampleland;
import com.thcreate.vegsurveyassistant.http.api.LandApi;
import com.thcreate.vegsurveyassistant.http.service.HttpServiceGenerator;

import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class LandSyncService implements ISyncService {

    private static final String TAG = "LandSyncService";

    private LandApi mRequest;
    private boolean mIsSuccess;

    public LandSyncService() {
        mIsSuccess = true;
    }

    @Override
    public void start() {
        if (mRequest == null){
            mRequest = HttpServiceGenerator.getInstance().createServiceAuth(LandApi.class);
        }
        executeSync();
    }

    @Override
    public void cancel() {

    }

    public boolean isSuccess(){
        return mIsSuccess;
    }

    private void executeSync(){
        try {
            Call<List<Sampleland>> call = mRequest.getLands();
            Response<List<Sampleland>> response = call.execute();
            if (response.isSuccessful()){
                List<Sampleland> samplelandList = response.body();
                if (samplelandList != null && samplelandList.size() > 0){
                    for (Sampleland data :
                            samplelandList) {
                        data.userId = Integer.valueOf(data.landId.split("-")[0]);
                        data.createAt = data.uploadAt;
                        data.updateAt = data.uploadAt;
                        SaveLand(data);
                    }
                }
            }
            else {
                onLandSyncFail();
            }
        }
        catch (Exception e){
            onLandSyncFail();
            e.printStackTrace();
        }
    }

    private void SaveLand(Sampleland data){
        SamplelandEntity remoteData = data.getEntity();
        SamplelandEntity localData = BasicApp.getAppliction().getSamplelandRepository().getSampleLandEntityByLandIdSync(remoteData.landId);
        if (localData == null){
            BasicApp.getAppliction().getSamplelandRepository().insertSamplelandEntityManualSync(remoteData);
        }
        else {
            if (localData.updateAt.before(remoteData.updateAt)){
                remoteData.id = localData.id;
                BasicApp.getAppliction().getSamplelandRepository().updateSamplelandEntityManualSync(remoteData);
            }
        }
        if (data.plotList != null && data.plotList.size() > 0){
            Collections.sort(data.plotList, (p1, p2)->Integer.compare(p1.ownerList.get("plot").size(), p2.ownerList.get("plot").size()));
            for (BaseSampleplot item :
                    data.plotList) {
                item.landId = data.landId;
                if (!PlotSyncService.savePlot(item)){
                    mIsSuccess = false;
                }
            }
        }
    }

    private void onLandSyncFail(){
        mIsSuccess = false;
    }

}
