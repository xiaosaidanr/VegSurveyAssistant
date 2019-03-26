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

import java.util.Date;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

public class SpeciesUploadService implements IUploadService {

    private SpeciesApi mRequest;

    public SpeciesUploadService() {
    }

    @Override
    public void start() {
        if (mRequest == null){
            mRequest = HttpServiceGenerator.getInstance().createServiceAuth(SpeciesApi.class);
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
            Call<ResponseBody> call = mRequest.deleteSpecies(getLandIdByPlotId(data.plotId), data.plotId, data.speciesId);
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
            Call<ResponseBody> call = null;
            if(data instanceof ArborSpecies){
                call = mRequest.addArborSpecies(getLandIdByPlotId(data.plotId), data.plotId, (ArborSpecies)data);
            }
            if(data instanceof HerbSpecies){
                call = mRequest.addHerbSpecies(getLandIdByPlotId(data.plotId), data.plotId, (HerbSpecies)data);
            }
            if(data instanceof ShrubSpecies){
                call = mRequest.addShrubSpecies(getLandIdByPlotId(data.plotId), data.plotId, (ShrubSpecies)data);
            }
//            Call<ResponseBody> call = mRequest.addSpecies(getLandIdByPlotId(data.plotId), data.plotId, data);
            Response<ResponseBody> response = call.execute();
            if (response.isSuccessful()){
                onAddDataRemoteSuccess(data);
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
        entity.uploadAt = new Date();
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
    public static <T extends BaseSpecies> void onAddDataRemoteSuccess(T data){
        BasicApp.getAppliction().getSpeicesRepository().updateSpeciesEntityUploadAtBySpeciesId(data.speciesId, data.uploadAt.getTime());
    }
    public static void onAddDataRemoteFail(String speciesId){

    }

    private String getLandIdByPlotId(String plotId){
        return BasicApp.getAppliction().getSampleplotRepository().getLandIdByPlotId(plotId);
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
            data.uploadAt = new Date();
            Call<ResponseBody> call = null;
            switch (data.type){
                case Macro.HERB:
                    call = mRequest.updateHerbSpecies(getLandIdByPlotId(data.plotId), data.plotId, data.speciesId, HerbSpecies.getInstance(data));
                    break;
                case Macro.SHRUB:
                    call = mRequest.updateShrubSpecies(getLandIdByPlotId(data.plotId), data.plotId, data.speciesId, ShrubSpecies.getInstance(data));
                    break;
                case Macro.ARBOR:
                    call = mRequest.updateArborSpecies(getLandIdByPlotId(data.plotId), data.plotId, data.speciesId, ArborSpecies.getInstance(data));
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
        BasicApp.getAppliction().getSpeicesRepository().updateSpeciesEntityUploadAtBySpeciesId(data.speciesId, data.uploadAt.getTime());
    }
    private void onUpdateDataRemoteFail(SpeciesEntity data){

    }

}
