package com.thcreate.vegsurveyassistant.upload;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.SamplepointEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.Samplepoint;
import com.thcreate.vegsurveyassistant.http.api.PointApi;
import com.thcreate.vegsurveyassistant.http.service.HttpServiceGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class PointUploadService implements IUploadService {

    private PointApi mRequest;

    public PointUploadService() {
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

    private void executeSync(){
        deleteDataListRemote(getDataListNeedDeleteRemote());
        deleteDataListLocal();
        addDataListRemote(getDataListNeedAddRemote());
        updateDataListRemote(getDataListNeedUpdateRemote());
    }

    private List<Samplepoint> getDataListNeedDeleteRemote(){
        ArrayList<Samplepoint> result = new ArrayList<>();
        List<SamplepointEntity> samplepointEntityList = BasicApp.getAppliction().getSamplepointRepository().getSamplepointEntityListNeedDeleteRemote();
        if (samplepointEntityList != null && samplepointEntityList.size() > 0){
            for (SamplepointEntity item:
                    samplepointEntityList) {
                result.add(Samplepoint.getInstance(item));
            }
        }
        return result;
    }
    private void deleteDataRemote(Samplepoint data){
        try {
            Call<ResponseBody> call = mRequest.deletePoint(data.pointId);
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()){
                onDeleteDataRemoteSuccess(data);
            }
            else {
                onDeleteDataRemoteFail(data);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void deleteDataListRemote(List<Samplepoint> dataList){
        for (Samplepoint item:
             dataList) {
            deleteDataRemote(item);
        }
    }
    private void deleteDataListLocal(){
        BasicApp.getAppliction().getSamplepointRepository().deleteSamplepointEntitiesNeedDeleteLocal();
    }
    private void onDeleteDataRemoteSuccess(Samplepoint data){
        BasicApp.getAppliction().getSamplepointRepository().deleteSamplepointById(data.id);
    }
    private void onDeleteDataRemoteFail(Samplepoint data){

    }

    private List<Samplepoint> getDataListNeedAddRemote(){
        ArrayList<Samplepoint> result = new ArrayList<>();
        List<SamplepointEntity> samplepointEntityList = BasicApp.getAppliction().getSamplepointRepository().getSamplepointEntityListNeedAddRemote();
        if (samplepointEntityList != null && samplepointEntityList.size() > 0){
            for (SamplepointEntity item:
                    samplepointEntityList) {
                result.add(Samplepoint.getInstance(item));
            }
        }
        return result;
    }
    private void addDataListRemote(List<Samplepoint> dataList){
        for (Samplepoint item:
                dataList) {
            addDataRemote(item);
        }
    }
    private void addDataRemote(Samplepoint data){
        try {
            data.uploadAt = new Date();
            Call<ResponseBody> call = mRequest.addPoint(data);
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()){
                onAddDataRemoteSuccess(data);
            }
            else {
                onAddDataRemoteFail(data);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void onAddDataRemoteSuccess(Samplepoint data){
        SamplepointEntity entity = data.getEntity();
        BasicApp.getAppliction().getSamplepointRepository().updateSamplepointManual(entity);
    }
    private void onAddDataRemoteFail(Samplepoint data){

    }

    private List<Samplepoint> getDataListNeedUpdateRemote(){
        ArrayList<Samplepoint> result = new ArrayList<>();
        List<SamplepointEntity> samplepointEntityList = BasicApp.getAppliction().getSamplepointRepository().getSamplepointEntityListNeedUpdateRemote();
        if (samplepointEntityList != null && samplepointEntityList.size() > 0){
            for (SamplepointEntity item:
                    samplepointEntityList) {
                result.add(Samplepoint.getInstance(item));
            }
        }
        return result;
    }
    private void updateDataRemote(Samplepoint data){
        try {
            data.uploadAt = new Date();
            Call<ResponseBody> call = mRequest.updatePoint(data.pointId, data);
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()){
                onUpdateDataRemoteSuccess(data);
            }
            else {
                onUpdateDataRemoteFail(data);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    private void updateDataListRemote(List<Samplepoint> dataList){
        for (Samplepoint item:
             dataList) {
            updateDataRemote(item);
        }
    }
    private void onUpdateDataRemoteSuccess(Samplepoint data){
        SamplepointEntity entity = data.getEntity();
        BasicApp.getAppliction().getSamplepointRepository().updateSamplepointManual(entity);
    }
    private void onUpdateDataRemoteFail(Samplepoint data){
    }

}
