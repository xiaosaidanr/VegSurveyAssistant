package com.thcreate.vegsurveyassistant.upload;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.ArborSpecies;
import com.thcreate.vegsurveyassistant.db.entity.model.HerbSpecies;
import com.thcreate.vegsurveyassistant.db.entity.model.ShrubSpecies;
import com.thcreate.vegsurveyassistant.http.api.SpeciesApi;
import com.thcreate.vegsurveyassistant.repository.SpeciesRepository;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.util.URL;

import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpeciesUploadService implements IUploadService {

    private BasicApp mApplication;
    private Retrofit mRetrofit;
    private SpeciesApi mRequest;
    private SpeciesRepository mSpeciesRepository;

    public SpeciesUploadService() {
        mApplication = BasicApp.getAppliction();
        mSpeciesRepository = mApplication.getSpeicesRepository();
    }

    @Override
    public void start() {
        if (mRequest == null){
            if (mRetrofit == null){
                mRetrofit = new Retrofit.Builder()
                        .baseUrl(URL.SERVICE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
            mRequest = mRetrofit.create(SpeciesApi.class);
        }
    }

    @Override
    public void cancel() {

    }

    private void executeSync(){
        deleteDataListRemote(getDataListNeedDeleteRemote());
        deleteDataListLocal();
    }

    private void deleteDataListLocal(){
        mSpeciesRepository.deleteSpeciesEntitiesNeedDeleteLocal();
    }
    private List<SpeciesEntity> getDataListNeedDeleteRemote(){
        return mSpeciesRepository.getSpeciesEntityListNeedDeleteRemote();
    }
    private void deleteDataListRemote(List<SpeciesEntity> dataList){
        for (SpeciesEntity data :
                dataList) {
            deleteDataRemote(data);
        }
    }
    private void deleteDataRemote(SpeciesEntity data){
        try {
            Call<ResponseBody> call = mRequest.deleteSpecies(data.speciesId);
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
    private void onDeleteDataRemoteSuccess(SpeciesEntity data){
        mSpeciesRepository.deleteSpeciesEntityById(data.id);
    }
    private void onDeleteDataRemoteFail(SpeciesEntity data){

    }

    private List<SpeciesEntity> getDataListNeedAddRemote(){
        return mSpeciesRepository.getSpeciesEntityListNeedAddRemote();
    }
    private void addDataListRemote(List<SpeciesEntity> dataList){
        for (SpeciesEntity data :
                dataList) {
            addDataRemote(data);
        }
    }
    private void addDataRemote(SpeciesEntity data){
        try {
            Call<ResponseBody> call = null;
            switch (data.type){
                case Macro.HERB:
                    call = mRequest.addSpecies(HerbSpecies.getInstance(data));
                    break;
                case Macro.SHRUB:
                    call = mRequest.addSpecies(ShrubSpecies.getInstance(data));
                    break;
                case Macro.ARBOR:
                    call = mRequest.addSpecies(ArborSpecies.getInstance(data));
                    break;
                    default:
                        break;
            }
            if (call != null){
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful()){
                    onAddDataRemoteSuccess(data);
                }
                else {
                    onAddDataRemoteFail(data);
                }
            }
        }
        catch (Exception e){
            onAddDataRemoteFail(data);
            e.printStackTrace();
        }
    }
    private void onAddDataRemoteSuccess(SpeciesEntity data){
        data.uploadAt = new Date();
        mSpeciesRepository.updateSpeciesEntityManual(data);
    }
    private void onAddDataRemoteFail(SpeciesEntity data){

    }

    private List<SpeciesEntity> getDataListNeedUpdateRemote(){
        return mSpeciesRepository.getSpeciesEntityListNeedUpdateRemote();
    }
    private void updateDataListRemote(List<SpeciesEntity> dataList){
        for (SpeciesEntity data :
                dataList) {
            updateDataRemote(data);
        }
    }
    private void updateDataRemote(SpeciesEntity data){
        try {
            Call<ResponseBody> call = null;
            switch (data.type){
                case Macro.HERB:
                    call = mRequest.updateSpecies(data.speciesId, HerbSpecies.getInstance(data));
                    break;
                case Macro.SHRUB:
                    call = mRequest.updateSpecies(data.speciesId, ShrubSpecies.getInstance(data));
                    break;
                case Macro.ARBOR:
                    call = mRequest.updateSpecies(data.speciesId, ArborSpecies.getInstance(data));
                    break;
                default:
                    break;
            }
            if (call != null){
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful()){
                    onUpdateDataRemoteSuccess(data);
                }
                else {
                    onUpdateDataRemoteFail(data);
                }
            }
        }
        catch (Exception e){
            onUpdateDataRemoteFail(data);
            e.printStackTrace();
        }
    }
    private void onUpdateDataRemoteSuccess(SpeciesEntity data){
        data.uploadAt = new Date();
        mSpeciesRepository.updateSpeciesEntityManual(data);
    }
    private void onUpdateDataRemoteFail(SpeciesEntity data){

    }

}
