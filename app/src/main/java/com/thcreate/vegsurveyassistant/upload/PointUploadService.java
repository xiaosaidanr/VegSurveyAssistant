package com.thcreate.vegsurveyassistant.upload;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.SamplepointEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.Samplepoint;
import com.thcreate.vegsurveyassistant.http.api.PointApi;
import com.thcreate.vegsurveyassistant.http.service.HttpServiceGenerator;
import com.thcreate.vegsurveyassistant.repository.SamplepointRepository;
import com.thcreate.vegsurveyassistant.util.HTTP;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PointUploadService implements IUploadService {

    private BasicApp mApplication;
//    private Retrofit mRetrofit;
    private PointApi mRequest;
    private SamplepointRepository mPointRepository;

    public PointUploadService() {
        mApplication = BasicApp.getAppliction();
        mPointRepository = mApplication.getSamplepointRepository();
    }

    @Override
    public void start() {
        if (mRequest == null){
//            if (mRetrofit == null){
//                mRetrofit = new Retrofit.Builder()
//                        .baseUrl(HTTP.SERVICE_URL)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .build();
//            }
//            mRequest = mRetrofit.create(PointApi.class);
            mRequest = HttpServiceGenerator.createService(PointApi.class);

        }
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
        List<SamplepointEntity> samplepointEntityList = mPointRepository.getSamplepointEntityListNeedDeleteRemote();
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
        mPointRepository.deleteSamplepointEntitiesNeedDeleteLocal();
    }
    private void onDeleteDataRemoteSuccess(Samplepoint data){
        mPointRepository.deleteSamplepointById(data.id);
    }
    private void onDeleteDataRemoteFail(Samplepoint data){

    }

    private List<Samplepoint> getDataListNeedAddRemote(){
        ArrayList<Samplepoint> result = new ArrayList<>();
        List<SamplepointEntity> samplepointEntityList = mPointRepository.getSamplepointEntityListNeedAddRemote();
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
        entity.uploadAt = new Date();
        mPointRepository.updateSamplepointManual(entity);
    }
    private void onAddDataRemoteFail(Samplepoint data){

    }

    private List<Samplepoint> getDataListNeedUpdateRemote(){
        ArrayList<Samplepoint> result = new ArrayList<>();
        List<SamplepointEntity> samplepointEntityList = mPointRepository.getSamplepointEntityListNeedUpdateRemote();
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
        entity.uploadAt = new Date();
        mPointRepository.updateSamplepointManual(entity);
    }
    private void onUpdateDataRemoteFail(Samplepoint data){
    }

}
