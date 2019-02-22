package com.thcreate.vegsurveyassistant.upload;

import com.thcreate.vegsurveyassistant.BasicApp;
import com.thcreate.vegsurveyassistant.db.entity.SpeciesEntity;
import com.thcreate.vegsurveyassistant.db.entity.model.ArborSpecies;
import com.thcreate.vegsurveyassistant.db.entity.model.BaseSpecies;
import com.thcreate.vegsurveyassistant.db.entity.model.HerbSpecies;
import com.thcreate.vegsurveyassistant.db.entity.model.ShrubSpecies;
import com.thcreate.vegsurveyassistant.http.api.SpeciesApi;
import com.thcreate.vegsurveyassistant.http.service.HttpServiceGenerator;
import com.thcreate.vegsurveyassistant.util.Macro;
import com.thcreate.vegsurveyassistant.util.HTTP;

import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpeciesUploadService implements IUploadService {

//    private BasicApp mApplication;
//    private Retrofit mRetrofit;
    private SpeciesApi mRequest;
//    private SpeciesRepository mSpeciesRepository;

    public SpeciesUploadService() {
//        mApplication = BasicApp.getAppliction();
//        mSpeciesRepository = mApplication.getSpeicesRepository();
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
//            mRequest = mRetrofit.create(SpeciesApi.class);
            mRequest = HttpServiceGenerator.getInstance().createService(SpeciesApi.class);

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

    private void deleteDataListLocal(){
        BasicApp.getAppliction().getSpeicesRepository().deleteSpeciesEntitiesNeedDeleteLocal();
    }
    private List<SpeciesEntity> getDataListNeedDeleteRemote(){
        return BasicApp.getAppliction().getSpeicesRepository().getSpeciesEntityListNeedDeleteRemote();
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
        BasicApp.getAppliction().getSpeicesRepository().deleteSpeciesEntityById(data.id);
    }
    private void onDeleteDataRemoteFail(SpeciesEntity data){

    }

    private List<SpeciesEntity> getDataListNeedAddRemote(){
        return BasicApp.getAppliction().getSpeicesRepository().getSpeciesEntityListNeedAddRemote();
    }
    private void addDataListRemote(List<SpeciesEntity> dataList){
        for (SpeciesEntity data :
                dataList) {
            addDataRemote(data);
        }
    }
    private void addDataRemote(SpeciesEntity data){
        addDataRemoteGeneric(generateDataForAddRemote(data));
    }
    private <T extends BaseSpecies> void addDataRemoteGeneric(T data){
        try {
            Call<ResponseBody> call = mRequest.addSpecies(data);
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()){
                onAddDataRemoteSuccess(data.speciesId, new Date().getTime());
            }
            else {
                onAddDataRemoteFail(data.speciesId);
            }
        }
        catch (Exception e){
            onAddDataRemoteFail(data.speciesId);
            e.printStackTrace();
        }
    }
    public static <T extends BaseSpecies> T generateDataForAddRemote(SpeciesEntity entity){
        switch (entity.type){
            case Macro.HERB:
                return (T)HerbSpecies.getInstance(entity);
            case Macro.SHRUB:
                return (T)ShrubSpecies.getInstance(entity);
            case Macro.ARBOR:
                return (T)ArborSpecies.getInstance(entity);
            default:
                return null;
        }
    }
    public static void onAddDataRemoteSuccess(String speciesId, long uploadAt){
        BasicApp.getAppliction().getSpeicesRepository().updateSpeciesEntityUploadAtBySpeciesId(speciesId, uploadAt);
    }
    public static void onAddDataRemoteFail(String speciesId){

    }

    private List<SpeciesEntity> getDataListNeedUpdateRemote(){
        return BasicApp.getAppliction().getSpeicesRepository().getSpeciesEntityListNeedUpdateRemote();
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
        long uploadAt = new Date().getTime();
        BasicApp.getAppliction().getSpeicesRepository().updateSpeciesEntityUploadAtBySpeciesId(data.speciesId, uploadAt);
    }
    private void onUpdateDataRemoteFail(SpeciesEntity data){

    }

}
