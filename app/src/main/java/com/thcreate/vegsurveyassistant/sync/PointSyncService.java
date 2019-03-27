package com.thcreate.vegsurveyassistant.sync;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.SamplepointEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.Samplepoint;
import com.thcreate.vegsurveyassistant.http.api.PointApi;
import com.thcreate.vegsurveyassistant.http.service.HttpServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class PointSyncService implements ISyncService {

    private static final String TAG = "PointSyncService";

    private PointApi mRequest;
    private boolean mIsSuccess;

    public PointSyncService() {
        mIsSuccess = true;
    }

    @Override
    public void start() {
        if (mRequest == null){
            mRequest = HttpServiceGenerator.getInstance().createServiceAuth(PointApi.class);
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
            Call<List<Samplepoint>> call = mRequest.getPoints();
            Response<List<Samplepoint>> response = call.execute();
            if (response.isSuccessful()){
                List<Samplepoint> samplepointList = response.body();
                if (samplepointList != null && samplepointList.size() > 0){
                    for (Samplepoint data :
                            samplepointList) {
                        syncPoint(data);
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void syncPoint(Samplepoint data){
        try {
            data.userId = Integer.valueOf(data.pointId.split("-")[0]);
            if (data.investigatedAt != null){
                data.investigatedAt = data.investigatedAt.split(" ")[0];
            }
            data.createAt = data.uploadAt;
            data.updateAt = data.uploadAt;
            SamplepointEntity remoteEntity = data.getEntity();
            SamplepointEntity localEntity = BasicApp.getAppliction().getSamplepointRepository().getSamplepointEntityByPointIdSync(data.pointId);
            if (localEntity == null){
                BasicApp.getAppliction().getSamplepointRepository().insertSamplepointManualSync(remoteEntity);
            }
            else {
                if (localEntity.updateAt.before(remoteEntity.updateAt)){
                    BasicApp.getAppliction().getSamplepointRepository().updateSamplepointManualSync(remoteEntity);
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}
