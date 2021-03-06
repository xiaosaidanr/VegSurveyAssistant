package com.thcreate.vegsurveyassistant.upload;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.SamplelandEntity;
import com.thcreate.vegsurveyassistant.db.entity.SampleplotEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.BaseSampleplot;
import com.thcreate.vegsurveyassistant.db.entity.model.Sampleland;
import com.thcreate.vegsurveyassistant.http.api.LandApi;
import com.thcreate.vegsurveyassistant.http.service.HttpServiceGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class LandUploadService implements IUploadService {

    private LandApi mRequest;
    private boolean mIsSuccess;

    public LandUploadService() {
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
        deleteDataListRemote(getDataListNeedDeleteRemote());
        deleteDataListLocal();
        addDataListRemote(getDataListNeedAddRemote());
        updateDataListRemote(getDataListNeedUpdateRemote());
    }

    private List<SamplelandEntity> getDataListNeedDeleteRemote(){
        return BasicApp.getAppliction().getSamplelandRepository().getSamplelandEntityListNeedDeleteRemote();
    }
    private void deleteDataListLocal(){
        BasicApp.getAppliction().getSamplelandRepository().deleteSamplelandEntitiesNeedDeleteLocal();
    }
    private void deleteDataListRemote(List<SamplelandEntity> dataList){
        for (SamplelandEntity item :
                dataList) {
            deleteDataRemote(item);
        }
    }
    private void deleteDataRemote(SamplelandEntity data){
        try {
            Call<ResponseBody> call = mRequest.deleteLand(data.landId);
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()){
                onDeleteDataRemoteSuccess(data);
            }
            else {
                onDeleteDataRemoteFail(data);
            }
        }
        catch (Exception e){
            onDeleteDataRemoteFail(data);
            e.printStackTrace();
        }
    }
    private void onDeleteDataRemoteSuccess(SamplelandEntity data){
        BasicApp.getAppliction().getSamplelandRepository().deleteSamplelandEntity(data);
    }
    private void onDeleteDataRemoteFail(SamplelandEntity data){
        mIsSuccess = false;
    }

    private List<SamplelandEntity> getDataListNeedAddRemote(){
        return BasicApp.getAppliction().getSamplelandRepository().getSamplelandEntityListNeedAddRemote();
    }
    private void addDataListRemote(List<SamplelandEntity> dataList){
        for (SamplelandEntity item :
                dataList) {
            addDataRemote(item);
        }
    }
    private void addDataRemote(SamplelandEntity data){
        data.uploadAt = new Date();
        List<SampleplotEntity> sampleplotEntityList = getNotDeletedSampleplotEntityListByLandId(data.landId);
        List<BaseSampleplot> plotList = new ArrayList<>();
        for (SampleplotEntity item :
                sampleplotEntityList) {
            plotList.add(PlotUploadService.generateDataForAddRemote(item));
        }
        Sampleland landData = Sampleland.getInstance(data);
        landData.plotList = plotList;
        try {
            Call<ResponseBody> call = mRequest.addLand(landData);
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()){
                onAddDataRemoteSuccess(landData);
            }
            else {
                onAddDataRemoteFail(landData);
            }
        }
        catch (Exception e){
            onAddDataRemoteFail(landData);
            e.printStackTrace();
        }
    }
    private void onAddDataRemoteSuccess(Sampleland land){
        BasicApp.getAppliction().getSamplelandRepository().updateSamplelandEntityUploadAtByLandId(land.landId, land.uploadAt.getTime());
        for (BaseSampleplot item :
                land.plotList) {
            PlotUploadService.onAddDataRemoteSuccess(item);
        }
    }
    private void onAddDataRemoteFail(Sampleland land){
        mIsSuccess = false;
    }
    private static List<SampleplotEntity> getNotDeletedSampleplotEntityListByLandId(String landId){
        return BasicApp.getAppliction().getSampleplotRepository().getNotDeletedSampleplotEntityListByLandId(landId);
    }

    private List<SamplelandEntity> getDataListNeedUpdateRemote(){
        return BasicApp.getAppliction().getSamplelandRepository().getSamplelandEntityListNeedUpdateRemote();
    }
    private void updateDataListRemote(List<SamplelandEntity> dataList){
        for (SamplelandEntity item :
                dataList) {
            updateDataRemote(item);
        }
    }
    private void updateDataRemote(SamplelandEntity data){
        try {
            data.uploadAt = new Date();
            Call<ResponseBody> call = mRequest.updateLand(data.landId, Sampleland.getInstance(data));
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()){
                onUpdateDataRemoteSuccess(data);
            }
            else{
                onUpdateDataRemoteFail(data);
            }
        }
        catch (Exception e){
            onUpdateDataRemoteFail(data);
            e.printStackTrace();
        }
    }
    private void onUpdateDataRemoteSuccess(SamplelandEntity data){
        BasicApp.getAppliction().getSamplelandRepository().updateSamplelandEntityUploadAtByLandId(data.landId, data.uploadAt.getTime());
    }
    private void onUpdateDataRemoteFail(SamplelandEntity data){
        mIsSuccess = false;
    }

}
